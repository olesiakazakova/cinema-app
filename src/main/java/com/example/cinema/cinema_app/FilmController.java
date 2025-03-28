package com.example.cinema.cinema_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/films")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private DirectorService directorService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private FilmsActorsService filmsActorsService;

    @Autowired
    private FilmsDirectorsService filmsDirectorsService;

    @Autowired
    private FilmsGenresService filmsGenresService;

    @GetMapping
    public String listFilms(Model model) {
        List<Film> films = filmService.findAll();
        model.addAttribute("films", films);
        return "film/list";
    }

    @GetMapping("/add")
    public String showAddFilmForm(Model model) {
        model.addAttribute("film", new Film());
        model.addAttribute("allGenres", genreService.findAll());
        model.addAttribute("allDirectors", directorService.getAllDirectors());
        model.addAttribute("allActors", actorService.getAllActors());
        return "film/add";
    }

    @PostMapping("/add")
    public String addFilm(@Valid @ModelAttribute Film film,
                          BindingResult bindingResult,
                          @RequestParam(required = false) List<Long> genres,
                          @RequestParam(required = false) List<Long> actors,
                          @RequestParam(required = false) List<Long> directors,
                          @RequestParam(required = false) String newGenre,
                          @RequestParam(required = false) String newDirector,
                          @RequestParam(required = false) String newActor) {

        // Проверяем на наличие ошибок валидации
        if (bindingResult.hasErrors()) {
            System.out.println("Ошибки валидации: " + bindingResult.getAllErrors());
            return "film/error"; // Возвращаем на страницу добавления фильма
        }

        // Обработка существующих актёров и режиссёров
        if (genres != null) {
            List<Genre> selectedGenres = genreService.findAllById(genres);
            film.getGenres().addAll(selectedGenres);
        }

        if (actors != null) {
            List<Actor> selectedActors = actorService.findAllById(actors);
            film.getActors().addAll(selectedActors);
        }
        if (directors != null) {
            List<Director> selectedDirectors = directorService.findAllById(directors);
            film.getDirectors().addAll(selectedDirectors);
        }

        // Обработка новых жанров, режиссёров и актеров
        if (newGenre != null && !newGenre.trim().isEmpty()) {
            Genre genre = genreService.findByName(newGenre)
                    .orElseGet(() -> {
                        Genre newGenreObj = new Genre();
                        newGenreObj.setGenre(newGenre);
                        genreService.createGenre(newGenreObj);
                        return newGenreObj;
                    });
            film.getGenres().add(genre);
        }
        if (newDirector != null && !newDirector.trim().isEmpty()) {
            Director director = directorService.findByName(newDirector)
                    .orElseGet(() -> {
                        Director newDirectorObj = new Director();
                        newDirectorObj.setName(newDirector);
                        directorService.saveDirector(newDirectorObj);
                        return newDirectorObj;
                    });
            film.getDirectors().add(director);
        }
        if (newActor != null && !newActor.trim().isEmpty()) {
            Actor actor = actorService.findByName(newActor)
                    .orElseGet(() -> {
                        Actor newActorObj = new Actor();
                        newActorObj.setName(newActor);
                        actorService.createActor(newActorObj);
                        return newActorObj;
                    });
            film.getActors().add(actor);
        }

        // Сохраняем фильм с установленными связями
        filmService.save(film);
        return "redirect:/films";
    }

    @GetMapping("/edit")
    public String showEditFilmForm(@RequestParam("filmId") Long filmId, Model model) {
        Film film = filmService.findById(filmId);
        if (film == null) {
            return "redirect:/films";
        }
        model.addAttribute("film", film);
        model.addAttribute("allGenres", genreService.findAll());
        model.addAttribute("allDirectors", directorService.getAllDirectors());
        model.addAttribute("allActors", actorService.getAllActors());
        return "film/edit";
    }

    @PostMapping("/edit")
    public String editFilm(@RequestParam Long filmId, @Valid @ModelAttribute Film film,
                           BindingResult bindingResult,
                           @RequestParam(required = false) List<Long> genres,
                           @RequestParam(required = false) List<Long> actors,
                           @RequestParam(required = false) List<Long> directors) {
        if (bindingResult.hasErrors()) {
            System.out.println("Ошибки валидации: " + bindingResult.getAllErrors());
            return "film/error";
        }

        film.setFilmId(filmId); // Сохраняем ID, чтобы обновить правильный фильм в базе данных

        // Обработка жанров
        if (genres != null) {
            List<Genre> genreList = genreService.findAllById(genres);
            film.getGenres().addAll(genreList);
        }

        // Обработка актёров
        if (actors != null) {
            List<Actor> actorList = actorService.findAllById(actors);
            film.getActors().addAll(actorList);
        }

        // Обработка режиссёров
        if (directors != null) {
            List<Director> directorList = directorService.findAllById(directors);
            film.getDirectors().addAll(directorList);
        }

        filmService.save(film);
        return "redirect:/films";
    }

    @PostMapping("/delete")
    public String deleteFilm(@RequestParam Long filmId) {
        int maxRetries = 3;
        int attempt = 0;
        Film film = null;

        while (attempt < maxRetries) {
            try {
                film = filmService.findById(filmId);
                if (film != null) {
                    // Удаляем актеров
                    for (Actor actor : film.getActors()) {
                        Long actorId = actor.getActorId();
                        filmsActorsService.deleteFilmsActors(actorId, filmId);
                    }
                    // Удаляем режиссеров
                    for (Director director : film.getDirectors()) {
                        Long directorId = director.getDirectorId();
                        filmsDirectorsService.deleteFilmsDirectors(directorId, filmId);
                    }
                    // Удаляем жанры
                    for (Genre genre : film.getGenres()) {
                        filmsGenresService.deleteFilmsGenres(genre, film);
                    }
                    // Удаляем фильм
                    filmService.delete(filmId);
                }
                return "redirect:/films"; // Успешное завершение
            } catch (ObjectOptimisticLockingFailureException e) {
                attempt++;
                // Логирование ошибки для диагностики
                System.err.println("Попытка " + attempt + " не удалась. Оживление состояния...");
                // Обновляем объект фильма
                film = filmService.findById(filmId);
                if (film == null) {
                    // Если фильм больше не существует, выходим из цикла
                    break;
                }
                // Если достигнут максимум попыток
                if (attempt >= maxRetries) {
                    return "film/error";
                }
            }
        }
        return "redirect:/films";
    }
}


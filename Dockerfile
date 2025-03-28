# Используем официальный образ Maven с Java как базовый
FROM maven:3.9.9-eclipse-temurin-17

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файл pom.xml и загружаем зависимости
COPY pom.xml .
RUN mvn dependency:go-offline

# Копируем всю папку с исходным кодом
COPY src ./src

# Собираем приложение, пропуская тесты и проверяем содержимое папки target
RUN mvn package -DskipTests && ls target  # Проверяем наличие JAR-файла

# Копируем собранный jar-файл в рабочую директорию
COPY target/*.jar app.jar

# Указываем переменную окружения для порта
ENV PORT 8080

# Запускаем приложение
CMD ["java", "-jar", "app.jar", "--server.port=${PORT}"]









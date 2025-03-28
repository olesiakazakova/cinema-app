# Используем официальный образ Maven с Java как базовый
FROM maven:3.9.0-openjdk-17-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файл pom.xml и загружаем зависимости
COPY pom.xml .
RUN mvn dependency:go-offline

# Копируем всю папку с исходным кодом
COPY src ./src

# Собираем приложение
RUN mvn package

# Копируем собранный jar-файл в рабочую директорию
COPY target/*.jar app.jar

# Указываем переменную окружения для порта
ENV PORT 8080

# Запускаем приложение
CMD ["java", "-jar", "app.jar", "--server.port=${PORT}"]



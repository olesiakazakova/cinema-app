# Используем официальный образ Maven с Java как базовый
FROM maven:3.9.9-eclipse-temurin-17 AS build

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файл pom.xml и загружаем зависимости
COPY pom.xml .
RUN mvn dependency:go-offline

# Копируем всю папку с исходным кодом
COPY src ./src

# Собираем приложение
RUN mvn package -DskipTests

# Создаем новый образ для выполнения приложения
FROM openjdk:17-jdk-slim

# Копируем собранный jar-файл в рабочую директорию
COPY --from=build /app/target/cinema-app-0.0.1-SNAPSHOT.jar app.jar

# Указываем переменную окружной среды для порта
ENV PORT 8080

# Запускаем приложение
CMD ["java", "-jar", "app.jar"]

     
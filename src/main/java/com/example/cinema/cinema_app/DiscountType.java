package com.example.cinema.cinema_app;

public enum DiscountType {
    NO_DISCOUNT("нет скидки"),
    CHILD_DISCOUNT("для ребенка"),
    STUDENT_DISCOUNT("для студента"),
    SENIOR_DISCOUNT("для пенсионера");

    private final String label;

    DiscountType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static DiscountType fromLabel(String label) {
        if (label == null) {
            return NO_DISCOUNT; // Возвращаем NO_DISCOUNT, если метка null
        }

        String trimmedLabel = label.trim(); // Убираем лишние пробелы
        for (DiscountType type : values()) {
            if (type.getLabel().equalsIgnoreCase(trimmedLabel)) {
                return type; // Возвращаем соответствующий тип скидки
            }
        }
        return NO_DISCOUNT; // Возвращаем NO_DISCOUNT, если метка не найдена
    }

    public static DiscountType fromDatabaseValue(String databaseValue) {
        if (databaseValue == null) {
            return NO_DISCOUNT; // Возможная обработка нулевых значений из базы данных
        }

        for (DiscountType type : values()) {
            if (type.getLabel().equalsIgnoreCase(databaseValue.trim())) {
                return type;
            }
        }
        return NO_DISCOUNT; // Возвращаем NO_DISCOUNT, если значение из базы данных неизвестно
    }
}






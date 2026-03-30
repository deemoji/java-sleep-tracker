package ru.yandex.practicum.sleeptracker;

public enum Chronotype {
    OWL, LARK, DOVE;

    @Override
    public String toString() {
        return switch (this) {
            case OWL -> "Сова";
            case LARK -> "Жаворонок";
            case DOVE -> "Голубь";
        };
    }
}

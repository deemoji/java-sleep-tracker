package ru.yandex.practicum.sleeptracker;

public class SleepAnalysisResult<T> {
    private final T value;
    private final String description;

    public SleepAnalysisResult(T value, String description) {
        this.value = value;
        this.description = description;
    }

    public T getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static <T> SleepAnalysisResult<T> of(T value, String description) {
        return new SleepAnalysisResult<>(value, description);
    }
}

package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;

public class TestUtil {
    public static SleepSession createSession(int hours) {
        return createSession(hours, SleepQuality.NORMAL);
    }

    public static SleepSession createSession(int hours, SleepQuality quality) {
        LocalDateTime start = LocalDateTime.of(
                2026,
                3,
                1,
                0,
                0
        );
        LocalDateTime end = start.plusHours(hours);

        return new SleepSession(start, end, quality);
    }

    public static SleepSession createSession(String start, String end) {
        return new SleepSession(LocalDateTime.parse(start), LocalDateTime.parse(end), SleepQuality.NORMAL);
    }
}

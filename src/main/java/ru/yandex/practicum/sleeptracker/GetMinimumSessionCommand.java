package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class GetMinimumSessionCommand implements SleepTrackerCommand<Long> {

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepSession> sessions) {
        final String description = "Минимальная продолжительность сессии (в минутах)";
        return sessions
                .stream()
                .map(session -> session.getDuration().toMinutes())
                .min(Long::compare)
                .map(value -> SleepAnalysisResult.of(
                        value,
                        description)
                )
                .orElseGet(() -> SleepAnalysisResult.of(
                        0L,
                        description
                ));
    }

}

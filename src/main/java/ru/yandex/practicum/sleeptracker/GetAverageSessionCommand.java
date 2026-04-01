package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.stream.Collectors;

public class GetAverageSessionCommand implements SleepTrackerCommand<Long> {

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepSession> sessions) {
        long avg = (long)sessions
                .stream()
                .map(session -> session.getDuration().toMinutes())
                .collect(Collectors.summarizingLong(Long::longValue))
                .getAverage();

        return SleepAnalysisResult.of(avg, "Средняя продолжительность сессии (в минутах)");
    }


}

package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class GetBadSessionCountCommand implements SleepTrackerCommand<Long> {

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepSession> sessions) {
        long count = sessions
                .stream()
                .filter(session -> session.getQuality() == SleepQuality.BAD)
                .count();

        return SleepAnalysisResult.of(count, "Количество сессий с плохим качеством сна");
    }

}

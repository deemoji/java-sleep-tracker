package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class GetSleeplessNightCountCommand implements SleepTrackerCommand<Long> {

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepSession> sessions) {
        String description = "Количество бессонных ночей";
        if (sessions.isEmpty()) {
            return SleepAnalysisResult.of(0L, description);
        }
        LocalDateTime first = sessions.getFirst().getStart();
        LocalDateTime last = sessions.getLast().getEnd();

        LocalDate startDate = first.toLocalTime().isBefore(LocalTime.NOON)
                ? first.toLocalDate()
                : first.toLocalDate().plusDays(1);

        long daysTotal = ChronoUnit.DAYS.between(startDate, last.toLocalDate()) + 1;

        long nightSessionCount = sessions.stream().filter(session -> {
            LocalDateTime start = session.getStart();
            LocalDateTime end = session.getEnd();

            if (start.toLocalDate().isBefore(end.toLocalDate())) {
                return true;
            }

            LocalDateTime nightStart = start.toLocalDate().atStartOfDay();
            LocalDateTime nightEnd = start.toLocalDate().atTime(6, 0);

            return start.isBefore(nightEnd) && end.isAfter(nightStart);

        }).count();


        return SleepAnalysisResult.of(
                Math.max(0, daysTotal - nightSessionCount),
                description
        );
    }
}

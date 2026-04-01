package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GetChronotypeCommand implements SleepTrackerCommand<Chronotype> {

    @Override
    public SleepAnalysisResult<Chronotype> apply(List<SleepSession> sessions) {
        final String description = "Хронотип";
        Map<Chronotype, Long> chronotypes = sessions.stream()
                .filter(session -> {
                    LocalDateTime start = session.getStart();
                    LocalDateTime end = session.getEnd();

                    if (start.toLocalDate().isBefore(end.toLocalDate())) {
                        return true;
                    }

                    LocalDateTime nightStart = start.toLocalDate().atStartOfDay();
                    LocalDateTime nightEnd = start.toLocalDate().atTime(6, 0);

                    return start.isBefore(nightEnd) && end.isAfter(nightStart);

                })
                .map(session -> {
                    LocalDateTime start = session.getStart();
                    LocalDateTime end = session.getEnd();


                    LocalDateTime owlStart = start.toLocalDate().atTime(22,59);
                    LocalDateTime owlEnd = end.toLocalDate().atTime(8,59);
                    if (start.isAfter(owlStart) && end.isAfter(owlEnd)
                    || start.getDayOfMonth() == end.getDayOfMonth() && end.isAfter(owlEnd)) {
                        return Chronotype.OWL;
                    }

                    LocalDateTime larkStart = start.toLocalDate().atTime(22,0);
                    LocalDateTime larkEnd = end.toLocalDate().atTime(7,0);

                    if (start.isBefore(larkStart) && end.isBefore(larkEnd)) {
                        return Chronotype.LARK;
                    }

                    return Chronotype.DOVE;
                }).collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()));

        long larkCount = chronotypes.getOrDefault(Chronotype.LARK, 0L);
        long owlCount = chronotypes.getOrDefault(Chronotype.OWL, 0L);
        long doveCount = chronotypes.getOrDefault(Chronotype.DOVE, 0L);

        if (larkCount == owlCount || doveCount >= larkCount && doveCount >= owlCount) {
            return SleepAnalysisResult.of(Chronotype.DOVE, description);
        }
        return larkCount > owlCount
                ? SleepAnalysisResult.of(Chronotype.LARK, description)
                : SleepAnalysisResult.of(Chronotype.OWL, description);

    }
}

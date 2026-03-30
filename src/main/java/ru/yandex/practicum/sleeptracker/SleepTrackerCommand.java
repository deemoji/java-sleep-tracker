package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public interface SleepTrackerCommand<T> extends Function<List<SleepSession>, SleepAnalysisResult<T>> {

}

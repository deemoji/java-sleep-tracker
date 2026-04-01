package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GetSleeplessNightCountCommandTest {

    private final GetSleeplessNightCountCommand sut = new GetSleeplessNightCountCommand();

    private List<SleepSession> sessions;

    @BeforeEach
    public void beforeEach() {
        sessions = new ArrayList<>();
    }

    @Test
    void shouldReturnZeroWhenSleepCoversNight() {
        sessions = List.of(
                TestUtil.createSession("2026-01-01T23:00", "2026-01-02T02:00")
        );

        SleepAnalysisResult<Long> result = sut.apply(sessions);

        assertEquals(0L, result.getValue());
    }

    @Test
    void shouldCountWhenSleepBeforeNoonButAfterEndOfNight() {
        sessions = List.of(
                TestUtil.createSession("2026-01-01T10:00", "2026-01-01T11:00")
        );

        SleepAnalysisResult<Long> result = sut.apply(sessions);

        assertEquals(1L, result.getValue());
    }

    @Test
    void shouldReturnZeroWhenOnlyCoversSleepBeforeNoon() {
        sessions = List.of(
                TestUtil.createSession("2026-01-01T05:00", "2026-01-01T06:00")
        );

        SleepAnalysisResult<Long> result = sut.apply(sessions);

        assertEquals(0L, result.getValue());
    }

    @Test
    void shouldCountGapsBetweenSessionsAsSleepless() {
        sessions = List.of(
                TestUtil.createSession("2026-01-01T23:00", "2026-01-02T02:00"),
                TestUtil.createSession("2026-01-05T23:00", "2026-01-06T02:00")
        );

        SleepAnalysisResult<Long> result = sut.apply(sessions);

        assertEquals(3L, result.getValue());
    }

    @Test
    void shouldHandleNightBoundaries() {
        var sessions = List.of(
                TestUtil.createSession("2026-01-02T00:00", "2026-01-02T06:00")
        );

        SleepAnalysisResult<Long> result = sut.apply(sessions);

        assertEquals(0L, result.getValue());
    }

    @Test
    void shouldHandleSleepCrossingMidnight() {
        var sessions = List.of(
                TestUtil.createSession("2026-01-01T22:00", "2026-01-02T07:00")
        );

        SleepAnalysisResult<Long> result = sut.apply(sessions);

        assertEquals(0L, result.getValue());
    }

    @Test
    void shouldHandleDifferentMonths() {
        var sessions = List.of(
                TestUtil.createSession("2026-01-31T22:00", "2026-02-01T07:00")
        );

        SleepAnalysisResult<Long> result = sut.apply(sessions);

        assertEquals(0L, result.getValue());
    }

    @Test
    public void shouldReturnZeroWhenNoSessions() {
        SleepAnalysisResult<Long> result = sut.apply(sessions);

        assertEquals(0L, result.getValue());
    }
}

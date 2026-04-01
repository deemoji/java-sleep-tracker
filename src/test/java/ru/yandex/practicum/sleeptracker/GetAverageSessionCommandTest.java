package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GetAverageSessionCommandTest {

    private final GetAverageSessionCommand sut = new GetAverageSessionCommand();
    private List<SleepSession> sessions;

    @BeforeEach
    public void beforeEach() {
        sessions = new ArrayList<>();
    }

    @Test
    public void shouldReturnAverageDurationWhenMultipleSessions() {
        sessions = List.of(
                TestUtil.createSession(6),
                TestUtil.createSession(7),
                TestUtil.createSession(10)
        );

        SleepAnalysisResult<Long> result = sut.apply(sessions);

        assertEquals(460L, result.getValue());
    }

    @Test
    public void shouldReturnDurationWhenSingleSession() {
        sessions = List.of(
                TestUtil.createSession(5)
        );

        SleepAnalysisResult<Long> result = sut.apply(sessions);

        assertEquals(5L * 60L, result.getValue());
    }

    @Test
    public void shouldReturnDurationWhenEqualDurations() {
        sessions = List.of(
                TestUtil.createSession(7),
                TestUtil.createSession(7),
                TestUtil.createSession(7)
        );

        SleepAnalysisResult<Long> result = sut.apply(sessions);

        assertEquals(7L * 60L, result.getValue());
    }

    @Test
    public void shouldReturnZeroWhenNoSessions() {
        SleepAnalysisResult<Long> result = sut.apply(sessions);

        assertEquals(0L, result.getValue());
    }
}

package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class GetBadSessionCountCommandTest {

    private final GetBadSessionCountCommand sut = new GetBadSessionCountCommand();
    private List<SleepSession> sessions;

    @BeforeEach
    public void beforeEach() {
        sessions = new ArrayList<>();
    }

    @Test
    public void shouldReturnBadSessionCountWhenMultipleSessions() {
        sessions = List.of(
                TestUtil.createSession(6, SleepQuality.BAD),
                TestUtil.createSession(7, SleepQuality.NORMAL),
                TestUtil.createSession(10, SleepQuality.GOOD),
                TestUtil.createSession(5, SleepQuality.BAD)
        );

        SleepAnalysisResult<Long> result = sut.apply(sessions);

        assertEquals(2, result.getValue());
    }

    @Test
    public void shouldReturnCount1WhenSingleBadSession() {
        sessions = List.of(
                TestUtil.createSession(5, SleepQuality.BAD)
        );

        SleepAnalysisResult<Long> result = sut.apply(sessions);

        assertEquals(1, result.getValue());
    }

    @Test
    public void shouldReturnCountZeroWhenNoSessions() {
        SleepAnalysisResult<Long> result = sut.apply(sessions);

        assertEquals(0, result.getValue());
    }
}

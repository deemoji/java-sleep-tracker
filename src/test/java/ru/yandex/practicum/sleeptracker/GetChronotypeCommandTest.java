package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GetChronotypeCommandTest {

    private final GetChronotypeCommand sut = new GetChronotypeCommand();
    private List<SleepSession> sessions;

    @BeforeEach
    public void beforeEach() {
        sessions = new ArrayList<>();
    }

    @Test
    public void shouldHandleOwlBorder() {
        sessions = List.of(
                TestUtil.createSession("2026-01-01T23:00", "2026-01-02T09:00")
        );

        SleepAnalysisResult<Chronotype> result = sut.apply(sessions);

        assertEquals(Chronotype.OWL, result.getValue());
    }

    @Test
    public void shouldHandleLarkBorder() {
        sessions = List.of(
                TestUtil.createSession("2026-01-01T21:59", "2026-01-02T06:59")
        );

        SleepAnalysisResult<Chronotype> result = sut.apply(sessions);

        assertEquals(Chronotype.LARK, result.getValue());
    }

    @Test
    public void shouldReturnDoveAfter23_00AndBefore9_00() {
        sessions = List.of(
                TestUtil.createSession("2026-01-01T23:00", "2026-01-02T08:59")
        );

        SleepAnalysisResult<Chronotype> result = sut.apply(sessions);

        assertEquals(Chronotype.DOVE, result.getValue());
    }

    @Test
    public void shouldReturnDoveBefore22_00AndAfter7_00() {
        sessions = List.of(
                TestUtil.createSession("2026-01-01T21:59", "2026-01-02T07:00")
        );

        SleepAnalysisResult<Chronotype> result = sut.apply(sessions);

        assertEquals(Chronotype.DOVE, result.getValue());
    }

    @Test
    public void shouldReturnDoveWhenCountIsSameForOwlAndLark() {
        sessions = List.of(
                TestUtil.createSession("2026-01-01T23:30", "2026-01-02T09:30"),
                TestUtil.createSession("2026-01-02T21:30", "2026-01-03T06:30")
        );

        SleepAnalysisResult<Chronotype> result = sut.apply(sessions);

        assertEquals(Chronotype.DOVE, result.getValue());
    }

    @Test
    public void shouldReturnDoveWhenCountIsSameForAllChronotypes() {
        sessions = List.of(
                TestUtil.createSession("2026-01-01T23:30", "2026-01-02T09:30"),
                TestUtil.createSession("2026-01-02T22:00", "2026-01-03T06:30"),
                TestUtil.createSession("2026-01-03T21:59", "2026-01-04T07:00")
        );

        SleepAnalysisResult<Chronotype> result = sut.apply(sessions);

        assertEquals(Chronotype.DOVE, result.getValue());
    }

    @Test
    public void shouldReturnOwlWhenOwlCountIsBiggest() {
        sessions = List.of(
                TestUtil.createSession("2026-01-01T23:30", "2026-01-02T09:30"),
                TestUtil.createSession("2026-01-03T00:00", "2026-01-03T09:30"),
                TestUtil.createSession("2026-01-03T21:59", "2026-01-04T07:00")
        );

        SleepAnalysisResult<Chronotype> result = sut.apply(sessions);

        assertEquals(Chronotype.OWL, result.getValue());
    }

    @Test
    public void shouldReturnLarkWhenLarkCountIsBiggest() {
        sessions = List.of(
                TestUtil.createSession("2026-01-01T21:30", "2026-01-02T06:30"),
                TestUtil.createSession("2026-01-02T21:59", "2026-01-03T06:00"),
                TestUtil.createSession("2026-01-03T23:59", "2026-01-04T09:30")
        );

        SleepAnalysisResult<Chronotype> result = sut.apply(sessions);

        assertEquals(Chronotype.LARK, result.getValue());
    }

    @Test
    public void shouldReturnDoveWhenDoveCountIsBiggest() {
        sessions = List.of(
                TestUtil.createSession("2026-01-01T21:30", "2026-01-02T06:30"),
                TestUtil.createSession("2026-01-02T21:30", "2026-01-03T08:00"),
                TestUtil.createSession("2026-01-03T23:30", "2026-01-04T08:00")
        );

        SleepAnalysisResult<Chronotype> result = sut.apply(sessions);

        assertEquals(Chronotype.DOVE, result.getValue());
    }

    @Test
    public void shouldReturnDoveWhenNoSessions() {
        SleepAnalysisResult<Chronotype> result = sut.apply(sessions);

        assertEquals(Chronotype.DOVE, result.getValue());
    }
}

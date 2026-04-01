package ru.yandex.practicum.sleeptracker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

public class SleepTrackerApp {

    private static final List<SleepTrackerCommand<?>> commands = List.of(
            new GetMinimumSessionCommand(),
            new GetMaximumSessionCommand(),
            new GetAverageSessionCommand(),
            new GetBadSessionCountCommand(),
            new GetSleeplessNightCountCommand(),
            new GetChronotypeCommand()
    );

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");


    public static void main(String[] args) {
        if (args.length == 0) return;

        final String filename = args[0];
        List<SleepSession> sessions = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename, StandardCharsets.UTF_8))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sessions.add(parse(line));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        for (SleepTrackerCommand<?> command : commands) {
            SleepAnalysisResult<?> result = command.apply(sessions);
            System.out.println(result.getDescription() + ": " + result.getValue());
        }
    }

    private static SleepSession parse(String input) {
        String[] parts = input.split(";");

        LocalDateTime start = LocalDateTime.parse(parts[0], FORMATTER);
        LocalDateTime end = LocalDateTime.parse(parts[1], FORMATTER);
        SleepQuality quality = SleepQuality.valueOf(parts[2].toUpperCase());

        return new SleepSession(start, end, quality);
    }
}
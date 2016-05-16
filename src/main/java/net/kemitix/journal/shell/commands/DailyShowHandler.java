package net.kemitix.journal.shell.commands;

import lombok.val;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.kemitix.journal.LogEntryGlyphs;
import net.kemitix.journal.LogEntryList;
import net.kemitix.journal.TypeSafeMap;
import net.kemitix.journal.model.LogEntry;
import net.kemitix.journal.service.JournalService;
import net.kemitix.journal.shell.AbstractCommandHandler;

/**
 * Show the log entries for the currently selected date.
 *
 * @author pcampbell
 */
@Service
class DailyShowHandler extends AbstractCommandHandler {

    private static final List<String> ALIASES = Arrays.asList("daily show",
            "show daily");

    private final JournalService journalService;

    private final TypeSafeMap applicationState;

    private final LogEntryGlyphs glyphs;

    @Inject
    DailyShowHandler(
            final JournalService journalService,
            final TypeSafeMap applicationState, final LogEntryGlyphs glyphs) {
        this.journalService = journalService;
        this.applicationState = applicationState;
        this.glyphs = glyphs;
    }

    @Override
    public List<String> getAliases() {
        return ALIASES;
    }

    @Override
    public String getDescription() {
        return "Shows the log entries for the currently selected date";
    }

    @Override
    public String handle(final Map<String, String> args) {
        val logEntries = new LogEntryList();
        applicationState.get("selected date", LocalDate.class)
                        .ifPresent(date -> logEntries.addAll(
                                journalService.getLogs(date)));
        applicationState.put("log entries", logEntries, LogEntryList.class);
        return logEntriesAsAList(logEntries);
    }

    private String logEntriesAsAList(final LogEntryList logEntries) {
        AtomicInteger i = new AtomicInteger(0);
        return logEntries.stream()
                         .map((logEntry) -> logEntryAsItem(i.incrementAndGet(),
                                 logEntry))
                         .collect(Collectors.joining("\n"));
    }

    private String logEntryAsItem(final Integer i, final LogEntry logEntry) {
        return String.format("%2d: %s %s", i, glyphs.getGlyph(logEntry),
                logEntry.getTitle());
    }

}

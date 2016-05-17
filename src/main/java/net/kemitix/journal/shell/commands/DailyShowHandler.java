package net.kemitix.journal.shell.commands;

import lombok.val;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.kemitix.journal.LogEntryGlyphs;
import net.kemitix.journal.LogEntryList;
import net.kemitix.journal.service.JournalService;
import net.kemitix.journal.shell.AbstractCommandHandler;
import net.kemitix.journal.shell.ShellState;

/**
 * Show the log entries for the currently selected date.
 *
 * @author pcampbell
 */
@Service
class DailyShowHandler extends AbstractCommandHandler {

    private static final List<String> ALIASES = Arrays.asList("daily show",
            "show daily");

    private final JournalService service;

    private final ShellState shellState;

    private final LogEntryGlyphs glyphs;

    private final PrintWriter writer;

    @Inject
    DailyShowHandler(
            final JournalService journalService, final ShellState shellState,
            final LogEntryGlyphs glyphs, final PrintWriter writer) {
        this.service = journalService;
        this.shellState = shellState;
        this.glyphs = glyphs;
        this.writer = writer;
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
    public void handle(final Map<String, String> args) {
        // get log entries for args.'date' and place in state.'log entries'
        val entries = new LogEntryList();
        entries.addAll(service.getLogs(shellState.getDefaultDate()));
        shellState.setLogEntryList(entries);
        // write as a list with index and glyph
        AtomicInteger index = new AtomicInteger(0);
        entries.stream()
               .map((entry) -> String.format("%2d: %s %s",
                       index.incrementAndGet(), glyphs.getGlyph(entry),
                       entry.getTitle()))
               .forEach(writer::println);
    }

}

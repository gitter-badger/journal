package net.kemitix.journal.shell.commands;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.kemitix.journal.model.DailyLog;
import net.kemitix.journal.service.JournalService;
import net.kemitix.journal.shell.AbstractCommandHandler;

/**
 * Lists the DailyLogs available.
 *
 * @author pcampbell
 */
@Service
class DailyListHandler extends AbstractCommandHandler {

    private static final List<String> ALIASES = Arrays.asList("daily list",
            "list daily");

    private final JournalService journalService;

    private final PrintWriter writer;

    @Inject
    DailyListHandler(
            final JournalService journalService, final PrintWriter writer) {
        this.journalService = journalService;
        this.writer = writer;
    }

    @Override
    public List<String> getAliases() {
        return ALIASES;
    }

    @Override
    public String getDescription() {
        return "Lists all the Daily Logs with a count of the items each has";
    }

    @Override
    public void handle(final Map<String, String> args) {
        final List<DailyLog> all = journalService.getAllDailyLogs();
        if (all.size() == 0) {
            writer.println("No Daily Logs found");
        }
        all.stream()
                  .sorted(sortByDate())
                  .map(dailyLogSummary())
                  .forEach(writer::println);
    }

    private Function<DailyLog, String> dailyLogSummary() {
        return dl -> "- " + dl.getDate() + ": " + dl.getEntries().size()
                + " item(s)";
    }

    private Comparator<DailyLog> sortByDate() {
        return (o1, o2) -> o2.getDate().compareTo(o1.getDate());
    }
}

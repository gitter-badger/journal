package net.kemitix.journal.shell;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.kemitix.journal.dao.DailyLogDAO;
import net.kemitix.journal.model.DailyLog;

/**
 * Lists the DailyLogs available.
 *
 * @author pcampbell
 */
@Service
class ListDailyHandler extends AbstractCommandHandler {

    private static final List<String> ALIASES = Collections.singletonList(
            "list daily");

    private final DailyLogDAO dailyLogDAO;

    @Inject
    ListDailyHandler(final DailyLogDAO dailyLogDAO) {
        this.dailyLogDAO = dailyLogDAO;
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
    public String handle(
            final Map<String, String> context, final Map<String, String> args) {
        final List<DailyLog> all = dailyLogDAO.findAll();
        if (all.size() == 0) {
            return "No Daily Logs found";
        }
        return all.stream()
                  .sorted(sortByDate())
                  .map(dailyLogSummary())
                  .collect(Collectors.joining("\n"));
    }

    private Function<DailyLog, String> dailyLogSummary() {
        return dl -> "- " + dl.getDate() + ": " + dl.getEntries().size()
                + " item(s)";
    }

    private Comparator<DailyLog> sortByDate() {
        return (o1, o2) -> o2.getDate().compareTo(o1.getDate());
    }
}

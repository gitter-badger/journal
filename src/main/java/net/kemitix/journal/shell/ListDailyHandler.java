package net.kemitix.journal.shell;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
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
class ListDailyHandler implements CommandHandler {

    private final DailyLogDAO dailyLogDAO;

    @Inject
    ListDailyHandler(final DailyLogDAO dailyLogDAO) {
        this.dailyLogDAO = dailyLogDAO;
    }

    @Override
    public List<String> getCommands() {
        return Collections.singletonList("list-daily");
    }

    @Override
    public String handle(
            final Map<String, String> context, final String command,
            final Queue<String> args) {
        final List<DailyLog> all = dailyLogDAO.findAll();
        if (all.size() == 0) {
            return "No Daily Logs found";
        }
        return String.join("\n", all.stream()
                                    .sorted(sortByDate())
                                    .map(dl -> "- " + dl.getDate() + ": "
                                            + dl.getEntries().size()
                                            + " item(s)")
                                    .collect(Collectors.toList()));
    }

    private Comparator<DailyLog> sortByDate() {
        return (o1, o2) -> o2.getDate().compareTo(o1.getDate());
    }
}

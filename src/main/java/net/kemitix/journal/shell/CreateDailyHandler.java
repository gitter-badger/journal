package net.kemitix.journal.shell;

import lombok.val;

import static net.kemitix.journal.shell.CommandPrompt.SELECTED_DATE;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.kemitix.journal.dao.DailyLogDAO;
import net.kemitix.journal.model.DailyLog;

/**
 * Create a Daily Log for the supplied date, or today if none given.
 *
 * @author pcampbell
 */
@Service
public class CreateDailyHandler implements CommandHandler {

    private final DailyLogDAO dailyLogDAO;

    @Inject
    CreateDailyHandler(final DailyLogDAO dailyLogDAO) {
        this.dailyLogDAO = dailyLogDAO;
    }

    @Override
    public List<String> getCommands() {
        return Collections.singletonList("create-daily");
    }

    @Override
    public String getSyntax() {
        return "create-daily [yyyy-mm-dd]";
    }

    @Override
    public String getDescription() {
        return "Creates a new Daily Log for the date given or for default\n"
                + "date if none is provided";
    }

    @Override
    public String handle(
            final Map<String, String> context, final String command,
            final Queue<String> args) {
        LocalDate date = LocalDate.parse(context.get(SELECTED_DATE));
        if (args.size() > 0) {
            final String suppliedDate = args.remove();
            date = LocalDate.parse(suppliedDate);
        }
        val dailyLog = new DailyLog(date);
        dailyLogDAO.save(dailyLog);
        return "Daily log created for " + dailyLog.getDate();
    }
}

package net.kemitix.journal.shell.commands;

import lombok.val;

import static net.kemitix.journal.shell.CommandPrompt.SELECTED_DATE;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.kemitix.journal.dao.DailyLogDAO;
import net.kemitix.journal.model.DailyLog;
import net.kemitix.journal.shell.AbstractCommandHandler;
import net.kemitix.journal.TypeSafeMap;

/**
 * Create a Daily Log for the supplied date, or today if none given.
 *
 * @author pcampbell
 */
@Service
public class CreateDailyHandler extends AbstractCommandHandler {

    private static final List<String> ALIASES = Collections.singletonList(
            "create daily");

    private static final String PARAMETER_REGEX
            = "(?<date>\\d\\d\\d\\d-\\d\\d-\\d\\d)";

    private static final List<String> PARAMETERS = Collections.singletonList(
            "date");

    private final DailyLogDAO dailyLogDAO;

    private final SetDateHandler setDateHandler;

    private final TypeSafeMap applicationState;

    @Inject
    CreateDailyHandler(
            final DailyLogDAO dailyLogDAO, final SetDateHandler setDateHandler,
            final TypeSafeMap applicationState) {
        this.dailyLogDAO = dailyLogDAO;
        this.setDateHandler = setDateHandler;
        this.applicationState = applicationState;
    }

    @Override
    public List<String> getAliases() {
        return ALIASES;
    }

    @Override
    public Optional<String> getParameterRegex() {
        return Optional.of(PARAMETER_REGEX);
    }

    @Override
    public List<String> getParameterNames() {
        return PARAMETERS;
    }

    @Override
    public String getSyntax() {
        return ALIASES.stream()
                      .map(a -> a + " [yyyy-mm-dd]")
                      .collect(Collectors.joining("\n"));
    }

    @Override
    public String getDescription() {
        return "Creates a new Daily Log for the date given or for default\n"
                + "date if none is provided. The application date is set to\n"
                + "this new date.";
    }

    @Override
    public String handle(
            final Map<String, String> args) {
        LocalDate date;
        List<String> output = new ArrayList<>();
        if (args.containsKey("date")) {
            date = LocalDate.parse(args.get("date"));
            output.add(setDateHandler.handle(args));
        } else {
            val dateOptional = applicationState.get(SELECTED_DATE,
                    LocalDate.class);
            if (dateOptional.isPresent()) {
                date = dateOptional.get();
            } else {
                date = LocalDate.now();
            }
        }
        val dailyLog = new DailyLog(date);
        dailyLogDAO.save(dailyLog);
        output.add("Daily log created for " + dailyLog.getDate());
        return String.join("\n", output);
    }

}

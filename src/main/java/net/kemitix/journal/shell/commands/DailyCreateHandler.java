package net.kemitix.journal.shell.commands;

import lombok.val;

import static net.kemitix.journal.shell.CommandPrompt.SELECTED_DATE;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.kemitix.journal.TypeSafeMap;
import net.kemitix.journal.service.JournalService;
import net.kemitix.journal.shell.AbstractCommandHandler;

/**
 * Create a Daily Log for the supplied date, or today if none given.
 *
 * @author pcampbell
 */
@Service
class DailyCreateHandler extends AbstractCommandHandler {

    private static final List<String> ALIASES = Arrays.asList("daily create",
            "create daily");

    private static final String PARAMETER_REGEX
            = "(?<date>\\d\\d\\d\\d-\\d\\d-\\d\\d)";

    private static final List<String> PARAMETERS = Collections.singletonList(
            "date");

    private final JournalService journalService;

    private final DateSetHandler dateSetHandler;

    private final TypeSafeMap applicationState;

    private final PrintWriter writer;

    @Inject
    DailyCreateHandler(
            final JournalService journalService,
            final DateSetHandler dateSetHandler,
            final TypeSafeMap applicationState, final PrintWriter writer) {
        this.journalService = journalService;
        this.dateSetHandler = dateSetHandler;
        this.applicationState = applicationState;
        this.writer = writer;
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
    public void handle(
            final Map<String, String> args) {
        LocalDate date;
        if (args.containsKey("date")) {
            dateSetHandler.handle(args);
        }
        val dateOptional = applicationState.get(SELECTED_DATE, LocalDate.class);
        if (dateOptional.isPresent()) {
            date = dateOptional.get();
        } else {
            date = LocalDate.now();
        }
        val dailyLog = journalService.createDailyLog(date);
        writer.println("Daily log created for " + dailyLog.getDate());
    }

}

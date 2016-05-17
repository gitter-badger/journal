package net.kemitix.journal.shell.commands;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.kemitix.journal.service.JournalService;
import net.kemitix.journal.shell.AbstractCommandHandler;
import net.kemitix.journal.shell.ShellState;

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

    private final ShellState shellState;

    private final PrintWriter writer;

    @Inject
    DailyCreateHandler(
            final JournalService journalService,
            final DateSetHandler dateSetHandler, final ShellState shellState,
            final PrintWriter writer) {
        this.journalService = journalService;
        this.dateSetHandler = dateSetHandler;
        this.shellState = shellState;
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
        if (args.containsKey("date")) {
            dateSetHandler.handle(args);
        }
        writer.println("Daily log created for " + journalService.createDailyLog(
                shellState.getDefaultDate()).getDate());
    }

}

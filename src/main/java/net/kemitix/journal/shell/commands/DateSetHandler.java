package net.kemitix.journal.shell.commands;

import java.io.PrintWriter;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.kemitix.journal.shell.AbstractCommandHandler;
import net.kemitix.journal.shell.CommandHandlerException;
import net.kemitix.journal.shell.ShellState;

/**
 * Sets the application's current date to that provided. This date will be the
 * default date for other commands unless they take one from the command line.
 *
 * @author pcampbell
 */
@Service
class DateSetHandler extends AbstractCommandHandler {

    private static final List<String> ALIASES = Arrays.asList("date set",
            "set date");

    private static final String PARAMETER_REGEX
            = "(?<date>\\d\\d\\d\\d-\\d\\d-\\d\\d)?";

    private static final List<String> PARAMETER_NAMES
            = Collections.singletonList("date");

    private final ShellState shellState;

    private final PrintWriter writer;

    @Inject
    DateSetHandler(
            final ShellState shellState, final PrintWriter writer) {
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
        return PARAMETER_NAMES;
    }

    @Override
    public String getSyntax() {
        return ALIASES.stream()
                      .map(a -> a + " [yyyy-mm-dd]")
                      .collect(Collectors.joining("\n"));
    }

    @Override
    public String getDescription() {
        return "Sets the application's date. This will be the default date\n"
                + "for other commands. If no date is given then the date will\n"
                + " be set to today.";
    }

    @Override
    public void handle(final Map<String, String> args) {
        LocalDate selectedDate = LocalDate.now();
        if (args.containsKey("date")) {
            try {
                selectedDate = LocalDate.parse(args.get("date"));
            } catch (DateTimeException e) {
                throw new CommandHandlerException(
                        "Invalid date: " + args.get("date"), e);
            }
        }
        shellState.setDefaultDate(selectedDate);
        writer.println("Date set to " + selectedDate);
    }
}

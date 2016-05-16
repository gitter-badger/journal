package net.kemitix.journal.shell;

import static net.kemitix.journal.shell.CommandPrompt.SELECTED_DATE;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

/**
 * Sets the application's current date to that provided. This date will be the
 * default date for other commands unless they take one from the command line.
 *
 * @author pcampbell
 */
@Service
class SetDateHandler extends AbstractCommandHandler {

    private static final List<String> ALIASES = Collections.singletonList(
            "set date");

    private static final String PARAMETER_REGEX
            = "(?<date>\\d\\d\\d\\d-\\d\\d-\\d\\d)?";

    private static final List<String> PARAMETER_NAMES
            = Collections.singletonList("date");

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
    public String handle(
            final Map<String, String> context, final Map<String, String> args) {
        LocalDate selectedDate = LocalDate.now();
        if (args.containsKey("date")) {
            selectedDate = LocalDate.parse(args.get("date"));
        }
        context.put(SELECTED_DATE, selectedDate.toString());
        return "Date set to " + context.get(SELECTED_DATE);
    }
}

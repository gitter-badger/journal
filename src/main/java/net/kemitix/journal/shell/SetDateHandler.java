package net.kemitix.journal.shell;

import static net.kemitix.journal.shell.CommandPrompt.SELECTED_DATE;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.springframework.stereotype.Service;

/**
 * Sets the application's current date to that provided. This date will be the
 * default date for other commands unless they take one from the command line.
 *
 * @author pcampbell
 */
@Service
class SetDateHandler implements CommandHandler {

    private static final String COMMAND = "set-date";

    @Override
    public List<String> getCommands() {
        return Collections.singletonList(COMMAND);
    }

    @Override
    public String getSyntax() {
        return COMMAND + " [yyyy-mm-dd]";
    }

    @Override
    public String getDescription() {
        return "Sets the application's date. This will be the default date\n"
                + "for other commands.\n"
                + "If no date is given then the date will be set to today.";
    }

    @Override
    public String handle(
            final Map<String, String> context, final String command,
            final Queue<String> args) {
        LocalDate selectedDate = LocalDate.now();
        if (!args.isEmpty()) {
            selectedDate = LocalDate.parse(args.remove());
        }
        context.put(SELECTED_DATE, selectedDate.toString());
        return "Date set to " + context.get(SELECTED_DATE);
    }
}

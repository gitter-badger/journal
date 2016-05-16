package net.kemitix.journal.shell;

import static net.kemitix.journal.shell.CommandPrompt.STATE;
import static net.kemitix.journal.shell.CommandPrompt.STATE_EXITING;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * Exit handler.
 *
 * @author pcampbell
 */
@Service
class ExitHandler extends AbstractCommandHandler {

    private static final List<String> ALIASES = Arrays.asList("exit", "quit",
            "bye");

    @Override
    public List<String> getAliases() {
        return ALIASES;
    }

    @Override
    public String getDescription() {
        return "Exits the application";
    }

    @Override
    public String handle(
            final Map<String, String> context, final Map<String, String> args) {
        context.put(STATE, STATE_EXITING);
        return "Exiting...";
    }
}

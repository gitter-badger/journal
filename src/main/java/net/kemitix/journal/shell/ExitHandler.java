package net.kemitix.journal.shell;

import static net.kemitix.journal.shell.CommandPrompt.STATE;
import static net.kemitix.journal.shell.CommandPrompt.STATE_EXITING;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.springframework.stereotype.Service;

/**
 * Exit handler.
 *
 * @author pcampbell
 */
@Service
class ExitHandler implements CommandHandler {

    private static final String COMMAND = "exit";

    @Override
    public List<String> getCommands() {
        return Collections.singletonList(COMMAND);
    }

    @Override
    public String handle(
            final Map<String, String> context, final String command,
            final Queue<String> args) {
        context.put(STATE, STATE_EXITING);
        return "Exiting...";
    }
}

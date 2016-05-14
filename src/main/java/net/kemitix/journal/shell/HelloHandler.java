package net.kemitix.journal.shell;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.springframework.stereotype.Service;

/**
 * Hello World handler.
 *
 * @author pcampbell
 */
@Service
class HelloHandler implements CommandHandler {

    private static final String COMMAND_HELLO = "hello";

    private static final String COMMAND_HI = "hi";

    private static final String COMMAND_HIYA = "hiya";

    @Override
    public List<String> getCommands() {
        return Arrays.asList(COMMAND_HELLO, COMMAND_HI, COMMAND_HIYA);
    }

    @Override
    public String handle(
            final Map<String, String> context, final String command,
            final Queue<String> args) {
        String name = "World";
        if (args.size() > 0) {
            name = args.remove();
        }
        return "Hello, " + name + "!";
    }
}

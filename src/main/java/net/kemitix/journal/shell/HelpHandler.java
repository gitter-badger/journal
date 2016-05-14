package net.kemitix.journal.shell;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

/**
 * Help handler.
 *
 * @author pcampbell
 */
@Service
class HelpHandler implements CommandHandler {

    private final List<CommandHandler> handlerList;

    @Inject
    HelpHandler(final List<CommandHandler> handlerList) {
        this.handlerList = handlerList;
    }

    @Override
    public List<String> getCommands() {
        return Collections.singletonList("help");
    }

    @Override
    public String getSyntax() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "lists the commands available";
    }

    @Override
    public String handle(
            final Map<String, String> context, final String command,
            final Queue<String> args) {
        return "The following commands are available:\n" + String.join("\n",
                handlerList.stream()
                           .map(this::formatUsage)
                           .collect(Collectors.toList()));
    }

    String formatUsage(final CommandHandler handler) {
        return handler.getSyntax() + "\n - " + handler.getDescription();
    }
}

package net.kemitix.journal.shell;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

/**
 * Help handler.
 *
 * @author pcampbell
 */
@Service
class HelpHandler extends AbstractCommandHandler {

    private static final List<String> ALIASES = Collections.singletonList(
            "help");

    private final List<CommandHandler> handlerList;

    @Inject
    HelpHandler(final List<CommandHandler> handlerList) {
        this.handlerList = handlerList;
    }

    @Override
    public List<String> getAliases() {
        return ALIASES;
    }

    @Override
    public String getDescription() {
        return "lists the commands available";
    }

    @Override
    public String handle(final Map<String, String> args) {
        return "The following commands are available:\n" + String.join("\n",
                handlerList.stream()
                           .map(CommandHandler::getUsage)
                           .collect(Collectors.toList()));
    }

}

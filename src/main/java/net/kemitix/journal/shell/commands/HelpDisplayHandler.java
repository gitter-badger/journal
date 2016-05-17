package net.kemitix.journal.shell.commands;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.kemitix.journal.shell.AbstractCommandHandler;
import net.kemitix.journal.shell.CommandHandler;

/**
 * Help handler.
 *
 * @author pcampbell
 */
@Service
class HelpDisplayHandler extends AbstractCommandHandler {

    private static final List<String> ALIASES = Collections.singletonList(
            "help");

    private final List<CommandHandler> handlerList;

    private final PrintWriter writer;

    @Inject
    HelpDisplayHandler(
            final List<CommandHandler> handlerList, final PrintWriter writer) {
        this.handlerList = handlerList;
        this.writer = writer;
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
    public void handle(final Map<String, String> args) {
        writer.println("The following commands are available:");
        handlerList.stream()
                   .map(CommandHandler::getUsage)
                   .forEach(writer::println);
    }

}

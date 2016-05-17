package net.kemitix.journal.shell.commands;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.kemitix.journal.shell.AbstractCommandHandler;
import net.kemitix.journal.shell.ShellState;

/**
 * Exit handler.
 *
 * @author pcampbell
 */
@Service
class ApplicationExitHandler extends AbstractCommandHandler {

    private static final List<String> ALIASES = Arrays.asList("exit", "quit",
            "bye");

    private final ShellState shellState;

    private final PrintWriter writer;

    @Inject
    ApplicationExitHandler(
            final ShellState shellState, final PrintWriter writer) {
        this.shellState = shellState;
        this.writer = writer;
    }

    @Override
    public List<String> getAliases() {
        return ALIASES;
    }

    @Override
    public String getDescription() {
        return "Exits the application";
    }

    @Override
    public void handle(final Map<String, String> args) {
        shellState.shutdown();
        writer.println("Exiting...");
    }
}

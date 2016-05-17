package net.kemitix.journal.shell.commands;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.kemitix.journal.TypeSafeMap;
import net.kemitix.journal.shell.AbstractCommandHandler;

/**
 * Exit handler.
 *
 * @author pcampbell
 */
@Service
class ApplicationExitHandler extends AbstractCommandHandler {

    private static final List<String> ALIASES = Arrays.asList("exit", "quit",
            "bye");

    private final TypeSafeMap applicationState;

    private final PrintWriter writer;

    @Inject
    ApplicationExitHandler(
            final TypeSafeMap applicationState, final PrintWriter writer) {
        this.applicationState = applicationState;
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
        applicationState.remove("running");
        writer.println("Exiting...");
    }
}

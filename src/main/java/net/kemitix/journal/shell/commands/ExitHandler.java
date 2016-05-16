package net.kemitix.journal.shell.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.kemitix.journal.shell.AbstractCommandHandler;
import net.kemitix.journal.shell.TypeSafeMap;

/**
 * Exit handler.
 *
 * @author pcampbell
 */
@Service
class ExitHandler extends AbstractCommandHandler {

    private static final List<String> ALIASES = Arrays.asList("exit", "quit",
            "bye");

    private final TypeSafeMap applicationState;

    @Inject
    ExitHandler(final TypeSafeMap applicationState) {
        this.applicationState = applicationState;
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
    public String handle(final Map<String, String> args) {
        applicationState.remove("running");
        return "Exiting...";
    }
}

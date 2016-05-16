package net.kemitix.journal.shell;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Base class for {@link CommandHandler} implementations.
 *
 * @author pcampbell
 */
public abstract class AbstractCommandHandler implements CommandHandler {

    @Override
    public String getSyntax() {
        return getAliases().stream().collect(Collectors.joining("\n"));
    }

    @Override
    public Optional<String> getParameterRegex() {
        return Optional.empty();
    }

    @Override
    public List<String> getParameterNames() {
        return Collections.emptyList();
    }

    @Override
    public String getUsage() {
        return getSyntax() + "\n - " + getDescription();
    }
}

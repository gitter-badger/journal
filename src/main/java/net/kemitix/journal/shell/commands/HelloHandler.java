package net.kemitix.journal.shell.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import net.kemitix.journal.shell.AbstractCommandHandler;

/**
 * Hello World handler.
 *
 * @author pcampbell
 */
@Service
class HelloHandler extends AbstractCommandHandler {

    private static final List<String> ALIASES = Arrays.asList("hello", "hi",
            "hiya");

    private static final String PARAMETER_REGEX = "(?<name>.+)?";

    private static final List<String> PARAMETERS = Collections.singletonList(
            "name");

    @Override
    public List<String> getAliases() {
        return ALIASES;
    }

    @Override
    public Optional<String> getParameterRegex() {
        return Optional.of(PARAMETER_REGEX);
    }

    @Override
    public List<String> getParameterNames() {
        return PARAMETERS;
    }

    @Override
    public String getSyntax() {
        return ALIASES.stream()
                      .map(a -> a + " [name]")
                      .collect(Collectors.joining("\n"));
    }

    @Override
    public String getDescription() {
        return "Hello World example command";
    }

    @Override
    public String handle(final Map<String, String> args) {
        String name = "World";
        if (args.containsKey("name")) {
            name = args.get("name");
        }
        return "Hello, " + name + "!";
    }
}

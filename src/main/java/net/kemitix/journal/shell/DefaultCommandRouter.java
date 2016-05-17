package net.kemitix.journal.shell;

import lombok.val;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.stereotype.Service;

/**
 * Default implementation of the {@link CommandRouter} interface.
 *
 * @author pcampbell
 */
@Service
class DefaultCommandRouter implements CommandRouter {

    private final List<CommandHandler> handlerList;

    private final PrintWriter writer;

    private Map<Pattern, CommandHandler> commandMap;

    @Inject
    DefaultCommandRouter(
            final List<CommandHandler> handlerList, final PrintWriter writer) {
        this.handlerList = handlerList;
        this.writer = writer;
    }

    @PostConstruct
    public void init() {
        commandMap = new HashMap<>();
        handlerList.stream().forEach(h -> {
            h.getAliases().stream().forEach(a -> {
                h.getParameterRegex()
                 .ifPresent(p -> commandMap.put(Pattern.compile(a + "\\s+" + p),
                         h));
                commandMap.put(Pattern.compile(a), h);
            });
        });
    }

    @Override
    public Optional<CommandMapping> selectCommand(final String command) {
        val matching = commandMap.keySet()
                                 .stream()
                                 .filter(p -> p.matcher(command).matches())
                                 .collect(Collectors.toList());
        if (matching.size() == 1) {
            val handler = commandMap.get(matching.get(0));
            val mapping = CommandMapping.builder()
                                        .handler(handler)
                                        .args(getArgs(command, matching.get(0),
                                                handler))
                                        .build();
            return Optional.of(mapping);
        }
        if (!matching.isEmpty()) {
            writer.println("Command matches multiple commands:");
            matching.stream()
                    .map(p -> commandMap.get(p))
                    .map(CommandHandler::getSyntax)
                    .map(s -> "- " + s)
                    .forEach(writer::println);
        }
        return Optional.empty();
    }

    private Map<String, String> getArgs(
            final String command, final Pattern matching,
            final CommandHandler handler) {
        val args = new HashMap<String, String>();
        val matcher = matching.matcher(command);
        if (matcher.matches()) {
            handler.getParameterNames().stream().forEach(n -> {
                try {
                    final String value = matcher.group(n);
                    args.put(n, value);
                } catch (IllegalArgumentException e) {
                    // no argument - must have been optional
                }
            });
        }
        return args;
    }

}

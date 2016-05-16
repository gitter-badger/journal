package net.kemitix.journal.shell;

import lombok.Getter;
import lombok.experimental.Builder;

import java.util.Map;

/**
 * Represents the result of mapping a command line to the handler that will
 * process it and a Map of the named arguments to be passed to the handler.
 *
 * @author pcampbell
 */
@Builder
@Getter
class CommandMapping {

    private final CommandHandler handler;

    private final Map<String, String> args;

}

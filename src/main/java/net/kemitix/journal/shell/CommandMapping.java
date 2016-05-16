package net.kemitix.journal.shell;

import lombok.Getter;
import lombok.experimental.Builder;

import java.util.Map;

/**
 * .
 *
 * @author pcampbell
 */
@Builder
@Getter
class CommandMapping {

    private final CommandHandler handler;

    private final Map<String, String> args;

}

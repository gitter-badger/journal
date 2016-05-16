package net.kemitix.journal.shell;

import java.util.Optional;

/**
 * Interface for routing to a command from a command string.
 *
 * @author pcampbell
 */
interface CommandRouter {

    /**
     * Selects the best matching {@link CommandHandler} for the supplied command
     * line.
     *
     * @param command the command string
     *
     * @return an Optional containing the best matching command handler if any
     */
    Optional<CommandMapping> selectCommand(String command);

}

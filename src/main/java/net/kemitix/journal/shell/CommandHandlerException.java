package net.kemitix.journal.shell;

/**
 * Exception for error occurring within a CommandHandler..
 *
 * @author pcampbell
 */
public class CommandHandlerException extends RuntimeException {

    public CommandHandlerException(
            final String message, final Exception cause) {
        super(message, cause);
    }
}

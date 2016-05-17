package net.kemitix.journal.shell;

/**
 * Exception for error occurring within a CommandHandler..
 *
 * @author pcampbell
 */
public class CommandHandlerException extends RuntimeException {

    /**
     * Constructor.
     * @param message the message to display to the user
     * @param cause the cause
     */
    public CommandHandlerException(
            final String message, final Exception cause) {
        super(message, cause);
    }
}

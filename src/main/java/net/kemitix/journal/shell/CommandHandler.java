package net.kemitix.journal.shell;

import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Interface for handling a command.
 *
 * @author pcampbell
 */
interface CommandHandler {

    /**
     * Returns the list of commands that can be handled.
     */
    List<String> getCommands();

    /**
     * Handle the command.
     *
     * @param context the application context
     * @param command the command alias used to invoke the command
     * @param args    the remaining arguments to the command
     *
     * @return the command output
     */
    String handle(
            Map<String, String> context, String command, Queue<String> args);

}

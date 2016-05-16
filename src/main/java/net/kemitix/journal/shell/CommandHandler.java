package net.kemitix.journal.shell;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Interface for handling a command.
 *
 * @author pcampbell
 */
interface CommandHandler {

    /**
     * Returns a list of aliases for the command. May be multiple words.
     *
     * @return list of command aliases
     */
    List<String> getAliases();

    /**
     * Returns an Optional containing a regex Pattern to match against
     * parameters that follow the matched alias. If the command does not take
     * any parameters then the Optional will be empty.
     *
     * @return an Optional containing any parameter regex
     */
    Optional<String> getParameterRegex();

    /**
     * Returns the list of the named parameters captured by {@link
     * #getParameterRegex()}.
     *
     * @return the list of parameter names
     */
    List<String> getParameterNames();

    /**
     * Returns the syntax of the command for display in help messages.
     *
     * @return the syntax
     */
    String getSyntax();

    /**
     * Returns the description of the command.
     *
     * @return the description
     */
    String getDescription();

    /**
     * Returns a summary of the command and how to use it.
     *
     * @return description of how to use the command
     */
    String getUsage();

    /**
     * Handle the command.
     *
     * @param args the matched arguments
     *
     * @return the command output
     */
    String handle(Map<String, String> args);

}

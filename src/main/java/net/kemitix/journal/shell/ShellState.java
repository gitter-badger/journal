package net.kemitix.journal.shell;

import java.time.LocalDate;
import java.util.Optional;

import net.kemitix.journal.LogEntryList;
import net.kemitix.journal.model.LogEntry;

/**
 * Interface for maintaining the state of the shell.
 *
 * @author pcampbell
 */
public interface ShellState {

    /**
     * Sets the state to shutdown at the next opportunity.
     */
    void shutdown();

    /**
     * Returns true if the {@link #shutdown()} method has been called.
     *
     * @return true if shutting down
     */
    boolean isShuttingDown();

    /**
     * Selects the date as the new default date.
     *
     * @param date the default date
     */
    void setDefaultDate(LocalDate date);

    /**
     * Returns the current default date.
     *
     * @return the default date
     */
    LocalDate getDefaultDate();

    /**
     * Stashes a list log entries. The user should have just seen this list, so
     * that they can select on of them later using {@link
     * #getLogEntryFromList(Integer)}.
     *
     * @param list the list of log entries
     */
    void setLogEntryList(LogEntryList list);

    /**
     * Returns the indexed LogEntry from the stashed LogEntryList.
     *
     * @param index the 0-based index of the entry to return
     *
     * @return an Optional containing the LogEntry, or empty if none found
     */
    Optional<LogEntry> getLogEntryFromList(Integer index);

}

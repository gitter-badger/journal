package net.kemitix.journal.model;

/**
 * Interface for log entry items.
 *
 * @author pcampbell
 */
public interface LogEntry {

    /**
     * Gets the title of the log entry.
     *
     * @return the title
     */
    String getTitle();

    /**
     * Sets the title of the log entry
     *
     * @param title the title
     */
    void setTitle(String title);

    /**
     * Gets the body of the log entry.
     *
     * @return the body
     */
    String getBody();

    /**
     * Sets the body of the log entry.
     *
     * @param body the body
     */
    void setBody(String body);
}

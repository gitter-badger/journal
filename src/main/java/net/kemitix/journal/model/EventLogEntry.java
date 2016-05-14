package net.kemitix.journal.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * Event Log Entry.
 *
 * @author pcampbell
 */
@Setter
@Getter
@Entity
public class EventLogEntry implements LogEntry {

    private String title;

    private String body;

}

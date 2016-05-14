package net.kemitix.journal.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Event Log Entry.
 *
 * @author pcampbell
 */
@Entity
@DiscriminatorValue(value = "Event")
public class EventLogEntry extends LogEntry {

}

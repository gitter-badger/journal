package net.kemitix.journal.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Note Log Entry.
 *
 * @author pcampbell
 */
@Entity
@DiscriminatorValue(value = "Note")
public class NoteLogEntry extends LogEntry {

}

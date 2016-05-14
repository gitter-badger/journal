package net.kemitix.journal.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * Note Log Entry.
 *
 * @author pcampbell
 */
@Setter
@Getter
@Entity
public class NoteLogEntry implements LogEntry {

    private String title;

    private String body;

}

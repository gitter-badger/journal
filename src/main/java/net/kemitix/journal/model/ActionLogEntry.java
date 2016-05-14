package net.kemitix.journal.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * Action Log Entry.
 *
 * @author pcampbell
 */
@Setter
@Getter
@Entity
public class ActionLogEntry implements LogEntry {

    private String title;

    private String body;

    private ActionState state;

}

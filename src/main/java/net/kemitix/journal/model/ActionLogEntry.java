package net.kemitix.journal.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Action Log Entry.
 *
 * @author pcampbell
 */
@Setter
@Getter
@Entity
@DiscriminatorValue(value = "Action")
public class ActionLogEntry extends LogEntry {

    private ActionState state;

}

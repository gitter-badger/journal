package net.kemitix.journal.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * Interface for log entry items.
 *
 * @author pcampbell
 */
@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type",
        discriminatorType = DiscriminatorType.STRING, length = 6)
@SuppressWarnings("magicnumber")
public abstract class LogEntry {

    @Id
    @GeneratedValue
    private Long id;

    @Column(insertable = false, updatable = false)
    private String type;

    private String title;

    private String body;

}

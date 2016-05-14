package net.kemitix.journal.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * DailyLog.
 *
 * @author pcampbell
 */
@Setter
@Getter
@Entity
public class DailyLog {

    @Id
    private LocalDate date;

    @ElementCollection(targetClass = LogEntry.class)
    private List<LogEntry> entries;

}

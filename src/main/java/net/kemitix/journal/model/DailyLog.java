package net.kemitix.journal.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;

/**
 * DailyLog.
 *
 * @author pcampbell
 */
@Setter
@Getter
@Entity
public class DailyLog {

    private LocalDate date;

    private List<LogEntry> entries;

}

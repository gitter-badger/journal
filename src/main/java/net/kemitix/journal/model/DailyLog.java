package net.kemitix.journal.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * DailyLog.
 *
 * @author pcampbell
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DailyLog {

    @Id
    private LocalDate date;

    @ElementCollection(targetClass = LogEntry.class)
    @OneToMany(fetch = FetchType.EAGER)
    private final List<LogEntry> entries = new ArrayList<>();

}

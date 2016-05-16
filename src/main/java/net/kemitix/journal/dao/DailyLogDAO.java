package net.kemitix.journal.dao;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.kemitix.journal.model.DailyLog;

/**
 * DAO for {@link DailyLog}.
 *
 * @author pcampbell
 */
public interface DailyLogDAO extends JpaRepository<DailyLog, LocalDate> {

    /**
     * Find the Daily Log for the given date.
     *
     * @param date the date
     *
     * @return the daily log
     */
    Optional<DailyLog> findByDate(LocalDate date);
}

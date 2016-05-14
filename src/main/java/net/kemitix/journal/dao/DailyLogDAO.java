package net.kemitix.journal.dao;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import net.kemitix.journal.model.DailyLog;

/**
 * DAO for {@link net.kemitix.journal.model.DailyLog}.
 *
 * @author pcampbell
 */
public interface DailyLogDAO extends JpaRepository<DailyLog, LocalDate> {

}

package net.kemitix.journal.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import net.kemitix.journal.model.LogEntry;

/**
 * DAO for {@link LogEntry}.
 *
 * @author pcampbell
 */
public interface LogEntryDAO extends JpaRepository<LogEntry, Long> {

}

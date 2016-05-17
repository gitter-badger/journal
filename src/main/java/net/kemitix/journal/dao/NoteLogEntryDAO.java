package net.kemitix.journal.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import net.kemitix.journal.model.NoteLogEntry;

/**
 * DAO for {@link NoteLogEntry}.
 *
 * @author pcampbell
 */
public interface NoteLogEntryDAO extends JpaRepository<NoteLogEntry, Long> {

}

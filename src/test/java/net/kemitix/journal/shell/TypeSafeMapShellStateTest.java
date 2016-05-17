package net.kemitix.journal.shell;

import lombok.val;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import net.kemitix.journal.LogEntryList;
import net.kemitix.journal.TypeSafeHashMap;
import net.kemitix.journal.TypeSafeMap;
import net.kemitix.journal.model.ActionLogEntry;
import net.kemitix.journal.model.EventLogEntry;
import net.kemitix.journal.model.NoteLogEntry;

/**
 * Tests for {@link TypeSafeMapShellState}.
 *
 * @author pcampbell
 */
public class TypeSafeMapShellStateTest {

    private TypeSafeMap map;

    private TypeSafeMapShellState shellState;

    @Before
    public void setUp() throws Exception {
        map = new TypeSafeHashMap();
        shellState = new TypeSafeMapShellState(map);
    }

    @Test
    public void shouldIsShuttingDown() throws Exception {
        //given
        assertThat(shellState.isShuttingDown()).as(
                "Initial statue is not shutting down").isFalse();
        //when
        shellState.shutdown();
        //then
        assertThat(shellState.isShuttingDown()).as(
                "Shutdown has been requested").isTrue();
    }

    @Test
    public void shouldGetDefaultDate() throws Exception {
        //given
        val now = LocalDate.now();
        val tomorrow = now.plusDays(1);
        assertThat(shellState.getDefaultDate()).as(
                "Initial default date is today").isEqualTo(now);
        //when
        shellState.setDefaultDate(tomorrow);
        //then
        assertThat(shellState.getDefaultDate()).as(
                "Default date has been set to tomorrow").isEqualTo(tomorrow);
    }

        @Test
    public void shouldGetLogEntryFromList() throws Exception {
        //given
        assertThat(shellState.getLogEntryFromList(0)).isEmpty();
        val list = new LogEntryList();
        val actionLogEntry = new ActionLogEntry();
        val noteLogEntry = new NoteLogEntry();
        val eventLogEntry = new EventLogEntry();
        list.add(actionLogEntry);
        list.add(noteLogEntry);
        list.add(eventLogEntry);
        //when
        shellState.setLogEntryList(list);
        //then
        assertThat(shellState.getLogEntryFromList(0)).contains(actionLogEntry);
        assertThat(shellState.getLogEntryFromList(1)).contains(noteLogEntry);
        assertThat(shellState.getLogEntryFromList(2)).contains(eventLogEntry);
    }

}

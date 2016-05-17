package net.kemitix.journal.shell.commands;

import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import net.kemitix.journal.LogEntryGlyphs;
import net.kemitix.journal.model.LogEntry;
import net.kemitix.journal.service.JournalService;
import net.kemitix.journal.shell.ShellState;

/**
 * Tests for {@link DailyShowHandler}.
 *
 * @author pcampbell
 */
public class DailyShowHandlerTest {

    @InjectMocks
    private DailyShowHandler handler;

    @Mock
    private JournalService journalService;

    @Mock
    private ShellState shellState;

    @Mock
    private LogEntryGlyphs glyphs;

    @Mock
    private LogEntry logEntry1;

    @Mock
    private LogEntry logEntry2;

    @Mock
    private PrintWriter writer;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetAliases() {
        assertThat(handler.getAliases()).isNotEmpty();
    }

    @Test
    public void shouldGetDescription() {
        assertThat(handler.getDescription()).isNotEmpty();
    }

    @Test
    public void shouldHandle() throws Exception {
        //given
        val now = LocalDate.now();
        val logEntries = new ArrayList<LogEntry>();
        logEntries.add(logEntry1);
        logEntries.add(logEntry2);
        given(shellState.getDefaultDate()).willReturn(now);
        given(journalService.getLogs(now)).willReturn(logEntries);
        given(glyphs.getGlyph(logEntry1)).willReturn("a");
        given(glyphs.getGlyph(logEntry2)).willReturn("b");
        given(logEntry1.getTitle()).willReturn("entry 1");
        given(logEntry2.getTitle()).willReturn("entry 2");
        //when
        handler.handle(new HashMap<>());
        //then
        verify(writer).println(" 1: a entry 1");
        verify(writer).println(" 2: b entry 2");
    }

}

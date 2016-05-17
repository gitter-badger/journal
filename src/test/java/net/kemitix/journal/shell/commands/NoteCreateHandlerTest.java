package net.kemitix.journal.shell.commands;

import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.HashMap;

import net.kemitix.journal.model.NoteLogEntry;
import net.kemitix.journal.service.JournalService;
import net.kemitix.journal.shell.MultiLinePromptedTextReader;
import net.kemitix.journal.shell.ShellState;

/**
 * Tests for {@link NoteCreateHandler}.
 *
 * @author pcampbell
 */
public class NoteCreateHandlerTest {

    @InjectMocks
    private NoteCreateHandler handler;

    @Mock
    private ShellState shellState;

    @Mock
    private PrintWriter writer;

    @Mock
    private JournalService journalService;

    @Mock
    private MultiLinePromptedTextReader promptedTextReader;

    @Mock
    private NoteLogEntry noteLogEntry;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetAliases() throws Exception {
        assertThat(handler.getAliases()).isNotEmpty();
    }

    @Test
    public void shouldGetParameterRegex() throws Exception {
        assertThat(handler.getParameterRegex()).isNotEmpty();
    }

    @Test
    public void shouldGetParameterNames() throws Exception {
        assertThat(handler.getParameterNames()).isNotEmpty();
    }

    @Test
    public void shouldGetDescription() throws Exception {
        assertThat(handler.getDescription()).isNotEmpty();
    }

    @Test
    public void shouldHandle() throws Exception {
        //given
        val now = LocalDate.now();
        val title = "note title";
        val body = "note body";
        val args = new HashMap<String, String>();
        args.put("title", title);
        given(shellState.getDefaultDate()).willReturn(now);
        given(promptedTextReader.readText(any(String.class))).willReturn(body);
        given(journalService.createNoteLogEntry(now, title, body)).willReturn(
                noteLogEntry);
        //when
        handler.handle(args);
        //then
        verify(journalService).createNoteLogEntry(now, title, body);
    }

}

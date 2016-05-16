package net.kemitix.journal.shell.commands;

import lombok.val;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import net.kemitix.journal.LogEntryGlyphs;
import net.kemitix.journal.TypeSafeMap;
import net.kemitix.journal.model.LogEntry;
import net.kemitix.journal.service.JournalService;

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
    private TypeSafeMap applicationState;

    @Mock
    private LogEntryGlyphs glyphs;

    @Mock
    private LogEntry logEntry1;

    @Mock
    private LogEntry logEntry2;

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
        given(applicationState.get("selected date",
                LocalDate.class)).willReturn(Optional.of(now));
        val logEntries = new ArrayList<LogEntry>();
        logEntries.add(logEntry1);
        logEntries.add(logEntry2);
        given(journalService.getLogs(now)).willReturn(logEntries);
        given(glyphs.getGlyph(logEntry1)).willReturn("a");
        given(glyphs.getGlyph(logEntry2)).willReturn("b");
        given(logEntry1.getTitle()).willReturn("entry 1");
        given(logEntry2.getTitle()).willReturn("entry 2");
        //when
        val result = handler.handle(new HashMap<>());
        //then
        val softly = new SoftAssertions();
        softly.assertThat(result).contains("1: a entry 1");
        softly.assertThat(result).contains("2: b entry 2");
        softly.assertAll();
    }

}

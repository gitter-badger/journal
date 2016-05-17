package net.kemitix.journal.shell.commands;

import lombok.val;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.kemitix.journal.service.JournalService;
import net.kemitix.journal.shell.AbstractCommandHandler;
import net.kemitix.journal.shell.MultiLinePromptedTextReader;
import net.kemitix.journal.shell.ShellState;

/**
 * Command handler for adding a note log entry.
 *
 * @author pcampbell
 */
@Service
class NoteCreateHandler extends AbstractCommandHandler {

    private static final List<String> ALIASES = Arrays.asList("note create",
            "create note");

    private static final String PARAMETER_REGEX = "(?<title>.+)";

    private static final List<String> PARAMETER_NAMES
            = Collections.singletonList("title");

    private final ShellState shellState;

    private final PrintWriter writer;

    private final JournalService journalService;

    private final MultiLinePromptedTextReader promptedTextReader;

    @Inject
    NoteCreateHandler(
            final ShellState shellState, final PrintWriter writer,
            final JournalService journalService,
            final MultiLinePromptedTextReader promptedTextReader) {
        this.shellState = shellState;
        this.writer = writer;
        this.journalService = journalService;
        this.promptedTextReader = promptedTextReader;
    }

    @Override
    public List<String> getAliases() {
        return ALIASES;
    }

    @Override
    public Optional<String> getParameterRegex() {
        return Optional.of(PARAMETER_REGEX);
    }

    @Override
    public List<String> getParameterNames() {
        return PARAMETER_NAMES;
    }

    @Override
    public String getDescription() {
        return "Creates a new note";
    }

    @Override
    public void handle(final Map<String, String> args) {
        val title = args.get("title");
        writer.println("Create Note: " + title);
        String body = promptedTextReader.readText("Enter body");
        val note = journalService.createNoteLogEntry(
                shellState.getDefaultDate(), title, body);
        writer.println(String.format("Note created: %d - %s", note.getId(),
                note.getTitle()));
    }
}

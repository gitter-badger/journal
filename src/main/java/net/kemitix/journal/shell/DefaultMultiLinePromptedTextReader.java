package net.kemitix.journal.shell;

import lombok.val;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

/**
 * Default implementation of the multi-line reader.
 *
 * @author pcampbell
 */
@Component
class DefaultMultiLinePromptedTextReader
        implements MultiLinePromptedTextReader {

    private final PrintWriter writer;

    private final BufferedReader reader;

    @Inject
    DefaultMultiLinePromptedTextReader(
            final PrintWriter writer, final BufferedReader reader) {
        this.writer = writer;
        this.reader = reader;
    }

    @Override
    public String readText(final String prompt) {
        writer.println(prompt + ": (. on a line by itself to finish)");
        boolean reading = true;
        List<String> lines = new ArrayList<>();
        while (reading) {
            try {
                val line = reader.readLine();
                if (".".equals(line)) {
                    reading = false;
                } else {
                    lines.add(line);
                }
            } catch (IOException e) {
                throw new CommandHandlerException("Can't read from input", e);
            }
        }
        return String.join("\n", lines);
    }
}

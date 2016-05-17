package net.kemitix.journal.shell;

import lombok.val;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.stereotype.Component;

import net.kemitix.journal.TypeSafeMap;

/**
 * The Command Prompt. Reads input and looks up and dispatches to commands to
 * implement the command.
 *
 * @author pcampbell
 */
@Component
public class CommandPrompt {

    public static final String SELECTED_DATE = "selected-date";

    private final CommandRouter commandRouter;

    private final TypeSafeMap applicationState;

    private final BufferedReader reader;

    private final PrintWriter writer;

    @Inject
    CommandPrompt(
            final CommandRouter commandRouter,
            final TypeSafeMap applicationState,
            final BufferedReader commandLineReader,
            final PrintWriter commandLineWriter) {
        this.commandRouter = commandRouter;
        this.applicationState = applicationState;
        this.reader = commandLineReader;
        this.writer = commandLineWriter;
    }

    /**
     * Runs the command line interface.
     */
    @PostConstruct
    public void run() {
        writer.println("Ctrl-D to quit");
        applicationState.put("running", true, Boolean.class);
        applicationState.put("selected date", LocalDate.now(), LocalDate.class);
        try {
            while (applicationState.get("running", Boolean.class).isPresent()) {
                writer.print("> ");
                writer.flush();
                val input = reader.readLine();
                if (input == null) {
                    applicationState.remove("running");
                    writer.println();
                    writer.println("Exiting...");
                } else if (input.length() > 0) {
                    // look up command
                    val commandMapping = commandRouter.selectCommand(input);
                    // dispatch command
                    if (commandMapping.isPresent()) {
                        val mapping = commandMapping.get();
                        try {
                            mapping.getHandler().handle(mapping.getArgs());
                        } catch (CommandHandlerException e) {
                            writer.println("Error: " + e.getMessage());
                        }
                    } else {
                        writer.println("Not a recognised command!");
                    }
                }
            }
        } catch (IOException e) {
            writer.println("Aborting: " + e.getMessage());
        }
    }
}

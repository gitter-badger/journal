package net.kemitix.journal.shell;

import lombok.val;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.stereotype.Component;

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

    private final ShellState shellState;

    private final BufferedReader reader;

    private final PrintWriter writer;

    @Inject
    CommandPrompt(
            final CommandRouter commandRouter, final ShellState shellState,
            final BufferedReader commandLineReader,
            final PrintWriter commandLineWriter) {
        this.commandRouter = commandRouter;
        this.shellState = shellState;
        this.reader = commandLineReader;
        this.writer = commandLineWriter;
    }

    /**
     * Runs the command line interface.
     */
    @PostConstruct
    public void run() {
        writer.println("Ctrl-D to quit");
        try {
            while (!shellState.isShuttingDown()) {
                writer.print("> ");
                writer.flush();
                val input = reader.readLine();
                if (input == null) {
                    shellState.shutdown();
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

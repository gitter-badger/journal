package net.kemitix.journal.shell;

import lombok.val;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

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
class CommandPrompt {

    private final List<CommandHandler> handlerList;

    private Map<String, CommandHandler> handlers;

    static final String STATE = "state";

    static final String STATE_RUNNING = "running";

    static final String STATE_EXITING = "exiting";

    @Inject
    CommandPrompt(final List<CommandHandler> handlerList) {
        this.handlerList = handlerList;
    }

    @PostConstruct
    public void run() throws Exception {
        handlers = new HashMap<>();
        handlerList.stream()
                   .forEach(h -> h.getCommands().stream().forEach(command -> {
                       if (handlers.containsKey(command)) {
                           throw new RuntimeException(
                                   "Duplicate command implementations found: '"
                                           + command + "'");
                       }
                       handlers.put(command, h);
                   }));
        try (val br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Ctrl-D to quit");
            val context = new HashMap<String, String>();
            context.put(STATE, STATE_RUNNING);
            while (STATE_RUNNING.equals(context.get(STATE))) {
                System.out.print("> ");
                val input = br.readLine();
                if (input == null) {
                    context.put(STATE, STATE_EXITING);
                    System.out.println();
                    System.out.println("Exiting...");
                } else if (input.length() > 0) {
                    // look up command
                    Queue<String> args = new ArrayDeque<>(
                            Arrays.asList(input.split(" ")));
                    val command = args.remove();
                    // dispatch command
                    if (handlers.containsKey(command)) {
                        System.out.println(handlers.get(command)
                                                   .handle(context, command,
                                                           args));
                    } else {
                        System.out.println(
                                "Command '" + command + "' is not recognised!");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Aborting: " + e.getMessage());
        }
    }
}

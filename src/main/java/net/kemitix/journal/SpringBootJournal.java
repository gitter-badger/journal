package net.kemitix.journal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application Main Class.
 *
 * @author pcampbell
 */
@SpringBootApplication
@SuppressWarnings("hideutilityclassconstructor")
public class SpringBootJournal {

    /**
     * Application Main Methods.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(SpringBootJournal.class, args);
    }
}
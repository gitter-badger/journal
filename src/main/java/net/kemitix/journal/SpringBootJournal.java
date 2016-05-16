package net.kemitix.journal;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties
        .EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Application Main Class.
 *
 * @author pcampbell
 */
@SpringBootApplication
@EnableConfigurationProperties
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

    @Bean
    BufferedReader commandLineReader() {
        return new BufferedReader(new InputStreamReader(System.in, UTF_8));
    }

    @Bean
    PrintWriter writer() {
        return new PrintWriter(System.out);
    }

    @Bean
    TypeSafeMap applicationState() {
        return new TypeSafeHashMap();
    }
}

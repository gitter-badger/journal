package net.kemitix.journal.shell;

import lombok.val;

import java.time.LocalDate;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import net.kemitix.journal.LogEntryList;
import net.kemitix.journal.TypeSafeMap;
import net.kemitix.journal.model.LogEntry;

/**
 * An implementation of the ShellState interface using a {@link TypeSafeMap} for
 * storage.
 *
 * @author pcampbell
 */
@Component
public class TypeSafeMapShellState implements ShellState {

    private final TypeSafeMap map;

    /**
     * Constructor.
     *
     * @param map the map for storage
     */
    @Inject
    public TypeSafeMapShellState(final TypeSafeMap map) {
        this.map = map;
    }

    @Override
    public void shutdown() {
        map.put("shutting-down", Boolean.TRUE, Boolean.class);
    }

    @Override
    public boolean isShuttingDown() {
        return map.get("shutting-down", Boolean.class).isPresent();
    }

    @Override
    public void setDefaultDate(final LocalDate date) {
        map.put("default-date", date, LocalDate.class);
    }

    @Override
    public LocalDate getDefaultDate() {
        return map.get("default-date", LocalDate.class)
                  .orElseGet(LocalDate::now);
    }

    @Override
    public void setLogEntryList(final LogEntryList list) {
        map.put("log-entry-list", list, LogEntryList.class);
    }

    @Override
    public Optional<LogEntry> getLogEntryFromList(final Integer index) {
        val optionalList = map.get("log-entry-list", LogEntryList.class);
        if (optionalList.isPresent()) {
            return Optional.ofNullable(optionalList.get().get(index));
        }
        return Optional.empty();
    }
}

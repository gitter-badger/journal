package net.kemitix.journal.shell;

import java.util.Optional;

/**
 * Typesafe Map.
 *
 * @author pcampbell
 */
public interface TypeSafeMap {

    /**
     * Stores a value and it's specific type.
     *
     * @param key   the key
     * @param value the value
     * @param type  the type of the value
     * @param <T>   the type of the value
     */
    <T> void put(
            String key, T value, Class<T> type);

    /**
     * Retrieves a type safe value for the key.
     *
     * @param key          the key
     * @param requiredType the type to be returned
     * @param <T>          the type to be retutned
     *
     * @return the value
     */
    @SuppressWarnings("unchecked")
    <T> Optional<T> get(String key, Class<T> requiredType);

    /**
     * Removes the key and value from the map.
     *
     * @param key the key to remove
     */
    void remove(String key);
}

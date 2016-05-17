package net.kemitix.journal;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

/**
 * Implementation of {@link TypeSafeMap} using HashMaps.
 *
 * @author pcampbell
 */
@Component
public class TypeSafeHashMap implements TypeSafeMap {

    private Map<String, Object> values = new HashMap<>();

    private Map<String, Class> types = new HashMap<>();

    @Override
    public <T> void put(
            final String key, final T value, final Class<T> type) {
        values.put(key, value);
        types.put(key, type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Optional<T> get(final String key, final Class<T> requiredType) {
        if (!values.containsKey(key)) {
            return Optional.empty();
        }
        if (requiredType.isAssignableFrom(types.get(key))) {
            return Optional.ofNullable((T) values.get(key));
        }
        throw new IllegalArgumentException(
                "required type does not match value found");
    }

    @Override
    public void remove(final String key) {
        values.remove(key);
        types.remove(key);
    }
}

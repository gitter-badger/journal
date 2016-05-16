package net.kemitix.journal;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

/**
 * .
 *
 * @author pcampbell
 */
public class TypeSafeHashMapTest {

    private TypeSafeMap map;

    @Before
    public void setUp() {
        map = new TypeSafeHashMap();
    }

    @Test
    public void canAddAnObjectAndRetrieveIt() {
        //given
        Object o = new Object();
        //when
        map.put("key", o, Object.class);
        //then
        final Optional<Object> result = map.get("key", Object.class);
        assertThat(result.isPresent()).isTrue();
        if (result.isPresent()) {
            assertThat(result.get()).isSameAs(o);
        }
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void throwsExceptionWhenGetWithWrongRequiredType() {
        //given
        Alpha a = new Alpha();
        map.put("alpha", a, Alpha.class);
        exception.expect(IllegalArgumentException.class);
        //when
        map.get("alpha", Beta.class);
    }

    @Test
    public void getAnEmptyOptional() {
        assertThat(map.get("foo", Object.class)).isSameAs(Optional.empty());
    }

    @Test
    public void removeValue() {
        //given
        map.put("key", new Alpha(), Alpha.class);
        //when
        assertThat(map.get("key", Alpha.class).isPresent()).isTrue();
        map.remove("key");
        assertThat(map.get("key", Alpha.class).isPresent()).isFalse();
    }

    private class Alpha {

    }

    private class Beta {

    }
}

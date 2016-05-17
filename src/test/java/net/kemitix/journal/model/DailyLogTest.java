package net.kemitix.journal.model;

import lombok.val;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

/**
 * Tests for {@link DailyLog}.
 *
 * @author pcampbell
 */
public class DailyLogTest {

    @Test
    public void createWithDefaultConstructor() {
        //when
        val dailyLog = new DailyLog();
        //then
        assertThat(dailyLog.getDate()).isNull();
        assertThat(dailyLog.getEntries()).isEmpty();
    }

    @Test
    public void createWithDate() {
        //given
        val now = LocalDate.now();
        //when
        val dailyLog = new DailyLog(now);
        //then
        assertThat(dailyLog.getDate()).isEqualTo(now);
    }
}

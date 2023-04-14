package io.github.devsong.base.entity.util;

import io.github.devsong.base.entity.split.SplitIdUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class SplitIdUtilTest {
    static final Long SPLIT_KEY = 109L;

    @Test
    void test_create_util() {
        SplitIdUtil s = new SplitIdUtil();
        assertThat(s).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(longs = {109L, 40004205L, 20993539949L, 2561281133L})
    void should_calc_correct_new_id(long splitKey) {
        long origin = 1 << 4;
        long l = SplitIdUtil.calcNewId(origin, splitKey);
        assertThat(l).isEqualTo((origin << SplitIdUtil.SPLIT_BIT) + SPLIT_KEY);
    }

    @ParameterizedTest
    @ValueSource(longs = {109L, 40004205L, 20993539949L, 2561281133L})
    void should_have_same_last_byte(long id) {
        long key = SplitIdUtil.calcLastByte(id);
        assertThat(key).isEqualTo(SPLIT_KEY);
    }
}

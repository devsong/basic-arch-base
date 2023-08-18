package io.github.devsong.base.common.util;

import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtil extends NumberUtils {

    /**
     * 转换工具
     *
     * @param val
     * @param scale
     * @return
     */
    public static String fen2yuan(long val, int scale) {
        return new BigDecimal(val)
                .divide(new BigDecimal(100))
                .setScale(scale, RoundingMode.HALF_UP)
                .toString();
    }

    /**
     * 转换工具
     *
     * @param val
     * @param scale
     * @return
     */
    public static long yuan2fen(String val, int scale) {
        if (val == null) {
            throw new NullPointerException("val is null");
        }
        return new BigDecimal(val)
                .multiply(new BigDecimal(100))
                .setScale(scale, RoundingMode.HALF_UP)
                .longValue();
    }
}

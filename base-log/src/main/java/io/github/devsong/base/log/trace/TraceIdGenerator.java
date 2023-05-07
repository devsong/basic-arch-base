package io.github.devsong.base.log.trace;

import java.security.SecureRandom;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhisong.guan
 */
public class TraceIdGenerator {

    private static final char[] CHARS = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };

    private static final SecureRandom RND = new SecureRandom();

    private static final AtomicLong INC = new AtomicLong();

    private static final AtomicLong SPANID_INC = new AtomicLong(1);

    public static String getTraceId(IdGenEnum idGenEnum, String prefix) {
        String traceId;
        switch (idGenEnum) {
            case UUID_LONG:
                traceId = String.valueOf(getTraceIdByUuid());
                break;
            case CURRENT_TIME:
                traceId = getTraceIdByCurrentTime(prefix);
                break;
            case UUID:
            default:
                traceId = getNormalUuid();
                break;
        }
        return traceId;
    }

    /**
     * 时间戳+ 自增位+ 随机数
     *
     * @return 19位字符串
     */
    private static String getTraceIdByCurrentTime(String prefix) {
        return StringUtils.defaultString(prefix) + Long.toHexString(System.currentTimeMillis()) + getInc() + random(4);
    }

    /**
     * UUID_LONG
     *
     * @return 19位long
     */
    private static long getTraceIdByUuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.getLeastSignificantBits() ^ uuid.getMostSignificantBits();
    }

    /**
     * 普通uuid
     *
     * @return uuid string
     */
    private static String getNormalUuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }

    private static String random(int count) {
        if (count <= 0) {
            return "";
        }

        char[] result = new char[count];

        for (int i = 0; i < count; i++) {
            result[i] = CHARS[RND.nextInt(CHARS.length)];
        }
        return new String(result);
    }

    private static String getInc() {
        long i = INC.addAndGet(1);
        return String.format("%04d", (int) (i % 10000));
    }

    public static String getSpanId() {
        return String.valueOf(SPANID_INC.getAndIncrement());
    }
}

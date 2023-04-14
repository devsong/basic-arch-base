package io.github.devsong.base.entity.split;

/**
 * 分库工具类
 *
 * @author guanzhisong
 */
public class SplitIdUtil {

    /**
     * 分库键所占的bit数,通常一个字节的量即够用
     */
    public static final int SPLIT_BIT = 8;

    public static final int LAST_BYTE_SHIFT = (1 << SPLIT_BIT) - 1;

    /**
     * 会员系统db/table拆分数量
     */
    public static final int MEMBER_DB_SPLIT = 1 << 2;

    public static final int MEMBER_TABLE_SPLIT = 1 << 2;

    /**
     * 订单系统db/table拆分数量
     */
    public static final int ORDER_DB_SPLIT = 1 << 4;

    public static final int ORDER_TABLE_SPLIT = 1 << 2;

    /**
     * 支付系统db/table拆分数量
     */
    public static final int PAYMENT_DB_SPLIT = 1 << 2;

    public static final int PAYMENT_TABLE_SPLIT = 1 << 2;

    /**
     * 计算新的ID
     *
     * @param id
     * @param splitId
     * @return
     */
    public static long calcNewId(long id, long splitId) {
        return (id << SPLIT_BIT) + calcLastByte(splitId);
    }

    /**
     * 计算最后一byte数据
     *
     * @param id
     * @return
     */
    public static long calcLastByte(long id) {
        return id & LAST_BYTE_SHIFT;
    }
}

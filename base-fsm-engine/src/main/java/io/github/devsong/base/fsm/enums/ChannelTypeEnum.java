package io.github.devsong.base.fsm.enums;

/**
 * 渠道类型(流量渠道来源)
 * date:  2024/6/15
 * author:guanzhisong
 */
public enum ChannelTypeEnum {
    OWN(0, "自有"),

    DOUYIN(1, "抖音"),

    WECHAT(2, "微信"),

    TAOBAO(3, "淘宝"),

    JD(4, "京东"),

    PDD(5, "拼多多"),
    ;

    int code;

    String msg;

    ChannelTypeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

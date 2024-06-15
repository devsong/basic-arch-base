package io.github.devsong.base.fsm.enums;

/**
 * 业务类型(自由业务逻辑类型)
 * date:  2024/6/15
 * author:guanzhisong
 */
public enum BusinessTypeEnum {
    DIGITAL(101, "数码"),

    HOTEL(102, "酒店"),

    AIR_TICKETS(103, "机票"),

    CLOTHING(104, "服装"),

    DEPARTMENT(105, "百货"),

    BOOK(106, "图书"),

    COMPUTER(107, "电脑"),
    ;

    int code;

    String msg;

    BusinessTypeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

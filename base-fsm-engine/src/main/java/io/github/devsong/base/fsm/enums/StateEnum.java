package io.github.devsong.base.fsm.enums;

/**
 * date:  2024/6/15
 * author:guanzhisong
 */
public enum StateEnum {

    /**
     * 虚拟状态,状态不产生变更
     */
    VIRTUAL(-1, "虚拟状态"),

    INIT(0, "初始化"),

    NEW(1000, "新单"),

    WAIT_PAYMENT(2000, "待支付"),

    PAID(3000, "支付成功"),

    PACKAGING(4000, "打包"),

    DISPATCHING(5000, "配送"),

    RECEIVE(6000, "签收"),

    FINISH(8000, "完成"),

    CANCEL(9000, "取消"),

    CLOSE(9100, "关闭"),
    ;

    int code;

    String msg;

    StateEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

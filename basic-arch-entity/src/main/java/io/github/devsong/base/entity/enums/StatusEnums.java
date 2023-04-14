package io.github.devsong.base.entity.enums;

import lombok.Getter;

@Getter
public enum StatusEnums {
    ENABLE(0, "启用"),

    UNENABLE(1, "禁用"),

    DELETE(2, "删除")

    ;

    int code;
    String desc;

    StatusEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}

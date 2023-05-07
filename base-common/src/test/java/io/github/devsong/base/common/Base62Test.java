package io.github.devsong.base.common;

import io.github.devsong.base.common.util.Base62;
import org.junit.jupiter.api.Test;

class Base62Test {

    @Test
    public void testEncode() {
        long id = 12121221;
        System.out.println(Base62.fromBase10(id));
    }

    @Test
    public void testDecode() {
        String code = "Y1rJ";
        System.out.println(Base62.toBase10(code));
    }
}

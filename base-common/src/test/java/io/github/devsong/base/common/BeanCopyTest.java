package io.github.devsong.base.common;

import com.google.common.collect.Lists;
import io.github.devsong.base.common.util.BeanUtil;
import io.github.devsong.base.common.util.JsonUtil;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.List;

class BeanCopyTest {

    @Test
    void testCopyObj() {
        Foo foo = new Foo();
        System.out.println(foo);
        Bar bar = new Bar();
        BeanUtil.copyProperties(foo, bar);
        System.out.println(bar);
    }

    @Test
    void testCopyList() {
        List<Foo> listSrc = Lists.newArrayList(new Foo());
        List<Bar> listDesc = Lists.newArrayListWithCapacity(listSrc.size());
        listDesc = BeanUtil.copyList(listSrc, Bar.class);
        System.out.println(JsonUtil.toJSONString(listDesc));
    }

}

@Data
class Foo {
    private int id;
    private String name;
    private String idFoo;

    public Foo() {
        this.id = 0;
        this.name = "foo";
        this.idFoo = "idfoo";
    }
}

@Data
class Bar {
    private int id;
    private String name;
    private String idBar;

    public Bar() {
    }
}

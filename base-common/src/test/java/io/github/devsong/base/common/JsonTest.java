package io.github.devsong.base.common;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.vdurmont.emoji.EmojiParser;
import io.github.devsong.base.common.util.JsonUtil;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;

class JsonTest {

    @Test
    void testToJson() {
        FooJson foo = new FooJson(1, "test");
        System.out.println(JsonUtil.toJSONString(foo));
    }

    @Test
    void testFromJson() {
        String json = "{\"id\":1,\"name\":\"test\"}";
        FooJson obj = JsonUtil.parseObject(json, FooJson.class);
        assertThat(obj.getId()).isEqualTo(1);
    }

    @Test
    void testParseList() {
        List<FooJson> list = Lists.newArrayList(new FooJson(1, "test"), new FooJson(2, "test2"));
        list = JsonUtil.parseList(JsonUtil.toJSONString(list), FooJson.class);
        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    void testParseList2() {
        List<FooJson> list = Lists.newArrayList(new FooJson(1, "test"), new FooJson(2, "test2"));
        list = JsonUtil.parseObject(JsonUtil.toJSONString(list), new TypeReference<List<FooJson>>() {});
        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    void testEmoji() {
        String nickName = "地点弄错了，我们是去考试的，地点搞错了🤣能麻烦老板让我们免费取消一下么🤣🤣🤣🤣❤️️❤️";
        System.out.println(nickName);
        System.out.println(filterEmoji(nickName));
    }

    private static String filterEmoji(String nick_name) {
        // nick_name 所获取的用户昵称
        if (nick_name == null) {
            return nick_name;
        }
        return EmojiParser.removeAllEmojis(nick_name);
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class FooJson {
    private int id;
    private String name;

    @Override
    public String toString() {
        return JsonUtil.toJSONString(this);
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class BarJson {
    private int id;
    private String name;
}

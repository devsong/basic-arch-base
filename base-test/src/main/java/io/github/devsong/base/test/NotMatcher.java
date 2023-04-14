package io.github.devsong.base.test;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.util.Objects;

/**
 * date:  2023/4/8
 * author:guanzhisong
 */
public class NotMatcher<T> extends BaseMatcher {
    T code;

    public NotMatcher(T code) {
        this.code = code;
    }

    @Override
    public boolean matches(Object actual) {
        return Objects.equals(this.code, actual);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("not " + this.code);
    }
}

package io.github.devsong.base.test;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.util.Objects;

/**
 * date:  2023/4/8
 * author:guanzhisong
 */
public class NotMatcher<T> extends BaseMatcher {
    T result;

    public NotMatcher(T result) {
        this.result = result;
    }

    @Override
    public boolean matches(Object actual) {
        return !Objects.equals(this.result, actual);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("not " + this.result);
    }
}

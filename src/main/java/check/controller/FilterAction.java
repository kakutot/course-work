package check.controller;

import java.util.List;

public interface FilterAction<T> {
    List<T> apply(String filter);
}

package org.sadnatau.data;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Provides an Object Oriented API of the {@link org.sadnatau.relc.data.DataProvider}
 * @author Eli Polonsky
 * @since 0.1
 */
public interface DataStore<T> {

    Set<T> query(T template, List<String> resultFields) throws Exception;

    void insert(final T data) throws Exception;

    void remove(final T template) throws Exception;

    void update(final T matchingData, Map<String, Object> fieldsToUpdate) throws Exception;

    void empty();
}

package org.sadnatau.classloading;

import com.google.common.base.Throwables;
import org.sadnatau.relc.data.DataProvider;
import org.sadnatau.relc.util.SemanticError;

import java.lang.reflect.Method;
import java.util.List;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class ReflectionDelegatingDataProvider implements DataProvider {

    private Object delegateTo;

    public ReflectionDelegatingDataProvider(final Object obj) {
        this.delegateTo = obj;
    }

    @Override
    public List<List<String>> query(final List<String> query,
                                    final List<String> resultColumns) {

        try {
            Method method = delegateTo.getClass().getMethod("query", List.class, List.class);
            return (List<List<String>>) method.invoke(delegateTo, query, resultColumns);
        } catch (Throwable t) {
            throw Throwables.propagate(t);
        }

    }

    @Override
    public void remove(final List<String> tuple) {

        try {
            Method method = delegateTo.getClass().getMethod("remove", List.class);
            method.invoke(delegateTo, tuple);
        } catch (Throwable t) {
            throw Throwables.propagate(t);
        }
    }

    @Override
    public void update(List<String> matchingTuple, List<String> updateValues) throws SemanticError {

        try {
            Method method = delegateTo.getClass().getMethod("update", List.class, List.class);
            method.invoke(delegateTo, matchingTuple, updateValues);
        } catch (Throwable t) {
            throw Throwables.propagate(t);
        }
    }

    @Override
    public void empty() {

        try {
            Method method = delegateTo.getClass().getMethod("empty");
            method.invoke(delegateTo);
        } catch (Throwable t) {
            throw Throwables.propagate(t);
        }
    }

    @Override
    public void insert(final List<String> tuple) {

        try {
            Method method = delegateTo.getClass().getMethod("insert", List.class);
            method.invoke(delegateTo, tuple);
        } catch (Throwable t) {
            throw Throwables.propagate(t);
        }
    }
}

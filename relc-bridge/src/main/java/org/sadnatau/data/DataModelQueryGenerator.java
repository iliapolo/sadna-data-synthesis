package org.sadnatau.data;

import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class DataModelQueryGenerator {

    /**
     * Generates a query in the format acceptable by the compiler from an object template.
     * @param template - Object template to generate query from.
     * @return a string representing the query.
     * @throws Exception .
     */
    public final List<String> generate(final Object template) throws Exception {

        List<String> query = new ArrayList<String>();

        Field[] fields = template.getClass().getDeclaredFields();
        for (Field  field : fields) {
            Object value = PropertyUtils.getProperty(template, field.getName());
            if (value != null) {
                String column = field.getName() + ":" + value;
                query.add(column);
            }
        }
        return query;
    }
}

package org.sadnatau.relwiki.data;

import org.sadnatau.relwiki.model.QueryTemplate;
import org.sadnatau.relwiki.model.SearchResultData;

/**
 * Interface for querying data based on the on the relations decomposition.
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public interface RelationalDataProvider {

    String getPageDate(String pageName, String author);

    SearchResultData get(QueryTemplate template);

}

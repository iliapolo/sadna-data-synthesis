package org.sadnatau.relwiki.data;

import org.sadnatau.relwiki.model.Comments;
import org.sadnatau.relwiki.model.Page;
import org.sadnatau.relwiki.model.QueryTemplate;
import org.sadnatau.relwiki.model.SearchResult;

/**
 * Interface for querying data based on the on the relations decomposition.
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public interface RelationalDataProvider {

    Page getPage(String pageName);

    SearchResult get(QueryTemplate template);

    Comments getComments(String pageTitle);

    void savePageContent(Page currentPageState);

}

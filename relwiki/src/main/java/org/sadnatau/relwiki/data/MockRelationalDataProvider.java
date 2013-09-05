package org.sadnatau.relwiki.data;

import org.sadnatau.relwiki.model.QueryTemplate;
import org.sadnatau.relwiki.model.SearchResultData;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Mock for retrieving data.
 *
 * @author Eli Polonsky
 * @since 0.1
 */
@Component
public class MockRelationalDataProvider implements RelationalDataProvider {

    private static final String[] ALL_AUTHORS = {"Tom Clancy", "Stephen King", "John Steinbeck", "J.D Salinger",
            "Franz Kafka", "Brian Green"};

    private static final String[] ALL_KEYWORDS = {"Space", "Drama", "Love", "60's", "String Theory", "Cemetery",
            "Prague", "Zoe", "Mystery"};

    private static final String[] ALL_PAGES = {"Me, Myself and Physics", "A trip to netherlands", "Lonely as i go",
            "Where are the proportions?", "Could this be right?"};

    @Override
    public String getPageContent(final String pageName) {
        return "This is the page data for page " + pageName;
    }

    @Override
    public SearchResultData get(final QueryTemplate template) {

        SearchResultData result = new SearchResultData();
        result.setKeywords(Arrays.asList(ALL_KEYWORDS));
        result.setPages(Arrays.asList(ALL_PAGES));
        result.setAuthors(Arrays.asList(ALL_AUTHORS));
        return result;

    }
}

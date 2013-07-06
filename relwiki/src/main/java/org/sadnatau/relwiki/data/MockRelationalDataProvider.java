package org.sadnatau.relwiki.data;

import org.apache.commons.lang.StringUtils;
import org.sadnatau.relwiki.model.QueryTemplate;
import org.sadnatau.relwiki.model.SearchResultData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public String getPageDate(String pageName, String author) {
        return "This is the page data for page " + pageName + " by " + author;
    }

    @Override
    public SearchResultData get(final QueryTemplate template) {

        SearchResultData result = new SearchResultData();

        String author = template.getAuthor();
        if (StringUtils.isBlank(author)) {
            // No author filter was applied. display all authors
            result.setAuthors(Arrays.asList(ALL_AUTHORS));
        } else if (author.equals("Tom Clancy")) {

            List<String> keywords = new ArrayList<>();
            keywords.add(ALL_KEYWORDS[0]);
            keywords.add(ALL_KEYWORDS[1]);
            keywords.add(ALL_KEYWORDS[3]);
            keywords.add(ALL_KEYWORDS[5]);

            List<String> pages = new ArrayList<>();
            pages.add(ALL_PAGES[0]);
            pages.add(ALL_PAGES[1]);

            List<String> authors = new ArrayList<>();
            authors.add("Tom Clancy");

            result.setAuthors(authors);
            result.setKeywords(keywords);
            result.setPages(pages);
            return result;

        }
        List<String> keywords = template.getKeywords();
        if (keywords == null || keywords.isEmpty()) {
            result.setKeywords(Arrays.asList(ALL_KEYWORDS));
        } else {
            // TODO - implement a basic filter
        }
        result.setPages(Arrays.asList(ALL_PAGES));


        return result;
    }
}

package org.sadnatau.relwiki.data;

import org.sadnatau.relwiki.model.Comment;
import org.sadnatau.relwiki.model.Comments;
import org.sadnatau.relwiki.model.Page;
import org.sadnatau.relwiki.model.QueryTemplate;
import org.sadnatau.relwiki.model.SearchResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

    private HashMap<String, Page> pages = new HashMap<String, Page>();

    @Override
    public Page getPage(final String pageName) {

        Page page = pages.get(pageName);
        if (page == null) {
            page = new Page();
            // return default page if no one has edited yet
            page.setAuthors(Arrays.asList("Author1, Author2"));
            page.setContent("This is the page data for page " + pageName);
            page.setTitle(pageName);
        }
        return page;
    }

    @Override
    public SearchResult get(final QueryTemplate template) {

        SearchResult result = new SearchResult();
        result.setKeywords(Arrays.asList(ALL_KEYWORDS));
        result.setPages(Arrays.asList(ALL_PAGES));
        result.setAuthors(Arrays.asList(ALL_AUTHORS));
        return result;

    }

    @Override
    public Comments getComments(String pageTitle) {

        List<Comment> commentList = new ArrayList<Comment>();
        for (int i = 1; i < 6; i++) {
            commentList.add(createComment(i));
        }
        Page page = getPage(pageTitle);
        Comments comments = new Comments(page);
        comments.setComments(commentList);
        return comments;
    }

    @Override
    public void savePageContent(final Page currentPageState) {
        String title = currentPageState.getTitle();
        pages.put("title", currentPageState);
    }

    private Comment createComment(int i) {
        Comment comment = new Comment();
        comment.setContent("This is comment" + i);
        comment.setName("Author" + i);
        return comment;
    }
}

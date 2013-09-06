package org.sadnatau.relwiki.controller;


import org.sadnatau.relwiki.data.RelationalDataProvider;
import org.sadnatau.relwiki.model.Comment;
import org.sadnatau.relwiki.model.Comments;
import org.sadnatau.relwiki.model.Page;
import org.sadnatau.relwiki.model.QueryTemplate;
import org.sadnatau.relwiki.model.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Controller for handling search queries.
 *
 * @author Eli Polonsky
 * @since 0.1
 */

@Controller
@SessionAttributes
public class AppController {

    @Autowired
    private RelationalDataProvider relationalDataProvider;

    @RequestMapping(value = "" , method = RequestMethod.GET)
    public ModelAndView welcome(@RequestParam(defaultValue = "") final List<String> keywords,
                                @RequestParam(defaultValue = "") final List<String> authors) {
        return getSearchResultData(null, null, "");
    }

    @RequestMapping(value = "search" , method = RequestMethod.GET)
    public ModelAndView getSearchResultData(@RequestParam(defaultValue = "") final List<String> keywords,
                                            @RequestParam(defaultValue = "") final List<String> authors,
                                            @RequestParam(defaultValue = "") final String pageTitle) {
        QueryTemplate queryTemplate = new QueryTemplate();
        Page page = null;
        ModelAndView modelAndView = new ModelAndView("search");
        if (pageTitle.equals("")) {
            // this means the query does not include the desired page yet.
            // in this case we filter the query parameters
            queryTemplate.setKeywords(keywords);
            queryTemplate.setAuthors(authors);
        } else {
            // this means the desired page was found.
            // revert any search filter and include the page to the jsp
            page = relationalDataProvider.getPage(pageTitle);
        }

        SearchResult searchResult = relationalDataProvider.get(queryTemplate);
        modelAndView.addObject("result", searchResult);
        modelAndView.addObject("page", page);
        return modelAndView;
    }
    @RequestMapping(value = "comments/{pageTitle}" , method = RequestMethod.GET)
    public ModelAndView getComments(@PathVariable("pageTitle") final String pageTitle) {

        Comments comments = relationalDataProvider.getComments(pageTitle);
        ModelAndView modelAndView = new ModelAndView("comments");
        modelAndView.addObject("comments", comments);
        return modelAndView;

    }

    @RequestMapping(value = "comments/{pageTitle}" , method = RequestMethod.POST)
    @ResponseBody public Comment postComment(@RequestBody final Comment comment,
                                            @PathVariable("pageTitle") final String pageTitle) {

        Comment comm = new Comment();
        comm.setName(comment.getName());
        comm.setContent(comment.getContent());
        return comm;
    }
}

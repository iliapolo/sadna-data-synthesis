package org.sadnatau.relwiki.controller;


import org.sadnatau.relwiki.data.RelationalDataProvider;
import org.sadnatau.relwiki.model.QueryTemplate;
import org.sadnatau.relwiki.model.SearchResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Controller for handling search queries.
 *
 * @author Eli Polonsky
 * @since 0.1
 */

@Controller
public class SearchController {

    @Autowired
    private RelationalDataProvider relationalDataProvider;

    @RequestMapping(value = "" , method = RequestMethod.GET)
    public ModelAndView welcome(@RequestParam(defaultValue = "") final List<String> keywords,
                                @RequestParam(defaultValue = "") final String author) {
        return getSearchResultData(null, null);
    }

    @RequestMapping(value = "search" , method = RequestMethod.GET)
    public ModelAndView getSearchResultData(@RequestParam(defaultValue = "") final List<String> keywords,
                                            @RequestParam(defaultValue = "") final List<String> authors) {

        QueryTemplate queryTemplate = new QueryTemplate();
        queryTemplate.setKeywords(keywords);
        queryTemplate.setAuthors(authors);

        SearchResultData searchResultData = relationalDataProvider.get(queryTemplate);
        ModelAndView modelAndView = new ModelAndView("search");
        modelAndView.addObject("result", searchResultData);
        return modelAndView;

    }

    @RequestMapping(value = "search/{page}" , method = RequestMethod.GET)
    public ModelAndView getPage(@PathVariable("page") final String page) {
        // return all keywords and authors so that the user can search again
        QueryTemplate queryTemplate = new QueryTemplate();
        SearchResultData searchResultData = relationalDataProvider.get(queryTemplate);
        ModelAndView modelAndView = new ModelAndView("search");
        modelAndView.addObject("result", searchResultData);

        String pageContent = relationalDataProvider.getPageContent(page);
        modelAndView.addObject("pageContent", pageContent);
        return modelAndView;
    }


}

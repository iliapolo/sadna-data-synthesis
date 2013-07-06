package org.sadnatau.relwiki.controller;


import org.sadnatau.relwiki.data.RelationalDataProvider;
import org.sadnatau.relwiki.model.QueryTemplate;
import org.sadnatau.relwiki.model.SearchResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
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
                                            @RequestParam(defaultValue = "") final String author) {

        QueryTemplate queryTemplate = new QueryTemplate();
        queryTemplate.setKeywords(keywords);
        queryTemplate.setAuthor(author);

        SearchResultData searchResultData = relationalDataProvider.get(queryTemplate);
        ModelAndView modelAndView = new ModelAndView("search");
        modelAndView.addObject("result", searchResultData);
        return modelAndView;

    }


}

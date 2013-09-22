package org.sadnatau.relwiki.controller;

import org.sadnatau.relwiki.data.PageDataStore;
import org.sadnatau.relwiki.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
@Controller
@SessionAttributes
public class SearchController {

    @Autowired
    private PageDataStore pagesDataProvider;

    @RequestMapping(value = "" , method = RequestMethod.GET)
    public ModelAndView welcome(@RequestParam(defaultValue = "") final List<String> keywords,
                                @RequestParam(defaultValue = "") final List<String> authors) throws Exception {
        return search(null, null, "");
    }

    /**
     * Accepts search parameters and filters out the results.
     * @param keywords - keywords to search by.
     * @param authors - authors to search by.
     * @param title - page title.
     * @return The search view injected with the necessary model.
     * @throws Exception .
     */
    @RequestMapping(value = "search" , method = RequestMethod.GET)
    public ModelAndView search(@RequestParam(defaultValue = "") final List<String> keywords,
                               @RequestParam(defaultValue = "") final List<String> authors,
                               @RequestParam(defaultValue = "") final String title) throws Exception {

        // in case page was found.
        Set<String> pageAuthors = null;
        String pageContent = null;
        String pageTitle = null;

        // in case the page was not found.
        Set<String> filteredKeywords = new HashSet<String>();
        Set<String> filteredAuthors = new HashSet<String>();
        Set<String> filteredPages = new HashSet<String>();

        if (!title.equals("")) { // this means the desired page was found.
            pageTitle = title;
            pageAuthors = pagesDataProvider.getAuthors(pageTitle);
            pageContent = pagesDataProvider.getWikiText(pageTitle);

            Collection<Page> pages = pagesDataProvider.getAll();
            for (Page page : pages) {
                filteredAuthors.add(page.getAuthor());
                filteredKeywords.add(page.getKeyword());
                filteredPages.add(page.getTitle());
            }
        } else {
            // TODO implement
            throw new UnsupportedOperationException("This case is not supported yet");
        }

        ModelAndView modelAndView = new ModelAndView("search");
        modelAndView.addObject("filteredKeywords", filteredKeywords);
        modelAndView.addObject("filteredAuthors", filteredAuthors);
        modelAndView.addObject("filteredPages", filteredPages);
        modelAndView.addObject("pageAuthors", pageAuthors);
        modelAndView.addObject("pageContent", pageContent);
        modelAndView.addObject("pageTitle", pageTitle);

        return modelAndView;
    }
}

package org.sadnatau.relwiki.controller;

import org.sadnatau.relwiki.data.PageDataStore;
import org.sadnatau.relwiki.model.Edit;
import org.sadnatau.relwiki.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
@Controller
@SessionAttributes
public class EditController {

    @Autowired
    private PageDataStore pagesDataProvider;

    @RequestMapping(value = "edit/{title}" , method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("title") final String title) throws Exception {

        Set<String> authors = pagesDataProvider.getAuthors(title);
        String content = pagesDataProvider.getWikiText(title);

        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("title", title);
        modelAndView.addObject("authors", authors);
        modelAndView.addObject("wikitext", content);
        return modelAndView;

    }
    @RequestMapping(value = "edit/{title}" , method = RequestMethod.POST)
    @ResponseBody
    public Page savePage(@PathVariable("title") final String title,
                         @RequestBody final Edit edit) {
        return null;
    }

}

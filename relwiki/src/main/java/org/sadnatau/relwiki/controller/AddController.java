package org.sadnatau.relwiki.controller;

import org.sadnatau.relwiki.data.PageDataStore;
import org.sadnatau.relwiki.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
@Controller
@SessionAttributes
public class AddController {

    @Autowired
    private PageDataStore pagesDataProvider;

    /**
     *
     * @return
     */
    @RequestMapping(value = "add" , method = RequestMethod.GET)
    public final ModelAndView addPage() {
        return new ModelAndView("add");
    }

    /**
     *
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "add" , method = RequestMethod.POST)
    @ResponseBody
    public final Page addPage(@RequestBody final Page page) throws Exception {
        pagesDataProvider.add(page);
        return page;
    }

}

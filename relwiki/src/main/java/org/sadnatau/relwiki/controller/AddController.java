package org.sadnatau.relwiki.controller;

import org.sadnatau.relwiki.data.PageDataStore;
import org.sadnatau.relwiki.model.Page;
import org.sadnatau.relwiki.transport.model.NewPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
@Controller
@SessionAttributes
public class AddController {

    private Logger logger = LoggerFactory.getLogger(AddController.class);

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
     * @param newPage
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "add" , method = RequestMethod.POST)
    @ResponseBody
    public final NewPage addPage(@RequestBody final NewPage newPage) throws Exception {

        logger.debug("Adding a new page : " + newPage);

        List<String> keywords = newPage.getKeywords();
        for (String keyword : keywords) {
            // for every keyword, create a page
            Page page = new Page();
            page.setAuthor(newPage.getAuthor());
            page.setTitle(newPage.getTitle());
            page.setKeyword(keyword);
            page.setWikitext(newPage.getWikitext());
            pagesDataProvider.add(page);
        }
        return newPage;
    }

}

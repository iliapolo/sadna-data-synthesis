package org.sadnatau.relwiki.controller;

import org.sadnatau.relwiki.data.CommentDataStore;
import org.sadnatau.relwiki.data.PageDataStore;
import org.sadnatau.relwiki.model.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
@Controller
@SessionAttributes
public class CommentController {

    private Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private PageDataStore pagesDataProvider;

    @Autowired
    private CommentDataStore commentDataProvider;

    @RequestMapping(value = "comments/{title}" , method = RequestMethod.GET)
    public ModelAndView comments(@PathVariable("title") final String title) throws Exception {

        Set<Comment> comments = commentDataProvider.getAll(title);

        List<Comment> sortedComments = new ArrayList<Comment>(comments);

        Collections.sort(sortedComments);
        Set<String> authors = pagesDataProvider.getAuthors(title);

        ModelAndView modelAndView = new ModelAndView("comments");
        modelAndView.addObject("title", title);
        modelAndView.addObject("authors", authors);
        modelAndView.addObject("comments", sortedComments);
        return modelAndView;
    }

    @RequestMapping(value = "comments/{title}" , method = RequestMethod.POST)
    @ResponseBody
    public Comment postComment(@RequestBody final Comment comment,
                               @PathVariable("title") final String title) throws Exception {
        logger.debug("Adding a new comment to page " + title + " : " + comment);
        commentDataProvider.add(comment);
        return comment;
    }

}

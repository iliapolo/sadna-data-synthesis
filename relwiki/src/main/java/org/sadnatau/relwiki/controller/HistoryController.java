package org.sadnatau.relwiki.controller;

import org.sadnatau.relwiki.data.EditDataStore;
import org.sadnatau.relwiki.data.PageDataStore;
import org.sadnatau.relwiki.model.Edit;
import org.sadnatau.relwiki.transport.model.Change;
import org.sadnatau.relwiki.transport.model.Revision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
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
public class HistoryController {

    private Logger logger = LoggerFactory.getLogger(HistoryController.class);

    @Autowired
    private PageDataStore pagesDataProvider;

    @Autowired
    private EditDataStore editsDataProvider;

    @RequestMapping(value = "history/{title}" , method = RequestMethod.GET)
    public ModelAndView history(@PathVariable("title") final String title) throws Exception {

        logger.debug("Showing revision history for page " + title);

        Set<String> authors = pagesDataProvider.getAuthors(title);

        Set<Integer> revisionsForPage = editsDataProvider.getAllRevisions(title);

        // create a revision for each revision
        List<Revision> revisions = new ArrayList<Revision>();
        for (Integer revisionNumber : revisionsForPage) {
            Revision revision = new Revision();
            revision.setNumber(revisionNumber);
            revisions.add(revision);
        }

        // for every revision, get added and removed
        for (Revision revision : revisions) {
            Set<Edit> allForRevision = editsDataProvider.getAllForRevisionAndTitle(revision.getNumber() + "", title);
            // for every edit create a change and add it to the revision
            Set<Change> changes = new HashSet<Change>();
            for (Edit edit : allForRevision) {
                Change change = new Change();
                change.setLineNumber(Integer.valueOf(edit.getLineNumber()));
                change.setText(edit.getText());
                change.setAddedOrRemoved(edit.getAddedOrRemoved());
                changes.add(change);
            }
            revision.setChanges(changes);
        }

        // sort revisions by number
        Collections.sort(revisions);

        ModelAndView modelAndView = new ModelAndView("history");
        modelAndView.addObject("title", title);
        modelAndView.addObject("authors", authors);
        modelAndView.addObject("revisions", revisions);
        return modelAndView;
    }


}

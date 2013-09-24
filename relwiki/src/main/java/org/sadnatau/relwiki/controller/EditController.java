package org.sadnatau.relwiki.controller;

import org.sadnatau.relwiki.data.EditDataStore;
import org.sadnatau.relwiki.data.PageDataStore;
import org.sadnatau.relwiki.model.Edit;
import org.sadnatau.relwiki.model.Page;
import org.sadnatau.relwiki.transport.model.Change;
import org.sadnatau.relwiki.transport.model.PageEdit;
import org.sadnatau.relwiki.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

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
public class EditController {

    @Autowired
    private PageDataStore pagesDataProvider;

    @Autowired
    private EditDataStore editDataProvider;

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
                         @RequestBody final PageEdit pageEdit) throws Exception {

        String existingText = pagesDataProvider.getWikiText(title);
        String currentText = pageEdit.getCurrentText();

        Set<String> newKeywords = new HashSet<String>(pageEdit.getKeywords());

        // create a page entry for all new keywords
        for (String keyword : newKeywords) {
            Page page = new Page();
            page.setTitle(title);
            page.setWikitext(currentText);
            page.setAuthor(pageEdit.getAuthor());
            page.setKeyword(keyword);
            pagesDataProvider.add(page);
        }

        // create new edits for this page
        Set<Change> diff = Utils.findDiff(existingText, currentText);
        String revision = editDataProvider.getAllRevisions(title).size() + 1 + "";
        // for every line added or removed, create an edit
        for (Change change : diff) {
            Edit edit = new Edit();
            edit.setRevision(revision);
            edit.setAddedOrRemoved(change.getAddedOrRemoved());
            edit.setLineNumber(change.getLineNumber() + "");
            edit.setTitle(title);
            edit.setText(change.getText());
            editDataProvider.add(edit);
        }

        // update the text for all page entries with this title

        pagesDataProvider.updateWikiText(title, currentText);
        return new Page(); // dummy
    }
}

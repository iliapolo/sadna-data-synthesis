Relational Data Bridge
=======================

This compiler accepts to inputs:

* 1. [Relations file](src/test/resources/org/sadnatau/data/relation.txt) - this file describes the funtional dependencie of the specific data.
* 2. [Decomposition graph file](src/test/resources/org/sadnatau/data/decompositions.txt) - this file describes the decomposition graph on the given data.

### API ###

    RelationalDataStore<Page> pageRelationalDataStore = new RelationalDataStore<>(relationPath, decompositionPath);
    
    Page page = new Page();
    
    // set page fields...
    page.setTitle("my-titile");
    page.setWikiText("my-text")
    
    // init store
    pageRelationalDataStore.empty();
    
    // data store operations
    pageRelationalDataStore.insert(page);
    pageRelationalDataStore.remove(page);
    
    Page template = new Page();
    template.setTitle("my-titile");
    
    // query all pages with title 'my-title'. populate only the author field in the results.
    pageRelationalDataStore.query(template, Arrays.asList("author"))
    
    Page updateTemplate = new Page();
    updateTemplate.setTitle("my-title");
    
    Map<String, String> updates = new HashMap<String, String>();
    updates.put("wikitext", "updatedText");
    
    // update all pages with title 'my-title' to new wikitext
    pageRelationalDataStore.update(updateTemplate, updates);`
    

The return value is an implmentation of the [DataStore] interface. which is an object oriented API of the [DataProvider] interface you saw in [relc]
    
    



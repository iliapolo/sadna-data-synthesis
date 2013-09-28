Relational Data Bridge
=======================

Designed to provide an object oriented [API](src/main/java/org/sadnatau/bridge/data/DataStore.java) that wrappes the string based [DataProvider](../relc/src/main/java/org/sadnatau/relc/data/DataProvider.java) API.
It compiles the Java code outputed by the RelcCompiler into JVM bytecode, and dinamically returns an implementation of the API to the user.

You can use this to execute data base opertations (insert, query, remove, update, empty) using your domain objects directly.
Each domain object should be a simple POJO. Thas is, a Java object containing private fields, and getter and setter methods for each fields.

You should have a different set of configuration files for every object. for example, these are configuration files for the [Page](src/test/java/org/sadnatau/bridge/data/Page.java) POJO.
* 1. [Relations file](src/test/resources/org/sadnatau/bridge/data/relation.txt) - this file describes the funtional dependencie of the specific data.
* 2. [Decomposition graph file](src/test/resources/org/sadnatau/bridge/data/decompositions.txt) - this file describes the decomposition graph on the given data.

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
    

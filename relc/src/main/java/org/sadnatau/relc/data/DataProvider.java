package org.sadnatau.relc.data;

import org.sadnatau.relc.util.SemanticError;

import java.util.List;

/**
 * Interface for third party application to integrate with.
 * The compiler will output a Java file implementing this interface.
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public interface DataProvider {

    /**
     * Creates an empty data provider.
     */
    void empty();

    /**
     * <h1>Inserts a tuple into the provider.</h1>
     * @param tuple The tuple to add. Tuple size must be the same as declared in the relation.
     *
     * <br><br>
     * Input format - ["title:my page", "author:my", "keyword:test", "wikitext:this is my text"]
     * @throws SemanticError In case a wrong tuple size is given.
     */
    void insert(List<String> tuple) throws SemanticError;

    /**
     * <h1>Queries the provider for tuples with matching values.</h1>
     * @param query The matching tuples query.
     * @param resultColumns The tuples to return in the result.
     *
     * <br><br>
     * For example:
     * <br><br>
     * input - ["title:my-title"], ["author", "keyword"]
     * <br><br>
     * output - [["author:my-author1", "keyword:my-keyword1"], ["author:my-author2", "keyword:my-keyword2"]]
     *
     * @return A list of tuples with the requested results.
     */
    List<List<String>> query(final List<String> query,
                             final List<String> resultColumns) throws SemanticError;

    /**
     * Removes all tuples matching the given tuple from the provider.
     * @param tuple The matching tuple.
     * <br><br>
     * For example:
     * <br><br>
     * input - ["title:my-title", "author:my-author"] will remove all tuples with title 'my-title' and author
     *              'my-author'
     *
     */
    void remove(final List<String> tuple) throws SemanticError;

    /**
     * Updates all tuples matching the matchingTuple with updates specified in updateValues.<br>
     * Note : The matching tuple must only contain the keys of the relation as specified in the relation file.<br>
     *
     * @param matchingTuple The matching criteria.
     * @param updateValues The new values.
     * @throws SemanticError In case something other than relation keys was given in the matchingTuple.
     */
    void update(List<String> matchingTuple, List<String> updateValues) throws SemanticError;
}

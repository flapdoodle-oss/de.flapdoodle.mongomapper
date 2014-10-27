package de.flapdoodle.mongomapper.query;


public class NotQuery<T> implements Query {

    private OperatorQuery<T> invertedQuery;

    private NotQuery(OperatorQuery<T> invertedQuery) {
        this.invertedQuery = invertedQuery;
    }

    @Override
    public String generate() {
        StringBuffer buffer = new StringBuffer("{ $not: ");
        buffer.append(invertedQuery.generate());
        buffer.append("}");
        return buffer.toString();
    }
    
}

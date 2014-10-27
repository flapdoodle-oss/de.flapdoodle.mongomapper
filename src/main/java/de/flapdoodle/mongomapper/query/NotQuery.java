package de.flapdoodle.mongomapper.query;


public class NotQuery<T> implements Query {

    private OperatorQuery<T> invertedQuery;

    private NotQuery(OperatorQuery<T> invertedQuery) {
        this.invertedQuery = invertedQuery;
    }

    @Override
    public String generate() {
        StringBuffer buffer = new StringBuffer("{");
        buffer.append(basicQuery());
        buffer.append("}");
        return buffer.toString();
    }

    @Override
    public String basicQuery() {
        StringBuffer buffer = new StringBuffer("$not: ");
        buffer.append(invertedQuery.generate());
        return buffer.toString();
    }
    
}

package de.flapdoodle.mongomapper.query;

import java.util.List;

import com.google.common.collect.ImmutableList;

import de.flapdoodle.mongomapper.query.operators.NamedMongoDBOperator;

public class SquenceQuery implements Query {

    private final NamedMongoDBOperator operator;

    private final ImmutableList<Query> queries;
    
    private final char subQueryBracketLeft;
    
    private final char subQueryBracketRight;
    
    private final boolean useBasicQueries;

    public SquenceQuery(NamedMongoDBOperator operator, List<Query> queries, char subQueryBracketLeft, char subQueryBracketRight, boolean useBasicQueries) {
        this.operator = operator;
        this.queries = ImmutableList.<Query> copyOf(queries);
        this.subQueryBracketLeft = subQueryBracketLeft;
        this.subQueryBracketRight = subQueryBracketRight;
        this.useBasicQueries = useBasicQueries;
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
        StringBuffer buffer = new StringBuffer();
        buffer.append(operator.toString());
        buffer.append(": ");
        buffer.append(subQueryBracketLeft);

        for (int i = 0; i < queries.size(); i++) {
            Query query = queries.get(i);
            buffer.append(this.useBasicQueries ? query.basicQuery() : query.generate());

            if (i < (queries.size() - 1)) {
                buffer.append(", ");
            }
        }

        buffer.append(subQueryBracketRight);
        return buffer.toString();
    }

}

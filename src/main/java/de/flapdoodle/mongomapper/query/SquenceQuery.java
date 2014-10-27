package de.flapdoodle.mongomapper.query;

import java.util.List;

import com.google.common.collect.ImmutableList;

import de.flapdoodle.mongomapper.query.operators.LogicalSequence;

public class SquenceQuery implements Query {

    private final LogicalSequence operator;

    private final ImmutableList<Query> queries;

    public SquenceQuery(LogicalSequence operator, List<Query> queries) {
        this.operator = operator;
        this.queries = ImmutableList.<Query> copyOf(queries);
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
        buffer.append(": [");

        for (int i = 0; i < queries.size(); i++) {
            Query query = queries.get(i);
            buffer.append(query.generate());

            if (i < (queries.size() - 1)) {
                buffer.append(", ");
            }
        }

        buffer.append("]");
        return buffer.toString();
    }

}

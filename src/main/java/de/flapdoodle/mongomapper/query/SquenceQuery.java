package de.flapdoodle.mongomapper.query;

import java.util.List;

import com.google.common.collect.ImmutableList;

import de.flapdoodle.mongomapper.query.operators.NamedMongoOperator;
import de.flapdoodle.mongomapper.querybuilder.QuerySpecifcation;

public class SquenceQuery implements Query {

    private final NamedMongoOperator operator;

    private final ImmutableList<Query> queries;

    private final QuerySpecifcation querySpec;

    public SquenceQuery(NamedMongoOperator operator, List<Query> queries, QuerySpecifcation querySpec) {
        this.operator = operator;
        this.queries = ImmutableList.<Query> copyOf(queries);
        this.querySpec = querySpec;
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
        buffer.append(querySpec.leftBracket());

        for (int i = 0; i < queries.size(); i++) {
            Query query = queries.get(i);
            buffer.append(this.querySpec.shouldUseSubQueries() ? query.basicQuery() : query.generate());

            if (i < (queries.size() - 1)) {
                buffer.append(", ");
            }
        }

        buffer.append(querySpec.rightBracket());
        return buffer.toString();
    }

}

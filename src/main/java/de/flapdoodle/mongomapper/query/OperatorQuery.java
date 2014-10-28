package de.flapdoodle.mongomapper.query;

import de.flapdoodle.mongomapper.query.operators.Comparison;

public class OperatorQuery<T> implements Query {

    private final T value;

    private final Comparison operator;

    public OperatorQuery(T value, Comparison operator) {
        this.value = value;
        this.operator = operator;
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
        buffer.append(value.toString());

        return buffer.toString();
    }
}

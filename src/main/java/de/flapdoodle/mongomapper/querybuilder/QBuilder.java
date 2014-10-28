package de.flapdoodle.mongomapper.querybuilder;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

import de.flapdoodle.mongomapper.Attr;
import de.flapdoodle.mongomapper.query.AtomicAttributeQuery;
import de.flapdoodle.mongomapper.query.ComposedAttributeQuery;
import de.flapdoodle.mongomapper.query.OperatorQuery;
import de.flapdoodle.mongomapper.query.Query;
import de.flapdoodle.mongomapper.query.SquenceQuery;
import de.flapdoodle.mongomapper.query.operators.ArrayQueryType;
import de.flapdoodle.mongomapper.query.operators.Comparison;
import de.flapdoodle.mongomapper.query.operators.LogicalSequence;
import de.flapdoodle.mongomapper.query.operators.NamedMongoOperator;

public class QBuilder<P extends QBuilder<?>> {

    public static class VoidBuilder extends QBuilder<VoidBuilder> {
        private VoidBuilder() {
            super(LogicalSequence.AND);
        }
    }

    private final Attr<?> attribute;

    private final Optional<P> parent;

    private final List<QBuilder<?>> children;

    private final List<Query> queryParts;

    private final NamedMongoOperator operator;

    private final QuerySpecifcation querySpec;

    public QBuilder(NamedMongoOperator operator) {
        this.parent = Optional.absent();
        this.children = new ArrayList<QBuilder<?>>();
        this.queryParts = new ArrayList<Query>();
        this.operator = operator;
        this.attribute = null;
        this.querySpec = QuerySpecifcation.SEQUENCE;
    }

    public QBuilder(P parent, NamedMongoOperator operator, Attr<?> attribute, QuerySpecifcation querySpec) {
        this.parent = Optional.of(parent);
        this.children = new ArrayList<QBuilder<?>>();
        this.queryParts = new ArrayList<Query>();
        this.operator = operator;
        this.attribute = attribute;
        this.querySpec = querySpec;
    }

    public Attr<?> attribute() {
        return attribute;
    }

    public NamedMongoOperator operator() {
        return this.operator;
    }

    public static QBuilder<VoidBuilder> start() {
        return new QBuilder<VoidBuilder>(LogicalSequence.AND);
    }

    public static QBuilder<VoidBuilder> start(LogicalSequence operator) {
        return new QBuilder<VoidBuilder>(operator);
    }

    public <Type> QBuilder<P> is(Attr<Type> attribute, Type value) {
        queryParts.add(new AtomicAttributeQuery<Type>(attribute, value));
        return this;
    }

    public <Type> QBuilder<P> gt(Attr<Type> attribute, Type value) {
        queryParts.add(new ComposedAttributeQuery<Type>(attribute, new OperatorQuery<Type>(value,
                Comparison.GT)));
        return this;
    }

    public <Type extends ImmutableList<?>> QBuilder<? extends P> size(Attr<Type> attribute, int size) {
        queryParts.add(new ComposedAttributeQuery<Type>(attribute, new OperatorQuery<Integer>(size,
                Comparison.SIZE)));
        return this;
    }

    public ImmutableList<Query> queryParts() {
        return ImmutableList.copyOf(this.queryParts);
    }

    public <X> QBuilder<QBuilder<P>> oneOrMany(Attr<X> attr) {
        // TODO: Prüfe, ob immer der eigene Sequence-Operator übergeben werden
        // soll.
        QBuilder<QBuilder<P>> builder = new QBuilder<QBuilder<P>>(this, ArrayQueryType.ELEMMATCH, attr,
                QuerySpecifcation.ARRAY);
        children.add(builder);

        return builder;
    }

    public <X> QBuilder<QBuilder<P>> all(Attr<X> attr) {
        // TODO: Prüfe, ob immer der eigene Sequence-Operator übergeben werden
        // soll.
        QBuilder<QBuilder<P>> builder = new QBuilder<QBuilder<P>>(this, ArrayQueryType.ALL, attr,
                QuerySpecifcation.ARRAY);
        children.add(builder);

        return builder;
    }

    public <X> QBuilder<QBuilder<P>> size(Attr<X> attr) {
        // TODO: Prüfe, ob immer der eigene Sequence-Operator übergeben werden
        // soll.
        QBuilder<QBuilder<P>> builder = new QBuilder<QBuilder<P>>(this, ArrayQueryType.SIZE, attr,
                QuerySpecifcation.ARRAY);
        children.add(builder);

        return builder;
    }

    public P finish() {
        return this.parent.get();
    }

    Query buildQuery() {
        List<Query> children = new ArrayList<Query>(this.queryParts.size() + this.children.size());
        children.addAll(this.queryParts);

        for (QBuilder<?> child : this.children) {
            children.add(child.buildQuery());
        }

        // TODO: Hier mit Optional arbeiten!
        if (this.attribute != null) {
            return new ComposedAttributeQuery(this.attribute, new SquenceQuery(this.operator, children,
                    querySpec));
        } else {
            return new SquenceQuery(this.operator, children, querySpec);
        }
    }

    public Query asQuery() {
        if (parent.isPresent()) {
            return parent.get().asQuery();
        } else {
            return buildQuery();
        }
    }
}
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
import de.flapdoodle.mongomapper.query.operators.NamedMongoDBOperator;

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

    private final NamedMongoDBOperator operator;

    public QBuilder(NamedMongoDBOperator operator) {
        this.parent = Optional.absent();
        this.children = new ArrayList<QBuilder<?>>();
        this.queryParts = new ArrayList<Query>();
        this.operator = operator;
        this.attribute = null;
    }

    public QBuilder(P parent, NamedMongoDBOperator operator, Attr<?> attribute) {
        this.parent = Optional.of(parent);
        this.children = new ArrayList<QBuilder<?>>();
        this.queryParts = new ArrayList<Query>();
        this.operator = operator;
        this.attribute = attribute;
    }

    public Attr<?> attribute() {
        return attribute;
    }
    
    public NamedMongoDBOperator operator() {
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

    public <X> ArrayQBuilder oneOrMany(Attr<X> attr) {
        // TODO: Prüfe, ob immer der eigene Sequence-Operator übergeben werden
        // soll.
        ArrayQBuilder builder = new ArrayQBuilder(this, operator, attr, ArrayQueryType.ELEMMATCH);
        children.add(builder);

        return builder;
    }

    public <X> ArrayQBuilder all(Attr<X> attr) {
        // TODO: Prüfe, ob immer der eigene Sequence-Operator übergeben werden
        // soll.
        ArrayQBuilder builder = new ArrayQBuilder(this, operator, attr, ArrayQueryType.ALL);
        children.add(builder);

        return builder;
    }

    public <X> ArrayQBuilder size(Attr<X> attr) {
        // TODO: Prüfe, ob immer der eigene Sequence-Operator übergeben werden
        // soll.
        ArrayQBuilder builder = new ArrayQBuilder(this, operator, attr, ArrayQueryType.SIZE);
        children.add(builder);

        return builder;
    }

    public P finish() {
        return this.parent.get();
    }

    Query buildQuery(){
        List<Query> children = new ArrayList<Query>(this.queryParts.size() + this.children.size());
        children.addAll(this.queryParts);

        for (QBuilder<?> child : this.children) {
            children.add(child.buildQuery());
        }

        // TODO: Hier mit Optional arbeiten!
        if (this.attribute != null) {
            return new ComposedAttributeQuery(this.attribute, new SquenceQuery(this.operator, children, '[', ']', false));
        } else {
            return new SquenceQuery(this.operator, children, '[', ']', false);
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
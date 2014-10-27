package de.flapdoodle.mongomapper.querybuilder;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

import de.flapdoodle.mongomapper.Attr;
import de.flapdoodle.mongomapper.ElemQuery;
import de.flapdoodle.mongomapper.query.AtomicAttributeQuery;
import de.flapdoodle.mongomapper.query.Query;
import de.flapdoodle.mongomapper.query.SquenceQuery;
import de.flapdoodle.mongomapper.query.operators.LogicalSequence;

public class QBuilder<P extends QBuilder<?, ?>, T> {

    public static class VoidBuilder extends QBuilder<VoidBuilder, Void> {
        private VoidBuilder() {
            super(LogicalSequence.AND);
        }
    }

    private final Optional<P> parent;

    private final List<QBuilder<?, ?>> children;
    
    private final List<Query> queryParts;

    private final LogicalSequence operator;

    public QBuilder(LogicalSequence operator) {
        this.parent = Optional.absent();
        this.children = new ArrayList<QBuilder<?, ?>>();
        this.queryParts = new ArrayList<Query>();
        this.operator = operator;
    }

    public QBuilder(P parent, LogicalSequence operator) {
        this.parent = Optional.of(parent);
        this.children = new ArrayList<QBuilder<?, ?>>();
        this.queryParts = new ArrayList<Query>();
        this.operator = operator;
    }

    public static QBuilder<VoidBuilder, Void> start() {
        return new QBuilder<VoidBuilder, Void>(LogicalSequence.AND);
    }

    public static QBuilder<VoidBuilder, Void> start(LogicalSequence operator) {
        return new QBuilder<VoidBuilder, Void>(operator);
    }

    public <Type> QBuilder<P, T> is(Attr<Type> attribute, Type value) {
        queryParts.add(new AtomicAttributeQuery<Type>(attribute, value));
        return this;
    }

    public <X extends ImmutableList<Y>, Y> QBuilder<P, T> subQuery(Attr<X> attr, ElemQuery<Y> elemQuery) {
        return this;
    }

    public <X extends ImmutableList<Y>, Y> QBuilder<QBuilder<P, T>, X> subQuery(Attr<X> attr) {
        // TODO: Prüfe, ob immer der eigene Sequence-Operator übergeben werden soll.
        QBuilder<QBuilder<P, T>, X> builder = new QBuilder<QBuilder<P, T>, X>(this, this.operator);
        children.add(builder);

        return builder;
    }

    public P finish() {
        return this.parent.get();
    }
    
    public Query asQuery(){
         return new SquenceQuery(this.operator, this.queryParts);
    }
}
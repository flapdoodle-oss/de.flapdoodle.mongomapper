package de.flapdoodle.mongomapper.example.query;

import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

public class TestQueryIdea {

    @Test
    public void play() {
        
        Attr<ImmutableList<String>> foo = new Attr<ImmutableList<String>>("foo");
        Attr<String> bar = new Attr<String>("bar");
        Attr<ImmutableList<String>> newbar = new Attr<ImmutableList<String>>("bar");
        
        QBuilder
            .start()
            .subQuery(foo,new ElemQuery<String>()
                    .is(bar, "sdfsad")
                    .build());
        
        QBuilder
            .start()
            .subQuery(foo)
                .is(bar,"nix")
                .subQuery(newbar)
                    .is(bar, "sdfsad")
                .finish()
            .finish();
        
        QBuilder<NoopBuilder, Void> start = QBuilder.start();
        
        start.is(bar, "nix");
        
        QBuilder<QBuilder<NoopBuilder, Void>, ImmutableList<String>> res = start.subQuery(foo);
        QBuilder<QBuilder<QBuilder<NoopBuilder, Void>, ImmutableList<String>>, ImmutableList<String>> subQuery = res.subQuery(foo);
        
        QBuilder<NoopBuilder, Void> parent = res.finish();
        QBuilder<QBuilder<NoopBuilder, Void>, ImmutableList<String>> oneUp = subQuery.finish();
        parent = oneUp.finish();

    }
    
    static class Attr<T> {
        
        private String name;

        public Attr(String name) {
            this.name = name;
        }
        
        public String name() {
            return name;
        }
    }

    static class NoopBuilder extends QBuilder<NoopBuilder, Void> {
        private NoopBuilder() {
            // TODO Auto-generated constructor stub
        }
    }
    
    static class QBuilder<P extends QBuilder<?, ?>,T> {
        
        private final Optional<P> parent;

        private QBuilder() {
            this.parent=Optional.absent();
        }
        
        public static QBuilder<NoopBuilder,Void> start() {
            return new QBuilder<NoopBuilder, Void>();
        }
        
        public QBuilder<P,T> is(Attr<String> bar, String value) {
            // daten merken
            return this;
        }

        public QBuilder(P parent) {
            this.parent = Optional.of(parent);
        }

        public <X extends ImmutableList<Y>,Y> QBuilder<P,T> subQuery(Attr<X> attr, ElemQuery<Y> elemQuery) {
            return this;
        }

        public <X extends ImmutableList<Y>,Y> QBuilder<QBuilder<P,T>,X> subQuery(Attr<X> attr) {
            return new QBuilder<QBuilder<P,T>,X>(this);
        }
        
        private P finish() {
            // process some stuff
            return this.parent.get();
        }
    }

    // variante mit methodenparam
    
    static class ElemQuery<T> {

        public ElemQuery<T> is(Attr<String> bar, String string) {
            return this;
        }

        public ElemQuery<T> build() {
            return this;
        }
        
    }
    
    // variante als builder
    static class ArrayQuery<T> {
        
    }
}

package de.flapdoodle.mongomapper;

import java.util.Iterator;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Iterators;

public abstract class SizedIterables {

    private SizedIterables() {
        // no instance
    }
    
    public static <T> SizedIterable<T> wrap(List<T> source) {
        return new SizedIterableWrapper<>(source, source.size());
    }

    public static <S,D> SizedIterable<D> transform(SizedIterable<S> source,Function<S, D> transformation) {
        return new TransformSizedIterable<>(source, transformation);
    }
    
    static class SizedIterableWrapper<T> implements SizedIterable<T> {

        private final Iterable<T> iterable;
        private final long size;

        public SizedIterableWrapper(Iterable<T> iterable,long size) {
            this.iterable = iterable;
            this.size = size;
        }
        
        @Override
        public Iterator<T> iterator() {
            return iterable.iterator();
        }

        @Override
        public long size() {
            return size;
        }
        
    }
    
    static class TransformSizedIterable<S,D> implements SizedIterable<D> {

        private final SizedIterable<S> delegate;
        private final Function<S, D> transformation;

        public TransformSizedIterable(SizedIterable<S> delegate, Function<S, D> transformation) {
            this.delegate = delegate;
            this.transformation = transformation;
        }
        
        @Override
        public Iterator<D> iterator() {
            return Iterators.transform(delegate.iterator(), transformation);
        }

        @Override
        public long size() {
            return delegate.size();
        }
        
    }
}

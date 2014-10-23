package de.flapdoodle.mongomapper;

import java.util.Collection;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Range;
import com.mongodb.QueryBuilder;

import de.flapdoodle.guava.Varargs;
import de.flapdoodle.mongomapper.types.DateMapper;

@Deprecated
public abstract class Queries {

    static final DateMapper DATE_MAPPER = new DateMapper("foo");

    private Queries() {
        // no instance
    }
   
    public static <T> void is(QueryBuilder queryBuilder, AttributeMapper<T> mapper, Optional<T> value, AttributeMapper<?> ... mappers) {
        if (value.isPresent()) {
            T v = value.get();
            is(queryBuilder, mapper, v, mappers);
        }
    }

    public static <T> void exists(QueryBuilder queryBuilder, AttributeMapper<T> mapper, boolean exists,AttributeMapper<?>... mappers) {
        String propertyName=propertyName(mapper, mappers);
        queryBuilder.put(propertyName).exists(exists);
    }

    public static <T> void is(QueryBuilder queryBuilder, AttributeMapper<T> mapper, T v,AttributeMapper<?>... mappers) {
        String propertyName=propertyName(mapper, mappers);
        queryBuilder.put(propertyName).is(mapper.asDBObject(v));
    }

    public static <T> void isWithMapper(QueryBuilder queryBuilder, AttributeMapper<T> mapper, T v,AttributeMapper<?>... mappers) {
        is(queryBuilder, mapper, v, mappers);
    }

    public static <T> String propertyName(AttributeMapper<T> mapper, AttributeMapper<?>... mappers) {
        return PropertyNames.name(Varargs.asArray(Varargs.asCollection(mappers, mapper),AttributeMapper.class));
    }
    
    public static <T, L extends Collection<? extends T>> void isInList(QueryBuilder queryBuilder, ObjectAsAttributeMapper<L,?> mapper, T v,AttributeMapper<?>... mappers) {
        String propertyName=propertyName(mapper, mappers);
        
        ObjectMapper<T> valueMapper = ((ListWrappingMapper<T>) mapper.wrapped()).wrappedMapper();
        queryBuilder.put(propertyName).is(valueMapper.asDBObject(v));
    }
    
//    public static <T> void isInList(QueryBuilder queryBuilder, AttributeMapper<ImmutableList<? extends T>> mapper, T v,AttributeMapper<?>... mappers) {
//        String propertyName=PropertyNames.name(Varargs.asArray(Varargs.asCollection(mappers, mapper),AttributeMapper.class));
//        queryBuilder.put(propertyName).is(mapper.asDBobject(ImmutableList.of(v)));
//    }
    
    
    
    public static <T> void inList(QueryBuilder queryBuilder, AttributeMapper<? super ImmutableList<? extends T>> mapper, Collection<? extends T> values, AttributeMapper<?> ... mappers) {
        if (!values.isEmpty()) {
            String propertyName=propertyName(mapper, mappers);
            queryBuilder.put(propertyName).in(mapper.asDBObject(ImmutableList.copyOf(values)));
        }
    }
    
    public static <T> void in(QueryBuilder queryBuilder, AttributeMapper<T> mapper, Collection<? extends T> values, AttributeMapper<?> ... mappers) {
        if (!values.isEmpty()) {
            String propertyName=propertyName(mapper, mappers);
            queryBuilder.put(propertyName).in(Collections2.transform(values,AttributeMappers.asDBObjectFunction(mapper)));
        }
    }


    /**
     * Wenn ein Zeitinterval gesetzt wurde, wird der QueryBuilder auf diesen zeitraum eingeschränkt.
     *
     * @param queryBuilder
     * @param property
     * @param dateRange
     */
    public static void between(QueryBuilder queryBuilder, String property, Optional<Range<DateTime>> dateRange) {
        if (dateRange.isPresent()) {
            Range<DateTime> interval = dateRange.get();

            if (interval.hasLowerBound()) {
                switch (interval.lowerBoundType()) {
                case CLOSED:
                    queryBuilder.put(property).greaterThanEquals(DATE_MAPPER.asDBObject(interval.lowerEndpoint()));
                    break;
                case OPEN:
                    queryBuilder.put(property).greaterThan(DATE_MAPPER.asDBObject(interval.lowerEndpoint()));
                    break;
                }
            }
            if (interval.hasUpperBound()) {
                switch (interval.upperBoundType()) {
                case CLOSED:
                    queryBuilder.put(property).lessThanEquals(DATE_MAPPER.asDBObject(interval.upperEndpoint()));
                    break;
                case OPEN:
                    queryBuilder.put(property).lessThan(DATE_MAPPER.asDBObject(interval.upperEndpoint()));
                    break;
                }
            }

        }
    }
}

package de.flapdoodle.mongomapper.querybuilder.legacy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

import de.flapdoodle.mongomapper.query.Properties;
import de.flapdoodle.mongomapper.query.Property;
import de.flapdoodle.mongomapper.query.PropertyNameAndMapper;
import de.flapdoodle.mongomapper.query.QueryableProperty;

public class QueryBuilder {

    /**
     * Creates a builder with an empty query
     */
    public QueryBuilder() {
        _query = new BasicDBObject();
    }

    /**
     * returns a new QueryBuilder
     * @return
     */
    public static QueryBuilder start() {
        return new QueryBuilder();
    }
        
//    /**
//     * Adds a new key to the query if not present yet.
//     * Sets this key as the current key.
//     * @param key MongoDB document key
//     * @return this
//     */
//    public QueryBuilder put(String key) {
//        _currentKey = name(key);
//        if(_query.get(key) == null) {
//            _query.put(_currentKey, new NullObject());
//        }
//        return this;
//    }
//        
//    /**
//     * Equivalent to <code>QueryBuilder.put(key)</code>. Intended for compound query chains to be more readable, e.g.
//     * {@code QueryBuilder.start("a").greaterThan(1).and("b").lessThan(3) }
//     * @param key MongoDB document key
//     * @return this
//     */
//    public QueryBuilder and(String key) {
//        return put(key);
//    }
        
    /**
     * Equivalent to the $gt operator
     * @param object Value to query
     * @param key 
     * @return Returns the current QueryBuilder with an appended "greater than" query  
     */
    public <T> QueryBuilder greaterThan(QueryableProperty<T, ? extends Property<?, ?>> key, T object) {
        addOperand(key, false, QueryOperators.GT, object);
        return this;
    }
        
    /**
     * Equivalent to the $gte operator
     * @param object Value to query
     * @return Returns the current QueryBuilder with an appended "greater than or equals" query
     */
    public <T> QueryBuilder greaterThanEquals(QueryableProperty<T, ? extends Property<?, ?>> key, T object) {
        addOperand(key, false, QueryOperators.GTE, object);
        return this;
    }
        
    /**
     * Equivalent to the $lt operand
     * @param object Value to query
     * @return Returns the current QueryBuilder with an appended "less than" query
     */
    public <T> QueryBuilder lessThan(QueryableProperty<T, ? extends Property<?, ?>> key, T object) {
        addOperand(key, false, QueryOperators.LT, object);
        return this;
    }
        
    /**
     * Equivalent to the $lte operand
     * @param object Value to query
     * @return Returns the current QueryBuilder with an appended "less than or equals" query
     */
    public <T> QueryBuilder lessThanEquals(QueryableProperty<T, ? extends Property<?, ?>> key, T object) {
        addOperand(key, false, QueryOperators.LTE, object);
        return this;
    }
        
    /**
     * Equivalent of the find({key:value})
     * @param object Value to query
     * @return Returns the current QueryBuilder with an appended equality query
     */
    public <T> QueryBuilder is(QueryableProperty<T, ? extends Property<?, ?>> key, T object) {
        addOperand(key, false, null, object);
        return this;
    }
        
    /**
     * Equivalent of the find({key:value})
     * @param object Value to query
     * @return Returns the current QueryBuilder with an appended equality query
     */
    public <T> QueryBuilder isNot(QueryableProperty<T, ? extends Property<?, ?>> key, T object) {
        addOperand(key, true, null, object);
        return this;
    }
        
    /**
     * Equivalent of the $ne operand
     * @param object Value to query
     * @return Returns the current QueryBuilder with an appended inequality query
     */
    public <T> QueryBuilder notEquals(QueryableProperty<T, ? extends Property<?, ?>> key, T object) {
        addOperand(key, false, QueryOperators.NE, object);
        return this;
    }
        
    /**
     * Equivalent of the $in operand
     * @param object Value to query
     * @return Returns the current QueryBuilder with an appended "in array" query
     */
    public <T> QueryBuilder in(final QueryableProperty<T, ? extends Property<?, ?>> key, Iterable<T> src) {
        addOperandUnmapped(key, false, QueryOperators.IN, mapAsList(key, src));
        return this;
    }
        
    /**
     * Equivalent of the $nin operand
     * @param object Value to query
     * @return Returns the current QueryBuilder with an appended "not in array" query
     */
    public <T> QueryBuilder notIn(QueryableProperty<T, ? extends Property<?, ?>> key, Iterable<T> src) {
        addOperandUnmapped(key, false, QueryOperators.NIN, mapAsList(key, src));
        return this;
    }

    private <T> ArrayList<Object> mapAsList(QueryableProperty<T, ? extends Property<?, ?>> key,
            Iterable<T> src) {
        return Lists.newArrayList(Iterables.transform(src, new ApplyMapper<T>(key)));
    }
        
    /**
     * Equivalent of the $mod operand
     * @param object Value to query
     * @return Returns the current QueryBuilder with an appended modulo query
     */
    public <T extends Number> QueryBuilder mod(QueryableProperty<T, ? extends Property<?, ?>> key, T object) {
        addOperand(key, false, QueryOperators.MOD, object);
        return this;
    }
        
    /**
     * Equivalent of the $mod operand
     * @param object Value to query
     * @return Returns the current QueryBuilder with an appended modulo query
     */
    public <T extends Number> QueryBuilder modNot(QueryableProperty<T, ? extends Property<?, ?>> key, T object) {
        addOperand(key, true, QueryOperators.MOD, object);
        return this;
    }
    /**
     * Equivalent of the $all operand
     * @param object Value to query
     * @return Returns the current QueryBuilder with an appended "matches all array contents" query
     */
    public <T> QueryBuilder all(QueryableProperty<T, ? extends Property<?, ?>> key, Iterable<T> src) {
        addOperandUnmapped(key, false, QueryOperators.ALL, mapAsList(key, src));
        return this;
    }
        
    /**
     * Equivalent of the $size operand
     * @param object Value to query
     * @return Returns the current QueryBuilder with an appended size operator
     */
    public <T> QueryBuilder size(QueryableProperty<T, ? extends Property<?, ?>> key, Long object) {
        addOperandUnmapped(key, false, QueryOperators.SIZE, object);
        return this;
    }
        
    /**
     * Equivalent of the $size operand
     * @param object Value to query
     * @return Returns the current QueryBuilder with an appended size operator
     */
    public <T> QueryBuilder sizeNot(QueryableProperty<T, ? extends Property<?, ?>> key, Long object) {
        addOperandUnmapped(key, true, QueryOperators.SIZE, object);
        return this;
    }
        
    /**
     * Equivalent of the $exists operand
     * @param object Value to query
     * @return Returns the current QueryBuilder with an appended exists operator
     */
    public <T> QueryBuilder exists(QueryableProperty<T, ? extends Property<?, ?>> key, boolean object) {
        addOperandUnmapped(key, false, QueryOperators.EXISTS, object);
        return this;
    }
        
    /**
     * Passes a regular expression for a query
     * @param regex Regex pattern object
     * @return Returns the current QueryBuilder with an appended regex query
     */
    public QueryBuilder regex(QueryableProperty<String, ? extends Property<?, ?>> key, Pattern regex) {
        addOperandUnmapped(key, false, null, regex);
        return this;
    }

//    /**
//     * Equivalent to the $elemMatch operand
//     * @param match  the object to match
//     * @return Returns the current QueryBuilder with an appended elemMatch operator
//     */
//    public <T> QueryBuilder elemMatch(CompositeProperty<T, ? extends Property<?, ?>> key, T match) {
//        addOperand(key, false, QueryOperators.ELEM_MATCH, match);
//        return this;
//    }



//    /**
//     * Equivalent of the $within operand, used for geospatial operation
//     * @param x x coordinate
//     * @param y y coordinate
//     * @param radius radius
//     * @return
//     */
//    public QueryBuilder withinCenter(QueryableProperty<String, ? extends Property<?, ?>> key, double x , double y , double radius ){
//        addOperand( QueryOperators.WITHIN ,
//                    new BasicDBObject(QueryOperators.CENTER, new Object[]{ new Double[]{ x , y } , radius } ) );
//        return this;
//    }
    
//    /**
//     * Equivalent of the $near operand
//     * @param x x coordinate
//     * @param y y coordinate
//     * @return
//     */
//    public QueryBuilder near( double x , double y  ){
//        addOperand(QueryOperators.NEAR,
//                    new Double[]{ x , y } );
//        return this;
//    }

//    /**
//     * Equivalent of the $near operand
//     * @param x x coordinate
//     * @param y y coordinate
//     * @param maxDistance max distance
//     * @return
//     */
//    public QueryBuilder near( double x , double y , double maxDistance ){
//        addOperand( QueryOperators.NEAR ,
//                    new Double[]{ x , y , maxDistance } );
//        return this;
//    }
    
//    /**
//     * Equivalent of the $nearSphere operand
//     * @param longitude coordinate in decimal degrees 
//     * @param latitude coordinate in decimal degrees
//     * @return
//     */
//    public QueryBuilder nearSphere( double longitude , double latitude ){
//        addOperand(QueryOperators.NEAR_SPHERE,
//                    new Double[]{ longitude , latitude } );
//        return this;
//    }
    
//    /**
//     * Equivalent of the $nearSphere operand
//     * @param longitude coordinate in decimal degrees 
//     * @param latitude coordinate in decimal degrees
//     * @param maxDistance max spherical distance
//     * @return
//     */
//    public QueryBuilder nearSphere( double longitude , double latitude , double maxDistance ){
//        addOperand( QueryOperators.NEAR_SPHERE ,
//                    new Double[]{ longitude , latitude , maxDistance } );
//        return this;
//    }
    
//    /**
//     * Equivalent of the $centerSphere operand
//     * mostly intended for queries up to a few hundred miles or km.
//     * @param longitude coordinate in decimal degrees 
//     * @param latitude coordinate in decimal degrees
//     * @param maxDistance max spherical distance
//     * @return
//     */
//    public QueryBuilder withinCenterSphere( double longitude , double latitude , double maxDistance ){
//        addOperand( QueryOperators.WITHIN ,
//                new BasicDBObject(QueryOperators.CENTER_SPHERE, new Object[]{ new Double[]{longitude , latitude} , maxDistance } ) );
//        return this;
//    }
    
//    /**
//     * Equivalent to a $within operand, based on a bounding box using represented by two corners
//     * 
//     * @param x the x coordinate of the first box corner.
//     * @param y the y coordinate of the first box corner.
//     * @param x2 the x coordinate of the second box corner.
//     * @param y2 the y coordinate of the second box corner.
//     * @return
//     */
//    public QueryBuilder withinBox(double x, double y, double x2, double y2) {
//        addOperand( QueryOperators.WITHIN ,
//                    new BasicDBObject(QueryOperators.BOX, new Object[] { new Double[] { x, y }, new Double[] { x2, y2 } } ) );
//        return this;
//    }
//    
//    /**
//     * Equivalent to a $within operand, based on a bounding polygon represented by an array of points
//     * 
//     * @param points an array of Double[] defining the vertices of the search area
//     * @return this
//     */
//    public QueryBuilder withinPolygon(List<Double[]> points) {
//        if(points == null || points.isEmpty() || points.size() < 3)
//            throw new IllegalArgumentException("Polygon insufficient number of vertices defined");
//        addOperand( QueryOperators.WITHIN ,
//                    new BasicDBObject(QueryOperators.POLYGON, points ) );
//        return this;
//    }

//    /**
//     * Equivalent to $not meta operator. Must be followed by an operand, not a value, e.g.
//     * {@code QueryBuilder.start("val").not().mod(Arrays.asList(10, 1)) }
//     *
//     * @return Returns the current QueryBuilder with an appended "not" meta operator
//     */
//    public QueryBuilder not() {
//        _hasNot = true;
//        return this;
//    }

    /**
     * Equivalent to an $or operand
     * @param ors the list of conditions to or together
     * @return Returns the current QueryBuilder with appended "or" operator
     */
    @SuppressWarnings("unchecked")
    public QueryBuilder or( DBObject ... ors ){
        List l = (List)_query.get( QueryOperators.OR );
        if ( l == null ){
            l = new ArrayList();
            _query.put( QueryOperators.OR , l );
        }
        Collections.addAll(l, ors);
        return this;
    }

    /**
     * Equivalent to an $and operand
     * @param ands the list of conditions to and together
     * @return Returns the current QueryBuilder with appended "and" operator
     */
    @SuppressWarnings("unchecked")
    public QueryBuilder and( DBObject ... ands ){
        List l = (List)_query.get( QueryOperators.AND );
        if ( l == null ){
            l = new ArrayList();
            _query.put( QueryOperators.AND , l );
        }
        Collections.addAll(l, ands);
        return this;
    }

    /**
     * Creates a <code>DBObject</code> query to be used for the driver's find operations
     * @return Returns a DBObject query instance
     * @throws RuntimeException if a key does not have a matching operand
     */
    public DBObject get() {
        for(String key : _query.keySet()) {
            if(_query.get(key) instanceof NullObject) {
                throw new QueryBuilderException("No operand for key:" + key);
            }
        }
        return _query;
    }
    
    private <T> void addOperand(PropertyNameAndMapper<T, ? extends Property<?,?>>key, boolean not, String op, T v) {
        Object value=key.mapper().asDBObject(v);
        addOperandUnmapped(key, not, op, value);
    }

    private <T> void addOperandUnmapped(PropertyNameAndMapper<T, ? extends Property<?, ?>> key, boolean not, String op,
            Object value) {
        String _currentKey=Properties.name(key);
        boolean _hasNot=not;
        
        if(op == null) {
            if (_hasNot) {
                value = new BasicDBObject(QueryOperators.NOT, value);
                _hasNot = false;
            }
            _query.put(_currentKey, value);
            return;
        }

        Object storedValue = _query.get(_currentKey);
        BasicDBObject operand;
        if(!(storedValue instanceof DBObject)) {
            operand = new BasicDBObject();
            if (_hasNot) {
                DBObject notOperand = new BasicDBObject(QueryOperators.NOT, operand);
                _query.put(_currentKey, notOperand);
                _hasNot = false;
            } else {
                _query.put(_currentKey, operand);
            }
        } else {
            operand = (BasicDBObject)_query.get(_currentKey);
            if (operand.get(QueryOperators.NOT) != null) {
                operand = (BasicDBObject) operand.get(QueryOperators.NOT);
            }
        }
        operand.put(op, value);
    }
    private final class ApplyMapper<T> implements Function<T, Object> {
        private final QueryableProperty<T, ? extends Property<?, ?>> key;

        private ApplyMapper(QueryableProperty<T, ? extends Property<?, ?>> key) {
            this.key = key;
        }

        @Override
        public Object apply(T input) {
            return key.mapper().asDBObject(input);
        }
    }
    @SuppressWarnings("serial")
        static class QueryBuilderException extends RuntimeException {
            QueryBuilderException(String message) {
                super(message);
            }
        }
    private static class NullObject {}
        
    private DBObject _query;
//    private String _currentKey;
//    private boolean _hasNot;
        

}

package de.flapdoodle.mongomapper.querybuilder;

import de.flapdoodle.mongomapper.Attr;
import de.flapdoodle.mongomapper.query.ComposedAttributeQuery;
import de.flapdoodle.mongomapper.query.Query;
import de.flapdoodle.mongomapper.query.SquenceQuery;
import de.flapdoodle.mongomapper.query.operators.ArrayQueryType;
import de.flapdoodle.mongomapper.query.operators.NamedMongoDBOperator;

public class ArrayQBuilder extends QBuilder<QBuilder<?>>{

    private final ArrayQueryType queryType;
    
    public ArrayQBuilder(QBuilder<?> parent, NamedMongoDBOperator operator, Attr<?> attribute,ArrayQueryType queryType) {
        super(parent, operator, attribute);
        this.queryType = queryType;
    }

    @Override
    Query buildQuery(){
        return new ComposedAttributeQuery(attribute(), new SquenceQuery(queryType, queryParts(), '{', '}', true));
    }

}

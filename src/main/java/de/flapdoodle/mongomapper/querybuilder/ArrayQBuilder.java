package de.flapdoodle.mongomapper.querybuilder;

import de.flapdoodle.mongomapper.Attr;
import de.flapdoodle.mongomapper.query.ArrayQuery;
import de.flapdoodle.mongomapper.query.ComposedAttributeQuery;
import de.flapdoodle.mongomapper.query.Query;
import de.flapdoodle.mongomapper.query.operators.LogicalSequence;
import de.flapdoodle.mongomapper.query.operators.SubQueryType;

public class ArrayQBuilder extends QBuilder<QBuilder<?>>{

    private final SubQueryType queryType;
    
    public ArrayQBuilder(QBuilder<?> parent, LogicalSequence operator, Attr<?> attribute,SubQueryType queryType) {
        super(parent, operator, attribute);
        this.queryType = queryType;
    }
    
    @Override
    public Query asQuery() {
        return new ComposedAttributeQuery(attribute(), new ArrayQuery(queryType, queryParts()));
    }

}

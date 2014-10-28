package de.flapdoodle.mongomapper.query;

import com.google.common.collect.ImmutableList;

import de.flapdoodle.mongomapper.query.operators.ArrayQueryType;

public class ArrayQuery implements Query {
    
    private final ArrayQueryType queryType;
    
    private final ImmutableList<Query> subQueries;
    
    public ArrayQuery(ArrayQueryType queryType, ImmutableList<Query> subQuery){
        this.queryType = queryType;
        this.subQueries = subQuery;
    }

    @Override
    public String generate() {
        StringBuffer buffer = new StringBuffer("{ ");
        buffer.append(basicQuery());
        buffer.append("}");
        return buffer.toString();
    }

    @Override
    public String basicQuery() {
        StringBuffer buffer = new StringBuffer("");
        buffer.append(queryType.toString());
        buffer.append(": {");
        
        for(int i = 0; i < subQueries.size(); i++){
            Query query = subQueries.get(i);
            buffer.append(query.basicQuery());
            
            if(i < (subQueries.size() - 1)){
                buffer.append(", ");
            }
        }
        
        buffer.append("}");
        return buffer.toString();
    }

}

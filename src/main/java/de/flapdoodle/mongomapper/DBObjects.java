package de.flapdoodle.mongomapper;


import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;


public abstract class DBObjects {

    private DBObjects() {
        // no instance
    }
    
    public static DBObject prefix(String prefix, DBObject src) {
        BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        for (String key : src.keySet()) {
            builder.append(prefix+"."+key, src.get(key));
        }
        return builder.get();
    }
}

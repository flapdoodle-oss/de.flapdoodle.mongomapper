package de.flapdoodle.mongomapper.example.list;

import org.joda.time.DateTime;

public class Sub {
    
    private final String name;
    private final DateTime created;
    
    public Sub(String name, DateTime created) {
        super();
        this.name = name;
        this.created = created;
    }
    
    public String name() {
        return name;
    }
    
    public DateTime created() {
        return created;
    }
    

}

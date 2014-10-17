package de.flapdoodle.mongomapper.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.joda.time.DateTime;
import org.junit.Test;

import com.mongodb.DBCollection;

import de.flapdoodle.mongomapper.AbstractMongoDBTest;
import de.flapdoodle.mongomapper.query.Property;
import de.flapdoodle.mongomapper.query.QueryableProperty;

public class TestSample extends AbstractMongoDBTest {

    @Test
    public void storeAndLoadStuff() {
        
        Root root=new Root("foo",2, DateTime.now());
        
        DBCollection collection=getMongo().getDB("test").getCollection("roots");
        RootStore store = new RootStore(collection);
        
        assertNotNull(store.store(root));
    }
    
    @Test
    public void queryStuff() {
        
        String createdName=name(RootMapper.INSTANCE.created());
        String yearName=name(RootMapper.INSTANCE.created().year());

        assertEquals("created.value",createdName);
        assertEquals("created.year",yearName);
    }

    static String name(QueryableProperty<?, ? extends Property<?,?>> property) {
        if (property.parent().isPresent()) {
            return name(property.parent().get())+"."+property.name();
        }
        return property.name();
    }
    
    static String name(Property<?, ? extends Property<?,?>> property) {
        if (property.parent().isPresent()) {
            return name(property.parent().get())+"."+property.name();
        }
        return property.name();
    }
}

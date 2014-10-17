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
        assertEquals("created.value",name(RootMapper.INSTANCE.created()));
        assertEquals("created.year",name(RootMapper.INSTANCE.created().year()));
        
        assertEquals("sub.created.value",name(RootMapper.INSTANCE.sub().created()));
        assertEquals("sub.created.year",name(RootMapper.INSTANCE.sub().created().year()));
        
        assertEquals("created.year",name(SubMapper.INSTANCE.created().year()));
    }

    static String name(QueryableProperty<?, ? extends Property<?,?>> property) {
        if (property.parentProperty().isPresent()) {
            return parentName(property.parentProperty().get())+"."+property.propertyName();
        }
        return property.propertyName();
    }
    
    static String parentName(Property<?, ? extends Property<?,?>> property) {
        if (property.parentProperty().isPresent()) {
            return parentName(property.parentProperty().get())+"."+property.propertyName();
        }
        return property.propertyName();
    }
}

package de.flapdoodle.mongomapper.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.joda.time.DateTime;
import org.junit.Test;

import com.mongodb.DBCollection;

import de.flapdoodle.mongomapper.AbstractMongoDBTest;
import de.flapdoodle.mongomapper.composite.QueryableDateMapper.Properties;
import de.flapdoodle.mongomapper.query.CascadedProperty;
import de.flapdoodle.mongomapper.query.QueryProperty;
import de.flapdoodle.mongomapper.query.VoidProperty;

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
        
        Properties<CascadedProperty<DateTime, VoidProperty>> created = RootMapper.INSTANCE.created();
        CascadedProperty<Integer, CascadedProperty<DateTime, VoidProperty>> year = created.year();
        
        String createdName=name(created);
        String yearName=name(year);

        assertEquals("created.value",createdName);
        assertEquals("created.year",yearName);
    }
    
    static String name(QueryProperty<?, ? extends QueryProperty<?,?>> property) {
        if (property.parent().isPresent()) {
            return name(property.parent().get())+"."+property.name();
        }
        return property.name();
    }
    
}

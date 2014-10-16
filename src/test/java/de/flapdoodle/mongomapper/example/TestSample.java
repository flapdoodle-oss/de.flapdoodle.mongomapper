package de.flapdoodle.mongomapper.example;

import static org.junit.Assert.assertNotNull;

import org.joda.time.DateTime;
import org.junit.Test;

import com.mongodb.DBCollection;

import de.flapdoodle.mongomapper.AbstractMongoDBTest;

public class TestSample extends AbstractMongoDBTest {

    @Test
    public void storeAndLoadStuff() {
        
        Root root=new Root("foo",2, DateTime.now());
        
        DBCollection collection=getMongo().getDB("test").getCollection("roots");
        RootStore store = new RootStore(collection);
        
        
        
        assertNotNull(store.store(root));
    }
    
}

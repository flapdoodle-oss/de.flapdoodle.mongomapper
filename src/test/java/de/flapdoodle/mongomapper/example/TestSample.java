package de.flapdoodle.mongomapper.example;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.mongodb.DBCollection;

import de.flapdoodle.mongomapper.AbstractMongoDBTest;
import de.flapdoodle.mongomapper.AbstractStringIdStore;

public class TestSample extends AbstractMongoDBTest {

    @Test
    public void storeAndLoadStuff() {
        
        Root root=new Root("foo",2);
        
        DBCollection collection=getMongo().getDB("test").getCollection("roots");
        AbstractStringIdStore<Root, Root> store = new AbstractStringIdStore<Root, Root>(collection, RootMapper.INSTANCE) {
            
        };
        
        assertNotNull(store.store(root));
        
    }
}

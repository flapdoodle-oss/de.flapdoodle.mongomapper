package de.flapdoodle.mongomapper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.mongodb.DBObject;

import de.flapdoodle.mongomapper.query.Properties;
import de.flapdoodle.mongomapper.types.StringMapper;

public class AbstractMapperAdapterTest {

    @Test
    public void indexKeysShouldMatch() {
        IndexedProperty foo=new IndexedProperty(Properties.with(new StringMapper("foo")), Sort.Direction.Descent);
        IndexedProperty bar=new IndexedProperty(Properties.with(Properties.with(new StringMapper("foo")),new StringMapper("bar")), Sort.Direction.Ascent);
        DBObject indexKeys = AbstractMapperAdapter.asIndexKeys(ImmutableList.<IndexedProperty>of(foo,bar));
        assertEquals("{ \"foo\" : -1 , \"foo.bar\" : 1}",indexKeys.toString());
    }

    @Test
    public void indexNameShouldMatch() {
        IndexedProperty foo=new IndexedProperty(Properties.with(new StringMapper("foo")), Sort.Direction.Descent);
        IndexedProperty bar=new IndexedProperty(Properties.with(Properties.with(new StringMapper("foo")),new StringMapper("bar")), Sort.Direction.Ascent);
        IndexDefinition def=new IndexDefinition(true, foo,bar);
        DBObject indexDefs = AbstractMapperAdapter.asIndexOptions(def);
        assertEquals("{ \"unique\" : true , \"name\" : \"foo.foo_bar\"}",indexDefs.toString());
    }
}

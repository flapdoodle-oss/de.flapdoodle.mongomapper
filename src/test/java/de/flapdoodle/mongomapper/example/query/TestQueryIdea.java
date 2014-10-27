package de.flapdoodle.mongomapper.example.query;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.flapdoodle.mongomapper.Attr;
import de.flapdoodle.mongomapper.querybuilder.QBuilder;

public class TestQueryIdea {

    @Test
    public void play() {
        
//        Attr<ImmutableList<String>> foo = new Attr<ImmutableList<String>>("foo");
//        Attr<String> bar = new Attr<String>("bar");
//        Attr<ImmutableList<String>> newbar = new Attr<ImmutableList<String>>("bar");
//        
//        QBuilder
//            .start()
//            .subQuery(foo,new ElemQuery<String>()
//                    .is(bar, "sdfsad")
//                    .build());
//        
//        QBuilder
//            .start()    
//            .oneOrMany(foo)
//                .is(bar,"nix")
//                .oneOrMany(newbar)
//                    .is(bar, "sdfsad")
//                .finish()
//            .finish();
//        
//        QBuilder<VoidBuilder> start = QBuilder.start();
//        
//        start.is(bar, "nix");
//        
////        QBuilder<QBuilder<VoidBuilder>> res = start.oneOrMany(foo);
////        QBuilder<QBuilder<QBuilder<VoidBuilder>>> subQuery = res.oneOrMany(foo);
//        
//        QBuilder<VoidBuilder> parent = res.finish();
//        QBuilder<QBuilder<VoidBuilder>> oneUp = subQuery.finish();
//        parent = oneUp.finish();

    }
    
    @Test
    public void equalsQueryWithOneAttribute(){
        final String EQ_QUERY = "{$and: [{\"mittagessen\": \"Spaghetti\"}]}";
        
        Attr<String> attr = new Attr<String>("mittagessen");
        
        String query = QBuilder
                        .start()
                        .is(attr, "Spaghetti")
                        .asQuery()
                        .generate();
        
        assertEquals(EQ_QUERY, query);
    }

    @Test
    public void equalsQueryWithTwoAttributes(){
        final String EQ_QUERY = "{$and: [{\"mittagessen\": \"Spaghetti\"}, {\"nachtisch\": \"Eis\"}]}";
        
        Attr<String> attrMittagessen = new Attr<String>("mittagessen");
        Attr<String> attrNachtisch = new Attr<String>("nachtisch");
        
        String query = QBuilder
                        .start()
                        .is(attrMittagessen, "Spaghetti")
                        .is(attrNachtisch, "Eis")
                        .asQuery()
                        .generate();
        
        assertEquals(EQ_QUERY, query);
    }

    private static class JSONObject{
        // TODO: Remove
    }
    
    @Test
    public void equalsQueryWithOneOreManySubquery(){
        final String EQ_QUERY = "{$and: [{\"mittagessen\": \"Spaghetti\"}, {\"eiskarte\": { $elemMatch: {\"name\": \"Schokolade\", \"price\": {$gt: 2.0}}}}]}";
        
        Attr<String> attrMittagessen = new Attr<String>("mittagessen");
        // TODO: Ein Attr auf Maps wie? Attribute der Map können unterschiedliche Typen haben.
        Attr<JSONObject> attrEiskarte = new Attr<JSONObject>("eiskarte");
        Attr<Double> attrPrice = new Attr<Double>("price");
        
        String query = QBuilder
                        .start()
                        .is(attrMittagessen, "Spaghetti")
                        .oneOrMany(attrEiskarte)
                            .is(new Attr<String>("name"), "Schokolade")
                            .gt(attrPrice, 2.0d)
                            .finish()
                        .asQuery()
                        .generate();
        
        assertEquals(EQ_QUERY, query);
    }
    
    @Test
    public void equalsQueryWithAllSubquery(){
        final String EQ_QUERY = "{$and: [{\"mittagessen\": \"Spaghetti\"}, {\"eiskarte\": { $all: {\"name\": \"Schokolade\", \"price\": {$gt: 2.0}}}}]}";
        
        Attr<String> attrMittagessen = new Attr<String>("mittagessen");
        // TODO: Ein Attr auf Maps wie? Attribute der Map können unterschiedliche Typen haben.
        Attr<JSONObject> attrEiskarte = new Attr<JSONObject>("eiskarte");
        Attr<Double> attrPrice = new Attr<Double>("price");
        
        String query = QBuilder
                        .start()
                        .is(attrMittagessen, "Spaghetti")
                        .all(attrEiskarte)
                            .is(new Attr<String>("name"), "Schokolade")
                            .gt(attrPrice, 2.0d)
                            .finish()
                        .asQuery()
                        .generate();
        
        assertEquals(EQ_QUERY, query);
    }
    
    @Test
    public void equalsQueryWithSizeSubquery(){
        final String EQ_QUERY = "{$and: [{\"mittagessen\": \"Spaghetti\"}, {\"eiskarte\": { $size: 2}}]}";
        
        Attr<String> attrMittagessen = new Attr<String>("mittagessen");
        // TODO: Ein Attr auf Maps wie? Attribute der Map können unterschiedliche Typen haben.
        Attr<JSONObject> attrEiskarte = new Attr<JSONObject>("eiskarte");
        Attr<Double> attrPrice = new Attr<Double>("price");
        
        String query = QBuilder
                        .start()
                        .is(attrMittagessen, "Spaghetti")
                        .size(attrEiskarte)
                        .asQuery()
                        .generate();
        
        assertEquals(EQ_QUERY, query);
    }
}

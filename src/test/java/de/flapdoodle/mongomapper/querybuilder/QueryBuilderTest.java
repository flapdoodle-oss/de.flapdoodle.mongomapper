package de.flapdoodle.mongomapper.querybuilder;

import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.mongodb.DBObject;

import de.flapdoodle.mongomapper.example.tree.RootMapper;

public class QueryBuilderTest {

    @Test
    public void queryBuilderStuff() {
        RootMapper mapper = RootMapper.INSTANCE;
        
        DBObject query = new QueryBuilder()
            .is(mapper.created(), DateTime.now())
            .is(mapper.created().year(), 2013)
            .isNot(mapper.foo(), "nix")
            .is(mapper.bar(), 2)
            .is(mapper.sub().created().year(), 2014)
            .is(mapper.sub().name(), "sunny")
            .regex(mapper.sub().name(), Pattern.compile("[a-z]"))
            .in(mapper.bar(), Lists.newArrayList(2,3,4))
            .get();
        
        System.out.println("query: "+query);
    }


}

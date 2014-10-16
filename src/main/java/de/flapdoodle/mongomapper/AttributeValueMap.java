package de.flapdoodle.mongomapper;

import java.util.Map;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

public class AttributeValueMap {

    private final ImmutableMap<String, Object> map;

    private AttributeValueMap(ImmutableMap<String, Object> map) {
        this.map = map;
    }
    
    public <T> T get(AttributeMapper<T> mapper) {
        return (T) map.get(mapper.name());
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        
        Map<String, Object> map=Maps.newHashMap();
        
        public <T> Builder put(AttributeMapper<T> mapper,T value) {
            if (value!=null) {
                Object old = map.put(mapper.name(), value);
                Preconditions.checkArgument(old==null,"value for %s allready set to %s instead of %s",mapper,old,value);
            }
            return this;
        }
        
        public <T> Builder put(AttributeMapper<T> mapper,Optional<T> value) {
            return put(mapper,value.orNull());
        }
        
        public <T> Builder putAll(AttributeValueMap src) {
            SetView<String> sameKeys = Sets.intersection(map.keySet(), src.map.keySet());
            Preconditions.checkArgument(sameKeys.isEmpty(),"values for %s allready set",sameKeys);
            map.putAll(src.map);
            return this;
        }
        
        public AttributeValueMap build() {
            return new AttributeValueMap(ImmutableMap.copyOf(map));
        }
    }

    public Map<String, Object> asMap() {
        return map;
    }
}

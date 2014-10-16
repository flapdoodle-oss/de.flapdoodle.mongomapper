package de.flapdoodle.data;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;


public class MutableTypedMapImpl implements MutableTypedMap {

	Map<Property<?>, Object> values=Maps.newHashMap();
	
	@Override
	public <X> X get(Property<X> key) {
		return (X) values.get(key);
	}

	@Override
	public boolean contains(Property<?> key) {
		return values.containsKey(key);
	}

	@Override
	public <X> X set(Property<X> key, X value) {
		return (X) values.put(key, value);
	}

	@Override
	public TypedMap asImmutable() {
		final ImmutableMap<Property<?>, Object> copy = ImmutableMap.copyOf(values);
		
		return new TypedMap() {
			
			@Override
			public <X> X get(Property<X> key) {
				return (X) copy.get(key);
			}
			
			@Override
			public boolean contains(Property<?> key) {
				return copy.containsKey(key);
			}
		};
	}
}

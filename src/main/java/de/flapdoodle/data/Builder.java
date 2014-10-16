package de.flapdoodle.data;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;


public abstract class Builder<T> {

	MutableTypedMap map=new MutableTypedMapImpl();
	
	private final ImmutableSet<? extends Property<?>> _validProperties;
	
	public Builder(ImmutableSet<? extends Property<?>> validProperties) {
		_validProperties = validProperties;
	}
	
	public <X> Builder<T> set(Property<X> key, X value) {
		Preconditions.checkArgument(_validProperties.contains(key),"unknown property %s", key);
		X oldValue = map.set(key, value);
		Preconditions.checkArgument(oldValue==null,"setting property %s to %s failed. already set to %s",key,value,oldValue);
		return this;
	}
	
	protected TypedMap map() {
		return map.asImmutable();
	}
	
	public abstract T build();
	
	
}

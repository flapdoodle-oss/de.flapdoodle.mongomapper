package de.flapdoodle.data;


public abstract class AbstractContainer implements Container {

	private final TypedMap _valueMap;

	public AbstractContainer(TypedMap valueMap) {
		_valueMap = valueMap;
	}
	
	@Override
	public boolean contains(Property<?> key) {
		return _valueMap.contains(key);
	}
	
	@Override
	public <X> X get(Property<X> key) {
		return _valueMap.get(key);
	}
}

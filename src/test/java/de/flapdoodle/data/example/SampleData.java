package de.flapdoodle.data.example;

import de.flapdoodle.data.AbstractContainer;
import de.flapdoodle.data.Container;
import de.flapdoodle.data.Definition;
import de.flapdoodle.data.Property;
import de.flapdoodle.data.TypedMap;


public class SampleData extends AbstractContainer {

	public static final Property<Integer> foo=Property.named("foo", Integer.class);
	public static final Property<String> bar=Property.named("bar", String.class);
	
	public SampleData(TypedMap valueMap) {
		super(valueMap);
	}

	@Override
	public Definition<? extends Container> definition() {
		return SampleDataDefinition.INSTANCE;
	}
}

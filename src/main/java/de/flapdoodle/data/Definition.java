package de.flapdoodle.data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;


public interface Definition<C extends Container> {

	ImmutableSet<? extends Property<?>> properties();
	
	Builder<C> builder();
}

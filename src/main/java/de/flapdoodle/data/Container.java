package de.flapdoodle.data;


public interface Container extends TypedMap {

	Definition<? extends Container> definition();
	
}

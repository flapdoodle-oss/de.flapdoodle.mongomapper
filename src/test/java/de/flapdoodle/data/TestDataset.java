package de.flapdoodle.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class TestDataset {

	static final Property<String> FOO = Property.named("foo", String.class);
	static final Property<Integer> X = Property.named("x", Integer.class);
	
	@Test
	public void thatPutAndGetBasicallyWorks() {
		Dataset ds = Dataset.EMPTY;
		ds = ds.put(FOO, "bar");
		
		assertEquals("bar", ds.get(FOO));
		
		Dataset ds2 = ds.put(X, 42);
		assertEquals(42, ds2.get(X).intValue());
		assertNull(ds.get(X));
	}
}

package de.flapdoodle.data.example;

import static org.junit.Assert.*;

import org.junit.Test;

import de.flapdoodle.data.Builder;


public class TestSampleData {

	@Test
	public void createInstance() {
		
		Builder<SampleData> builder = SampleDataDefinition.INSTANCE.builder();
		
		SampleData sampleData = builder
				.set(SampleData.foo, 12)
				.set(SampleData.bar, "nix")
				.build();
		
		assertNotNull(sampleData);
		
		assertEquals(Integer.valueOf(12),sampleData.get(SampleData.foo));
		assertEquals("nix",sampleData.get(SampleData.bar));
		
	}
}

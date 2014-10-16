package de.flapdoodle.data.example;

import de.flapdoodle.data.AbstractDefinition;
import de.flapdoodle.data.Builder;
import de.flapdoodle.data.Property;

public class SampleDataDefinition extends AbstractDefinition<SampleData> {

	public static final SampleDataDefinition INSTANCE = new SampleDataDefinition();

	private SampleDataDefinition() {
		super(SampleData.foo, SampleData.bar);
	}

	@Override
	public Builder<SampleData> builder() {
		return new Builder<SampleData>(properties()) {

			@Override
			public SampleData build() {
				return new SampleData(map());
			}
		};
	}

}

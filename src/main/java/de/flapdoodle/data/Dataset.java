package de.flapdoodle.data;

public final class Dataset {

	public static final Dataset EMPTY = new Dataset(null);
	
	private final Trie root;
	
	private Dataset(Trie root) {
		super();
		this.root = root;
	}
	
	public boolean isEmpty() {
		return root == null;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(Property<T> prop) {
		if (isEmpty())
			return null;
		Object value = root.get(prop.hashCode(), 0);
		if (value == null)
			return null;
		Class<?> type = value.getClass();
		if (type == Object[].class) {
			Object[] propertyValue = (Object[]) value;
			if (propertyValue[0] == prop)
				return (T)propertyValue[1];
			return null;
		}
		if (type == Object[][].class) {
			Object[][] propertiesValues = (Object[][]) value;
			for (int i = 0; i < propertiesValues[0].length; i++) {
				if (propertiesValues[0][i] == prop)
					return (T)propertiesValues[1][i];
			}
			return null;
		}
		throw new IllegalStateException("We should never get here...");
	}
	
	public <T> Dataset put(Property<T> prop, T value) {
		if (isEmpty())
			return new Dataset(new Trie().put(prop.hashCode(), 0, prop, value));
		return new Dataset(root.put(prop.hashCode(), 0, prop, value));
	}
	
	static final class Trie {
		
		private final Object[] elements;

		public Trie() {
			this(new Object[32]);
		}
		public Trie(Object[] elements) {
			super();
			this.elements = elements;
		}
		
		Object get(int key, int level) {
			Object value = elements[key & 0b11111];
			if (value == null) {
				return null;
			}
			if (value instanceof Trie) {
				return ((Trie)value).get(key >>> 5, level++);
			}
			return value;
		}
		
		Trie put(int key, int level, Property<?> prop, Object value) {
			int index = key & 0b11111;
			Object cur = elements[index];
			
			if (cur == null) {
				return with(index, new Object[] { prop, value });
			}
			if (cur instanceof Trie) {
				return with(index, ((Trie) cur).put(key >>> 5, level+1, prop, value));
			}
			Class<?> type = cur.getClass();
			if (type == Object[][].class) { // two arrays of same length: 0: properties, 1: values 
				Object[][] propertiesValues = (Object[][]) cur;
				int length = propertiesValues[0].length;
				for (int i = 0; i < length; i++) {
					if (propertiesValues[0][i] == prop) {
						Object[][] copy = new Object[2][length];
						copy[0] = propertiesValues[0]; // share properties
						copy[1] = propertiesValues[1].clone();
						copy[1][i] = value; 
						return with(index, copy);
					}
				}
				Object[][] extended = new Object[2][length+1];
				System.arraycopy(propertiesValues[0], 0, extended[0], 1, length);
				System.arraycopy(propertiesValues[1], 0, extended[1], 1, length);
				extended[0][0] = prop;
				extended[1][0] = value;
				return with(index, extended);
			}
			if (type == Object[].class) { // a single property-value...
				Object[] propertyValue = (Object[]) cur;
				if (level >= 5) {
					return with(index, new Object[][] { { propertyValue[0], prop }, { propertyValue[1], value } });
				}
				return with(index, new Trie() // TODO optimize away intermediate Trie build
					.put(propertyValue[0].hashCode() >> (5 * level+1), level+1, (Property<?>) propertyValue[0], propertyValue[1])
					.put(key >>> 5, level+1, prop, propertyValue));
			}
			throw new IllegalStateException("We should never get here...");
		}
		
		Trie with(int index, Object value) {
			Object[] copy = elements.clone();
			copy[index] = value;
			return new Trie(copy);
		}
	}
}

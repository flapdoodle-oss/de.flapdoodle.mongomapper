package de.flapdoodle.mongomapper;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class IndexDefinition {

    private final ImmutableList<IndexedProperty> properties;
    private final boolean unique;

    /**
     *
     * Erzeuge eine neue <code>IndexDefinition</code> Instanz.
     *
     * @param unique
     * @param properties
     */
    public IndexDefinition(boolean unique, Collection<IndexedProperty> properties) {
        this.unique = unique;
        this.properties = ImmutableList.copyOf(properties);
    }

    /**
     *
     * Erzeuge eine neue <code>IndexDefinition</code> Instanz.
     *
     * @param unique
     * @param properties
     */
    public IndexDefinition(boolean unique, IndexedProperty... properties) {
        this(unique, Lists.newArrayList(properties));
    }

    /**
     * Boolean, ob der Index unique ist.
     *
     * @return
     */
    public boolean isUnique() {
        return unique;
    }

    /**
     * Reicht eine Liste von Properties raus.
     *
     * @return
     */
    public ImmutableList<IndexedProperty> properties() {
        return properties;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(unique: " + unique + ", " + properties + ")";
    }

    /**
     * Fügt den Properties prefixe an.
     *
     * @param prefix
     * @param indexDefinition
     * @return
     */
    public static IndexDefinition prefix(String prefix, IndexDefinition indexDefinition) {
        return prefix(prefix, Lists.<IndexedProperty> newArrayList(), indexDefinition);
    }

    /**
     * Fügt den Properties Prefixe an
     *
     * @param prefix
     * @param prepend
     * @param indexDefinition
     * @return
     */
    public static IndexDefinition prefix(String prefix, List<IndexedProperty> prepend, IndexDefinition indexDefinition) {
        ImmutableList<IndexedProperty> properties = ImmutableList.<IndexedProperty> builder().addAll(prepend)
                .addAll(IndexedProperty.prefix(prefix, indexDefinition.properties())).build();
        return new IndexDefinition(indexDefinition.isUnique(), properties);
    }
}

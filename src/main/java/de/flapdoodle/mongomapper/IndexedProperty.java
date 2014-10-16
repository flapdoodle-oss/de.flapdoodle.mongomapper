package de.flapdoodle.mongomapper;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class IndexedProperty {

    private final String name;
    private final Sort.Direction sort;

    /**
     *
     * Erzeuge eine neue <code>IndexedProperty</code> Instanz.
     *
     * @param name
     * @param sort
     */
    public IndexedProperty(String name, Sort.Direction sort) {
        this.name = name;
        this.sort = sort;
    }

    /**
     * Reicht den Namen als String raus.
     *
     * @return
     */
    public String name() {
        return name;
    }

    /**
     * Reicht die Sortierrichtung raus.
     *
     * @return
     */
    public Sort.Direction sort() {
        return sort;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + name + ":" + sort + ")";
    }

    /**
     * Statische Methode um eine IndexedProperty zu erzeugen.
     *
     * @param name
     * @param sort
     * @return IndexedProperty-Instanz
     */
    public static IndexedProperty property(String name, Sort.Direction sort) {
        return new IndexedProperty(name, sort);
    }

    /**
     * Statische Methode um eine IndexedProperty zu erzeugen. Die Sortierrichtung wird auf Ascent gesetzt.
     *
     * @param name
     * @return IndexedProperty-Instanz
     */
    public static IndexedProperty property(String name) {
        return new IndexedProperty(name, Sort.Direction.Ascent);
    }

    /**
     * Setzt bei einer Liste von IndexedProperty Prefixe
     *
     * @param prefix
     * @param properties
     * @return
     */
    public static ImmutableList<IndexedProperty> prefix(final String prefix, List<IndexedProperty> properties) {
        return ImmutableList.copyOf(Lists.transform(properties, new Function<IndexedProperty, IndexedProperty>() {
            @Override
            public IndexedProperty apply(IndexedProperty source) {
                return new IndexedProperty(PropertyNames.name(prefix, source.name()), source.sort());
            }
        }));
    }
}

package de.flapdoodle.mongomapper;

import java.util.List;

import com.google.common.collect.Lists;

@Deprecated
public abstract class PropertyNames {

    private PropertyNames() {
        // no instance
    }

    /**
     * generiert aus den Propertypath Propertynames und reicht diese raus
     *
     * @param propertyPath
     * @return
     */
    public static String name(String... propertyPath) {
        StringBuilder sb = new StringBuilder();
        for (String prop : propertyPath) {
            if (sb.length() > 0) {
                sb.append(".");
            }
            sb.append(prop);
        }
        return sb.toString();
    }

    public static String name(AttributeMapper<?> ... mappers) {
        List<String> names = Lists.newArrayList();
        for (AttributeMapper<?> m : mappers) {
            names.add(m.name());
        }
        return name(names.toArray(new String[names.size()]));
    }
}

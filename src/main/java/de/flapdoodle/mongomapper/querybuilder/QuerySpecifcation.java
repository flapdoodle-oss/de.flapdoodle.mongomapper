package de.flapdoodle.mongomapper.querybuilder;

public class QuerySpecifcation {

    private final char leftBracket;

    private final char rightBracket;

    private final boolean useSubQueries;
    
    public static final QuerySpecifcation SEQUENCE = new QuerySpecifcation('[', ']', false);
    
    public static final QuerySpecifcation ARRAY = new QuerySpecifcation('{', '}', true);

    private QuerySpecifcation(char leftBracket, char rightBracket, boolean useSubQueries) {
        this.leftBracket = leftBracket;
        this.rightBracket = rightBracket;
        this.useSubQueries = useSubQueries;
    }

    public boolean shouldUseSubQueries() {
        return this.useSubQueries;
    }

    public char leftBracket() {
        return this.leftBracket;
    }

    public char rightBracket() {
        return this.rightBracket;
    }
}

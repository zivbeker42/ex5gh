package filesprocessing.filters;

import java.io.File;

/**
 * filter which negates any other filter.
 */
public class negateFilter implements Filterable {

    /* The filter which the negate filter negates.*/
    Filterable filterToNegate;

    /**
     * Creates a new negate filter by a given filter.
     * @param filterToNegate The filter which the negate filter negates.
     */
    public negateFilter(Filterable filterToNegate) {
        this.filterToNegate = filterToNegate;
    }

    @Override
    public boolean filter(File file) {
        return !filterToNegate.filter(file);
    }
}

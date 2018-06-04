package fileprocessing.filters;

import java.io.File;

/**
 * A class implementing a filters which accepts all files.
 */
public class allFilter implements Filterable {

    /**
     * Creates a new all filter.
     */
    public allFilter() { }

    @Override
    public boolean filter(File file) {
        return true;
    }
}

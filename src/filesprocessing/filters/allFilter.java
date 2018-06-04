package filesprocessing.filters;

import java.io.File;

/**
 * A class implementing an all filter
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

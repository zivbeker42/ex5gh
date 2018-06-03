package fileprocessing.filters;

import java.io.File;

/**
 * A class implementing a filter that passes only files that starts with a certain prefix.
 */
public class prefixFilter implements Filterable {

    /*The prefix that the file should have to pass this filter.*/
    private String prefix;

    /**
     * Creates a new prefix filter with a given file prefix.
     * @param prefix The given prefix that the file should have to pass this filter.
     */
    public prefixFilter(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public boolean filter(File file) {
        return file.getName().startsWith(prefix);
    }
}

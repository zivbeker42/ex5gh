package fileprocessing.filters;

import java.io.File;

public class suffixFilter implements Filterable {

    /*The suffix that the file should have to pass this filter.*/
    private String suffix;

    /**
     * Creates a new suffix filter with a given file suffix.
     * @param suffix The given suffix that the file should have to pass this filter.
     */
    public suffixFilter(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public boolean filter(File file) {
        return file.getName().endsWith(suffix);
    }
}

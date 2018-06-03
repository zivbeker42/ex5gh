package fileprocessing.filters;

import java.io.File;

/**
 * A class implementing a filter that passes only files that contain a certain name.
 */
public class containsFilter implements Filterable{

    /*The name that the file should contain to pass this filter*/
    private String containedName;

    /**
     * Creates a new contains filter with a given file name to contain.
     * @param name The name that the file should contain to pass this filter.
     */
    public containsFilter(String name) {
        this.containedName = name;
    }

    @Override
    public boolean filter(File file) {
        return file.getName().contains(containedName);
    }
}

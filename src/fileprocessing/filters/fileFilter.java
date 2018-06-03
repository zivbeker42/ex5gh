package fileprocessing.filters;

import java.io.File;

/**
 * A class implementing a filter that passes only files that their name is a certain name.
 */
public class fileFilter implements Filterable {

    /*The file's name that passes this filter*/
    private String name;

    /**
     * Creates a new file filter with a given file's name.
     * @param name The name of the file that the filter passes.
     */
    public fileFilter(String name) {
        this.name = name;
    }

    @Override
    public boolean filter(File file) {
        return file.getName().equals(name);
    }
}

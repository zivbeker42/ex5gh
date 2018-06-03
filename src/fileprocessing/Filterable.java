package fileprocessing;

import java.io.File;

/**
 * an interface describing what a filter class must implement
 */
public interface Filterable {

    /**
     * a function deciding whether a file is filtered or not.
     * @param file the file.
     * @return true iff file is filtered by the filter object.
     */
    public boolean filter(File file);
}

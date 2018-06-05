package filters;

import java.io.File;

/**
 * A class implementing a filter that is described by a string checking the file's name. Meaning it is
 * responsible for file, contains, prefix, suffix filter.
 */
public class stringFilters implements Filterable {

    /*The type of the filter.*/
    private Filterable.filterType type;

    /*The string that passes the filter (depending on it's type) .*/
    private String value;

    /**
     * A constructor for a string filter by a given value that describes the filter and a string describing
     * it's type.
     *
     * @param type  the type of the filter.
     * @param value the value of the filter.
     * @throws Exception iff type is not a string filter.
     */
    public stringFilters(Filterable.filterType type, String value) {
        this.type = type;
        this.value = value;
    }


    @Override
    public boolean filter(File file) {
        if (type == Filterable.filterType.FILE) {
            return file.getName().equals(value);
        } else if (type == Filterable.filterType.CONTAINS) {
            return file.getName().contains(value);
        } else if (type == Filterable.filterType.SUFFIX) {
            return file.getName().endsWith(value);
        } else if (type == Filterable.filterType.PREFIX) {
            return file.getName().startsWith(value);
        } else {
            //unsupported
            return false;
        }

    }
}

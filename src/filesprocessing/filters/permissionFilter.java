package filesprocessing.filters;

import java.io.File;

/**
 * A filter responsible for filters of type "writable", "executable", "hidden".
 */
public class permissionFilter implements Filterable {

    /*The type of the filter. can be either "WRITABLE", "EXECUTABLE", "HIDDEN".*/
    private Filterable.filterType type;
    /*The boolean factor that says whether .*/
    private boolean value;

    /**
     * A constructor for greater than and smaller than filters.
     *
     * @param type  the type of the filter.
     * @param value the value of the filter.
     * @throws Exception
     */
    public permissionFilter(Filterable.filterType type, String value) {
        this.type = type;
        if (value.equals("YES")) {
            this.value = true;
        } else if (value.equals("NO")) {
            this.value = false;
        }
    }

    @Override
    public boolean filter(File file) {
        if (this.type == Filterable.filterType.EXECUTABLE) {
            return file.canExecute() == value;
        }
        if (this.type == Filterable.filterType.WRITABLE) {
            return file.canWrite() == value;
        }
        if (this.type == Filterable.filterType.HIDDEN) {
            return file.isHidden() == value;
        }
        return false;
    }
}

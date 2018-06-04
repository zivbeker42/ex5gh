package fileprocessing.filters;

import java.io.File;

public class permissionFilter implements Filterable {

    ///*The types that are allowed to the strings filters*/
    //private static String[] allowedTypes = {"WRITABLE", "EXECUTABLE", "hidden"};

    /*The type of the filter. can be either "WRITABLE", "EXECUTABLE", "hidden".*/
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

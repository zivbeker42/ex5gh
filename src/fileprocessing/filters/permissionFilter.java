package fileprocessing.filters;

import java.io.File;

public class permissionFilter implements Filterable {

    ///*The types that are allowed to the strings filters*/
    //private static String[] allowedTypes = {"WRITABLE", "EXECUTABLE", "hidden"};

    /*The type of the filter. can be either "WRITABLE", "EXECUTABLE", "hidden".*/
    private filterType type;

    /*The boolean factor that says whether .*/
    private boolean value;

    /**
     * A constructor for greater than and smaller than filters.
     * @param type the type of the filter.
     * @param value the value of the filter.
     * @throws Exception
     */
    public permissionFilter(String type, String value) throws Exception{
        this.type = new filterType(type);
        if (!this.type.permissionFilter) {
            throw new Exception("not a permission type.");
        }
        if (value.equals("YES")) {
            this.value = true;
        }
        else if (value.equals("NO")) {
            this.value = false;
        }



    }

    @Override
    public boolean filter(File file){
        if(type.name == filterType.filterName.EXECUTABLE) {
            return file.canExecute() == value;

        }
        if(type.name == filterType.filterName.WRITABLE) {
            return file.canWrite() == value;
        }
        if(type.name == filterType.filterName.HIDDEN) {
            return file.isHidden() == value;
        }
        return false;
    }
}

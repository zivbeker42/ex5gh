package fileprocessing.filters;

/**
 * A helper class represents a filter type class.
 */
public class filterType {
    /* filter types.*/
    public enum filterName {FILE, CONTAINS, PREFIX, SUFFIX, HIDDEN, EXECUTABLE, WRITABLE};
    filterName name;
    boolean stringFilter = false;
    boolean permissionFilter = false;
    boolean numberFilter = false;

    public filterType(String type) throws Exception{
        this.name = stringToFilterType(type);
        if(name == filterName.FILE || name == filterName.PREFIX || name == filterName.SUFFIX || name ==
                filterName.CONTAINS){
            stringFilter = true;
        }
        else if (name == filterName.HIDDEN || name == filterName.EXECUTABLE || name == filterName.WRITABLE){
            permissionFilter = true;
        }
    }

    /**
     * gets a string representing a filter type and creating a matching filter type of it.
     * @param type the string type of the filter.
     * @return the filter type.
     * @throws Exception
     */
    static filterName stringToFilterType(String type) throws Exception{
        switch (type) {
            case "hidden":
                return filterName.HIDDEN;
            case "executable":
                return filterName.EXECUTABLE;
            case "writable":
                return filterName.WRITABLE;
            case "file":
                return filterName.FILE;
            case "contains":
                return filterName.CONTAINS;
            case "prefix":
                return filterName.PREFIX;
            case "suffix":
                return filterName.SUFFIX;
            default:
                throw new Exception("not a type");
        }
    }
}

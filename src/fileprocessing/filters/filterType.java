package fileprocessing.filters;

/**
 * A helper class represents a filter type class.
 */
public class filterType {
    /* filter types.*/
    public enum filterName {FILE, CONTAINS, PREFIX, SUFFIX, HIDDEN, EXECUTABLE, WRITABLE, GREATER_THAN,
        SMALLER_THAN, BETWEEN}
    filterName name;
    boolean stringFilter = false;
    boolean permissionFilter = false;
    boolean numbersFilter = false;

    public filterType(String type) throws Exception{
        this.name = stringToFilterType(type);
        if(name == filterName.FILE || name == filterName.PREFIX || name == filterName.SUFFIX || name ==
                filterName.CONTAINS){
            stringFilter = true;
        }
        else if (name == filterName.HIDDEN || name == filterName.EXECUTABLE || name == filterName.WRITABLE){
            permissionFilter = true;
        }
        else if (name == filterName.GREATER_THAN || name == filterName.SMALLER_THAN || name == filterName
                .BETWEEN){
            numbersFilter = true;
        }
    }

    /**
     * gets a string representing a filter type and creating a matching filter type of it.
     * @param type the string type of the filter.
     * @return the filter name as an enum.
     * @throws Exception iff not getting a string that describes a filter.
     */
    private static filterName stringToFilterType(String type) throws Exception{
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
            case "greater_than":
                return filterName.GREATER_THAN;
            case "smaller_than":
                return filterName.SMALLER_THAN;
            case "between":
                return filterName.BETWEEN;
            default:
                throw new Exception("not a type");
        }
    }
}

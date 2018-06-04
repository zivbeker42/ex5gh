package fileprocessing.filters;

import java.io.File;
import java.util.Arrays;

/**
 * A class implementing a filter that is described by a string checking the file's name. Meaning it is
 * responsible for file, contains, prefix, suffix filter.
 */
public class stringFilters implements Filterable{

    /*The type of the filter.*/
    private filterType type;

    /*The string that passes the filter (depending on it's type) .*/
    private String value;

    /**
     * A constructor for a string filter by a given value that describes the filter and a string describing
     * it's type.
     * @param type the type of the filter.
     * @param value the value of the filter.
     * @throws Exception iff type is not a string filter.
     */
    public stringFilters(String type, String value) throws Exception{
        this.type = new filterType(type);
        this.value = value;
        if(!this.type.stringFilter){
            throw new Exception("not a string type");
        }

    }


    @Override
    public boolean filter(File file){
        if (type.name == filterType.filterName.FILE){
            return file.getName().equals(value);
        }
        else if(type.name == filterType.filterName.CONTAINS){
            return file.getName().contains(value);
        }
        else if(type.name == filterType.filterName.SUFFIX){
            return file.getName().endsWith(value);
        }
        else {
            return file.getName().startsWith(value);
        }

    }
}

package fileprocessing.filters;

import java.io.File;
import java.util.Arrays;

/**
 * A class implementing a filter that is described by a string checking the file's name.
 */
public class stringFilters implements Filterable{

    /*The types that are allowed to the strings filters*/
    private static String[] allowedTypes = {"file", "contains", "suffix", "prefix"};

    /*The type of the filter. can be either "file", "contains", "suffix", "prefix".*/
    private String type;

    /*The string that can pass the filter (depending on it's type) .*/
    private String value;

    /**
     * A constructor for greater than and smaller than filters.
     * @param type the type of the filter.
     * @param value the value of the filter.
     * @throws Exception
     */
    public stringFilters(String type, String value) throws Exception{
        this.type = type;
        if(Arrays.asList(allowedTypes).contains(type)){
            this.value = value;
        }
        else{
            throw new Exception("not a type");
        }

    }


    @Override
    public boolean filter(File file){
        if (type.equals("file")){
            return file.getName().equals(value);
        }
        else if(type.equals("contains")){
            return file.getName().contains(value);
        }
        else if(type.equals("suffix")){
            return file.getName().endsWith(value);
        }
        else {
            return file.getName().startsWith(value);
        }

    }
}

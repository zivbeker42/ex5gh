package fileprocessing.filters;

import java.io.File;
import java.util.logging.Filter;

/**
 * A class implementing a filter that is described by a lower bound and/or an upper bound of the file size.
 */
public class numbersFilter implements Filterable{

    /*The type of the filter.*/
    private Filterable.filterType type;

    /*The min value of the file size that can pass greater than and BETWEEN filters.*/
    private double minValue = 0.0;

    /*The max value of the file size that can pass smaller than and BETWEEN filters.*/
    private double maxValue = Double.POSITIVE_INFINITY;

    /**
     * A constructor for greater than and smaller than filters.
     * @param type the type of the filter.
     * @param value the value of the filter.
     * @throws Exception iff type is not describing a numbers filter
     */
    public numbersFilter(Filterable.filterType type, double value) throws Exception{
        this.type = type;
        if(this.type == Filterable.filterType.GREATER_THAN){
            this.minValue = value;
        }
        else if(this.type == Filterable.filterType.SMALLER_THAN){
            maxValue = value;
        }
        else{
            throw new Exception("not a valid type");
        }

    }

    /**
     * A constructor for BETWEEN filter.
     * @param type type of the filter (for checking purposes).
     * @param min the min value of the filter.
     * @param max the max value of the filter.
     * @throws Exception iff max value is smaller than min
     */
    public numbersFilter(Filterable.filterType type, double min, double max) throws Exception {
        this.type = type;
        if(this.type == Filterable.filterType.BETWEEN){
            this.minValue = min;
            this.maxValue = max;
            if (minValue > maxValue){
                throw new Exception("max is smaller than min");
            }
        }
        else{
            throw new Exception("not a valid type");
        }
    }


    @Override
    public boolean filter(File file) {
        return  (double) (file.length()/1024) >= minValue & (double) (file.length()/1024) <= maxValue;
    }
}

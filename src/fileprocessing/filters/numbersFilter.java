package fileprocessing.filters;

import java.io.File;

/**
 * A class implementing a filter that is described by a lower bound and an upper bound of the file size.
 */
public class numbersFilter implements Filterable{

    /*The type of the filter. nac be either "greater_than", "between", "smaller_than"*/
    private String type;

    /*The min value of the file size that can pass greater than and between filters.*/
    private double minValue = 0.0;

    /*The max value of the file size that can pass smaller than and between filters.*/
    private double maxValue = Double.POSITIVE_INFINITY;

    /**
     * A constructor for greater than and smaller than filters.
     * @param type the type of the filter.
     * @param value the value of the filter.
     * @throws Exception
     */
    public numbersFilter(String type, double value) throws Exception{
        this.type = type;
        if(type.equals("greater_than")){
            this.minValue = value;
        }
        else if(type.equals("smaller_than")){
            maxValue = value;
        }
        else{
            throw new Exception("not a valid type");
        }

    }

    /**
     * A constructor for between filter.
     * @param type type of the filter (for checking purposes).
     * @param min the min value of the filter.
     * @param max the max value of the filter.
     * @throws Exception
     */
    public numbersFilter(String type, double min, double max) throws Exception {
        this.type = type;
        if(type.equals("between")){
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

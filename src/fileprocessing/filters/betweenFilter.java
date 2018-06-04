package fileprocessing.filters;

import java.io.File;

/**
 * A class representing a filter that passes only files that their size (in kb) is bigger than a certain
 * lower bound and smaller than a certain upper bound.
 */
public class betweenFilter implements Filterable{
    /* The lower bound of the file size that passes this filter.*/
    private double lowerBound;
    /* The upper bound of the file size that passes this filter.*/
    private double upperBound;

    /**
     * Creates a new greater then filter.
     * @param lowerBound The lower bound of the BETWEEN filter
     * @param upperBound The upper bound of the BETWEEN filter.
     */
    public betweenFilter (double lowerBound, double upperBound) throws Exception{
        if(upperBound < lowerBound)
            throw new Exception("upper is lower than lower");
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    @Override
    public boolean filter(File file) {
        return (double) (file.length()/1024) >= lowerBound & (double) (file.length()/1024) <= upperBound;
    }
}

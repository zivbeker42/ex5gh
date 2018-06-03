package fileprocessing.filters;

import java.io.File;

/**
 * A class implementing a filter that passes only files that their size (in kb) is strictly smaller than a
 * certain upper bound.
 */
public class smaller_thanFilter implements Filterable{
    /* The upper bound of the file size that passes this filter.*/
    private double upperBound;

    /**
     * Creates a new greater then filter.
     * @param value The upper bound of the smaller than filter
     */
    public smaller_thanFilter(double value) {
        this.upperBound = value;
    }

    @Override
    public boolean filter(File file) {
        return (double) file.length()/1024 < upperBound;
    }
}

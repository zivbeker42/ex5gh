package fileprocessing;

import java.io.File;

/**
 * A class implementing a filter that passes only files that their size (in kb) is strictly bigger than a
 * certain lower bound.
 */
public class greater_thanFilter implements Filterable{

    /* The lower bound of the file size that passes this filter.*/
    private double lowerBound;

    /**
     * Creates a new greater then filter.
     * @param value The lower Bound of the greater than filter
     */
    public greater_thanFilter(double value) {
        this.lowerBound = value;
    }

    @Override
    public boolean filter(File file) {
        return (double) file.length()/1024 > lowerBound;
    }
}

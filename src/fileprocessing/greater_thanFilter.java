package fileprocessing;

import java.io.File;

public class greater_thanFilter implements Filterable{

    private final String name = "greater than";
    private double value;

    public greater_thanFilter(double value) {
        this.value = value;
    }

    @Override
    public boolean filter(File file) {
        return (double) file.length()/1024 > value;
    }
}

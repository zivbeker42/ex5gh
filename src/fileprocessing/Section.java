package fileprocessing;
import

import java.io.File;
import java.util.Comparator;

public class Section {
    private Filterable filter;
    private Comparator<File> order;

    public Section(Filterable filter, Comparator<File> order) {
        this.filter = filter;
        this.order = order;
    }

}

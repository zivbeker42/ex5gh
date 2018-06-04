package filesprocessing;
import filesprocessing.filters.Filterable;

import java.io.File;
import java.util.Comparator;

/**
 * A class representing a section.
 */
public class Section {

    /*The filter of the section.*/
    private Filterable filter;
    /*The order of the section.*/
    private Comparator<File> order;

    /**
     * Creates a new section by a filter and an order.
     * @param filter the given filter.
     * @param order the given order.
     */
    public Section(Filterable filter, Comparator<File> order) {
        this.filter = filter;
        this.order = order;
    }

    /**
     * @return the filter of the section.
     */
    public Filterable getFilter() {
        return filter;
    }

    /**
     * @return the order of the section.
     */
    public Comparator<File> getOrder() {
        return order;
    }
}

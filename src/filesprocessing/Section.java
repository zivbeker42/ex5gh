package filesprocessing;
import filters.Filterable;

import java.io.File;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * A class representing a section.
 */
public class Section {

    /*The filter of the section.*/
    private Filterable filter;
    /*The order of the section.*/
    private Comparator<File> order;
    private LinkedList<Type1Exception> errors;

    /**
     * Creates a new section by a filter and an order.
     * @param filter the given filter.
     * @param order the given order.
     */
    public Section(Filterable filter, Comparator<File> order, LinkedList<Type1Exception> errors) {
        this.filter = filter;
        this.order = order;
        this.errors = errors;
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

    public LinkedList<Type1Exception> getErrors() {
        return errors;
    }
}

package filesprocessing;

import filters.*;
import orderTypes.OrderException;
import orderTypes.Orders;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Section factory class is a static class that hansles the parsing of the command file.
 * it's main method is parse which reads the command file and returns a linkedlist of Section object that contains
 * all the neccessary data for the program to run.
 */

public class SectionFactory {


    /**
     * the number of lines per section
     */
    private static final int LINESPERSECTION = 4;
    /**
     * saved Strings
     */
    private static final String FILTER = "FILTER";
    private static final String ORDER = "ORDER";
    private static final String REVERSE = "REVERSE";
    private static final String NOT = "NOT";

    /**
     * this method recieves a File object that points to the commands file. it reads it and interprets it. it returns a
     * linkedlist of sections that contain all the necessary data for the program to run.
     * this function reads every line in the command file and checks if it matches the expected pattern.
     *
     * @param cmdfile the command file
     * @return a linkedlist of Section objects, based of the content of the command file.
     * @throws Type2Exception throws in case of bad input
     */
    public static LinkedList<Section> parse(File cmdfile) throws Type2Exception {

        LinkedList<String> rawtext = fileToString(cmdfile);//see filetostring
        LinkedList<Section> lst = new LinkedList<>();//the list to be returned
        /*
          this buffer is created in order to force the number of rows to be 4. that way any missing segments
          of the command file will be noticed
         */


        int len = rawtext.size();
        int linecount = 1;//counts the lines
        int part = 1;//holds the current part that we are now parsing, can be:1-FILTER, 2-filter content, 3-ORDER, 4-
        //order content

        //the parameters to be passed to the Section object
        Filterable filter = null;
        Comparator<File> order;
        LinkedList<Type1Exception> errors = new LinkedList<>();

        //the loop itself
        for (String line : rawtext) {
            int linenum = part % LINESPERSECTION; //this variable saves the part is the section that is being currently
            //obsereved

            if (linenum == 1) {//FILTER expected
                validateFilter(line);
                if (linecount == len) {
                    throw new FilterException();
                }
            } else if (linenum == 2) {//filter content expected
                if (linecount == len) {
                    throw new OrderException();
                }
                try {
                    filter = interpretFilter(line, linecount);
                } catch (Type1Exception error) {
                    errors.add(error);
                    filter = getDeafaultFilter();
                }
            } else if (linenum == 3) {//order expected
                validateOrder(line);
                if (linecount == len) {
                    order = getDeafaultComparator();
                    lst.add(new Section(filter, order, errors));
                    errors = new LinkedList<>();
                }
            } else if (linenum == 0) {//order content expected
                if (line.equals("FILTER")) {//checks if the order content was skipped and a new section starts
                    order = getDeafaultComparator();
                    part++;
                } else {
                    try {
                        order = interpretOrder(line, linecount);
                    } catch (Type1Exception error) {
                        errors.add(error);
                        order = getDeafaultComparator();
                    }
                }
                lst.add(new Section(filter, order, errors));// adds a new section to the list
                errors = new LinkedList<>();//clears the errors list pointer
            }
            linecount++;
            part++;
        }

        return lst;


    }

    /**
     * this function reads a line and returns an interpretation of the line. it tries to read the line assuming it
     * contains instructions for the ORDER part
     * @param line the line to read
     * @param linenum the number of the line, for error handling
     * @return an Orderable object
     * @throws Type1Exception in case the line is not readable.
     */
    private static Comparator<File> interpretOrder(String line, int linenum) throws Type1Exception {

        if (line.matches(".*#" + REVERSE)) {
            int j = line.indexOf("#" + REVERSE);
            String newline = line.substring(0, j);
            return interpretOrder(newline, linenum).reversed();
        } else {
            if (line.matches("abs")) {
                return Orders.absComparator();
            } else if (line.matches("type")) {
                return Orders.typeComparator();
            } else if (line.matches("size")) {
                return Orders.sizeComparator();
            }
        }
        throw new Type1Exception(linenum);
    }


    /**
     * this function reads a line and returns an interpretation of the line. it tries to read the line assuming it
     * contains instructions for the FILTER part
     * @param line the line to read
     * @param linenum the number of the line, for error handling
     * @return a Filter object
     * @throws Type1Exception in case the line is not readable.
     */

    private static Filterable interpretFilter(String line, int linenum) throws Type1Exception {
        if (line.matches(".*#" + NOT)) {
            //this part checks if the line ends with NOT and if it is it calls itself again
            int j = line.indexOf("#" + NOT);
            String newline = line.substring(0, j);
            return new negateFilter(interpretFilter(newline, linenum));
        } else {

            if (line.matches("greater_than#((\\d)+(\\.\\d+)?)")) {
                String stringValue = line.split("#")[1];
                double value = Double.valueOf(stringValue);
                return new numbersFilter(Filterable.filterType.GREATER_THAN, value);
            } else if (line.matches("between#((\\d)+(\\.\\d+)?)#((\\d)+(\\.\\d+)?)")) {

                String stringValue1 = line.split("#")[1];
                double value1 = Double.valueOf(stringValue1);
                String stringValue2 = line.split("#")[2];
                double value2 = Double.valueOf(stringValue2);

                if (value1 < value2 & 0 <= value1)
                    return new numbersFilter(Filterable.filterType.BETWEEN, value1, value2);
            } else if (line.matches("smaller_than#((\\d)+(\\.\\d+)?)")) {
                String stringValue = line.split("#")[1];
                double value = Double.valueOf(stringValue);
                return new numbersFilter(Filterable.filterType.SMALLER_THAN, value);

            } else if (line.matches("file#[\\w\\s/\\-.]+")) {
                String stringValue = line.split("#")[1];
                return new stringFilters(Filterable.filterType.FILE, stringValue);

            } else if (line.matches("contains#[\\w\\s/\\-.]+")) {
                String stringValue = line.split("#")[1];
                return new stringFilters(Filterable.filterType.CONTAINS, stringValue);

            } else if (line.matches("prefix#[\\w\\s/\\-.]+")) {
                String stringValue = line.split("#")[1];
                return new stringFilters(Filterable.filterType.PREFIX, stringValue);

            } else if (line.matches("suffix#[\\w\\s/\\-.]+")) {
                String stringValue = line.split("#")[1];
                return new stringFilters(Filterable.filterType.SUFFIX, stringValue);

            } else if (line.matches("writable#(YES|NO)")) {
                String stringValue = line.split("#")[1];
                return new permissionFilter(Filterable.filterType.WRITABLE, stringValue);

            } else if (line.matches("executable#(YES|NO)")) {
                String stringValue = line.split("#")[1];
                return new permissionFilter(Filterable.filterType.EXECUTABLE, stringValue);

            } else if (line.matches("hidden#(YES|NO)")) {
                String stringValue = line.split("#")[1];
                return new permissionFilter(Filterable.filterType.HIDDEN, stringValue);

            } else if (line.matches("all")) {
                return new allFilter();
            }
            throw new Type1Exception(linenum);

        }

    }

    /**
     * the function validates that the line contains only the correct text for the FILTER part and throws an error other
     * wise
     * @param line the line to validate
     * @throws FilterException
     */
    private static void validateFilter(String line) throws FilterException {
        if (!line.equals(FILTER)) {
            throw new FilterException();
        }
    }

    /**
     * the function validates that the line contains only the correct text for the ORDER part and throws an error other
     * wise
     * @param line the line to validate
     * @throws OrderException
     */
    private static void validateOrder(String line) throws OrderException {
        if (!line.equals(ORDER)) {
            throw new OrderException();
        }
    }

    //the default section component in the case where there is an error
    private static Comparator<File> getDeafaultComparator() {
        return Orders.absComparator();
    }

    private static Filterable getDeafaultFilter() {
        return new allFilter();
    }

    /**
     * this fucntion recieves a file object and return its text in the form of a linked list of strings
     * @param file the file to read
     * @return a linked list of strings that are the lines of the text files
     */
    private static LinkedList<String> fileToString(File file) {
        LinkedList<String> lst = new LinkedList<>();

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                lst.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return lst;

    }
}

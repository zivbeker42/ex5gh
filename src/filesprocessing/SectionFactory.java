package filesprocessing;

import filesprocessing.filters.*;
import filesprocessing.orderTypes.Orders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;


public class SectionFactory {
    private static final int LINESPERSECTION = 4;
    private static final String BUFFERSTRING = "***AUTOMATIC_BUFFER***";
    private static final String FILTER = "FILTER";
    private static final String ORDER = "ORDER";
    private static final String REVERSE = "REVERSE";
    private static final String NOT = "NOT";


    public static class Type2Error extends IOException {
    }

    public static class OrderError extends Type2Error {

    }

    public static class FilterError extends Type2Error {
    }

    public SectionFactory() {
    }

    public static LinkedList<Section> parse(File cmdfile) throws Type2Error {
        LinkedList<String> rawtext = fileToString(cmdfile);
        LinkedList<Section> lst = new LinkedList<Section>();
        int len = rawtext.size();
        LinkedList<String> buffer = new LinkedList<>();
        /**
         * this buffer is created in order to force the number of rows to be 4. that way any missing segments
         * of the command file will be noticed
         */
//        for (int j = 0; j < LINESPERSECTION - 1 - (len + LINESPERSECTION - 1) % LINESPERSECTION; j++) {
//            buffer.add(BUFFERSTRING);
//        }
//        rawtext.addAll(buffer);

        len = rawtext.size();
        int linecount = 1;//counts the lines
        int part = 1;//holds the current part that we are now parsing, can be:1-FILTER, 2-filter content, 3-ORDER, 4-
        //order content
        Filterable filter = null;
        Comparator<File> order;
        LinkedList<Type1Exception> errors = new LinkedList<Type1Exception>();
        for (String line : rawtext) {
            int linenum = part % LINESPERSECTION; //this variable saves the part is the section that is being currently
            //obsereved
            if (linenum == 1) {
                validateFilter(line);
                if(linecount==len) {
                    throw new OrderError();
                }
                } else if (linenum == 2) {
                if(linecount==len) {
                    throw new OrderError();
                }
                try {
                    filter = interpretFilter(line,linecount);
                } catch (Type1Exception error) {
                    errors.add(error);
                    filter = getDeafaultFilter();
                }
            } else if (linenum == 3) {
                validateOrder(line);
                if(linecount==len){
                    order=getDeafaultComparator();
                    lst.add(new Section(filter, order, errors));
                }
            } else if (linenum == 0) {
                if (line.equals(BUFFERSTRING)) {
                    order = getDeafaultComparator();
                } else if(line.equals("FILTER")) {
                    order = getDeafaultComparator();
                    part++;
                }else{
                    try {
                        order = interpretOrder(line, linecount);
                    } catch (Type1Exception error) {
                        errors.add(error);
                        order = getDeafaultComparator();
                    }
                }
                lst.add(new Section(filter, order, errors));
            } else {
                //if in the future more segemets will be present in each section
            }
            linecount++;
            part++;
        }

        return lst;


    }

    private static Comparator<File> interpretOrder(String line, int linenum) throws Type1Exception {
        if (line.matches(".*#"+REVERSE)) {
            int j = line.indexOf("#"+REVERSE);
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

    private static void validateOrder(String line) throws OrderError {
        if (!line.equals(ORDER)) {
            throw new OrderError();
        }


    }

    private static Filterable interpretFilter(String line , int i) throws Type1Exception {
        if (line.matches(".*#"+NOT)) {
            int j = line.indexOf("#"+NOT);
            String newline = line.substring(0, j);
            return new negateFilter(interpretFilter(newline,i));
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

                if (value1 < value2 & 0 < value1)
                    return new numbersFilter(Filterable.filterType.BETWEEN, value1, value2);
            } else if (line.matches("smaller_than#((\\d)+(\\.\\d+)?)")) {
                String stringValue = line.split("#")[1];
                double value = Double.valueOf(stringValue);
                return new numbersFilter(Filterable.filterType.SMALLER_THAN, value);

            } else if (line.matches("file#[\\w\\s/\\-\\.]+")) {
                String stringValue = line.split("#")[1];
                return new stringFilters(Filterable.filterType.FILE, stringValue);

            } else if (line.matches("contains#((\\d)+(\\.\\d+)?)")) {
                String stringValue = line.split("#")[1];
                return new stringFilters(Filterable.filterType.CONTAINS, stringValue);

            } else if (line.matches("prefix#((\\d)+(\\.\\d+)?)")) {
                String stringValue = line.split("#")[1];
                return new stringFilters(Filterable.filterType.PREFIX, stringValue);

            } else if (line.matches("suffix#((\\d)+(\\.\\d+)?)")) {
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
            throw new Type1Exception(i);

        }

    }

    private static void validateFilter(String line) throws FilterError {
        if (!line.equals(FILTER)) {
            throw new FilterError();
        }
    }

    private static Comparator<File> getDeafaultComparator() {
        return Orders.absComparator();
    }

    private static Filterable getDeafaultFilter() {
        return new allFilter();
    }

    public static LinkedList<String> fileToString(File file) {
        /**
         * should be public
         */
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        LinkedList<String> lst = new LinkedList<String>();
        while (scanner.hasNextLine()) {
            lst.add(scanner.nextLine());
        }
        return lst;

    }
}

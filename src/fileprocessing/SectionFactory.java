package fileprocessing;

import com.sun.org.apache.xpath.internal.operations.Or;
import fileprocessing.filters.Filterable;
import fileprocessing.filters.numbersFilter;
import fileprocessing.orderTypes.Orders;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;


public class SectionFactory {

    public SectionFactory() {
    }

    public static LinkedList<Section> parse(File cmdfile) {
        LinkedList<String> rawtext = fileToString(cmdfile);
        LinkedList<Section> lst = new LinkedList<Section>();
        int i = 0;
        Filterable filter = null;
        Comparator<File> order;
        for (String line : rawtext) {
            if (i % 4 == 0) {
                validateFilter(line, i);
            } else if (i % 4 == 1) {
                filter = interpretFilter(line, i);
            } else if (i % 4 == 2) {
                validateOrder(line, i);
            } else if (i % 4 == 3) {
                order = interpretOrder(line, i);
                lst.add(new Section(filter, order));
            }
            i++;
        }
        return lst;


    }

    private static Comparator<File> interpretOrder(String line, int i) {
        if (line.matches(".*#REVERSED")) {
            int j = line.indexOf("#REVERSED");
            String newline = line.substring(0,j);
            return interpretOrder(newline, i).reversed();
        } else {
            if (line.matches("abs")) {
                return Orders.absComparator();
            } else if (line.matches("type")) {
                return Orders.typeComparator();
            } else if (line.matches("size")) {
                return Orders.sizeComparator();
            }
        }
        //throw error
        return Orders.absComparator();
    }

    private static void validateOrder(String line, int i) {
        if (line.equals("ORDER")) {
            //everything is okey
        } else {
            //throw type 2 exception
            System.out.println("no order");
        }


    }

    private static Filterable interpretFilter(String line, int i) {
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

        } else if (line.matches("file#[\\w\\s/\\-\\.]+")) {

        } else if (line.matches("contains#((\\d)+(\\.\\d+)?)")) {

        } else if (line.matches("prefix#((\\d)+(\\.\\d+)?)")) {

        } else if (line.matches("suffix#((\\d)+(\\.\\d+)?)")) {

        } else if (line.matches("writable#(YES|NO)")) {

        } else if (line.matches("executable#(YES|NO)")) {

        } else if (line.matches("hidden#(YES|NO)")) {

        } else if (line.matches("all")) {

        }
        return null;
    }

    private static void validateFilter(String line, int i) {
        if (line.equals("FILTER")) {
        } else {
            //exeption
            System.out.println("no filter");
        }
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

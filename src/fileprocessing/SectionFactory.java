package fileprocessing;

import fileprocessing.filters.Filterable;
import fileprocessing.filters.numbersFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;


public class SectionFactory {

    public SectionFactory() {
    }

    public static LinkedList<Section> parse(File cmdfile){
        LinkedList<String> rawtext = fileToString(cmdfile);
        LinkedList<Section> lst = new LinkedList<Section>();
        int i=0;
        Filterable filter = null;
        Comparator<File> order;
        for(String line :  rawtext){
            if(i%4==0){
                validateFilter(line, i);
            }else if(i%4==1){
                filter = interpretFilter(line, i);
            }else if(i%4==2){
                validateOrder(line,i);
            }else if(i%4==3){
                order= interpretOrder(line,i);
                lst.add(new Section(filter, order));
            }
            i++;
        }
        return lst;


    }

    private static Comparator<File> interpretOrder(String line, int i) {
        String[] partitioned= line.split("#");
        return null;
    }

    private static void validateOrder(String line, int i) {
        if(line.equals("ORDER")){
            //everything is okey
        }else{
            //throw type 2 exception
        }


    }

    private static Filterable interpretFilter(String line, int i) {
        if (line.matches("greater_than#((\\d)+(\\.\\d+)?)")){
            String stringValue = line.split("#")[1];
            double value=Double.valueOf(stringValue);
            return new numbersFilter(Filterable.filterType.GREATER_THAN, value);
        }else if(line.matches("between#((\\d)+(\\.\\d+)?)#((\\d)+(\\.\\d+)?)")){

        }else if(line.matches("smaller_than#((\\d)+(\\.\\d+)?)")){

        }else if(line.matches("file#[\\w\\s/\\-\\.]+")){

        }else if(line.matches("contains#((\\d)+(\\.\\d+)?)")){

        }else if(line.matches("prefix#((\\d)+(\\.\\d+)?)")){

        }else if(line.matches("suffix#((\\d)+(\\.\\d+)?)")){

        }else if(line.matches("writable#(YES|NO)")){

        }else if(line.matches("executable#(YES|NO)")){

        }else if(line.matches("hidden#(YES|NO)")){

        }else if(line.matches("all")){

        }
        return null;
    }

    private static int validateFilter(String line, int i) {
        if(line.equals("FILTER")){
            return 1;
        }else{
            //exeption
            return 2;
        }
    }


    private static LinkedList<String> fileToString(File file){
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        LinkedList<String> lst = new LinkedList<String>();
        while(scanner.hasNextLine()){
            lst.add(scanner.nextLine());
        }
        return lst;

    }
}

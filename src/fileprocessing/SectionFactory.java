package fileprocessing;

import fileprocessing.filters.Filterable;

import java.io.File;
import java.io.FileReader;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;
import

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
                validateFilter(line);
            }else if(i%4==1){
                filter = interpretFilter(line);
            }else if(i%4==2){
                validateOrder(line);
            }else if(i%4==3){
                order= interpretOrder(line);
                lst.add(new Section(filter, order));
            }
            i++;
        }
        return lst;


    }

    private static Comparator<File> interpretOrder(String line) {


    }

    private static void validateOrder(String line) {


    }

    private static Filterable interpretFilter(String line) {

    }

    private static int validateFilter(String line) {
        if(line=="FILTER"){
            return 1;
        }else{
            //exeption
            return 2;
        }
    }


    private static LinkedList<String> fileToString(File file){
        Scanner scanner = new Scanner(file);
        LinkedList<String> lst = new LinkedList<String>();
        while(scanner.hasNextLine()){
            lst.add(scanner.nextLine());
        }
        return lst;
    }
}

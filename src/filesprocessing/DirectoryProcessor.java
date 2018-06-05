package filesprocessing;

import filters.FilterException;
import orderTypes.OrderException;

import java.io.File;
import java.util.LinkedList;


/**
 * A class which handles the whole process of a directory by a given command file.
 */
public class DirectoryProcessor {




    /**
     * prints the desired output with the given command file and source directory.
     *
     * @param args the source directory (at args[0]) and command file (at args[1])
     */

    private static final String ERRORSTRING = "ERROR";
    private static final String ORDERSTRING = "ORDER";
    private static final String FILTERSTRING = "FILTER";
    private static final String MESSAGE = "subsection is missing or not written correctly";
    private static final String MESSAGEARGS = "there should be only two args";
    private static final String MESSAGEFILES = "Files paths are missing or corrupted";

    public static void main(String[] args) throws Type2Exception{

        if(args.length!=2){
            System.err.println(MESSAGEARGS);
            throw new ArgsException();
        }

        try {
            File sourcedir = new File(args[0]);
            File cmdFile = new File(args[1]);
            try {
                LinkedList<Section> sections = SectionFactory.parse(cmdFile);
                for (Section s :sections) {
                    printSection(s, sourcedir);
                }
            }catch (OrderException error){
                System.err.println(ERRORSTRING+" "+ ORDERSTRING+" "+MESSAGE);
            }catch (FilterException error){
                System.err.println(ERRORSTRING+" "+ FILTERSTRING+" "+MESSAGE);
            } catch (Type2Exception error) {
                System.err.println("failed to open files");
            }
        }catch (Exception e){
            System.err.println(MESSAGEFILES);
            throw new Type2Exception();
        }



    }

    /**
     * prints all the filtered files in a given directory in the correct order stated by section s.
     *
     * @param s         the section that describes the order and filter.
     * @param directory the directory of the files.
     */
    private static void printSection(Section s, File directory) {

        printWarnings(s);
        // create filtered files
        File[] dirFiles = directory.listFiles();
        if (dirFiles == null)
            return;
        LinkedList<File> Filtered;
        Filtered = new LinkedList<>();
        for (File file : dirFiles) {
            if (s.getFilter().filter(file)) {
                Filtered.add(file);
            }
        }

        //sort files
        Filtered.sort(s.getOrder());

        //print by order
        for (File file : Filtered) {
            System.out.println(file.getName());
        }

    }

    private static void printWarnings(Section s){
        for(Type1Exception warning : s.getErrors()){
            warning.print();
        }
    }
}

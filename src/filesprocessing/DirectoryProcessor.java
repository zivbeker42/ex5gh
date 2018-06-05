package filesprocessing;

import java.io.File;
import java.io.IOException;
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
    public static void main(String[] args) {



        File sourcedir = new File(args[0]);
        File cmdFile = new File(args[1]);
        try {
            LinkedList<Section> Sections = SectionFactory.parse(cmdFile);
            while (!Sections.isEmpty()) {
                Section s = Sections.removeFirst();
                printSection(s, sourcedir);
            }
        }catch (SectionFactory.OrderError error){
            System.err.println("ERROR: ORDER subsection is missing or not written correctly");
        }catch (SectionFactory.FilterError error){
            System.err.println("ERROR: FILTER subsection is missing or not written correctly");
        } catch (SectionFactory.Type2Error error) {

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
        LinkedList<File> Filtered = new LinkedList<File>();
        for (File file : dirFiles) {
            if (s.getFilter().filter(file)) {
                Filtered.add(file);
            }
        }

        //sort files
        Filtered.sort(s.getOrder());

        //print by order
        while (!Filtered.isEmpty()) {
            System.out.println(Filtered.getFirst().getName());
            Filtered.removeFirst();
        }

    }

    private static void printWarnings(Section s){
        while(!s.getErrors().isEmpty()){
            s.getErrors().getFirst().print();
            s.getErrors().removeFirst();
        }
    }
}

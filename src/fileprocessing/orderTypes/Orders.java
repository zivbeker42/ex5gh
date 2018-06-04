package fileprocessing.orderTypes;

import java.io.File;
import java.util.Comparator;

/**
 * A class containing the different comparators.
 */
public class Orders {

    /**
     * Creates an abs comparator
     * @return abs comparator
     */
    public static Comparator<File> absComparator(){
        return (file1, file2) -> file1.getAbsolutePath().compareTo(file2.getAbsolutePath());
    }

    /**
     * Creates a type comparator
     * @return type comparator
     */
    public static Comparator<File> typeComparator(){
        return (file1, file2) -> getType(file1).compareTo(getType(file2));
    }

    /**
     * Creates a size comparator
     * @return size comparator
     */
    public static Comparator<File> sizeComparator(){
        return new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                Long l1 = file1.length();
                Long l2 = file2.length();
                return l1.compareTo(l2);
            }
        };
    }

    /**
     * finds and returns the type of a given file (stated as the substring after the last dot).
     * @param file the given file.
     * @return the type of the file.
     */
    private static String getType(File file){
        String path = file.getAbsolutePath();
        return path.substring(path.lastIndexOf('.'));
    }
}

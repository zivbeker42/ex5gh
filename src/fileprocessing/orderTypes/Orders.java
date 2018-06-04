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

    
    public static Comparator<File> typeComparator(){
        return (file1, file2) -> getType(file1).compareTo(getType(file2));
    }

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

    private static String getType(File file){
        String path = file.getAbsolutePath();
        return path.substring(path.lastIndexOf('.'));
    }
}

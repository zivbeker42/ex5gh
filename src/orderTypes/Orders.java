package orderTypes;

import java.io.File;
import java.util.Comparator;

/**
 * A class containing the different comparators.
 */
public class Orders {

    /**
     * Creates an abs comparator
     *
     * @return abs comparator
     */
    public static Comparator<File> absComparator() {
        return (file1, file2) -> file1.getAbsolutePath().compareTo(file2.getAbsolutePath());
    }

    /**
     * Creates a type comparator
     *
     * @return type comparator
     */
    public static Comparator<File> typeComparator() {

        return new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                String type1 = getType(file1);
                String type2 = getType(file2);
                if (!type1.equals(type2)) {
                    return type1.compareTo(type2);
                } else {
                    return absComparator().compare(file1, file2);
                }
            }
        };
//        return (file1, file2) -> getType(file1).compareTo(getType(file2));
    }

    /**
     * Creates a size comparator
     *
     * @return size comparator
     */
    public static Comparator<File> sizeComparator() {
        return new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                Long l1 = file1.length();
                Long l2 = file2.length();
                if (l1 != l2) {
                    return l1.compareTo(l2);
                } else {
                    return absComparator().compare(file1, file2);
                }
            }
        };
    }

    /**
     * finds and returns the type of a given file (stated as the substring after the last dot).
     *
     * @param file the given file.
     * @return the type of the file.
     */
    private static String getType(File file) {
        String[] path = file.getAbsolutePath().split("\\.");
        int len = path.length;
        if (len > 1) {
            return path[len - 1];
        } else {
            return "";
        }
    }
}

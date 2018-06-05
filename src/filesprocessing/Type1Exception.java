package filesprocessing;

/**
 * A class representing a type1 exception.
 */
public class Type1Exception extends Exception{

    /*The line in which the exception occurred.*/
    private int line;
    /*The message the exception prints.*/
    private static final String message = "Warning in line ";

    /**
     * Creates a new Type1Exception.
     * @param line the line in which the exception occurred.
     */
    public Type1Exception(int line) {
        this.line = line;
    }

    /**
     * prints the exception.
     */
    public void print() {
        System.err.println(message+line);
    }


}

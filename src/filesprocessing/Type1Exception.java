package filesprocessing;

public class Type1Exception extends Exception{

    private int line;
    private static final String message = "Warning in line ";

    /**
     *
     * @param line
     */
    public Type1Exception(int line) {
        this.line = line;
    }


    public void print() {
        System.err.print(message+line);
    }


}

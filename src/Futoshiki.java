import java.util.ArrayList;

public class Futoshiki {
    private int[][] board;
    private int dimensions;
    private ArrayList<Restriction> restrictions;

    Futoshiki(int dimensions){
        this.dimensions = dimensions;
    }

    void setBoard(int[][] board){
        this.board = board;
    }

    int getDimensions() {
        return dimensions;
    }
}

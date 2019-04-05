public class Futoshiki {
    private int[][] board;
    private int dimensions;

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

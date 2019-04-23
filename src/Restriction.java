public class Restriction {
    private int rowSmaller;
    private int columnSmaller;

    private int rowBigger;
    private int columnBigger;

    Restriction(int rowSmaller, int columnSmaller, int rowBigger, int columnBigger) {
        this.rowSmaller = rowSmaller;
        this.columnSmaller = columnSmaller;

        this.rowBigger = rowBigger;
        this.columnBigger = columnBigger;
    }

    public int getRowSmaller() {
        return rowSmaller;
    }

    public int getColumnSmaller() {
        return columnSmaller;
    }

    public int getRowBigger() {
        return rowBigger;
    }

    public int getColumnBigger() {
        return columnBigger;
    }
}

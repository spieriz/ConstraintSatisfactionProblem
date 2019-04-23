public class Restriction {
    private int rowSmaller;
    private int columnSmaller;

    private int rowBigger;
    private int columnBigger;

    Restriction() {
        this.rowSmaller = -1;
        this.columnSmaller = -1;

        this.rowBigger = -1;
        this.columnBigger = -1;
    }

    Restriction(int rowSmaller, int columnSmaller, int rowBigger, int columnBigger) {
        this.rowSmaller = rowSmaller;
        this.columnSmaller = columnSmaller;

        this.rowBigger = rowBigger;
        this.columnBigger = columnBigger;
    }

    int getRowSmaller() {
        return rowSmaller;
    }

    void setRowSmaller(int rowSmaller) {
        this.rowSmaller = rowSmaller;
    }

    int getColumnSmaller() {
        return columnSmaller;
    }

    void setColumnSmaller(int columnSmaller) {
        this.columnSmaller = columnSmaller;
    }

    int getRowBigger() {
        return rowBigger;
    }

    void setRowBigger(int rowBigger) {
        this.rowBigger = rowBigger;
    }

    int getColumnBigger() {
        return columnBigger;
    }

    void setColumnBigger(int columnBigger) {
        this.columnBigger = columnBigger;
    }
}

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

    public int getRowSmaller() {
        return rowSmaller;
    }

    public void setRowSmaller(int rowSmaller) {
        this.rowSmaller = rowSmaller;
    }

    public int getColumnSmaller() {
        return columnSmaller;
    }

    public void setColumnSmaller(int columnSmaller) {
        this.columnSmaller = columnSmaller;
    }

    public int getRowBigger() {
        return rowBigger;
    }

    public void setRowBigger(int rowBigger) {
        this.rowBigger = rowBigger;
    }

    public int getColumnBigger() {
        return columnBigger;
    }

    public void setColumnBigger(int columnBigger) {
        this.columnBigger = columnBigger;
    }
}

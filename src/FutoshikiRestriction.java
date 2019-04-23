public class FutoshikiRestriction {
    private int rowSmaller;
    private int columnSmaller;

    private int rowBigger;
    private int columnBigger;

    FutoshikiRestriction() {
        this.rowSmaller = -1;
        this.columnSmaller = -1;

        this.rowBigger = -1;
        this.columnBigger = -1;
    }

    FutoshikiRestriction(int rowSmaller, int columnSmaller, int rowBigger, int columnBigger) {
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

    public String toString() {
        return rowSmaller + "" + columnSmaller + "<" + rowBigger + "" + columnBigger;
    }
}

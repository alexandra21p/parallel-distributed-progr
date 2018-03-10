package main.java;

/**
 * Created by Alexandra on 04/11/2017.
 */
public class MatrixCell {
    private int row;
    private int column;

    public MatrixCell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "MatrixCell{" +
                " row = " + row +
                ", column = " + column +
                " }";
    }

}

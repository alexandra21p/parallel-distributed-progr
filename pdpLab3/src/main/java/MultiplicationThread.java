package main.java;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Alexandra on 04/11/2017.
 */
public class MultiplicationThread implements Callable {
    private List<MatrixCell> slots = new ArrayList<>();

    public MultiplicationThread(List<MatrixCell> slots) {
        this.slots = slots;
    }

    @Override
    public List call() {
        int commonCols = Main.getFirstMatrix()[0].length;
        List<Integer> result = new ArrayList<>();

        for (MatrixCell cell : slots) {
            //System.out.println(cell.toString());
            Integer val = 0;
            int row = cell.getRow();
            int col = cell.getColumn();
            for (int i = 0; i < commonCols; i++) {
                val += Main.getFirstMatrix()[row][i] * Main.getSecondMatrix()[i][col];
            }
            Main.getResultMatrix()[row][col] = val;
            result.add(val);
        }

        return result;
    }
}

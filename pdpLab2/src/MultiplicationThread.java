import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandra on 29/10/2017.
 */
public class MultiplicationThread implements Runnable {
    private List<MatrixCell> slots = new ArrayList<>();

    public MultiplicationThread(List<MatrixCell> slots) {
        this.slots = slots;
    }

    @Override
    public void run() {
        int commonCols = Main.getFirstMatrix()[0].length;
        for (MatrixCell cell : slots) {
            //System.out.println(cell.toString());
            int val = 0;
            int row = cell.getRow();
            int col = cell.getColumn();
            for (int i = 0; i < commonCols; i++) {
                val += Main.getFirstMatrix()[row][i] * Main.getSecondMatrix()[i][col];
            }
            Main.getResultMatrix()[row][col] = val;
        }
    }
}

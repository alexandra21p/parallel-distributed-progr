package main.java;

/**
 * Created by Alexandra on 04/11/2017.
 */
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class Main {
    private final static Random rand = new Random();
    private static Scanner input = new Scanner(System.in);

    private static int[][] firstMatrix;
    private static int[][] secondMatrix;
    private static int[][] resultMatrix;

    public static int[][] getFirstMatrix() {
        return firstMatrix;
    }

    public static int[][] getSecondMatrix() {
        return secondMatrix;
    }

    public static int[][] getResultMatrix() {
        return resultMatrix;
    }

    public static void compute(List<List<MatrixCell>> list) throws InterruptedException, TimeoutException, ExecutionException {

        ExecutorService executor = Executors.newFixedThreadPool(list.size());
        List<FutureTask<List<Integer>>> futures = new ArrayList<>();
        for (List<MatrixCell> slots : list) {
            MultiplicationThread task = new MultiplicationThread( slots );
            FutureTask futureTask = new FutureTask<>(task);
            futures.add(futureTask);

            executor.execute(futureTask);
        }
        executor.shutdown();
        for (FutureTask<List<Integer>> futureTask : futures) {
            futureTask.get(5, TimeUnit.SECONDS);
        }
    }


    public static void generateMatrices(){
        System.out.println("Enter the number of ROWS for 1st matrix: ");
        int rows1 = input.nextInt();
        System.out.println("Enter the number of COLUMNS for 1st matrix: ");
        int cols1 = input.nextInt();

        firstMatrix = new int[rows1][cols1];

        for ( int i = 0; i < rows1; i++ ) {
            for ( int j = 0; j < cols1; j++ ) {
                firstMatrix[i][j] =  Math.abs(rand.nextInt() % 1000);
            }
        }

        System.out.println("Enter the number of ROWS for 2nd matrix: ");
        int rows2 = input.nextInt();
        System.out.println("Enter the number of COLUMNS for 2nd matrix: ");
        int cols2 = input.nextInt();

        secondMatrix = new int[rows2][cols2];

        for ( int i = 0; i < rows2; i++ ) {
            for ( int j = 0; j < cols2; j++ ) {
                secondMatrix[i][j] =  Math.abs(rand.nextInt() % 1000);
            }
        }

        if ( cols1 == rows2 ) {
            resultMatrix = new int[rows1][cols2];
        }
        else {
            System.out.println("\tThe nr of COLUMNS in the 1st matrix must be equal to the nr of ROWS in the 2nd matrix!\n");
            generateMatrices();
        }
    }

    public static void printMatrix(int[][] matrix) {

        for (int[] aMatrix : matrix) {
            for (int j = 0; j < matrix[0].length; j++)
                System.out.print(aMatrix[j] + "  ");

            System.out.println();
        }
    }

    public static int[] divideTaskBetweenThreads( int number, int threads ) {
        int[] subtasks = new int[threads];
        int slot = (int)(Math.floor( number / threads));

        for (int i = 0; i < threads; i++) {
            subtasks[i] = slot;
        }

        if ( number % threads == 0 ) {
            return subtasks;
        }
        else {
            for (int j = 0; j < subtasks.length; j++) {
                if ( getSum(subtasks) < number ) {
                    subtasks[j] += 1;
                }
            }
        }

        return subtasks;
    }

    public static List assignCellsToThreads( int[] subtasks) {
        int row = 0;
        int col = 0;
        List<List<MatrixCell>> threadTasks = new ArrayList<>();
        for ( int i = 0; i < subtasks.length; i++  ) {
            List<MatrixCell> slots = new ArrayList<>();
            for( int j = 0; j < subtasks[i]; j++) {
                if ( col < secondMatrix[0].length ) {
                    MatrixCell cell = new MatrixCell(row, col);
                    slots.add(cell);
                    col++;
                }
                if ( col == secondMatrix[0].length ) {
                    col = 0;
                    row++;
                }
            }
            threadTasks.add(slots);
        }
        return threadTasks;
    }

    public static int getSum( int[] list ) {
        return  IntStream.of(list).sum();
    }

    public static void main(String args[]) throws InterruptedException {

        generateMatrices();

        System.out.println("Enter the number of threads: ");
        int numberOfThreads = input.nextInt();

        System.out.println("\t\t-- FIRST MATRIX:\n");
        printMatrix(firstMatrix);
        System.out.println("\n\t\t-- SECOND MATRIX:\n");
        printMatrix(secondMatrix);


        int[] subtasks = divideTaskBetweenThreads(firstMatrix.length * secondMatrix[0].length, numberOfThreads);
        List<List<MatrixCell>> threadTasks = assignCellsToThreads(subtasks);


        long startTime = new Date().getTime();

        try {
            compute(threadTasks);
        } catch (TimeoutException | ExecutionException e) {
            e.printStackTrace();
        }

        long stopTime = new Date().getTime();

        System.out.println("\n\t\t-- RESULTED MATRIX:\n");
        printMatrix( resultMatrix );
        System.out.println("\n\tExecution time (in milliseconds): " + (stopTime - startTime) );

    }
}

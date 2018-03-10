/**
 * Created by Alexandra on 29/10/2017.
 */

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class Main {
    private final static Random rand = new Random();
    private static Scanner input = new Scanner(System.in);

    private static Long[][] firstMatrix;
    private static Long[][] secondMatrix;
    private static Long[][] thirdMatrix;
    private static Long[][] firstResultMatrix;
    private static Long[][] secondResultMatrix;

    private static ExecutorService secondExecutor;
    private static List<FutureTask<List<Long>>> secondFutures;
    private static Integer counter = 0;

    public static void setCounter(Integer counter) {
        Main.counter = counter;
    }

    public static Integer getCounter() {
        return counter;

    }

    public static ExecutorService getSecondExecutor() {
        return secondExecutor;
    }

    public static List<FutureTask<List<Long>>> getSecondFutures() {
        return secondFutures;
    }

    public static List<Integer> getComputedColumns() {
        return computedColumns;
    }

    private static List<Integer> computedColumns;

    public static Long[][] getFirstMatrix() {
        return firstMatrix;
    }

    public static Long[][] getSecondMatrix() {
        return secondMatrix;
    }

    public static Long[][] getThirdMatrix() {
        return thirdMatrix;
    }

    public static Long[][] getFirstResultMatrix() {
        return firstResultMatrix;
    }

    public static Long[][] getSecondResultMatrix() {
        return secondResultMatrix;
    }


    public static void compute(int firstNumberOfThreads, int secondNumberOfThreads) throws InterruptedException, TimeoutException, ExecutionException {

        ExecutorService executor = Executors.newFixedThreadPool( firstNumberOfThreads );
        secondExecutor = Executors.newFixedThreadPool( secondNumberOfThreads );

        List<Future<Integer>> futures = new ArrayList<>();
        secondFutures = new ArrayList<>();

        for ( int i = 0; i < firstMatrix.length; i++ ) {
            FutureTask<Integer> future = new CustomFutureTask(new MultiplicationThread( i ), i);
            futures.add( future );
            executor.submit( future );
//            MultiplicationThread task = new MultiplicationThread( i );
//            Future futureTask =  executor.submit( task );
//            futures.add( futureTask );
//
//            SecondMultiplicationThread secondTask = new SecondMultiplicationThread( i );
//            FutureTask secondFutureTask = new FutureTask( secondTask );
//            secondFutures.add( secondFutureTask );

        }

        executor.shutdown();

        for (Future<Integer> futureTask : futures) {
           futureTask.get(1, TimeUnit.MINUTES);
//            if ( futureTask.isDone() ) {
//                Integer currentRow = futureTask.get(1, TimeUnit.MINUTES);
//                Integer col = computedColumns.get( currentRow );
//                if ( col == firstResultMatrix[0].length ) {
//                    SecondMultiplicationThread secondTask = new SecondMultiplicationThread( currentRow );
//                    secondExecutor.execute( secondFutures.get( currentRow ) );
//                    secondFutures.add( secondFutureTask );
//                }
//            }
        }

        //executor.shutdown();
        //secondExecutor.shutdown();

//        for( Future<List<Long>> secondFuture : secondFutures ) {
//            secondFuture.get(1, TimeUnit.MINUTES);
//        }
    }


    public static void generateMatrices(){
        System.out.println("Enter the number of ROWS for 1st matrix: ");
        int rows1 = input.nextInt();
        System.out.println("Enter the number of COLUMNS for 1st matrix AND number of ROWS for 2nd matrix: ");
        int cols1rows2 = input.nextInt();

        firstMatrix = new Long[rows1][cols1rows2];

        for ( int i = 0; i < rows1; i++ ) {
            for ( int j = 0; j < cols1rows2; j++ ) {
                firstMatrix[i][j] =  Math.abs(rand.nextLong() % 1000);
            }
        }

        System.out.println("Enter the number of COLUMNS for 2nd matrix AND number of ROWS for 3rd matrix: ");
        int cols2rows3 = input.nextInt();

        secondMatrix = new Long[cols1rows2][cols2rows3];

        for ( int i = 0; i < cols1rows2; i++ ) {
            for ( int j = 0; j < cols2rows3; j++ ) {
                secondMatrix[i][j] =  Math.abs(rand.nextLong() % 1000);
            }
        }

        firstResultMatrix = new Long[rows1][cols2rows3];

        // list to keep track of cols computed from first multiplication
        // will be shared and modified by threads
        computedColumns = new ArrayList<>();
        for ( int i = 0; i < rows1; i++ ) {
            computedColumns.add(0);
        }

        System.out.println("Enter the number of COLUMNS for 3rd matrix: ");
        int cols3 = input.nextInt();

        thirdMatrix = new Long[cols2rows3][cols3];

        for ( int i = 0; i < cols2rows3; i++ ) {
            for ( int j = 0; j < cols3; j++ ) {
                thirdMatrix[i][j] =  Math.abs(rand.nextLong() % 1000);
            }
        }

        secondResultMatrix = new Long[rows1][cols3];
    }

    public static void printMatrix(Long[][] matrix) {

        for (Long[] aMatrix : matrix) {
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

        System.out.println("Enter the number of threads for 1st multiplication: ");
        int firstNumberOfThreads = input.nextInt();

        System.out.println("Enter the number of threads for 2nd multiplication: ");
        int secondNumberOfThreads = input.nextInt();

        System.out.println("\t\t-- FIRST MATRIX:\n");
        printMatrix(firstMatrix);
        System.out.println("\n\t\t-- SECOND MATRIX:\n");
        printMatrix(secondMatrix);
        System.out.println("\n\t\t-- THIRD MATRIX:\n");
        printMatrix(thirdMatrix);


        //int[] subtasks = divideTaskBetweenThreads( firstMatrix.length * secondMatrix[0].length, firstNumberOfThreads );
        //List<List<MatrixCell>> threadTasks = assignCellsToThreads(subtasks);


        long startTime = System.nanoTime();

        try {
            compute( firstNumberOfThreads, secondNumberOfThreads );
        } catch (TimeoutException | ExecutionException e) {
            e.printStackTrace();
        }

        long stopTime = System.nanoTime();

        System.out.println("\n\t\t-- RESULTED MATRIX FROM FIRST MULTIPLICATION:\n");
       // printMatrix( firstResultMatrix );

        System.out.println("\n\t\t-- RESULTED MATRIX FROM SECOND MULTIPLICATION:\n");
        //printMatrix( secondResultMatrix );
        System.out.println("\n\tExecution time (in seconds): " + ( stopTime - startTime ) / 1000000000.0 );

    }
}
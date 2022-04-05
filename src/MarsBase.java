import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class MarsBase {
    private static long[] task1(int[] A, int n) {
        long[] resultList = new long[3];
        if (A.length == 0) {
            return resultList;
        }
        long maxSum = Long.MIN_VALUE;
        int l = 0;
        int r = 0;
        for (int start = 0; start < n; start++) {
            for (int end = start; end < n; end++) {
                long currSum = 0;
                for (int k = start; k <= end; k++) {
                    currSum += A[k];
                }
                if (currSum > maxSum) {
                    l = start;
                    r = end;
                    maxSum = currSum;
                }
            }
        }
        resultList[0] = l + 1;
        resultList[1] = r + 1;
        resultList[2] = maxSum;
        return resultList;
    }

    private static long[] task2(int[] A, int n) {
        long[] resultList = new long[3];
        if (A.length == 0) {
            return resultList;
        }
        long maxSum = Long.MIN_VALUE;
        int l = 0;
        int r = 0;
        for (int start = 0; start < n; start++) {
            long currSum = 0;
            for (int end = start; end < n; end++) {
                currSum += A[end];
                if (currSum > maxSum) {
                    maxSum = currSum;
                    l = start;
                    r = end;
                }
            }
        }
        resultList[0] = l + 1;
        resultList[1] = r + 1;
        resultList[2] = maxSum;
        return resultList;
    }

    private static long[] task3a(int[] A, int n) {
        long[] resultList = new long[3];
        if (A.length == 0) {
            return resultList;
        }

        resultList = helper(A, 0, Long.MIN_VALUE, 0, 0, 0, 0);

        return resultList;
    }

    private static long[] helper(int[] A, int index, long maxSum, long currSum, int l, int r, int start) {
        if (index == A.length) {
            return new long[] {l, r, maxSum};
        }
        currSum += A[index];
        if (currSum > maxSum) {
            maxSum = currSum;
            l = start;
            r = index;
        }
        if (currSum < 0) {
            currSum = 0;
            start = index + 1;
        }
        return helper(A, index + 1, maxSum, currSum, l + 1, r + 1, start);
    }

    private static long[] task3b(int[] A, int n) {
        long[] resultList = new long[3];
        if (A.length == 0) {
            return resultList;
        }
        long maxSum = Long.MIN_VALUE;
        int l = 0;
        int r = 0;
        long currSum = 0;
        int prevIndex = 0;
        long prevMinSum = 0;
        for (int end = 0; end < n; end++) {
            currSum += A[end];
            long currMaxSum = currSum - prevMinSum;
            if (currMaxSum > maxSum) {
                maxSum = currMaxSum;
                l = prevIndex + 1;
                r = end;
            }
            if (currSum < prevMinSum) {
                prevMinSum = currSum;
                prevIndex = end;
            }
        }
        resultList[0] = l + 1;
        resultList[1] = r + 1;
        resultList[2] = maxSum;
        return resultList;
    }

    private static long[] task4(int[][] A, int m, int n) {
        long[] resultList = new long[5];
        if (A.length == 0) {
            return resultList;
        }
        long maxSum = Long.MIN_VALUE;
        int x1 = 0;
        int y1 = 0;
        int x2 = 0;
        int y2 = 0;
        for (int row1 = 0; row1 < m; row1++) {
            for (int col1 = 0; col1 < n; col1++) {
                for (int row2 = row1; row2 < m; row2++) {
                    for (int col2 = 0; col2 < n; col2++) {
                        long currSum = 0;
                        for (int i = row1; i <= row2; i++) {
                            for (int j = 0; j <= col2; j++) {
                                currSum += A[i][j];
                            }
                        }
                        if (currSum > maxSum) {
                            maxSum = currSum;
                            x1 = row1;
                            y1 = col1;
                            x2 = row2;
                            y2 = col2;
                        }
                    }
                }
            }
        }
        resultList[0] = x1 + 1;
        resultList[1] = y1 + 1;
        resultList[2] = x2 + 1;
        resultList[3] = y2 + 1;
        resultList[4] = maxSum;
        return resultList;
    }

    private static long[] task5(int[][] A, int m, int n) {
        long[] resultList = new long[5];
        if (A.length == 0) {
            return resultList;
        }
        long maxSum = Long.MIN_VALUE;
        int x1 = 0;
        int y1 = 0;
        int x2 = 0;
        int y2 = 0;

        //Construct an array to save the sum from A[0][0] to A[i][j]
        long[][] sumArray = new long[m + 1][n + 1];
        /*for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int totalSum = 0;
                for (int k = 0; k <= i; k++) {
                    for (int l = 0; l <= j; l++) {
                        totalSum += A[k][l];
                        sumArray[i + 1][j + 1] = totalSum;
                    }
                }
            }
        }*/
        for (int row = 1; row <= m; row++) {
            for (int col = 1; col <= n; col++) {
                sumArray[row][col] = sumArray[row - 1][col] + sumArray[row][col - 1] - sumArray[row - 1][col - 1] + A[row - 1][col - 1];
            }
        }

        //Find the maximum subarray
        for (int row1 = 0; row1 < m; row1++) {
            for (int col1 = 0; col1 < n; col1++) {
                for (int row2 = row1; row2 < m; row2++) {
                    for (int col2 = col1; col2 < n; col2++) {
                        long currSum = sumArray[row2 + 1][col2 + 1] - sumArray[row2 + 1][col1] - sumArray[row1][col2 + 1] + sumArray[row1][col1];
                        if (currSum > maxSum) {
                            maxSum = currSum;
                            x1 = row1;
                            y1 = col1;
                            x2 = row2;
                            y2 = col2;
                        }
                    }
                }
            }
        }

        resultList[0] = x1 + 1;
        resultList[1] = y1 + 1;
        resultList[2] = x2 + 1;
        resultList[3] = y2 + 1;
        resultList[4] = maxSum;
        return resultList;
    }

    private static long[] task6(int[][] A, int m, int n) {
        long[] resultList = new long[5];
        if (A.length == 0) {
            return resultList;
        }
        long maxSum = Long.MIN_VALUE;
        int x1 = 0;
        int y1 = 0;
        int x2 = 0;
        int y2 = 0;

        //Construct an array to save the sum from A[0][0] to A[i][j]
        long[][] sumArray = new long[m + 1][n + 1];
        for (int row = 1; row <= m; row++) {
            for (int col = 1; col <= n; col++) {
                sumArray[row][col] = sumArray[row - 1][col] + sumArray[row][col - 1] - sumArray[row - 1][col - 1] + A[row - 1][col - 1];
            }
        }

        //Find the maximum subarray
        for (int row1 = 0; row1 < m; row1++) {
            for (int row2 = row1; row2 < m; row2++) {
                long[] prefix = new long[n + 1];
                for (int col = 0; col < n; col++) {
                    prefix[col + 1] = sumArray[row2 + 1][col + 1] - sumArray[row2 + 1][0] - sumArray[row1][col + 1] + sumArray[row1][0];
                }
//                System.out.println(Arrays.toString(prefix));
                long prevMinSum = 0;
                int prevIndex = 0;
                for (int k = 0; k < n; k++) {
                    long currMaxSum = prefix[k + 1] - prevMinSum;
                    if (currMaxSum > maxSum) {
                        maxSum = currMaxSum;
                        x1 = row1;
                        x2 = row2;
                        y1 = prevIndex;
                        y2 = k;
                    }
                    if (prefix[k + 1] < prevMinSum) {
                        prevMinSum = prefix[k + 1];
                        prevIndex = k;
                    }
                }
            }
        }

        resultList[0] = x1 + 1;
        resultList[1] = y1 + 1;
        resultList[2] = x2 + 1;
        resultList[3] = y2 + 1;
        resultList[4] = maxSum;
        return resultList;
    }

    private static void print(long[] resultList) {
        for (long result : resultList) {
            System.out.print(result + " ");
        }
        System.out.print("\n");
    }


    public static void main(String[] args) {
        Random random = new Random();
        switch (args[0]) {
            case "1": {
                Scanner sc = new Scanner(System.in);
                int n = sc.nextInt();
                int[] A = new int[n];
                for (int i = 0; i < n; i++) {
                    A[i] = sc.nextInt();
//                    A[i] = (int) (random.nextInt() / (Math.pow(2, 16)));
                }
//                long startTime = System.currentTimeMillis();
                long[] resultList = task1(A, n);
//                long endTime = System.currentTimeMillis();
//                System.out.println("Task 1 running time: " + (endTime - startTime) + " ms");
                print(resultList);
                break;
            }
            case "2": {
                Scanner sc = new Scanner(System.in);
                int n = sc.nextInt();
                int[] A = new int[n];
                for (int i = 0; i < n; i++) {
                    A[i] = sc.nextInt();
//                    A[i] = (int) (random.nextInt() / (Math.pow(2, 16)));
                }
//                long startTime = System.currentTimeMillis();
                long[] resultList = task2(A, n);
//                long endTime = System.currentTimeMillis();
//                System.out.println("Task 2 running time: " + (endTime - startTime) + " ms");
                print(resultList);
                break;
            }
            case "3a": {
                Scanner sc = new Scanner(System.in);
                int n = sc.nextInt();
                int[] A = new int[n];
                for (int i = 0; i < n; i++) {
                    A[i] = sc.nextInt();
//                    A[i] = (int) (random.nextInt() / (Math.pow(2, 16)));
                }
//                int[] A = new int[] {10106, 20526, -12726, 2067, -32419, -19991, -24083, 18070, -24869, -1403, -10575, 28013, -32239, 2129, -25533, -3242, 16030, -18371, 22479, -10687};

//                System.out.println(Arrays.toString(A));
//                long startTime = System.nanoTime();
                long[] resultList = task3a(A, n);
//                long endTime = System.nanoTime();
//                System.out.println("Task 3a running time: " + (endTime - startTime) + " ns");
                print(resultList);
                break;
            }
            case "3b": {
                Scanner sc = new Scanner(System.in);
                int n = sc.nextInt();
                int[] A = new int[n];
                for (int i = 0; i < n; i++) {
                    A[i] = sc.nextInt();
//                    A[i] = (int) (random.nextInt() / (Math.pow(2, 16)));
                }
//                long startTime = System.nanoTime();
                long[] resultList = task3b(A, n);
//                long endTime = System.nanoTime();
//                System.out.println("Task 3b running time: " + (endTime - startTime) + " ns");
                print(resultList);
                break;
            }
            case "4": {
                Scanner sc = new Scanner(System.in);
                int m = sc.nextInt();
                int n = sc.nextInt();
                int[][] A = new int[m][n];
                sc.nextLine();
                for (int i = 0; i < m; i++) {
                    for (int j = 0; j < n; j++) {
                        A[i][j] = sc.nextInt();
//                        A[i][j] = (int) (random.nextInt() / (Math.pow(2, 16)));
                    }
                }
//                long startTime = System.currentTimeMillis();
                long[] resultList = task4(A, m, n);
//                long endTime = System.currentTimeMillis();
//                System.out.println("Task 4 running time: " + (endTime - startTime) + " ms");
                print(resultList);
                break;
            }
            case "5": {
                Scanner sc = new Scanner(System.in);
                int m = sc.nextInt();
                int n = sc.nextInt();
                int[][] A = new int[m][n];
                sc.nextLine();
                for (int i = 0; i < m; i++) {
                    for (int j = 0; j < n; j++) {
                        A[i][j] = sc.nextInt();
//                        A[i][j] = (int) (random.nextInt() / (Math.pow(2, 16)));
                    }
                }
//                long startTime = System.currentTimeMillis();
                long[] resultList = task5(A, m, n);
//                long endTime = System.currentTimeMillis();
//                System.out.println("Task 5 running time: " + (endTime - startTime) + " ms");
                print(resultList);
                break;
            }
            case "6": {
                Scanner sc = new Scanner(System.in);
                int m = sc.nextInt();
                int n = sc.nextInt();
                int[][] A = new int[m][n];
                sc.nextLine();
                for (int i = 0; i < m; i++) {
                    for (int j = 0; j < n; j++) {
                        A[i][j] = sc.nextInt();
//                        A[i][j] = (int) (random.nextInt() / (Math.pow(2, 16)));
                    }
                }
//                long startTime = System.currentTimeMillis();
                long[] resultList = task6(A, m, n);
//                long endTime = System.currentTimeMillis();
//                System.out.println("Task 6 running time: " + (endTime - startTime) + " ms");
                print(resultList);
                break;
            }
        }



//        int[] A = new int[]{-2,1,-3,4,-1,2,1,-5,4};
//        int n = 9;

//        int[] resultList = task1(A, n);
//        int[] resultList = task2(A, n);
//        int[] resultList = task3a(A, n);
//        int[] resultList = task3b(A, n);

//        int[][] A = new int[][]{{-1, -2, 4}, {6, 1, -3}, {-7, 1, -6}};
//        int m = 3, n = 3;
//        int[] resultList = task4(A, m, n);
//        int[] resultList = task5(A, m, n);
//        int[] resultList = task6(A, m, n);
//        print(resultList);

    }
}

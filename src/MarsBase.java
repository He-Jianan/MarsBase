import java.util.Scanner;

public class MarsBase {
    private static long[] task1(int[] A, int n) {
        //Initialize the parameters
        long[] resultList = new long[3];
        if (A.length == 0) {
            return resultList;
        }
        long maxSum = Long.MIN_VALUE;
        int l = 0;
        int r = 0;

        //Iterate over all possible starting and ending index
        for (int start = 0; start < n; start++) {
            for (int end = start; end < n; end++) {
                //Calculate the sum of this subarray.
                long currSum = 0;
                for (int k = start; k <= end; k++) {
                    currSum += A[k];
                }
                //If the current sum is larger than maximum Sum, update the result parameters
                if (currSum > maxSum) {
                    l = start;
                    r = end;
                    maxSum = currSum;
                }
            }
        }

        //Get the task result
        resultList[0] = l + 1;
        resultList[1] = r + 1;
        resultList[2] = maxSum;
        return resultList;
    }

    private static long[] task2(int[] A, int n) {
        // Initialize the parameters
        long[] resultList = new long[3];
        if (A.length == 0) {
            return resultList;
        }
        long maxSum = Long.MIN_VALUE;
        int l = 0;
        int r = 0;


        for (int start = 0; start < n; start++) {    // Fix the starting index.
            long currSum = 0;

            // Iterate over all possible ending index.
            for (int end = start; end < n; end++) {
                currSum += A[end];
                // If the current sum is larger than maximum sum, update the result parameters
                if (currSum > maxSum) {
                    maxSum = currSum;
                    l = start;
                    r = end;
                }
            }
        }

        // Get the task result
        resultList[0] = l + 1;
        resultList[1] = r + 1;
        resultList[2] = maxSum;
        return resultList;
    }

    private static long[] task3a(int[] A, int n) {
        // Initialize the parameters
        long[] resultList = new long[3];
        if (A.length == 0) {
            return resultList;
        }

        // Find the result recursively
        resultList = helper(A, 0, Long.MIN_VALUE, 0, 0, 0, 0);

        return resultList;
    }

    private static long[] helper(int[] A, int index, long maxSum, long currSum, int l, int r, int start) {
        // If the current index is out of the bound of the array, stop the recursion
        if (index == A.length) {
            return new long[] {l, r, maxSum};
        }

        // Calculate the current sum of the subarray
        currSum += A[index];

        // If the current sum is larger than maximum sum, update the result parameters
        if (currSum > maxSum) {
            maxSum = currSum;
            l = start;
            r = index;
        }

        // If the current sum is less than 0, update the starting index of the subarray and reset the sum to zero.
        if (currSum < 0) {
            currSum = 0;
            start = index + 1;
        }

        // Move the current index to next element and continue the recursion
        return helper(A, index + 1, maxSum, currSum, l + 1, r + 1, start);
    }

    private static long[] task3b(int[] A, int n) {
        // Initialize the parameters
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

        // Iterate over all possible ending index.
        for (int end = 0; end < n; end++) {
            // Update the current sum of the subarray
            currSum += A[end];

            // Calculate the current maximum sum of the subarray
            long currMaxSum = currSum - prevMinSum;
            //  If the current maximum sum is larger than the previous maximum sum, update the result parameters
            if (currMaxSum > maxSum) {
                maxSum = currMaxSum;
                l = prevIndex + 1;
                r = end;
            }

            // If the current sum is less than previous minimum sum, update the parameters
            if (currSum < prevMinSum) {
                prevMinSum = currSum;
                prevIndex = end;
            }
        }

        // Get the task result
        resultList[0] = l + 1;
        resultList[1] = r + 1;
        resultList[2] = maxSum;
        return resultList;
    }

    private static long[] task4(int[][] A, int m, int n) {
        // Initialize the parameters
        long[] resultList = new long[5];
        if (A.length == 0) {
            return resultList;
        }
        long maxSum = Long.MIN_VALUE;
        int x1 = 0;
        int y1 = 0;
        int x2 = 0;
        int y2 = 0;

        // Iterate over all possible starting and ending index
        for (int row1 = 0; row1 < m; row1++) {
            for (int col1 = 0; col1 < n; col1++) {
                for (int row2 = row1; row2 < m; row2++) {
                    for (int col2 = 0; col2 < n; col2++) {

                        //Calculate the sum of the current subarray
                        long currSum = 0;
                        for (int i = row1; i <= row2; i++) {
                            for (int j = 0; j <= col2; j++) {
                                currSum += A[i][j];
                            }
                        }

                        // If the current sum is larger than maximum sum, update the result parameters
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

        // Get the result
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

        /*
        Iterate over all combinations of row numbers (row_1, row_2)
        and focus on all the submatrix that having row_1 as the top
        row and row_2 as the bottom row.
         */
        for (int row1 = 0; row1 < m; row1++) {
            for (int row2 = row1; row2 < m; row2++) {
                // Build an array representing the prefix sum of the corresponding matrix.
                long[] ss = new long[n];
                long[] prefix = new long[n + 1];
                for (int col = 0; col < n; col++) {
                    for (int row = row1; row <= row2; row++) {
                        ss[col] += A[row][col];
                    }
                }
                for (int col = 0; col < n; col++) {
                    prefix[col + 1] = prefix[col] + ss[col];
                }
                // Then we use the algorithm from task3 to find the maximum sum from prefix array.
                int pre_idx = 0;
                for (int i = 1; i <= n; i++) {
                    if (prefix[i] - prefix[pre_idx] > maxSum) {
                        y1 = pre_idx + 1;
                        y2 = i;
                        x1 = row1;
                        x2 = row2;
                        maxSum = prefix[i] - prefix[pre_idx];
                    }
                    if (prefix[i] < prefix[pre_idx]) {
                        pre_idx = i;
                    }
                }
            }
        }



        // Get the task result
        resultList[0] = x1 + 1;
        resultList[1] = y1;
        resultList[2] = x2 + 1;
        resultList[3] = y2;
        resultList[4] = maxSum;
        return resultList;
    }

    private static long[] task6(int[][] A, int m, int n) {
        // Initialize the parameters
        long[] resultList = new long[5];
        if (A.length == 0) {
            return resultList;
        }
        long maxSum = Long.MIN_VALUE;
        int x1 = 0;
        int y1 = 0;
        int x2 = 0;
        int y2 = 0;

        /*
         Build prefix sum matrix for A. P[x][y] stands for the
         sum of the matrix that having (0, 0) as its top-left cell
         and (x, y) as the bottom-right cell
         */
        long[][] sumArray = new long[m + 1][n + 1];
        for (int row = 1; row <= m; row++) {
            for (int col = 1; col <= n; col++) {
                sumArray[row][col] = sumArray[row - 1][col] + sumArray[row][col - 1] - sumArray[row - 1][col - 1] + A[row - 1][col - 1];
            }
        }

        //Iterate over all possible starting and ending index
        for (int row1 = 0; row1 < m; row1++) {
            for (int row2 = row1; row2 < m; row2++) {
                long[] prefix = new long[n + 1];

                // Fix the starting and ending row index and iterate all the column index
                for (int col = 0; col < n; col++) {
                    prefix[col + 1] = sumArray[row2 + 1][col + 1] - sumArray[row2 + 1][0] - sumArray[row1][col + 1] + sumArray[row1][0];
                }
//                System.out.println(Arrays.toString(prefix));
                long prevMinSum = 0;
                int prevIndex = 0;
                for (int k = 0; k < n; k++) {

                    // Calculate the current maximum sum
                    long currMaxSum = prefix[k + 1] - prevMinSum;

                    // If the current sum is larger than maximum sum, update the result parameters
                    if (currMaxSum > maxSum) {
                        maxSum = currMaxSum;
                        x1 = row1;
                        x2 = row2;
                        y1 = prevIndex;
                        y2 = k;
                    }

                    // If the current prefix is less than previous minimum sum, update the parameters
                    if (prefix[k + 1] < prevMinSum) {
                        prevMinSum = prefix[k + 1];
                        prevIndex = k;
                    }
                }
            }
        }

        // Get the task result
        resultList[0] = x1 + 1;
        resultList[1] = y1 + 1;
        resultList[2] = x2 + 1;
        resultList[3] = y2 + 1;
        resultList[4] = maxSum;
        return resultList;
    }

    // Method to print the result on the console
    private static void print(long[] resultList) {
        for (long result : resultList) {
            System.out.print(result + " ");
        }
        System.out.print("\n");
    }


    public static void main(String[] args) {
//        Random random = new Random();

        // Determine which task will be executed according to the input
        switch (args[0]) {

            // Execute the task 1
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

            // Execute the task 2
            case "2": {
                Scanner sc = new Scanner(System.in);
                int n = sc.nextInt();
                int[] A = new int[n];
                for (int i = 0; i < n; i++) {
                    A[i] = sc.nextInt();
//                    A[i] = (int) (random.nextInt() / (Math.pow(2, 16)));
//                    A[i] = Integer.MAX_VALUE;
                }
//                long startTime = System.currentTimeMillis();
                long[] resultList = task2(A, n);
//                long endTime = System.currentTimeMillis();
//                System.out.println("Task 2 running time: " + (endTime - startTime) + " ms");
                print(resultList);
                break;
            }

            // Execute the task 3a
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

            // Execute the task 3b
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

            // Execute the task 4
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

            // Execute the task 5
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

            // Execute the task 6
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

    }
}

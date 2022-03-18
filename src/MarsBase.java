import java.util.Scanner;

public class MarsBase {
    private static int[] task1(int[] A, int n) {
        int[] resultList = new int[3];
        if (A.length == 0) {
            return resultList;
        }
        int maxSum = Integer.MIN_VALUE;
        int l = 0;
        int r = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int sum = 0;
                for (int k = i; k <= j; k++) {
                    sum += A[k];
                }
                if (sum > maxSum) {
                    l = i;
                    r = j;
                    maxSum = sum;
                }
            }
        }
        resultList[0] = l;
        resultList[1] = r;
        resultList[2] = maxSum;
        return resultList;
    }

    private static int[] task2(int[] A, int n) {
        int[] resultList = new int[3];
        if (A.length == 0) {
            return resultList;
        }
        int maxSum = Integer.MIN_VALUE;
        int l = 0;
        int r = 0;
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = i; j < n; j++) {
                sum += A[j];
                if (sum > maxSum) {
                    maxSum = sum;
                    l = i;
                    r = j;
                }
            }
        }
        resultList[0] = l;
        resultList[1] = r;
        resultList[2] = maxSum;
        return resultList;
    }

    private static int[] task3a(int[] A, int n) {
        int[] resultList = new int[3];
        if (A.length == 0) {
            return resultList;
        }

        int[] res = helper(A, n - 1);


        resultList[0] = res[2] + 1;
        resultList[1] = res[4];
        resultList[2] = res[0];
        return resultList;

    }

    private static int[] helper(int[] A, int index) {
        if (index == 0) {
            return new int[]{A[0], A[0], 0, 0, 1};
        }
        int[] memo = helper(A, index - 1);
        int prevMaxSum = memo[0];
        int prefixSum = memo[1];
        int prevLeft = memo[2];
        int prevMinSum = memo[3];
        int right = memo[4];

        prefixSum += A[index];
        int sum = prefixSum - prevMinSum;
        if (sum > prevMaxSum) {
            prevMaxSum = sum;
            right = index;
        }
        if (prefixSum < prevMinSum) {
            prevMinSum = prefixSum;
            prevLeft = index;
        }
        return new int[] {prevMaxSum, prefixSum, prevLeft, prevMinSum, right};
    }

    private static int[] task3b(int[] A, int n) {
        int[] resultList = new int[3];
        if (A.length == 0) {
            return resultList;
        }
        int maxSum = Integer.MIN_VALUE;
        int l = 0;
        int r = 0;
        int sum = 0;
        int prevIndex = 0;
        int prevMinSum = 0;
        for (int i = 0; i < n; i++) {
            sum += A[i];
            int currMaxSum = sum - prevMinSum;
            if (currMaxSum > maxSum) {
                maxSum = currMaxSum;
                l = prevIndex + 1;
                r = i;
            }
            if (sum < prevMinSum) {
                prevMinSum = sum;
                prevIndex = i;
            }
        }
        resultList[0] = l;
        resultList[1] = r;
        resultList[2] = maxSum;
        return resultList;
    }

    private static int[] task4(int[][] A, int m, int n) {
        int[] resultList = new int[5];
        if (A.length == 0) {
            return resultList;
        }
        int maxSum = Integer.MIN_VALUE;
        int x1 = 0;
        int y1 = 0;
        int x2 = 0;
        int y2 = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = i; k < m; k++) {
                    for (int l = 0; l < n; l++) {
                        int sum = 0;
                        for (int o = i; o <= k; o++) {
                            for (int p = 0; p <= l; p++) {
                                sum += A[o][p];
                            }
                        }
                        if (sum > maxSum) {
                            maxSum = sum;
                            x1 = i;
                            y1 = j;
                            x2 = k;
                            y2 = l;
                        }
                    }
                }
            }
        }
        resultList[0] = x1;
        resultList[1] = y1;
        resultList[2] = x2;
        resultList[3] = y2;
        resultList[4] = maxSum;
        return resultList;
    }

    private static int[] task5(int[][] A, int m, int n) {
        int[] resultList = new int[5];
        if (A.length == 0) {
            return resultList;
        }
        int maxSum = Integer.MIN_VALUE;
        int x1 = 0;
        int y1 = 0;
        int x2 = 0;
        int y2 = 0;

        //Construct an array to save the sum from A[0][0] to A[i][j]
        int[][] sumArray = new int[m + 1][n + 1];
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
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                sumArray[i][j] = sumArray[i - 1][j] + sumArray[i][j - 1] - sumArray[i - 1][j - 1] + A[i - 1][j - 1];
            }
        }

        //Find the maximum subarray
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = i; k < m; k++) {
                    for (int l = j; l < n; l++) {
                        int sum = sumArray[k + 1][l + 1] - sumArray[k + 1][j] - sumArray[i][l + 1] + sumArray[i][j];
                        if (sum > maxSum) {
                            maxSum = sum;
                            x1 = i;
                            y1 = j;
                            x2 = k;
                            y2 = l;
                        }
                    }
                }
            }
        }

        resultList[0] = x1;
        resultList[1] = y1;
        resultList[2] = x2;
        resultList[3] = y2;
        resultList[4] = maxSum;
        return resultList;
    }

    private static int[] task6(int[][] A, int m, int n) {
        int[] resultList = new int[5];
        if (A.length == 0) {
            return resultList;
        }
        int maxSum = Integer.MIN_VALUE;
        int x1 = 0;
        int y1 = 0;
        int x2 = 0;
        int y2 = 0;

        //Construct an array to save the sum from A[0][0] to A[i][j]
        int[][] sumArray = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                sumArray[i][j] = sumArray[i - 1][j] + sumArray[i][j - 1] - sumArray[i - 1][j - 1] + A[i - 1][j - 1];
            }
        }

        //Find the maximum subarray
        for (int i = 0; i < m; i++) {
            for (int j = i; j < m; j++) {
                int[] prefix = new int[n + 1];
                for (int k = 0; k < n; k++) {
                    prefix[k + 1] = sumArray[j + 1][k + 1] - sumArray[j + 1][0] - sumArray[i][k + 1] + sumArray[i][0];
                }
//                System.out.println(Arrays.toString(prefix));
                int prevMinSum = 0;
                int prevIndex = 0;
                for (int k = 0; k < n; k++) {
                    int currMaxSum = prefix[k + 1] - prevMinSum;
                    if (currMaxSum > maxSum) {
                        maxSum = currMaxSum;
                        x1 = i;
                        x2 = j;
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

        resultList[0] = x1;
        resultList[1] = y1;
        resultList[2] = x2;
        resultList[3] = y2;
        resultList[4] = maxSum;
        return resultList;
    }

    private static void print(int[] resultList) {
        for (int result : resultList) {
            System.out.print(result + " ");
        }
        System.out.print("\n");
    }


    public static void main(String[] args) {
        switch (args[0]) {
            case "1": {
                Scanner sc = new Scanner(System.in);
                int n = sc.nextInt();
                int[] A = new int[n];
                for (int i = 0; i < n; i++) {
                    A[i] = sc.nextInt();
                }
                int[] resultList = task1(A, n);
                print(resultList);
                break;
            }
            case "2": {
                Scanner sc = new Scanner(System.in);
                int n = sc.nextInt();
                int[] A = new int[n];
                for (int i = 0; i < n; i++) {
                    A[i] = sc.nextInt();
                }
                int[] resultList = task2(A, n);
                print(resultList);
                break;
            }
            case "3a": {
                Scanner sc = new Scanner(System.in);
                int n = sc.nextInt();
                int[] A = new int[n];
                for (int i = 0; i < n; i++) {
                    A[i] = sc.nextInt();
                }
                int[] resultList = task3a(A, n);
                print(resultList);
                break;
            }
            case "3b": {
                Scanner sc = new Scanner(System.in);
                int n = sc.nextInt();
                int[] A = new int[n];
                for (int i = 0; i < n; i++) {
                    A[i] = sc.nextInt();
                }
                int[] resultList = task3b(A, n);
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
                    }
                    sc.nextLine();
                }
                int[] resultList = task4(A, m, n);
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
                    }
                    sc.nextLine();
                }
                int[] resultList = task5(A, m, n);
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
                    }
                    sc.nextLine();
                }
                int[] resultList = task6(A, m, n);
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

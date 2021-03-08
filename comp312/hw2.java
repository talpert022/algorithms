import java.util.*;
class hw2 {

  // low is the left index, and hi is the right index of the sub array to be sorted
  static void mergeSort(int[] A, int low, int hi) {
    if (hi <= low) return;

    int mid = low + (hi - low) / 2;
    mergeSort(A, low, mid);
    mergeSort(A, mid+1, hi);
    merge(A, low, mid, hi);
  }

  // Merges two sub arrays where the first subarray is A[start...mid] and second subarray is A[mid+1...end
  // For this function to work the first and second subarrays must be sorted
  static void merge(int[] A, int start, int mid, int end) {

    if (end-start <= 0 || mid < start || mid >= end) return;

    int i = mid;
    int j = mid+1;
    int swapLength = 0;

    // Find out how long the subarrays are that you want to swap/rotate
    while (i >= start && j <= end && A[i] >= A[j]) {
      i--;
      j++;
      swapLength++;
    }

    // Rotates the subarrays
    for (int a = 0; a < swapLength; a++) {
      int temp = A[mid-swapLength+1+a];
      A[mid-swapLength+1+a] = A[mid+1+a];
      A[mid+1+a] = temp;
    }

    // Recursively merge both sublists
    merge(A, start, mid-swapLength, mid);
    merge(A, mid+1, mid+swapLength, end);
  }

  public static boolean sortAndFind(int[] A, int[] B, int[] C) {

    // Sort the arrays (nlogn complexity)
    Arrays.sort(A);
    Arrays.sort(B);
    Arrays.sort(C);

    for (int i = 0; i < A.length; i++) {
      int target = 7 - A[i];
      int secondNum = 0; int thirdNum = C.length-1;

      while (secondNum < B.length && thirdNum >= 0) { //TODO: check this
        int sum = B[secondNum] + C[thirdNum];

        if (sum < target && secondNum != B.length) secondNum++;
        else if (sum > target && thirdNum >= 0) thirdNum--;
        else if (sum == target) return true;
      }
    }
    return false;
  }

  static List<Integer> mergeLists1(int[][] sortedLists) {

    int len = sortedLists[0].length;
    List<Integer> totalList = new ArrayList<Integer>();

    // Merge first two lists into totalList
    int i = 0; int j = 0;
    while (i < len || j < len) {
      if (j == len || (i != len && sortedLists[0][i] <= sortedLists[1][j])) {
        totalList.add(sortedLists[0][i]);
        i++;
      } else {
        totalList.add(sortedLists[1][j]);
        j++;
      }
    }

    // Merge the rest of the sublists
    for (int a = 2; a < sortedLists.length; a++) {
      i = 0; j = 0;
      while (j < len) {
        if (i == totalList.size() || sortedLists[a][j] <= totalList.get(i)) {
          totalList.add(i, sortedLists[a][j]);
          i++;
          j++;
        } else {
          i++;
        }
      }
    }
    return totalList;
  }
  /*
    Problem 3
    ---------
    The complexity of merging the first two lists would be 2n. The complexity of merging the rest of the lists
    would be (k-2)*(kn+n), because you are going through k lists minus the two you already sorted, and in the worst
    case the total list will be length k*n and you are also iterating through the list you are merging, which
    is n. Overall, this reduces to (k^2)n - kn steps. So this algorithm has an overall complexity of O((k^2)*n)
  */

  static List<Integer> mergeLists2(int[][] sortedLists) {

    if (sortedLists.length == 1) return convertToList(sortedLists[0]);
    else if (sortedLists.length == 2) return combine(convertToList(sortedLists[0]), convertToList(sortedLists[1]));
    else { // recursive case
      int mid = sortedLists.length / 2;
      int[][] firstHalf = Arrays.copyOfRange(sortedLists, 0, mid);
      int[][] secondHalf = Arrays.copyOfRange(sortedLists, mid, sortedLists.length);
      return combine(mergeLists2(firstHalf), mergeLists2(secondHalf));
    }
  }
  /*
     Problem 4
    -----------
    The complexity of this algorithm would be O(kn * log(k)). This is because when merging the lists, this problem takes on a
    tree structure where you are doing k/2 merges on the first level, k/4 merges on the second level and so on. This shows us
    that there are going to be log(k) levels until the list is finally merged. On each level the amount of work does not change,
    the lists that you are merging just get larger. So each level, you are doing kn work because their are k lists that have
    length of n each.
  */

  static List<Integer> combine(List<Integer> A, List<Integer> B) {
    List<Integer> res = new ArrayList<Integer>();
    int len1 = A.size();
    int len2 = B.size();
    // Merge first two lists into 'res'
    int i = 0; int j = 0;
    while (i < len1 || j < len2) {
      if (j == len2 || (i != len1 && A.get(i) <= B.get(j))) {
        res.add(A.get(i));
        i++;
      } else {
        res.add(B.get(j));
        j++;
      }
    }
    return res;
  }

  static List<Integer> convertToList(int[] ints) {
    List<Integer> intList = new ArrayList<Integer>(ints.length);
    for (int i : ints) {
      intList.add(i);
    }
    return intList;
  }

  public static void main(String[] args) {

    int[] list = new int[50];
    for (int i = 0; i < list.length; i++) {
         list[i] = new Random().nextInt(1000); // random number between 0 (inclusive) and 100 (exclusive)
    }
    // Problem 1
    System.out.println("Problem 1 \n ---------- \n");
    System.out.println("Test 1: " + Arrays.toString(list) + "\n");
    mergeSort(list, 0, list.length-1);
    System.out.println("After sort: " + Arrays.toString(list) + "\n\n");

    // Problem 2
    System.out.println("Problem 2 \n ---------- \n");

    int[] A1 = new int[] {3, -2, -8}; int[] B1 = new int[] {-5, -5, 4}; int[] C1 = new int[] {5, 1, 3};
    System.out.println("Test 1: List 1: " + Arrays.toString(A1) + ", List 2: " + Arrays.toString(B1) + ", List 3: " + Arrays.toString(C1) + "\n");
    System.out.println("Result: " + sortAndFind(A1,B1,C1));

    int[] A2 = new int[] {3, -2, -8}; int[] B2 = new int[] {-5, -5, 4}; int[] C2 = new int[] {-5, 1, 3};
    System.out.println("Test 2: List 1: " + Arrays.toString(A2) + ", List 2: " + Arrays.toString(B2) + ", List 3: " + Arrays.toString(C2) + "\n");
    System.out.println("Result: " + sortAndFind(A2,B2,C2) + "\n\n");

    // Problem 3
    System.out.println("Problem 3 \n ---------- \n (same test cases as python file) \n");
    int[][] totalArray1 = new int[][] {{1,3,4},{1,6,8},{1,2,6},{0,2,3}};
    System.out.println("Test 1: " + Arrays.toString(mergeLists1(totalArray1).toArray()));
    int[][] totalArray2 = new int[][] {{1,7}, {2,3}, {5,9}, {2,7}, {3,6}, {1,1}, {2,9}, {2,6}};
    System.out.println("Test 2: " + Arrays.toString(mergeLists1(totalArray2).toArray()) + "\n\n");

    //Problem 4
    System.out.println("Problem 4 \n ---------- \n (same test cases as python file) \n");
    System.out.println("Test 1: " + Arrays.toString(mergeLists2(totalArray1).toArray()));
    System.out.println("Test 2: " + Arrays.toString(mergeLists2(totalArray2).toArray()) + "\n\n");
  }
}

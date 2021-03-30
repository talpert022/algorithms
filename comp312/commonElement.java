import java.util.Arrays;
class exam1 {

  static boolean CommonElement1(int[] A, int[] B) {
    int n = A.length;

    if (n == 1) {
      return A[0] == B[0];
    }

    boolean r = CommonElement1(Arrays.copyOfRange(A, 0, (n+1)/2), Arrays.copyOfRange(B, 0, (n+1)/2));
    if (r) return r;

    boolean r1 = CommonElement1(Arrays.copyOfRange(A, (n+1)/2, n), Arrays.copyOfRange(B, 0, (n+1)/2));
    if (r1) return r1;

    boolean r2 = CommonElement1(Arrays.copyOfRange(A, 0, (n+1)/2), Arrays.copyOfRange(B, (n+1)/2, n));
    if (r2) return r2;

    boolean r3 = CommonElement1(Arrays.copyOfRange(A, (n+1)/2, n), Arrays.copyOfRange(B, (n+1)/2, n));
    if (r3) return r3;

    return false;
  }

  static boolean CommonElement2(int[] A, int[] B) {
    int n = A.length;

    // perform mergesort on both arrays, worst case nlogn runtime
    mergeSort(A,n);
    mergeSort(B,n);

    // search in linear time for a common element
    int i = 0, j = 0;
    while (i < n && j < n) {

      if (A[i] < B[j]) i++;
      else if (B[j] < A[i]) j++;
      else return true;
    }
    return false;
  }

  /*
   * Helper function for CommonElement2 that performs merge sort on a list A, with length len
   * Complexity: T(n) = 2T(n/2) + O(n) --> O(nlogn)
   */
  static void mergeSort(int[] A, int len) {
    if (len <= 1) return;

    int m = len / 2;
    int[] l = new int[m];
    int[] r = new int[len - m];

    // Create sublists from left and right sides of A
    for(int i = 0; i < m; ++i) {
      l[i] = A[i];
    }

    for(int i = m; i < len; ++i) {
      r[i - m] = A[i];
    }

    // Recursively create sublists until all lists are of size 1
    mergeSort(l, m);
    mergeSort(r, len - m);

    // Merge sublists l and r and combine them into A
    merge(A, l, r);
  }

  // Helper function for mergesort that merges sorted sublists
  private static void merge(int[] A, int[] l, int[] r) {

    int i = 0, j = 0;
    int count = 0;

    // Compare elements add the smaller one back into A
    while (i < l.length && j < r.length) {
      if (l[i] <= r[j])
        A[count++] = l[i++];
      else
        A[count++] = r[j++];
    }

    // Adds extra members of l or r to A if there are any
    while (i < l.length) {
      A[count++] = l[i++];
    }

    while (j < r.length) {
      A[count++] = r[j++];
    }
  }

  public static void main(String[] args) {

    int[] A = new int[] {1,2,3,2,4,5,2,3,4,1,5,4,2,1,2,3,2,4,5,2,3,4,1,5,4,2};
    int[] B = new int[] {7,7,8,9,0,9,6,7,6,9,0,7,8,7,7,8,9,0,9,6,7,6,4,0,7,8};

    int[] A1 = new int[] {1,2,3,2,4,5,2,3,4,1,5,4,2,1,2,3,2,4,5,2,3,4,1,5,4,2};
    int[] B1 = new int[] {7,7,8,9,0,9,6,7,6,9,0,7,8,7,7,8,9,0,9,6,7,6,8,0,7,8};

    // True test case for CommonElement1
    System.out.println((CommonElement1(A,B)));
    // False test case for CommonElement1
    System.out.println((CommonElement1(A1,B1)));
    // True test case for CommonElement2
    System.out.println((CommonElement2(A,B)));
    // False test case for CommonElement2
    System.out.println((CommonElement2(A1,B1)));
  }
}

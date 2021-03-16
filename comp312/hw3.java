import java.util.Arrays;
class hw3 {

  static int localMinima(int[] list) {

    int l = 0; int r = list.length - 1;
    while (l <= r) {
      int m = l + (r - l) / 2;
      if (m != list.length-1 && list[m] > list[m+1]) { // then search for the local minima in the right half
        l = m+1;
      } else if (m != 0 && list[m-1] < list[m]) { // then search for the local minima in the left half
        r = m-1;
      } else return m;
    }
    return -1;
  }

  static int[] local2dMinima(int[][] list) {

    int n = list.length;

    for (int i = 0; i < n; i++) {
      int localMin = localMinima(list[i]);
      if (localMin != -1) { // Checks if there is a local min in one row
        // Check if its a local min in the 2d array
        if (i == 0 && list[i][localMin] <= list[i+1][localMin])
          return new int[] {i, localMin};
        else if (i > 0 && i < n-1 && list[i][localMin] <= list[i-1][localMin] && list[i][localMin] <= list[i+1][localMin])
          return new int[] {i, localMin};
        else if (i == n-1 && list[i][localMin] <= list[i-1][localMin])
          return new int[] {i, localMin};
      }
    }

    return new int[] {-1, -1};
  }

  public static void main(String[] args) {

    // Test case for 1d Min
    int[] l = new int[] {1,5,8,7,6,11,13};
    System.out.println("Index of local Min for " + Arrays.toString(l) + ": " + localMinima(l));

    //Test case for 2d Min
    int[][] list = new int[][] {{9,10,9,7},{7,8,10,6},{5,3,6,4},{3,1,2,3}};
    System.out.println("Index of local min for 2d array: " + Arrays.toString(local2dMinima(list)));
  }

}

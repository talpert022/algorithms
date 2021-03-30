import java.lang.Math;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
class hw4 {

  enum Pivot {
    FIRST,
    MIDDLE,
    RANDOM
  }

  // Returns the total number of comparisons and sorts the list
  static int qs1(int[] xs) {
    return quicksort_helper(xs, 0, xs.length, Pivot.FIRST, false);
  }

  static int qs2(int[] xs) {
    return quicksort_helper(xs, 0, xs.length, Pivot.RANDOM, false);
  }

  static int worst_qs(int[] xs) {
    Arrays.sort(xs);
    return quicksort_helper(xs, 0, xs.length, Pivot.FIRST, false);
  }

  static int better_qs2(int[] xs) {
    return quicksort_helper(xs, 0, xs.length, Pivot.RANDOM, true);
  }

  static int quicksort_helper(int[] xs, int i, int j, Pivot pivotIndex, boolean goodPartition) {

    int currentComparisons, rightComparisons, leftComparisons;
    currentComparisons = leftComparisons = rightComparisons = 0;
    if (i < j) {
      int r = 0;
      // Set pivot position
      switch (pivotIndex) {
        case FIRST:
          r = i;
          break;
        case MIDDLE:
          r = i + (j-i)/2;
          break;
        case RANDOM:
          r = ThreadLocalRandom.current().nextInt(i, j);
          break;
      }

      // make pivot first element in xs[i:j]
      int t = xs[r]; xs[r] = xs[i]; xs[i] = t;

      // partition the list around xs[i]
      // bool goodPartition determines which partition function we use
      if (!goodPartition) {
        int[] res = partition(xs, i, j);
        int m = res[0];
        currentComparisons = res[1];

        // sort each sublist recursively
        leftComparisons = quicksort_helper(xs, i, m, pivotIndex, goodPartition);
        rightComparisons = quicksort_helper(xs, m+1, j, pivotIndex, goodPartition);
      } else {
        int[] res = better_partition(xs, i, j);
        int e = res[0];
        int m = res[1];
        currentComparisons = res[2];

        // sort each sublist recursively
        leftComparisons = quicksort_helper(xs, i, e-1, pivotIndex, goodPartition);
        rightComparisons = quicksort_helper(xs, m, j, pivotIndex, goodPartition);
      }
    } // end if
    return currentComparisons + leftComparisons + rightComparisons;
  }

  static int[] partition(int[] xs, int i, int j) {
    int comparisons = 0;
    int pivot = xs[i];
    int m = i + 1;
    for (int k = i+1; k < j; k++) {
      if (xs[k] <= pivot) {
        // swap elements at k and m
        int t = xs[k]; xs[k] = xs[m]; xs[m] = t;
        m += 1;
      }
      comparisons++;
    }
    // partition the list
    int t = xs[i]; xs[i] = xs[m-1]; xs[m-1] = t;
    return new int[] {m-1, comparisons};
  }

  static int[] better_partition(int[] xs, int i, int j) {
    int comparisons = 0;
    int pivot = xs[i];
    int m = i + 1;
    int e = i + 1;
    for (int k = i+1; k < j; k++) {
      if (xs[k] < pivot) {
        // swap elements at k and m
        int t = xs[k]; xs[k] = xs[m]; xs[m] = t;
        m += 1;
        e += 1;
      } else if (xs[k] == pivot) {
        int t = xs[k]; xs[k] = xs[e]; xs[e] = t;
        m += 1;
      } // end if
      comparisons++;
    }
    // put the pivot in the middle equal list
    int t = xs[i]; xs[i] = xs[e-1]; xs[e-1] = t;
    m += 1; // increase size of equals list to account for pivot
    return new int[] {e-1, m-1, comparisons};
  }

  public static void main(String[] args) {

    int[] xs = new int[] {5,1,3,7,2,1,11,-5};
    System.out.println("Static pivot: " + qs1(xs) + " comparisons.");
    System.out.println("Random pivot: " + qs2(xs) + " comparisons.");
    System.out.println("Random pivot with better partition: " + better_qs2(xs) + " comparisons.");
    System.out.println("Worst pivot: " + worst_qs(xs) + " comparisons.");

    System.out.println("\n---------- Better partition test ----------");
    int[] ys = new int[] {7,1,2,2,3,4,7,7,7,7,8,9};
    int[] indexs = better_partition(ys, 0, ys.length);
    System.out.println("Less than: " + Arrays.toString(Arrays.copyOfRange(ys,0,indexs[0])));
    System.out.println("Equal to: " + Arrays.toString(Arrays.copyOfRange(ys,indexs[0],indexs[1])));
    System.out.println("More than: " + Arrays.toString(Arrays.copyOfRange(ys,indexs[1],ys.length)));

  }
}

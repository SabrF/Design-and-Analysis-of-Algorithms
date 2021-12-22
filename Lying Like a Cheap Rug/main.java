import java.util.Scanner;

public class Solution {
  
  static int n, m, lies;
  static char[] types;
  static int[] indexes, sums;
  
  public static void main(String[] args) {
    // Read input
    Scanner sc = new Scanner(System.in);
    n = sc.nextInt();
    m = sc.nextInt();
    
    int[] values = new int[n*m];
    for (int i = 0; i < values.length; i++) {
      values[i] = sc.nextInt();
    }
    
    int f = sc.nextInt();
    lies = sc.nextInt();
    types = new char[f];
    indexes = new int[f];
    sums = new int[f];
    for (int i = 0; i < f; i++) {
      types[i] = sc.next().charAt(0);
      indexes[i] = sc.nextInt();
      sums[i] = sc.nextInt();
    }
    
    // Explore all permutations of values
    Integer[] lbound = new Integer[n*m];
    Integer[] ubound = new Integer[n*m];
  
    explorePermutations(values, 0, lbound, ubound);
    
    // Output the bounds
    for (int i = 0; i < lbound.length; i++) {
      System.out.println(lbound[i] + " " + ubound[i]);
    }
    
  }

  // Generate all permutations of the values array by permutating values starting at index 
  static void explorePermutations(int[] values, int index, Integer[] lbound, Integer[] ubound) {
    if (index == values.length) {
      checkGrid(values, lbound, ubound);
      return;
    }
    
    int value = values[index];
    for (int i = index; i < values.length; i++) {
      values[index] = values[i];
      values[i] = value;
      explorePermutations(values, index + 1, lbound, ubound); 
      values[i] = values[index];
      values[index] = value;  
    }    
  }

  // Check to see if the permutation is consistent with the number of lies, and if so, update 
  // the bounds
  static void checkGrid(int[] values, Integer[] lbound, Integer[] ubound) {
    int bad = 0;
    for (int i = 0; i < types.length; i++) {
      int sum = 0;
      if (types[i] == 'c') {
        for (int j = 0; j < n; j++) {
          sum += values[indexes[i] + j * m];
        }
      }
      else { // types[i] == 'r'
        for (int j = 0; j < m; j++) {
          sum += values[indexes[i] * m + j];
        }
      }
      if (sum != sums[i]) bad++;
    }
    if (bad == lies) {
      for (int i = 0; i < lbound.length; i++) {
        if (lbound[i] == null) {
          lbound[i] = values[i];
          ubound[i] = values[i];
        }
        else {
          lbound[i] = Math.min(lbound[i], values[i]);
          ubound[i] = Math.max(ubound[i], values[i]);
        }
      }
    }
  }
}

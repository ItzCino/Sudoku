import java.util.ArrayList;
// https://www.geeksforgeeks.org/multidimensional-collections-in-java/
public class Learn_2D_ArrayLists {
  public static void main(String args[]) {
    example_1();
    example_2();
  }

  public static void example_1() {
    ArrayList<ArrayList<Integer>> myArray = new ArrayList<ArrayList<Integer>>();

    for (int i = 0; i < 3; i++) {
      myArray.add(new ArrayList<Integer>());
      for (int j = 0; j < 3; j++) {
        myArray.get(i).add(i*j);
      }
    }

    System.out.println(myArray.get(0));
    System.out.println(myArray.get(1));
    System.out.println(myArray.get(2));
  }

  public static void example_2() {
    ArrayList<ArrayList<Integer>> cool_numbers = new ArrayList<ArrayList<Integer>>();
    for (int i = 0; i < 11; i++) {
      cool_numbers.add(new ArrayList<Integer>());
      for (int j = 0; j < 11; j++) {
        cool_numbers.get(i).add(i*j);
      }
    }

    for (int i=0; i<11; i++) {
      System.out.println(cool_numbers.get(i));
    }
  }
}
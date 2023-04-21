import java.util.ArrayList;

public class GridCheck {
  public static void checkAllOuterBoxes(
          ArrayList<ArrayList<Integer>> duplicateValues, 
          ArrayList<ArrayList<Sudoku>> data) {

    System.out.println();
    for (int i=0; i<data.size(); i++) {
    //   System.out.println("OUTER BOX: " + i);
      ArrayList<Sudoku> duplicates;
      duplicates =  Sudoku.getBoxDuplicates(data.get(i), i);
    //   System.out.println("BOX "+i+":\n" + duplicates + "\n");
      Solver.addDuplicates(duplicates, duplicateValues);
    }
  }
// Horizontal Checking (Next 2 methods)
  public static void checkAllHorizontalRows(
          int boxSize, 
          ArrayList<ArrayList<Integer>> duplicateValues, 
          ArrayList<ArrayList<Sudoku>> data) {
    // boxes (0 to 3), innerboxes (0 to 3) (3 to 6) (6 to 9)
    // ArrayList<Sudoku> duplicates;
    int outerLow = 0;
    int outerHigh = boxSize;
    for (int i=0 ; i<boxSize; i++) {
      ArrayList<Sudoku> row;
      ArrayList<Sudoku> duplicates;

      int innerLow = 0;
      int innerHigh = boxSize;
      for (int j=0; j<boxSize; j++) {
        row = Sudoku.getHorizontalRow(outerLow, outerHigh, innerLow, innerHigh, data);
        duplicates = Solver.getDuplicates(row);
        Solver.addDuplicates(duplicates, duplicateValues);
        innerLow += boxSize;
        innerHigh += boxSize;
      }
      outerLow += boxSize;
      outerHigh += boxSize;
    }
  }

  public static void checkAllVerticalColumns(int boxSize, 
          ArrayList<ArrayList<Integer>> duplicateValues, 
          ArrayList<ArrayList<Sudoku>> data) {

    // go from top to bottom, left to right, check each column
    // inner from 0 to 6, outer from 0 to 6, increment by 1 per iteration
    // boxes (0 to 3), innerboxes (0 to 3) (3 to 6) (6 to 9)
    // ArrayList<Sudoku> duplicates;
    int outerLow = 0;
    int outerHigh = 2*boxSize;
    for (int i = 0; i < boxSize; i++) {
      ArrayList<Sudoku> column;
      ArrayList<Sudoku> duplicates;  
      int innerLow = 0;
      int innerHigh = 2*boxSize;
      for (int j = 0; j < boxSize; j++) {
        column = Sudoku.getVerticalColumn(outerLow, outerHigh, innerLow, innerHigh, data);
        duplicates = Solver.getDuplicates(column);
        Solver.addDuplicates(duplicates, duplicateValues);
        innerLow++;
        innerHigh++;
      }
      outerLow++;
      outerHigh++;
    }
  }
}

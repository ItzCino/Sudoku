import java.util.ArrayList;

// This class performs the checking of the grid for rows which are "complete"
public class GridCheck {
  // Checks that if each large 3x3 grid is complete
  public static void checkAllOuterBoxes(
          ArrayList<ArrayList<Integer>> duplicateValues, 
          ArrayList<ArrayList<Sudoku>> data) {

    System.out.println();
    for (int i=0; i<data.size(); i++) {
      ArrayList<Sudoku> duplicates;
      duplicates =  Sudoku.getBoxDuplicates(data.get(i), i);
      Solver.addDuplicates(duplicates, duplicateValues);
    }
  }
  // Checks if each horizontal rows is complete (Next 2 methods)
  public static void checkAllHorizontalRows(
          int boxSize, 
          ArrayList<ArrayList<Integer>> duplicateValues, 
          ArrayList<ArrayList<Sudoku>> data) {
    // boxes (0 to 3), innerboxes (0 to 3) (3 to 6) (6 to 9)
    int outerLow = 0;
    int outerHigh = boxSize;
    // Checks boxes from left to right, bottom to top
    for (int i=0 ; i<boxSize; i++) {
      ArrayList<Sudoku> row;
      ArrayList<Sudoku> duplicates;

      int innerLow = 0;
      int innerHigh = boxSize;
      // Checks each row from left to right, bottom to top
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

  // Checks if each vertical row is complete (Next 2 methods)
  public static void checkAllVerticalColumns(int boxSize, 
          ArrayList<ArrayList<Integer>> duplicateValues, 
          ArrayList<ArrayList<Sudoku>> data) {

    // go from top to bottom, left to right, check each column
    // inner from 0 to 6, outer from 0 to 6, increment by 1 per iteration
    // boxes (0 to 3), innerboxes (0 to 3) (3 to 6) (6 to 9)
    // ArrayList<Sudoku> duplicates;
    int outerLow = 0;
    int outerHigh = 2*boxSize;
    // Checks boxes from top to bottom, left to right, 
    for (int i = 0; i < boxSize; i++) {
      ArrayList<Sudoku> column;
      ArrayList<Sudoku> duplicates;  
      int innerLow = 0;
      int innerHigh = 2*boxSize;
      // Checks each column from top to bottom, left to right, 
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

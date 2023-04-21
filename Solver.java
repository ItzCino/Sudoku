import java.util.ArrayList;

public class Solver {
  public static ArrayList<Sudoku> getDuplicates(ArrayList<Sudoku> row) {
    ArrayList<Sudoku> duplicates = new ArrayList<Sudoku>();
    for (int i = 0; i < row.size(); i++) {
      for (int j = 0; j < row.size(); j++) {
        if (i == j) {
          continue; // skip if same index
        }  
        if (row.get(i).getValue().equals(row.get(j).getValue())) {
          // add the part where compare if the value is the emtrpy input i.e ""
          if ((row.get(i).getValue().equals("")) == false) {
            duplicates.add(row.get(i));
          }
        }
      }
    }
    return duplicates;
  }

  public static Boolean areThereDuplicates(ArrayList<ArrayList<Integer>> duplicateValues) {
    for (int i = 0; i < duplicateValues.size(); i++) {
      if (duplicateValues.get(i).size() > 0) {
        return true;
      }
    }
    return false;    
  }

  public static void addDuplicates(ArrayList<Sudoku> duplicates, ArrayList<ArrayList<Integer>> duplicateValues) {
    for (int i = 0; i < duplicates.size(); i++) {
      addDuplicateFromSudokuType(duplicates.get(i), duplicateValues);
    }
  }
  
  public static void addDuplicateFromSudokuType(Sudoku field, ArrayList<ArrayList<Integer>> duplicateValues) {
    int outerBox = field.getOuterBoxIDInt();
    int innerBox = field.getInnerBoxIDInt();
    duplicateValues.get(outerBox).add(innerBox);
    duplicateValues.get(outerBox).add(innerBox);
    duplicateValues.get(outerBox).add(innerBox);
  }
}

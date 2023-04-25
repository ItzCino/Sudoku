import java.util.ArrayList;

public class Solver {

  // solver should copy over the entire arraylist and check if it is valid 'i.e no red fields'
  // solver should then brute force to solution provided the problem is valid
  // solver should then be keeping the "problem" fields the same. 
  // and should not share the same pointer to the original so a copy is made

  public static void SolveSudoku(ArrayList<ArrayList<Sudoku>> data, Boolean areThereDuplicates) {
    Boolean puzzleSolved = false;
    if (areThereDuplicates == true) {
      System.out.println("CANNOT BE SOLVED");
      return;
    }

    ArrayList<ArrayList<Sudoku>> workingData = new ArrayList<ArrayList<Sudoku>>();
    copyData(data, workingData);
    setFieldsToOne(workingData);
    /*
     * try every combination to sudoku puzzle
     * if it is valid, then it is solved
     * if it is not valid, then it is not solved
     * if it is not solved, then try another combination
     * if it is solved, then return the solved puzzle
     */
    int boxes = MyFrame.noOfRows;
    int endIndex = boxes - 1;
    // outer box
    int workingOuterBox = 0;
    int workingInnerBox = 0;

    // gets the next empty field
    Boolean FoundIndex = false;
    for (int i = 0; i < boxes; i++) {
      for (int j = 0; j < boxes; j++) {
        if (workingData.get(i).get(j).getValue().equals("")) {
          workingOuterBox = i;
          workingInnerBox = j;
          FoundIndex = true;
          break;
        }
      }
      if (FoundIndex == true) {
        break;
      }
    }
    System.out.println("AREAAAA"+workingOuterBox + ", " + workingInnerBox);
    // outer box
    for (int i = 0; i < boxes; i++) {
      // inner box
      for (int j = 0; j < boxes; j++) {
        for (int k=1; k<= boxes; k++) {
          workingData.get(workingOuterBox).get(workingInnerBox).setValue(k);
          puzzleSolved = isPuzzleSolved(workingData);
          if (puzzleSolved == true) {
            break;
          }
          System.out.println("Working data: ");
          for (int a = 0; a < boxes; a++) {
            for (int b = 0; b < boxes; b++) {
              System.out.print(workingData.get(a).get(b).getValue());
            }
            System.out.println();
          }
        }
        if (puzzleSolved == true) {
            break;
        }
        // incrementor
        for (int x = 0; x <= i; x++) {
          for (int y = 0; y <= j; y++) {
            if ((x == workingOuterBox) && (y == workingInnerBox)) {
              continue;
            }
            workingData.get(x).get(y).plusOne();            
          }
        }
      }
      if (puzzleSolved == true) {
        break;
      }
    }

    isPuzzleSolved(workingData);

  }

  public static void copyData(ArrayList<ArrayList<Sudoku>> data, ArrayList<ArrayList<Sudoku>> workingData) {
    for (int i=0; i < MyFrame.noOfRows; i++) {
      workingData.add(new ArrayList<Sudoku>());
      for (int j = 0; j < MyFrame.noOfRows; j++) {
        workingData.get(i).add(data.get(i).get(j));
      }
    }
    // System.out.println("\nPREV:\n"+ backupData);
  }

  public static Boolean areFieldsFull(ArrayList<ArrayList<Sudoku>> workingData) {
    Boolean isFull = true;
    for (int i=0; i < MyFrame.noOfRows; i++) {
      for (int j = 0; j < MyFrame.noOfRows; j++) {
        if (workingData.get(i).get(j).getValue().equals("")) {
          isFull = false;
        }
      }
    }
    return isFull;
  }

  public static void setFieldsToOne(ArrayList<ArrayList<Sudoku>> workingData) {
    for (int i=0; i < MyFrame.noOfRows; i++) {
      for (int j = 0; j < MyFrame.noOfRows; j++) {
        if (workingData.get(i).get(j).getValue().equals("")) {
          workingData.get(i).get(j).setValue(1);
        }
      }
    }
  }

  public static Boolean isPuzzleSolved(ArrayList<ArrayList<Sudoku>> data) {
    ArrayList<ArrayList<Integer>> duplicateValues;
    duplicateValues = MyFrame.createDuplicateValuesArray();
    // System.out.println("======NEW DATA================");
    GridCheck.checkAllOuterBoxes(duplicateValues, data);
    GridCheck.checkAllHorizontalRows(MyFrame.boxSize, duplicateValues, data);
    GridCheck.checkAllVerticalColumns(MyFrame.boxSize, duplicateValues, data);
    Boolean areThereDuplicateValues = Solver.areThereDuplicates(duplicateValues);
    Boolean areFieldsFull = Solver.areFieldsFull(data);
    // System.out.println("Duplicates: " + areThereDuplicateValues);
    Fields.updateFieldColour(duplicateValues, data);
    if ((areFieldsFull == true) && (areThereDuplicateValues == false)) {
      System.out.println("SOLVED");
      return true;
    } else {
      System.out.println("NOT SOLVED");
      return false;
    }
  }

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

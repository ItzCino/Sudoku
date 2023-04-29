import java.awt.Color;
import java.util.ArrayList;

public class Solver {

  // solver should copy over the entire arraylist and check if it is valid 'i.e no red fields'
  // solver should then brute force to solution provided the problem is valid
  // solver should then be keeping the "problem" fields the same. 
  // and should not share the same pointer to the original so a copy is made

  public static void SolveSudoku(ArrayList<ArrayList<Sudoku>> data, Boolean areThereDuplicates) {
    Boolean isThereDuplicates;
    Boolean puzzleSolved = false;
    if (areThereDuplicates == true) {
      System.out.println("CANNOT BE SOLVED");
      return;
    }
    ArrayList<ArrayList<ArrayList<Integer>>> possibleSolutions = new ArrayList<ArrayList<ArrayList<Integer>>>();
    ArrayList<ArrayList<ArrayList<Integer>>> impossibleSolutions = new ArrayList<ArrayList<ArrayList<Integer>>>();
    ArrayList<ArrayList<Integer>> initalArray = new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<Sudoku>> workingData = new ArrayList<ArrayList<Sudoku>>();
    copyData(data, workingData);
    initalArray = toIntegerArray(workingData);
    // setFieldsToOne(workingData);
    /*
     * try every combination to sudoku puzzle
     * if it is valid, then it is solved
     * if it is not valid, then it is not solved
     * if it is not solved, then try another combination
     * if it is solved, then return the solved puzzle
     */
    int boxes = MyFrame.noOfRows;
    ArrayList<ArrayList<Integer>> tempIntegerArray;
    // outer box
    int workingOuterBox = 0 ;
    int workingInnerBox = 0;
    int backtrackcounter = 1;
    while (puzzleSolved == false) {
        if ((workingOuterBox == 8) && (workingInnerBox == 2)) {
            System.out.println("8,2");
        }
      Boolean FoundIndex = false;
      for (int i = 0; i < boxes; i++) {
        for (int j = 0; j < boxes; j++) {
          Sudoku tempSudoku = workingData.get(i).get(j);
          if (tempSudoku.getValueInt() == 0) {
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
      System.out.println("Found index:" + FoundIndex); 
      // if no index is found, then set the next index to be a duplicate field
      if (FoundIndex == false) {
        Boolean FoundDuplicate = false;
        for (int i = 0; i < boxes; i++) {
          for (int j = 0; j < boxes; j++) {
            Sudoku tempSudoku = workingData.get(i).get(j);
            if (tempSudoku.getFieldColour().equals(Color.RED)) {
              workingOuterBox = i;
              workingInnerBox = j;
              FoundDuplicate = true;
              break;
            }
          }
          if (FoundDuplicate == true) {
            break;
          }
        }
        System.out.println("Backtrackcounter: " + backtrackcounter);
        toSudokuArray(possibleSolutions.get(possibleSolutions.size() - 1), workingData);
        System.out.println(possibleSolutions.get(possibleSolutions.size() - 1));
        continue;
      }
      
      int duplicates = 0;
      for (int k=1; k<= boxes; k++) {
        workingData.get(workingOuterBox).get(workingInnerBox).setValue(k);
        isThereDuplicates = areThereDuplicatesStandalone(workingData);
        tempIntegerArray = toIntegerArray(workingData);  
        System.out.println(isThereDuplicates);
        /////////////////////////////
        /* THIS CODES NEEDS TO BE SPED UP ALOT */
        System.out.println("Working data: ");
        System.out.println(workingOuterBox + ", " + workingInnerBox);
        // for (int a = 0; a < boxes; a++) {
        //   for (int b = 0; b < boxes; b++) {
        //     System.out.print(workingData.get(a).get(b).getValueInt());
        //   }
        //   System.out.println();
        // }
        // PRINTING _>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        int boxSize = MyFrame.boxSize;
        int outerLow = 0;
        int outerHigh = boxSize;
        for (int i=0 ; i<boxSize; i++) {
          ArrayList<Sudoku> row;
    
          int innerLow = 0;
          int innerHigh = boxSize;
          for (int j=0; j<boxSize; j++) {
            row = Sudoku.getHorizontalRow(outerLow, outerHigh, innerLow, innerHigh, data);
            for (int a = 0; a < boxes; a++) {
              System.out.print(row.get(a).getValueInt());
            }
            System.out.println();
            innerLow += boxSize;
            innerHigh += boxSize;
          }
          outerLow += boxSize;
          outerHigh += boxSize;
        }
        // PRINTING _>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        System.out.println("Backtrackcounter: " + backtrackcounter);
        System.out.println("Duplicates: " + duplicates);
    
        ///////////////////////////  
        
        if (impossibleSolutions.contains(tempIntegerArray)) {
          duplicates++;
          continue;
        }
        if (isThereDuplicates == false) {
          puzzleSolved = isPuzzleSolved(workingData);
          possibleSolutions.add(tempIntegerArray);
          if (puzzleSolved == true) {
            break;
          }
          backtrackcounter = 0;
        }
        if (isThereDuplicates == true) {
          impossibleSolutions.add(tempIntegerArray);
          duplicates++;
          continue;
        }
        }
        // add backtrackcounter
        if (duplicates == 9) {
        //   removeOneDuplicate(workingData);
          backtrackcounter++;
        //   System.out.println("Backtrackcounter: " + backtrackcounter);
          toSudokuArray(possibleSolutions.get(possibleSolutions.size() - backtrackcounter), workingData);
        }
        
        if (puzzleSolved == true) {
            break;
        }
    }
    //   isPuzzleSolved(workingData);
  }

  public static void removeOneDuplicate(ArrayList<ArrayList<Sudoku>> workingData) {
    // remove duplicates from workingData
    int size = workingData.size();
    int counter = 0;
    for (int i = 0; i<size; i++) {
      for (int j = 0; j<size; j++) {
        Color fieldColor = workingData.get(i).get(j).getFieldColour();
        System.out.println(fieldColor.equals(Color.RED) + ", " + fieldColor + ", " + Color.RED);
        if (fieldColor.equals(Color.RED)) {
          workingData.get(i).get(j).setValue(0);
          workingData.get(i).get(j).setWhiteField();
          counter++;
        }
        if (counter == 1) {
          break;
        }
      }
      if (counter == 1) {
        break;
      }
    }
    System.out.println("No. of dupliates: " + counter);
  }

  public static ArrayList<ArrayList<Integer>> toIntegerArray(ArrayList<ArrayList<Sudoku>> workingData) {
    ArrayList<ArrayList<Integer>> integerData = new ArrayList<ArrayList<Integer>>();
    for (int i=0; i < MyFrame.noOfRows; i++) {
      integerData.add(new ArrayList<Integer>());
      for (int j = 0; j < MyFrame.noOfRows; j++) {
        integerData.get(i).add(j, workingData.get(i).get(j).getValueInt());
      }
    }
    return integerData;
  }

  public static void toSudokuArray(ArrayList<ArrayList<Integer>> integerData, ArrayList<ArrayList<Sudoku>> workingData) {
    for (int i=0; i < MyFrame.noOfRows; i++) {
      for (int j = 0; j < MyFrame.noOfRows; j++) {
        workingData.get(i).get(j).setValue(integerData.get(i).get(j));
      }
    }
  }

  public static ArrayList<Integer> nextEmptyField(ArrayList<ArrayList<Sudoku>> workingData){
    // gets the next empty 
    int boxes = MyFrame.noOfRows;
    int workingOuterBox = 0;
    int workingInnerBox = 0;
    int fieldFull = 0;
    ArrayList<Integer> emptyFields = new ArrayList<Integer>();
    Boolean FoundIndex = false;
    for (int i = 0; i < boxes; i++) {
      for (int j = 0; j < boxes; j++) {
        if (workingData.get(i).get(j).getValue().equals("")) {
          workingOuterBox = i;
          workingInnerBox = j;
          fieldFull = 1;
          FoundIndex = true;
          break;
        }
      }
      if (FoundIndex == true) {
        break;
      }
    }
    emptyFields.add(workingOuterBox);
    emptyFields.add(workingInnerBox);
    emptyFields.add(fieldFull);

    return emptyFields;
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

  public static Boolean areThereDuplicatesStandalone(ArrayList<ArrayList<Sudoku>> data) {
    ArrayList<ArrayList<Integer>> duplicateValues;
    duplicateValues = MyFrame.createDuplicateValuesArray();
    // System.out.println("======NEW DATA================");
    GridCheck.checkAllOuterBoxes(duplicateValues, data);
    GridCheck.checkAllHorizontalRows(MyFrame.boxSize, duplicateValues, data);
    GridCheck.checkAllVerticalColumns(MyFrame.boxSize, duplicateValues, data);
    Boolean areThereDuplicateValues = Solver.areThereDuplicates(duplicateValues);
    return areThereDuplicateValues;  
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

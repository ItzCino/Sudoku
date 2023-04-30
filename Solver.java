import java.util.ArrayList;

public class Solver {
  private static final int BOX_SIZE = 3;
  private static final int GRID_SIZE = 9;
  static ArrayList<ArrayList<Sudoku>> workingData = new ArrayList<ArrayList<Sudoku>>();
  static int[][] solvedArray = new int[GRID_SIZE][GRID_SIZE];
  // solver should copy over the entire arraylist and check if it is valid 'i.e no red fields'
  // solver should then brute force to solution provided the problem is valid
  // solver should then be keeping the "problem" fields the same. 
  // and should not share the same pointer to the original so a copy is made

  public static void SolveSudoku(ArrayList<ArrayList<Sudoku>> data, Boolean areThereDuplicates) {
    if (areThereDuplicates == true) {
      System.out.println("CANNOT BE SOLVED");
      return;
    }

    Boolean puzzleSolved = false;

    // this inital array is stored such, each ArrayList contains each row of the grid.
    toSolvingArray(data);

    copyData(data, workingData);

    puzzleSolved = solveBoard(solvedArray);
    toSolvedSudokuArray();

    if(puzzleSolved) {
      System.out.println("SOLVED");
    } else {
      System.out.println("UNSOLVEABLE");
    }
    printGrid(workingData);

    // setFieldsToOne(workingData);
    /*
     * try every combination to sudoku puzzle
     * if it is valid, then it is solved
     * if it is not valid, then it is not solved
     * if it is not solved, then try another combination
     * if it is solved, then return the solved puzzle
     */
    
    // if (solve(workingData)) {
    //   System.out.println("SOLVED");
    //   printGrid(workingData);
    //   puzzleSolved = true;
    // } else {
    //   System.out.println("UNSOLVEABLE");
    //   printGrid(workingData);
    //   puzzleSolved = false;
    // }

    // System.out.println("{}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}");
    //     for (int i = 0; i < GRID_SIZE; i++) {
    //       for (int j = 0; j < GRID_SIZE; j++) {
    //         System.out.print(board[i][j]);
    //         solvedArray[i][j] = board[i][j];
    //       }
    //       System.out.println();
        // }
    
  }
  private static boolean isNumberInRow(int[][] board, int number, int row) {
    for (int i = 0; i < GRID_SIZE; i++) {
      if (board[row][i] == number) {
        return true;
      }
    }
    return false;
  }

	private static boolean isNumberInColumn(int[][] board, int number, int column) {
	  for (int i = 0; i < GRID_SIZE; i++) {
	  	if (board[i][column] == number) {
          return true;
	  	}
	  }
	  return false;
	}

  private static boolean isNumberInBox(int[][] board, int number, int row, int column) {
    int initialBoxRow = row - row % BOX_SIZE;
    int initialBoxColumn = column - column % BOX_SIZE;  
    for (int i = initialBoxRow; i < initialBoxRow + BOX_SIZE; i++) {
      for (int j = initialBoxColumn; j < initialBoxColumn + BOX_SIZE; j++) {
        if (board[i][j] == number) {
          return true;
        }
      }
    }
    return false;
  }

  private static boolean isValidPlacement(int[][] board, int number, int row, int column) {
    return !isNumberInRow(board, number, row) && !isNumberInColumn(board, number, column)
    && !isNumberInBox(board, number, row, column);
  }

  private static boolean solveBoard(int[][] board) {
	for (int row = 0; row < GRID_SIZE; row++) {
	  for (int column = 0; column < GRID_SIZE; column++) {
	    if (board[row][column] == 0) {
        Boolean fitted = false;
	      for (int numberToTry = 1; numberToTry <= GRID_SIZE; numberToTry++) {
	        if (isValidPlacement(board, numberToTry, row, column)) {
	          board[row][column] = numberToTry;
              fitted = true;
	          if (solveBoard(board)) {
                return true;
              } else {
                board[row][column] = 0;
              }
	        }
	      }
	      if (!fitted) {
            return false;
          }
	    } 
	  }
	}
	return true;
	}

  public static void toSolvingArray(ArrayList<ArrayList<Sudoku>> data) {
    int boxSize = MyFrame.boxSize;
    int rowSize = MyFrame.noOfColumns;
    int outerLow = 0;
    int outerHigh = boxSize;
    int rowCounter = 0;
    System.out.println("====== SOLVING ARRAY DATA================");
    for (int i = 0; i < boxSize; i++) {
      ArrayList<Sudoku> row;
      int innerLow = 0;
      int innerHigh = boxSize;
      for (int j = 0; j < boxSize; j++) {
        row = Sudoku.getHorizontalRow(outerLow, outerHigh, innerLow, innerHigh, data);
        for (int a = 0; a < rowSize; a++) {
          System.out.print(row.get(a).getValueInt());
          solvedArray[rowCounter][a] = row.get(a).getValueInt();
        }
      System.out.println();
      innerLow += boxSize;
      innerHigh += boxSize;
      rowCounter++;
      }
      outerLow += boxSize;
      outerHigh += boxSize;
    }
  }

  public static void toSolvedSudokuArray() {
    int boxSize = MyFrame.boxSize;
    int rowSize = MyFrame.noOfColumns;
    int outerLow = 0;
    int outerHigh = boxSize;
    int currentRow = 0;
    System.out.println("====== 5OLVED ARRAY DATA================");
    for (int i = 0; i < boxSize; i++) {
      ArrayList<Sudoku> row;
      int innerLow = 0;
      int innerHigh = boxSize;
      for (int j = 0; j < boxSize; j++) {
        row = Sudoku.getHorizontalRow(outerLow, outerHigh, innerLow, innerHigh, workingData);
        for (int a = 0; a < rowSize; a++) {
          row.get(a).setValue(solvedArray[currentRow][a]);
        }
      innerLow += boxSize;
      innerHigh += boxSize;
      currentRow++;
      }
      outerLow += boxSize;
      outerHigh += boxSize;
    }
    for (int i=0; i<workingData.size(); i++) {
      for (int j=0; j<workingData.get(i).size(); j++) {
        System.out.print(workingData.get(i).get(j).getValueInt());
      }
      System.out.println();
    }
  }

  public static void printGrid(ArrayList<ArrayList<Sudoku>> data) {
    // PRINTING _>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    int boxSize = MyFrame.boxSize;
    int rowSize = MyFrame.noOfColumns;
    int outerLow = 0;
    int outerHigh = boxSize;
    System.out.println("======NEW DATA================");
    for (int i = 0; i < boxSize; i++) {
      ArrayList<Sudoku> row;
      int innerLow = 0;
      int innerHigh = boxSize;
      for (int j = 0; j < boxSize; j++) {
        row = Sudoku.getHorizontalRow(outerLow, outerHigh, innerLow, innerHigh, data);
        for (int a = 0; a < rowSize; a++) {
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

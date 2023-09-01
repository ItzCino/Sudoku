import java.util.ArrayList;

// Solver Class
public class Solver {
  private static final int BOX_SIZE = 3;
  private static final int GRID_SIZE = 9;
  static ArrayList<ArrayList<Sudoku>> workingData = new ArrayList<ArrayList<Sudoku>>();
  static int[][] solvedArray = new int[GRID_SIZE][GRID_SIZE];
  // solver should copy over the entire arraylist and check if it is valid 'i.e no red fields'
  // solver should then brute force to solution provided the problem is valid
  // solver should then be keeping the "problem" fields the same. 
  // and should not share the same pointer to the original so a copy is made

  // Checks if the puzzle is solveable. If so, it gets solved and result is returned.
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
  }


  // Checks if there are duplicates in each horizontal row
  private static boolean isNumberInRow(int[][] board, int number, int row) {
    for (int i = 0; i < GRID_SIZE; i++) {
      if (board[row][i] == number) {
        return true;
      }
    }
    return false;
  }

  // Checks if there are duplicates in each vertical column
  private static boolean isNumberInColumn(int[][] board, int number, int column) {
    for (int i = 0; i < GRID_SIZE; i++) {
      if (board[i][column] == number) {
        return true;
      }
    }
    return false;
  }

  // Checks there are duplicates in each box
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

  // Checks if the placement of the number is valid
  private static boolean isValidPlacement(int[][] board, int number, int row, int column) {
    return !isNumberInRow(board, number, row) && !isNumberInColumn(board, number, column)
    && !isNumberInBox(board, number, row, column);
  }

  // Recursive method to solve the board
  private static boolean solveBoard(int[][] board) {
	for (int row = 0; row < GRID_SIZE; row++) {
	  for (int column = 0; column < GRID_SIZE; column++) {
	    if (board[row][column] == 0) {
	      for (int numberToTry = 1; numberToTry <= GRID_SIZE; numberToTry++) {
	        if (isValidPlacement(board, numberToTry, row, column)) {
	          board[row][column] = numberToTry;
            //   printCurrentSolution(board);
	          if (solveBoard(board)) {
                return true;
              } else {
                board[row][column] = 0;
              }
	        }
	      }
        return false;
	    } 
	  }
	}
    printCurrentSolution(board);
	return true;
	}

  // Converts the 2D array list of strings to a 2D array of integers for comparing and solving.
  public static void toSolvingArray(ArrayList<ArrayList<Sudoku>> data) {
    int boxSize = MyFrame.boxSize;
    int rowSize = MyFrame.noOfColumns;
    int outerLow = 0;
    int outerHigh = boxSize;
    int rowCounter = 0;
    System.out.println("====== SOLVING ARRAY DATA================");
    // For loops through each outer box and copies all elments in each row into a 2D array of integers.
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
      // increments the row counter to move to the next row in the 2D array
      innerLow += boxSize;
      innerHigh += boxSize;
      rowCounter++;
      }
      outerLow += boxSize;
      outerHigh += boxSize;
    }
  }

  // Converts the 2D array list of integers to a 2D array of strings to be displayed onto the screen.
  public static void toSolvedSudokuArray() {
    int boxSize = MyFrame.boxSize;
    int rowSize = MyFrame.noOfColumns;
    int outerLow = 0;
    int outerHigh = boxSize;
    int currentRow = 0;
    System.out.println("====== SOLVED ARRAY DATA================");
    // For loops through each outer box and copies all elments in each row into a 2D array of strings.
    for (int i = 0; i < boxSize; i++) {
      ArrayList<Sudoku> row;
      int innerLow = 0;
      int innerHigh = boxSize;
      for (int j = 0; j < boxSize; j++) {
        row = Sudoku.getHorizontalRow(outerLow, outerHigh, innerLow, innerHigh, workingData);
        for (int a = 0; a < rowSize; a++) {
          row.get(a).setValue(solvedArray[currentRow][a]);
        }
      // increments the row counter to move to the next row in the 2D array
      innerLow += boxSize;
      innerHigh += boxSize;
      currentRow++;
      }
      outerLow += boxSize;
      outerHigh += boxSize;
    }

    // displays the solved puzzle in the console for debugging purposes
    for (int i=0; i<workingData.size(); i++) {
      for (int j=0; j<workingData.get(i).size(); j++) {
        System.out.print(workingData.get(i).get(j).getValueInt());
      }
      System.out.println();
    }
  }

  // displays the current puzzle in the console for debugging purposes
  public static void printCurrentSolution(int[][] Current) {
    System.out.println("======CURRENT SOLUTION================");
    for (int i = 0; i < GRID_SIZE; i++) {
      for (int j = 0; j < GRID_SIZE; j++) {
        System.out.print(Current[i][j]);
      }
      System.out.println();
    }
  }

  // displays the current puzzle in grid format in the console for debugging purposes
  public static void printGrid(ArrayList<ArrayList<Sudoku>> data) {
    // PRINTING _>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    int boxSize = MyFrame.boxSize;
    int rowSize = MyFrame.noOfColumns;
    int outerLow = 0;
    int outerHigh = boxSize;
    System.out.println("======NEW DATA================");
    // For loops through each outer box and copies all elments in each row into a 2D array of strings.
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
      // increments the row counter to move to the next row in the 2D array
      innerLow += boxSize;
      innerHigh += boxSize;
      }
      outerLow += boxSize;
      outerHigh += boxSize;
    }
      // PRINTING _>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  }

  // Converts the 2D array list of strings to a 2D array of integers for comparing and solving.
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

  // Converts the 2D array list of integers to a 2D array of strings for comparing and solving.
  public static void toSudokuArray(ArrayList<ArrayList<Integer>> integerData, ArrayList<ArrayList<Sudoku>> workingData) {
    for (int i=0; i < MyFrame.noOfRows; i++) {
      for (int j = 0; j < MyFrame.noOfRows; j++) {
        workingData.get(i).get(j).setValue(integerData.get(i).get(j));
      }
    }
  }

  // Copies the data from the original array list to a new array list
  public static void copyData(ArrayList<ArrayList<Sudoku>> data, ArrayList<ArrayList<Sudoku>> workingData) {
    for (int i=0; i < MyFrame.noOfRows; i++) {
      workingData.add(new ArrayList<Sudoku>());
      for (int j = 0; j < MyFrame.noOfRows; j++) {
        workingData.get(i).add(data.get(i).get(j));
      }
    }
  }

  // Checks if all the fields are full
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

  // Checks if puzzle is solved yb performing all the checks
  public static Boolean isPuzzleSolved(ArrayList<ArrayList<Sudoku>> data) {
    ArrayList<ArrayList<Integer>> duplicateValues;
    duplicateValues = MyFrame.createDuplicateValuesArray();
    GridCheck.checkAllOuterBoxes(duplicateValues, data);
    GridCheck.checkAllHorizontalRows(MyFrame.boxSize, duplicateValues, data);
    GridCheck.checkAllVerticalColumns(MyFrame.boxSize, duplicateValues, data);
    Boolean areThereDuplicateValues = Solver.areThereDuplicates(duplicateValues);
    Boolean areFieldsFull = Solver.areFieldsFull(data);
    Fields.updateFieldColour(duplicateValues, data);
    if ((areFieldsFull == true) && (areThereDuplicateValues == false)) {
      System.out.println("SOLVED");
      return true;
    } else {
      System.out.println("NOT SOLVED");
      return false;
    }
  }

  // Retrieves all the duplicates based on Sudoku game rules
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

  // Check if there are duplicates based on Sudoku game rules
  public static Boolean areThereDuplicatesStandalone(ArrayList<ArrayList<Sudoku>> data) {
    ArrayList<ArrayList<Integer>> duplicateValues;
    duplicateValues = MyFrame.createDuplicateValuesArray();
    GridCheck.checkAllOuterBoxes(duplicateValues, data);
    GridCheck.checkAllHorizontalRows(MyFrame.boxSize, duplicateValues, data);
    GridCheck.checkAllVerticalColumns(MyFrame.boxSize, duplicateValues, data);
    Boolean areThereDuplicateValues = Solver.areThereDuplicates(duplicateValues);
    return areThereDuplicateValues;  
  }

  // Check if there are duplicates based on Sudoku game rules
  public static Boolean areThereDuplicates(ArrayList<ArrayList<Integer>> duplicateValues) {
    for (int i = 0; i < duplicateValues.size(); i++) {
      if (duplicateValues.get(i).size() > 0) {
        return true;
      }
    }
    return false;    
  }

  // Adds duplicates to an arraylist of integers from an arraylist of type Sudoku 
  public static void addDuplicates(ArrayList<Sudoku> duplicates, ArrayList<ArrayList<Integer>> duplicateValues) {
    for (int i = 0; i < duplicates.size(); i++) {
      addDuplicateFromSudokuType(duplicates.get(i), duplicateValues);
    }
  }
  
  // Adds duplicates to an arraylist of type Sudoku from an arraylist of integers  
  public static void addDuplicateFromSudokuType(Sudoku field, ArrayList<ArrayList<Integer>> duplicateValues) {
    int outerBox = field.getOuterBoxIDInt();
    int innerBox = field.getInnerBoxIDInt();
    duplicateValues.get(outerBox).add(innerBox);
    duplicateValues.get(outerBox).add(innerBox);
    duplicateValues.get(outerBox).add(innerBox);
  }
}

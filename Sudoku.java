import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JTextField;

// Sudoku class
public class Sudoku {

  // instance variables
  JTextField textField;
  String value = "";

  int row;
  int column;
  int innerRow;
  int innerColumn;
  int outerBoxID;
  int innerBoxID;

  // constructor
  public Sudoku(int row, 
                int column, 
                int innerRow, 
                int innerColumn, 
                int outerBoxNumber, 
                int innerBoxNumber,
                JTextField textField) {
    this.row = row;
    this.column = column;
    this.innerRow = innerRow;
    this.innerColumn = innerColumn;
    this.outerBoxID = outerBoxNumber;
    this.innerBoxID = innerBoxNumber;
    this.textField = textField;
  }

  // Sets the value of the Sudoku object with integer values
  public void setValue(int text) {
    if (text == 0) {
      this.textField.setText("");
      this.value = "";
      return;
    }
    this.textField.setText(Integer.toString(text));
    this.value = Integer.toString(text);
  }

  // Updates the current value of the Sudoku object with what is in the text field
  public void updateValue() {
    this.value = this.textField.getText();
  }

  // Gets the value of the Sudoku object as a String
  public String getValue() {
    updateValue();
    return this.value;
  }

  // Gets the value of the Sudoku object as an Integer
  public int getValueInt() {
    updateValue();
    if (this.value.equals("")) {
      return 0;
    }
    return Integer.parseInt(this.value);
  }

  // Gets the ID of the inner box as an Integer
  public int getInnerBoxIDInt() {
      return this.innerBoxID;
  }

  // Gets the ID of the outer box as a Integer
  public int getOuterBoxIDInt() {
      return this.outerBoxID;
  }

  // Gets the colour of the current text field
  public Color getFieldColour() {
    return this.textField.getBackground();
  }

  // Sets the current text field color to red invicating a duplicate value
  public void setRedField() {
    this.textField.setBackground(Color.RED);
  }

  // Sets the current text field color to white invicating a non-duplicate value
  public void setWhiteField() {
    this.textField.setBackground(Color.WHITE);
  }

  // Returns an array of type Sudoku which contains all the duplicates.
  public static ArrayList<Sudoku> getBoxDuplicates(ArrayList<Sudoku> outerBox, int outerBoxNumber) {
    // creates a 2D array list to store duplicates
    ArrayList<Sudoku> duplicateArray = new ArrayList<Sudoku>();
    // * NOTE try to convert from string to int *
    for (int innerBox=0; innerBox<outerBox.size(); innerBox++) {
      // get the value of the current box
      String currentInnerBox = outerBox.get(innerBox).getValue();
      // check if the value is a duplicate
      for (int innerBoxBox = 0; innerBoxBox < outerBox.size(); innerBoxBox++) {
        int innerID = outerBox.get(innerBox).getInnerBoxIDInt();
        int outerID = outerBox.get(innerBox).getOuterBoxIDInt();
        if ((outerBoxNumber == (outerID)) && (innerBoxBox == (innerID))) {
          continue;
        }
        String currentValue = outerBox.get(innerBoxBox).getValue();
        if ((currentInnerBox.equals(currentValue)) && (currentValue.equals("") == false)) {
          // this condition prevents duplicate "red fields" from being added to the array 
          if (duplicateArray.contains(innerBoxBox) == false) {
            duplicateArray.add(outerBox.get(innerBox));
          }
        } 
      }
    }
    return duplicateArray;
  }
  
  // Paritions Sudoku array the data into Horizontal Rows
  // chosen route -> 0 to 3, 3 to 6, 6 to 9.
  // *BOUNDS*: (OUTER-LOW, OUTER-HIGH, INNER-LOW, INNER-HIGH)
  public static ArrayList<Sudoku> getHorizontalRow(int outerLow, int outerHigh, int innerLow, int innerHigh, ArrayList<ArrayList<Sudoku>> data) {
      ArrayList<Sudoku> tempHorizontalRows = new ArrayList<Sudoku>();
      for (int i = outerLow; i < outerHigh; i++) {
          for (int j = innerLow; j < innerHigh; j++) {
            tempHorizontalRows.add(data.get(i).get(j));
          }
      }
      return tempHorizontalRows;
  }

  // Paritions Sudoku array the data into Vertical Columns
  public static ArrayList<Sudoku> getVerticalColumn(int outerLow, int outerHigh, int innerLow, int innerHigh, ArrayList<ArrayList<Sudoku>> data) {
    ArrayList<Sudoku> tempVerticalColumns = new ArrayList<Sudoku>();  
    for (int i = outerLow; i <= outerHigh; i+=3) {
      for (int j = innerLow; j <= innerHigh; j+=3) {
        tempVerticalColumns.add(data.get(i).get(j));
      }
    }
    return tempVerticalColumns;
  }
}
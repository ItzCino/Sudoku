import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JTextField;

public class Sudoku {

  JTextField textField;
    
  String value = "";

  Boolean editable = true;

  int row;
  int column;
  int innerRow;
  int innerColumn;
  int outerBoxID;
  int innerBoxID;

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

  public void setEditable(Boolean immutability) {
    this.editable = immutability;
  }

  public Boolean getImmutability() {
    return this.editable;
  }

  public void setValue(int text) {
    if (text == 0) {
      this.textField.setText("");
      this.value = "";
      return;
    }
    this.textField.setText(Integer.toString(text));
    this.value = Integer.toString(text);
  }

  public void updateValue() {
    this.value = this.textField.getText();
  }

  public String getValue() {
    updateValue();
    return this.value;
  }

  public int getValueInt() {
    updateValue();
    if (this.value.equals("")) {
      return 0;
    }
    return Integer.parseInt(this.value);
  }

  public String getInnerBoxIDStr() {
    return Integer.toString(this.innerBoxID);
  }

  public String getOuterBoxIDStr() {
    return Integer.toString(this.outerBoxID);
  }

  public int getInnerBoxIDInt() {
      return this.innerBoxID;
  }

  public int getOuterBoxIDInt() {
      return this.outerBoxID;
  }

  public void setRedField() {
    this.textField.setBackground(Color.RED);
  }

  public void setWhiteField() {
      this.textField.setBackground(Color.WHITE);
  }

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
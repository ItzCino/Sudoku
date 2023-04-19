import javax.swing.JTextField;

public class Sudoku {
  JTextField textField;
    
  String value;
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

  public void setValue() {
    
    this.value = this.textField.getText();
    // System.out.println("::" + this.value);
  }

  public String getValue() {
    setValue();
    return this.value;
  }
}
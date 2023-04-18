public class Sudoku {
  int row;
  int column;
  int innerRow;
  int innerColumn;
  int innerBoxNumber;

  public Sudoku(int row, int column, int innerRow, int innerColumn, int innerBoxNumber) {
    this.row = row;
    this.column = column;
    this.innerRow = innerRow;
    this.innerColumn = innerColumn;
    this.innerBoxNumber = innerBoxNumber;
  }
}
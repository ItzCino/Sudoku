import java.util.ArrayList;

public class Fields {
  // **ONLY** updates each OUTER BOX, ONE at a time.
  public static void setTextFieldColor(
    ArrayList<Integer> redBoxes, 
    int outerBox, 
    ArrayList<ArrayList<Sudoku>> data) {

    // resets the color of the text field to white, even if there are no duplicates.
    if (redBoxes.size() == 0) {
      resetBoxFields(outerBox, data);
      return;
    }
    // sets all duplicate fields to red
    resetBoxFields(outerBox, data);
    for (int innerRedBox = 0; innerRedBox < redBoxes.size(); innerRedBox++) {
      for (int innerBox = 0; innerBox < data.size(); innerBox++) {
        if (redBoxes.get(innerRedBox) == innerBox) {
          data.get(outerBox).get(innerBox).setRedField();
          break;
        } 
      }
    }
  }

  public static void updateFieldColour(ArrayList<ArrayList<Integer>> duplicateValues, ArrayList<ArrayList<Sudoku>> data) {
    for (int i=0; i<duplicateValues.size(); i++) {
      ArrayList<Integer> currentOuterBox;
      currentOuterBox = duplicateValues.get(i);
      System.out.println(currentOuterBox);
      Fields.setTextFieldColor(currentOuterBox, i, data);
    }
  }

  public static void resetBoxFields(int outerBox, ArrayList<ArrayList<Sudoku>> data) {
    for (int innerBox = 0; innerBox < data.size(); innerBox++) {
      data.get(outerBox).get(innerBox).setWhiteField();
    }
  }

  public static void resetAllTextFields(ArrayList<ArrayList<Sudoku>> data) {
    for (int outerBox = 0; outerBox < data.size(); outerBox++) {
      for (int innerBox = 0; innerBox < data.size(); innerBox++) {
        data.get(outerBox).get(innerBox).setWhiteField();
      }
    }
  }
}

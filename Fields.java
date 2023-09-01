import java.util.ArrayList;

// This class is used to update the colour of the text fields in the GUI.
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

  // updates the colour of the text fields in the GUI.
  public static void updateFieldColour(ArrayList<ArrayList<Integer>> duplicateValues, ArrayList<ArrayList<Sudoku>> data) {
    for (int i=0; i<duplicateValues.size(); i++) {
      ArrayList<Integer> currentOuterBox;
      currentOuterBox = duplicateValues.get(i);
      Fields.setTextFieldColor(currentOuterBox, i, data);
    }
  }

  // resets the colour of the text fields in the GUI.
  public static void resetBoxFields(int outerBox, ArrayList<ArrayList<Sudoku>> data) {
    for (int innerBox = 0; innerBox < data.size(); innerBox++) {
      data.get(outerBox).get(innerBox).setWhiteField();
    }
  }

  // resets the colour of ALL the text fields in the GUI.
  public static void resetAllTextFields(ArrayList<ArrayList<Sudoku>> data) {
    for (int outerBox = 0; outerBox < data.size(); outerBox++) {
      for (int innerBox = 0; innerBox < data.size(); innerBox++) {
        data.get(outerBox).get(innerBox).setWhiteField();
      }
    }
  }
}

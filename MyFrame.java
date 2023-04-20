import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MyFrame extends JFrame implements ActionListener, DocumentListener{
  // new GUI window to add components to

  JButton button;
  ArrayList<JTextField> textFields = new ArrayList<JTextField>();
  int boxSize = 3; // 3x3 grid
  int noOfTextFields = boxSize* boxSize*boxSize;
  int noOfRows = boxSize*boxSize;
  int noOfColumns = boxSize* boxSize;
  // creates master panel
  JPanel mainPanel = new JPanel(new GridLayout(boxSize, boxSize, 1, 1));
  // creates a 3x3 cluster of pannels
  JPanel[][] panels = new JPanel[boxSize][boxSize];
  int[][] sudokuData = new int[noOfRows][noOfColumns];
  ArrayList<ArrayList<Sudoku>> data = new ArrayList<ArrayList<Sudoku>>();
  // EACH ROW REPRESENTS EACH OUTER BOX
  ArrayList<ArrayList<Integer>> duplicateValues = createDuplicateValuesArray();

  public MyFrame() {
    this.setTitle("Susdoku");  // sets title of window
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // exits program when window is closed
    this.setResizable(false);  // prevents window from being resized
    this.setSize(750, 750);
    this.setLocation(800, 200);

    // mainPanel.setLocation(100, 250);
    
    int boxCounter = 0;
    for (int row = 0; row < boxSize; row++) {

      for (int col = 0; col < boxSize; col++) {
        // System.out.println(boxCounter);
        data.add(new ArrayList<Sudoku>());
        JPanel panel = new JPanel(new GridLayout(boxSize, boxSize, 1, 1));
        // JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        int innerCounter = 0;
        for (int i = 0; i < boxSize; i++) {
          for (int j = 0; j < boxSize; j++) {
            JPanel panelTemplate = new JPanel();
            panelTemplate.setBackground(Color.BLACK);
            JTextField text = createTextField();
            text.getDocument().addDocumentListener(this);
            panelTemplate.add(text);
            panel.add(panelTemplate);
            data.get(boxCounter).add(innerCounter, new Sudoku(row, col, i, j, boxCounter, innerCounter, text)); 
            // System.out.println(innerCounter);
            innerCounter++;
          }
        }
        mainPanel.add(panel);
        boxCounter++;
      }
    }

    // this.setLayout(new FlowLayout());  // sets layout manager

    // sets background color of window & sets an icon image
    this.getContentPane().setBackground(new Color(255, 255, 255));
    ImageIcon windowIcon = new ImageIcon("sus.png");
    this.setIconImage(windowIcon.getImage());

    // button = new JButton("press_me");
    // button.addActionListener(this);

    // createGrid(noOfRows, noOfColumns);
    this.add(mainPanel);
    
    // this.add(button);
    this.setVisible(true); // makes window visible
  }

  public JTextField createTextField() {
    JTextField text = new JTextField("");
    text.setPreferredSize(new Dimension(80, 80));
    text.setFont(new Font("Arial", Font.PLAIN, 30));
    text.setHorizontalAlignment(JTextField.CENTER);
    text.setAlignmentY(JTextField.CENTER);
    text.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    return text;
  }
  
  // displays stored text
  public void printInput() {
    System.out.println("\nEACH ROW REPRESENTS EACH BOX");
    // System.out.println(data.size());
    for (int i=0; i<data.size(); i++) {
      System.out.println();
      System.out.print(i+":");
      for (int j = 0; j < data.size(); j++) {
        System.out.print(data.get(i).get(j).getValue());
      }
    }

    // System.out.println("============");
    // for (int i=0;i<noOfRows;i++) {
    //   System.out.println(i + " : " + textFields.get(i).getLocation(getLocation()) + " : " +textFields.get(i).getText());
    // }
  }

  public void updateData() {
    duplicateValues = createDuplicateValuesArray();
    for (int i=0; i<data.size(); i++) {
      for (int j = 0; j < data.size(); j++) {
        data.get(i).get(j).getValue();
      }
    }
    checkAllOuterBoxes();
    System.out.println("DUPSSS: \n"+duplicateValues+"\n");
    // updateFieldColour();

    printInput();
    // printInput();
  }

public void checkAllOuterBoxes() {
    System.out.println();
    for (int i=0; i<data.size(); i++) {
    //   System.out.println("OUTER BOX: " + i);
      ArrayList<ArrayList<Integer>> duplicates;
      duplicates =  Sudoku.checkOuterBox(data.get(i), i);
      System.out.println("BOX "+i+":\n" + duplicates + "\n");
      addDuplicates(duplicates, i);
      // duplicates.get(0).add(0,i);
    //   System.out.println(duplicates);
    //   setTextFieldColor(duplicates, i);
    }
  }

  public void checkAllHorizontalRows() {
    for (int i=0; i<data.size(); i++) {
      
    }
  }

  public void addDuplicates(ArrayList<ArrayList<Integer>> duplicates, int outerBox) {
    for (int i=0; i<duplicates.size(); i++) {
      duplicateValues.add(outerBox, duplicates.get(i));
    }
  }
  
  // **ONLY** updates each OUTER BOX, ONE at a time.
  public void setTextFieldColor(ArrayList<Integer> redBoxes, int outerBox) {
    // resets the color of the text field to white, even if there are no duplicates.
    if (redBoxes.size() == 0) {
      resetBoxFields(outerBox);
      return;
    }
    // sets all duplicate fields to red
    resetBoxFields(outerBox);
    for (int innerRedBox = 0; innerRedBox < redBoxes.size(); innerRedBox++) {
      for (int innerBox = 0; innerBox < data.size(); innerBox++) {
        if (redBoxes.get(innerRedBox) == innerBox) {
          data.get(outerBox).get(innerBox).setRedField();
          break;
        } 
      }
    }
  }

  public void resetAllTextFields() {
    for (int outerBox = 0; outerBox < data.size(); outerBox++) {
      for (int innerBox = 0; innerBox < data.size(); innerBox++) {
        data.get(outerBox).get(innerBox).setWhiteField();
      }
    }
  }

  public void resetBoxFields(int outerBox) {
    for (int innerBox = 0; innerBox < data.size(); innerBox++) {
      data.get(outerBox).get(innerBox).setWhiteField();
    }
  }

  public ArrayList<ArrayList<Integer>> createDuplicateValuesArray() {
    ArrayList<ArrayList<Integer>> duplicateArray;
    duplicateArray = new ArrayList<ArrayList<Integer>>();
    for (int i=0; i<data.size(); i++) {
      duplicateArray.add(new ArrayList<Integer>());
    }
    return duplicateArray;
  }

  private void updateFieldColour() {
    for (int i=0; i<duplicateValues.size(); i++) {
      ArrayList<Integer> currentOuterBox;
      currentOuterBox = duplicateValues.get(i);
      System.out.println(currentOuterBox);
      setTextFieldColor(currentOuterBox, i);
    }
  }
  
  

  @Override
  public void actionPerformed(ActionEvent e) {
  if (e.getSource() == button) {
    // System.out.println("hello! "+ text.getText());
    }
  }

  @Override
  public void insertUpdate(DocumentEvent e) {   
    updateData();
  }

  @Override
    public void removeUpdate(DocumentEvent e) {
    updateData();
  }

  @Override
  public void changedUpdate(DocumentEvent e) {
    // updateData();
  }
}
 
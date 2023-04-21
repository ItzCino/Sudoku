import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MyFrame extends JFrame implements ActionListener, DocumentListener{
  // new GUI window to add components to
  JButton solveButton;
  JCheckBox makePuzzle;
  
  Editing_Mode editingMode = Editing_Mode.NON_EDITABLE;
  Boolean editable = true;
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
  ArrayList<ArrayList<String>> previousData = new ArrayList<ArrayList<String>>();
  // EACH ROW REPRESENTS EACH OUTER BOX
  ArrayList<ArrayList<Integer>> duplicateValues;

  public MyFrame() {
    this.setTitle("Susdoku");  // sets title of window
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // exits program when window is closed
    this.setResizable(false);  // prevents window from being resized
    this.setSize(900, 800);
    this.setLocation(750, 175);

    int boxCounter = 0;
    for (int row = 0; row < boxSize; row++) {
      for (int col = 0; col < boxSize; col++) {
        data.add(new ArrayList<Sudoku>());
        JPanel panel = new JPanel(new GridLayout(boxSize, boxSize, 1, 1));
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
            innerCounter++;
          }
        }
        mainPanel.add(panel);
        boxCounter++;
      }
    }

    // sets background color of window & sets an icon image
    this.getContentPane().setBackground(new Color(255, 255, 255));
    ImageIcon windowIcon = new ImageIcon("sus.png");
    this.setIconImage(windowIcon.getImage());

    // button = new JButton("press_me");
    // button.addActionListener(this);    
    // this.add(button);

    this.solveButton = new JButton("Solve");
    this.solveButton.addActionListener(this);

    this.makePuzzle = new JCheckBox("Make Puzzle", false);
    this.makePuzzle.addActionListener(this);

    JPanel solvePanel = new JPanel();
    solvePanel.add(this.solveButton);
    solvePanel.add(this.makePuzzle);

    JSplitPane masterPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mainPanel, solvePanel);
    masterPanel.setDividerLocation(750);
    mainPanel.setMinimumSize(new Dimension(750, 750));
    this.add(masterPanel);
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
    for (int i=0; i<data.size(); i++) {
      System.out.println();
      System.out.print(i+":");
      for (int j = 0; j < data.size(); j++) {
        System.out.print(data.get(i).get(j).getNewValue());
      }
    }
  }

  public void updateData() {
    Boolean areThereDuplicateValues;
    duplicateValues = createDuplicateValuesArray();
    System.out.println("======NEW DATA================");
    
    updateGrid();
    GridCheck.checkAllOuterBoxes(duplicateValues, data);
    GridCheck.checkAllHorizontalRows(boxSize, duplicateValues, data);
    GridCheck.checkAllVerticalColumns(boxSize, duplicateValues, data);
    areThereDuplicateValues = Solver.areThereDuplicates(duplicateValues);
    System.out.println("Duplicates: " + areThereDuplicateValues);
    Fields.updateFieldColour(duplicateValues, data);
    printInput();
    copyData();
  }

  public void updateGrid() {
    Sudoku currentField;
    String currentCopy;
    if (this.editingMode == Editing_Mode.EDITABLE) {
      for (int i=0; i<data.size(); i++) {
        for (int j = 0; j < data.size(); j++) {
          currentField = data.get(i).get(j);
          currentCopy = previousData.get(i).get(j);
          if (currentField.getNewValue().equals(currentCopy) == false) {
            currentField.setEditable(editable);
          }
        //   data.get(i).get(j).setEditable(false);
        /*  
         * 
         * ENDED OFF HERE NEED TO FIGURE OUT HOW TO know if the value has changed
         * AND FROM KNOWING THIS, WE CAN UPDATE THE PROPERTIES OF THE FIELD, SO THAT
         * THE VALUE CANNOT BE CHANGED WHEN 'MAKE PUZZLE' MODE IS EXITED.
         */
        }
      }
      System.out.println("EDIT");
    } 
    if (this.editingMode == Editing_Mode.NON_EDITABLE) {
      for (int i=0; i<data.size(); i++) {
        for (int j = 0; j < data.size(); j++) {
          currentField = data.get(i).get(j);
          if (currentField.getImmutability() == true) {
            System.out.println("TRUE");
            currentField.getNewValue();
          }
        }
      }
      System.out.println("NO_EDIT");

    }
  }

  public void copyData() {
    previousData = new ArrayList<ArrayList<String>>();
    for (int i=0; i<data.size(); i++) {
      previousData.add(new ArrayList<String>());
      for (int j = 0; j < data.size(); j++) {
        previousData.get(i).add(data.get(i).get(j).getNewValue());
      }
    }
    System.out.println(previousData);
  }

  public ArrayList<ArrayList<Integer>> createDuplicateValuesArray() {
    ArrayList<ArrayList<Integer>> duplicateArray;
    duplicateArray = new ArrayList<ArrayList<Integer>>();
    for (int i=0; i<noOfRows; i++) {
      duplicateArray.add(new ArrayList<Integer>());
    }
    return duplicateArray;
  }
  
  @Override
  public void actionPerformed(ActionEvent e) {
    // System.out.println(this.makePuzzle);
    if (e.getSource() == this.makePuzzle) {
      if (this.makePuzzle.isSelected()) {
        this.editable = false;
        this.editingMode = Editing_Mode.EDITABLE;
      } else {
        this.editable = true;
        this.editingMode = Editing_Mode.NON_EDITABLE;
      }
      System.out.println(this.editable);
    }
    if (e.getSource() == this.solveButton) {
      Solver.SolveSudoku();
      System.out.println("Solve");
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
    return;
  }
}
 
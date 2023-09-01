import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
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
import java.util.Arrays;

public class MyFrame extends JFrame implements ActionListener, DocumentListener{
  // new GUI window to add components to
  JButton solveButton;
  JButton addPuzzle;
  JButton resetPuzzle;
  ArrayList<JTextField> textFields = new ArrayList<JTextField>();

  Boolean areThereDuplicateValues;

  // initialises variables
  static int boxSize = 3; // 3x3 grid
  static int noOfRows = boxSize*boxSize;
  static int noOfColumns = boxSize* boxSize;
  int noOfTextFields = boxSize* boxSize*boxSize;

  // creates master panel
  JPanel mainPanel = new JPanel(new GridLayout(boxSize, boxSize, 1, 1));
  // creates a 3x3 cluster of pannels
  JPanel[][] panels = new JPanel[boxSize][boxSize];
  int[][] sudokuData = new int[noOfRows][noOfColumns];

  // creates a 2D, holding the current and previous data.
  ArrayList<ArrayList<Sudoku>> data = new ArrayList<ArrayList<Sudoku>>();
  ArrayList<ArrayList<String>> previousData = new ArrayList<ArrayList<String>>();
  // EACH ROW REPRESENTS EACH OUTER BOX
  ArrayList<ArrayList<Integer>> duplicateValues;

  // Main window / frame
  public MyFrame() {
    // sets title of window, size, location, name and functions for the window.
    this.setTitle("Sudoku");  // sets title of window
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // exits program when window is closed
    this.setResizable(false);  // prevents window from being resized
    this.setSize(900, 800);
    this.setLocation(750, 175);

    // Sets up all the text fields, boxes, panels and listeners to the game "panel"
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

    // Adds "Solve" button to window
    this.solveButton = new JButton("Solve");
    this.solveButton.addActionListener(this);

    // Adds "Add Puzzle" button to window
    this.addPuzzle = new JButton("Add Puzzle");
    this.addPuzzle.addActionListener(this);

    // Adds "Clear Puzzle" button to window
    this.resetPuzzle = new JButton("Clear Puzzle");
    this.resetPuzzle.addActionListener(this);

    // Adds buttons from above to a "panel"
    JPanel solvePanel = new JPanel();
    solvePanel.add(this.solveButton);
    solvePanel.add(this.addPuzzle);
    solvePanel.add(this.resetPuzzle);

    // Combines the "game" and "buttons" pannels into the "master" panel
    JSplitPane masterPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mainPanel, solvePanel);
    masterPanel.setDividerLocation(750);
    mainPanel.setMinimumSize(new Dimension(750, 750));
    this.add(masterPanel);
    this.setVisible(true); // makes window visible
  }

  // creates a text field which is used to store the user's input.
  // This is initialised to be an Empty String.
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
    for (int i=0; i<data.size(); i++) {
      System.out.println();
      System.out.print(i+":");
      for (int j = 0; j < data.size(); j++) {
        System.out.print(data.get(i).get(j).getValue());
      }
    }
  }

  // Updates the data in the 2D array list
  // Checks for duplicates values and updates the colour of the text fields
  public void updateData() {
    duplicateValues = createDuplicateValuesArray();
    GridCheck.checkAllOuterBoxes(duplicateValues, data);
    GridCheck.checkAllHorizontalRows(boxSize, duplicateValues, data);
    GridCheck.checkAllVerticalColumns(boxSize, duplicateValues, data);
    this.areThereDuplicateValues = Solver.areThereDuplicates(duplicateValues);
    Fields.updateFieldColour(duplicateValues, data);
  }

  // Creates a 2D array list of array lists of integers so they can be used to store duplicate values
  // This is so that the user input is convert to a integer so each element in each box can be compared.
  public static ArrayList<ArrayList<Integer>> createDuplicateValuesArray() {
    ArrayList<ArrayList<Integer>> duplicateArray;
    duplicateArray = new ArrayList<ArrayList<Integer>>();
    for (int i=0; i<noOfRows; i++) {
      duplicateArray.add(new ArrayList<Integer>());
    }
    return duplicateArray;
  }
  
  // Listens for when presses any one of the buttons in the panels.
  @Override
  public void actionPerformed(ActionEvent e) {
    // If the solve button is pressed the solved puzzle is displayed.
    if (e.getSource() == this.solveButton) {
      updateData();
      Solver.SolveSudoku(data, areThereDuplicateValues);
      updateData();
      System.out.println("Solve Button Pressed");
    }

    // If the reset button is pressed the puzzle is reset to its original state.
    if (e.getSource() == this.resetPuzzle) {
      for (int i=0; i<data.size(); i++) {
        for (int j = 0; j < data.size(); j++) {
          data.get(i).get(j).setValue(0);
        }
      }
      System.out.println("Reset Puzzle Pressed");
    }

    // If the add puzzle button is pressed the puzzle is loaded.
    // This is a hard coded puzzle from testing purposes
    // This will be replaced with a randomiser puzzle when I have time.
    if (e.getSource() == this.addPuzzle) {
      System.out.println("Loaded puzzle");
      ArrayList<ArrayList<Integer>> puzzle = new ArrayList<ArrayList<Integer>>();
  
      puzzle.add(new ArrayList<Integer>(Arrays.asList(7, 0, 2, 0, 0, 0, 1, 0, 0)));
      puzzle.add(new ArrayList<Integer>(Arrays.asList(0, 5, 0, 0, 0, 3, 0, 0, 9)));
      puzzle.add(new ArrayList<Integer>(Arrays.asList(6, 0, 0, 0, 0, 0, 5, 0, 0)));
  
      puzzle.add(new ArrayList<Integer>(Arrays.asList(8, 0, 0, 0, 4, 3, 0, 9, 0)));
      puzzle.add(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0)));
      puzzle.add(new ArrayList<Integer>(Arrays.asList(0, 9, 0, 7, 5, 0, 0, 0, 8)));
  
      puzzle.add(new ArrayList<Integer>(Arrays.asList(0, 0, 9, 0, 0, 0, 0, 0, 7)));
      puzzle.add(new ArrayList<Integer>(Arrays.asList(7, 0, 0, 2, 0, 0, 0, 4, 0)));
      puzzle.add(new ArrayList<Integer>(Arrays.asList(0, 0, 5, 0, 0, 0, 2, 0, 3)));
      Solver.toSudokuArray(puzzle, data);
      updateData();
    }
  }

  // Dummy functions for the document listener  
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
 
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

  static int boxSize = 3; // 3x3 grid
  int noOfTextFields = boxSize* boxSize*boxSize;
  static int noOfRows = boxSize*boxSize;
  static int noOfColumns = boxSize* boxSize;
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

    this.addPuzzle = new JButton("Add Puzzle");
    this.addPuzzle.addActionListener(this);

    this.resetPuzzle = new JButton("Clear Puzzle");
    this.resetPuzzle.addActionListener(this);

    JPanel solvePanel = new JPanel();
    solvePanel.add(this.solveButton);
    solvePanel.add(this.addPuzzle);
    solvePanel.add(this.resetPuzzle);

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
        System.out.print(data.get(i).get(j).getValue());
      }
    }
  }

  public void updateData() {
    duplicateValues = createDuplicateValuesArray();
    // System.out.println("======NEW DATA================");
    GridCheck.checkAllOuterBoxes(duplicateValues, data);
    GridCheck.checkAllHorizontalRows(boxSize, duplicateValues, data);
    GridCheck.checkAllVerticalColumns(boxSize, duplicateValues, data);
    this.areThereDuplicateValues = Solver.areThereDuplicates(duplicateValues);
    // System.out.println("Duplicates: " + areThereDuplicateValues);
    Fields.updateFieldColour(duplicateValues, data);
    // printInput();
  }

  public static ArrayList<ArrayList<Integer>> createDuplicateValuesArray() {
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
    if (e.getSource() == this.solveButton) {
      updateData();
      Solver.SolveSudoku(data, areThereDuplicateValues);
      updateData();
      System.out.println("Solve Button Pressed");
    }

    if (e.getSource() == this.resetPuzzle) {
      for (int i=0; i<data.size(); i++) {
        for (int j = 0; j < data.size(); j++) {
          data.get(i).get(j).setValue(0);
        }
      }
      System.out.println("Reset Puzzle Pressed");
    }

    if (e.getSource() == this.addPuzzle) {
      System.out.println("Loaded puzzle");
      ArrayList<ArrayList<Integer>> puzzle = new ArrayList<ArrayList<Integer>>();
    //   puzzle.add(new ArrayList<Integer>(Arrays.asList(9, 4, 2, 5, 3, 6, 8, 7, 1)));
    //   puzzle.add(new ArrayList<Integer>(Arrays.asList(1, 6, 3, 2, 8, 7, 9, 5, 4)));
    //   puzzle.add(new ArrayList<Integer>(Arrays.asList(8, 5, 7, 9, 4, 1, 2, 3, 6)));
  
    //   puzzle.add(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0)));
    //   puzzle.add(new ArrayList<Integer>(Arrays.asList(8, 1, 9, 3, 2, 6, 7, 4, 5)));
    //   puzzle.add(new ArrayList<Integer>(Arrays.asList(4, 6, 5, 7, 9, 8, 1, 2, 3)));
  
    //   puzzle.add(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0)));
    //   puzzle.add(new ArrayList<Integer>(Arrays.asList(4, 7, 1, 6, 3, 2, 5, 9, 8)));
    //   puzzle.add(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0)));

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
 
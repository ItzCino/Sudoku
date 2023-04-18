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

  public MyFrame() {
    this.setTitle("Susdoku");  // sets title of window
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // exits program when window is closed
    this.setResizable(false);  // prevents window from being resized
    this.setSize(400, 400);
    this.setLocation(300, 300);

    mainPanel.setLocation(100, 250);;
    

    for (int row = 0; row < boxSize; row++) {
      for (int col = 0; col < boxSize; col++) {
        JPanel panel = new JPanel(new GridLayout(boxSize, boxSize, 1, 1));
        panel.setBackground(Color.GRAY);
        panel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        for (int i = 0; i < boxSize; i++) {
          for (int j = 0; j < boxSize; j++) {
            JPanel panelTemplate = new JPanel(new GridLayout(boxSize, boxSize, 1, 1));
            panelTemplate.setBackground(Color.GRAY);
            panelTemplate.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
            JTextField text = newTextField();
            text.getDocument().addDocumentListener(this);
            panelTemplate.add(text);
            panel.add(panelTemplate);
            
          }
        }
        mainPanel.add(panel);
        // JTextField text = newTextField();
        // text.getDocument().addDocumentListener(this);
      }
    }

    // for (int row = 0; row < panels.length; row++) {
    //   for (int col = 0; col < panels[row].length; col++) {
    //     JPanel panel = new JPanel(new GridLayout(boxSize, boxSize, 1, 1));
    //     panel.setBackground(Color.GRAY);
    //     panel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
    //     panels[row][col] = panel;
    //     // JTextField text = newTextField();
    //     // text.getDocument().addDocumentListener(this);
    //     // panel.add(text);
    //     // mainPanel.add(panel);
    //   }
    // }
    // for (int i=0;i<panels.length;i++) {
    //   for (int j=0;j<panels[i].length;j++) {
    //     mainPanel.add(panels[i][j]);
    //   }
    // }

    // createClusters();

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

  public void createClusters() {
    for (int row = 0; row < noOfRows; row++) {
      for (int col = 0; col < boxSize; col++) {
        JPanel panel = new JPanel(new GridLayout(boxSize, boxSize, 1, 1));
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        mainPanel.add(panel);
      }
    }
  }

  public void createGrid(int rows, int columns) {
    int posX = 50;
    int posY = 100;
    
    for (int r=0; r<rows; r++) {
      JPanel panel = new JPanel();
      panel.setLocation(posX, posY);
      panel.setSize(450, 50);

      for (int i=0; i<columns; i++) {
        JTextField text = newTextField();
        text.getDocument().addDocumentListener(this);
        textFields.add(text);
        panel.add(text);
      }

      posY += 50;
      mainPanel.add(panel);
    }
  }
  public JTextField newTextField() {
    JTextField text;
    // initializes text field
    text = new JTextField();
    text.setPreferredSize(new Dimension(40, 120));
    text.setBorder(BorderFactory.createLineBorder(Color.black));
    text.setFont(new Font("Consolas", Font.PLAIN, 20));
    return text;
  }

  // displays stored text
  public void printInput() {
    // System.out.println("============");
    // for (int i=0;i<noOfRows;i++) {
    //   System.out.println(i + " : " + textFields.get(i).getLocation(getLocation()) + " : " +textFields.get(i).getText());
    // }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
  if (e.getSource() == button) {
    // System.out.println("hello! "+ text.getText());
    }
  }

  @Override
  public void insertUpdate(DocumentEvent e) {
    printInput();
  }

  @Override
    public void removeUpdate(DocumentEvent e) {
    printInput();
  }

  @Override
  public void changedUpdate(DocumentEvent e) {
    // printInput();
    return;
  }
}
 
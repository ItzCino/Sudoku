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
    this.setSize(750, 750);
    this.setLocation(300, 300);

    // mainPanel.setLocation(100, 250);
    

    for (int row = 0; row < boxSize; row++) {
      for (int col = 0; col < boxSize; col++) {
        JPanel panel = new JPanel(new GridLayout(boxSize, boxSize, 1, 1));
        // JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        for (int i = 0; i < boxSize; i++) {
          for (int j = 0; j < boxSize; j++) {
            JPanel panelTemplate = new JPanel();
            panelTemplate.setBackground(Color.BLACK);
            JTextField text = createTextField();
            text.getDocument().addDocumentListener(this);
            panelTemplate.add(text);
            panel.add(panelTemplate);
          }
        }
        mainPanel.add(panel);
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
    JTextField text = new JTextField();
    text.setPreferredSize(new Dimension(80, 80));
    text.setFont(new Font("Arial", Font.PLAIN, 30));
    text.setHorizontalAlignment(JTextField.CENTER);
    text.setAlignmentY(JTextField.CENTER);
    text.setBorder(BorderFactory.createLineBorder(Color.BLACK));
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
 
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Position;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
  JPanel totalGUI = new JPanel();

  MyFrame() {
    this.setTitle("Susdoku");  // sets title of window
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // exits program when window is closed
    this.setResizable(false);  // prevents window from being resized
    this.setSize(750, 650);

    // this.setLayout(new FlowLayout());  // sets layout manager

    // sets background color of window & sets an icon image
    this.getContentPane().setBackground(new Color(1, 140, 140));
    ImageIcon windowIcon = new ImageIcon("sus.png");
    this.setIconImage(windowIcon.getImage());

    // button = new JButton("press_me");
    // button.addActionListener(this);

    createFields(noOfRows, noOfColumns);
    
    // this.add(button);
    this.setVisible(true); // makes window visible
  }

  public void createFields(int rows, int columns) {
    int posX = 50;
    int posY = 100;

    for (int r=0; r<rows; r++) {
      JPanel panel = new JPanel();
      panel.setLocation(posX, posY);
      panel.setSize(450, 50);
      for (int i=0; i<columns; i++) {
        JTextField text = newTextField(i);
        text.getDocument().addDocumentListener(this);
        textFields.add(text);
        panel.add(text);
      //   System.out.println(i);
      }
      totalGUI.add(panel);
      posY += 50;
    }
    this.add(totalGUI);
    // System.out.println(textFields.size());
    System.out.println("+");
    for (int i=0;i<textFields.size();i++) {
      System.out.println(i);
    }
    System.out.println("+");

  }
  public JTextField newTextField(int i) {
    JTextField text;
  // initializes text field
    text = new JTextField();
    text.setPreferredSize(new Dimension(40, 40));
    text.setBorder(BorderFactory.createLineBorder(Color.black));
    text.setFont(new Font("Consolas", Font.PLAIN, 40));
    return text;
  }

  // displays stored text
  public void printInput() {
    System.out.println("============");
    for (int i=0;i<noOfRows;i++) {
      System.out.println(i + " : " + textFields.get(i).getLocation(getLocation()) + " : " +textFields.get(i).getText());
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
 
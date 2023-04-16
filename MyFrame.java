import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame implements ActionListener{
  // new GUI window to add components to

  JButton button;
  JTextField textField;

  MyFrame() {
    this.setTitle("Susdoku");  // sets title of window
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // exits program when window is closed
    this.setResizable(true);  // prevents window from being resized
    this.setSize(500, 500);

    this.setLayout(new FlowLayout());  // sets layout manager

    // sets background color of window & sets an icon image
    this.getContentPane().setBackground(new Color(1, 140, 140));
    ImageIcon windowIcon = new ImageIcon("sus.png");
    this.setIconImage(windowIcon.getImage());

    button = new JButton("press_me");
    button.addActionListener(this);

    textField = new JTextField();
    textField.addActionListener(this);
    textField.setPreferredSize(new Dimension(250, 40));
    textField.setBorder(BorderFactory.createLineBorder(Color.black));
    textField.setFont(new Font("Consolas", Font.PLAIN, 35));

    this.add(button);
    this.add(textField);
    this.pack();
    this.setVisible(true); // makes window visible
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // TODO Auto-generated method stub
    if ((e.getSource() == button) || (e.getSource() == textField)) {
        String text = textField.getText();
        System.out.println("hello! "+ text);
    }
  }
}
 
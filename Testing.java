import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

// This is some testing code I developed to test the functionality of the text fields.
public class Testing extends JFrame {
  JTextField text = new JTextField("Press Return", 40);

  public Testing() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    text.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.out.println("Text=" + text.getText());
      }
    });

    getContentPane().add(text, "Center");
    pack();
  }

  public static void main(String[] args) {
    new Testing().setVisible(true);
  }
}
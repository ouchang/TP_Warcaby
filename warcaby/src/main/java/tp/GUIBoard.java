package tp;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUIBoard extends JFrame {
  private int countButtonClicked=0;
  
  private JLabel buttonIdxLabel;
  private JButton[][] buttonArray;

  public GUIBoard() {
    buttonArray = new JButton[8][8];
    //JButton[] blackButtons = new JButton[8];
    //JButton[] whiteButtons = new JButton[8];

    JPanel mainPanel = new JPanel(new BorderLayout());
    buttonIdxLabel = new JLabel();
    buttonIdxLabel.setText("CLICK THE BUTTON");
    mainPanel.add(buttonIdxLabel, BorderLayout.NORTH);
    JPanel p1 = new JPanel();
    p1.setLayout(new GridLayout(8, 8));
    p1.setSize(600, 600);

    for(int i=0; i<8; i++) {
      for(int j=0; j<8; j++) {
        if(i%2==0) {
          if(j%2==1){
            buttonArray[i][j] = new JButton();
            buttonArray[i][j].setBackground(Color.BLACK);
          } else {
            buttonArray[i][j] = new JButton();
            buttonArray[i][j].setBackground(Color.WHITE);
          }
        } else {
          if(j%2==1){
            buttonArray[i][j] = new JButton();
            buttonArray[i][j].setBackground(Color.WHITE);
          } else {
            buttonArray[i][j] = new JButton();
            buttonArray[i][j].setBackground(Color.BLACK);
          }
        }
      }
      //p1.add(buttonArray); //
    }

    mainPanel.add(p1, BorderLayout.CENTER);
    add(mainPanel);
    //super.add(p1);
  }

  private void findButton(Object c) {
    for (int x = 0; x < buttonArray.length; x++) {
      for (int y = 0; y < buttonArray[0].length; y++) {
          if (c.equals(buttonArray[x][y])) {
              buttonIdxLabel.setText(x + "," + y + " clicked");
              return;
          }
      }
    }
  }

  class ButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {  
      if(countButtonClicked == 2) {
        System.out.println("TUTAJ BEDE WYSYLAC KOMENDE DO SERWERA");
        countButtonClicked = 0;
      }
      findButton(e.getSource());
      countButtonClicked++;
    }
  }

  public static void main(String[] args) {

    GUIBoard frame = new GUIBoard();
    frame.setTitle("Checkers");
    frame.setSize(600, 600);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

  }
}

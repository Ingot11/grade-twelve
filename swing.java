import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class swing{
   private JFrame mainFrame;
   private JLabel headerLabel;
   private JPanel controlPanel;
   //Calculator Variables
   private JTextField[] numberInput;
   private JButton calcOption;
   private JLabel equalsLabel;
   private final String[] options = {"+","-","*","/","^","√"};
   private int optionNum;
   public static void main(String[] args){// Create Window
      swing calc = new swing();
      calc.calcWindow();
   }public swing(){// Window Constructor
      mainFrame = new JFrame("Azeez's SWING Tester");
      mainFrame.setSize(400,200);
      mainFrame.setLayout(new GridLayout(3, 1));
      headerLabel = new JLabel("",JLabel.CENTER );
      controlPanel = new JPanel();
      controlPanel.setLayout(new FlowLayout());
      mainFrame.addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent windowEvent){System.exit(0);}});
      mainFrame.add(headerLabel);
      mainFrame.add(controlPanel);
      mainFrame.setVisible(true);
   }private void calcWindow(){// Calculator Window
      // Set Variables
      headerLabel.setText("Calculator");
      numberInput = new JTextField[]{new JTextField(3),new JTextField(3)};
      calcOption = new JButton("+");
      optionNum = 1;
      equalsLabel = new JLabel("NaN");
      calcOption.setActionCommand("symbol");
      calcOption.addActionListener(new ButtonClickListener());
      // Equals Button
      JButton equalsButton = new JButton("=");
      equalsButton.setActionCommand("equals");
      equalsButton.addActionListener(new ButtonClickListener());
      // Add to Control Panel
      controlPanel.add(numberInput[0]);
      controlPanel.add(calcOption);
      controlPanel.add(numberInput[1]);
      controlPanel.add(equalsButton);
      controlPanel.add(equalsLabel);
      mainFrame.setVisible(true);  
   }private String calculate(){// Calculations
      double x,y,z;
      try {
         x = Float.parseFloat(numberInput[0].getText());
         y = Float.parseFloat(numberInput[1].getText());
         switch(calcOption.getText()){
            case "+" -> z = x + y;
            case "-" -> z = x - y ;
            case "*" -> z = x * y;
            case "/" -> z = x / y;
            case "^" -> z = Math.pow(x, y);
            case "√" -> z = Math.pow(x, 1.0/y);
            default -> {return "An Error Occured.";}
         }
      }catch (Exception e){return "Not a number.";}
      return "" + z;
   }private class ButtonClickListener implements ActionListener{// Equals Text
      public void actionPerformed(ActionEvent e) {
          switch (e.getActionCommand()) {
              case "symbol" -> {
                  calcOption.setText(options[optionNum++]);
                  if(optionNum >= options.length) optionNum = 0;
               }case "equals" -> equalsLabel.setText(calculate());
          }
      }		
   }
}
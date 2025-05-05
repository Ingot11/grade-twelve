import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class swing{
   // Window Variables
   private JFrame mainFrame;
   private JLabel headerLabel;
   private JPanel controlPanel;
   // Calculator Variables
   private JTextField[] numberInput;
   private JComboBox calcOption;
   private JPanel equalsPanel;
   private JLabel equalsLabel;
   private final String[] options = {"+","-","*","/","^","√","sin(θ)","cos(θ)","tan(θ)","log(x)","ln(x)"};
   private String optionSelect;
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
      mainFrame.setTitle("Azeez's Calculator");;
      headerLabel.setText("Calculator");
      numberInput = new JTextField[]{new JTextField(3),new JTextField(3)};
      // Operation Selector
      calcOption = new JComboBox(new DefaultComboBoxModel(options));
      calcOption.setSelectedIndex(0);
      calcOption.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            optionSelect=""+calcOption.getSelectedItem();
            for(int i=6;i<=options.length;i++){// Checks if it is trig or log
               if(i==options.length) {numberInput[1].setVisible(true); break;}
               if(optionSelect.equals(options[i])) {numberInput[1].setVisible(false); break;}
            }equalsLabel.setText(equalsLabel.getText()+"");
            mainFrame.revalidate();// Resets Frame
         }
      });
      optionSelect="+";
      // Equals Button and Answer
      equalsPanel = new JPanel();
      equalsLabel = new JLabel("NaN");
      JButton equalsButton = new JButton(" = ");
      equalsButton.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {equalsLabel.setText(calculate());}});
      // Add to Control Panel
      mainFrame.add(equalsPanel);
      controlPanel.add(numberInput[0]);
      controlPanel.add(calcOption);
      controlPanel.add(numberInput[1]);
      equalsPanel.add(equalsButton);
      equalsPanel.add(equalsLabel);
      mainFrame.setVisible(true);  
   }private String calculate(){// Calculations
      double x,y,z;
      try {
         x = Float.parseFloat(numberInput[0].getText());
         y = Float.parseFloat(numberInput[1].getText());
         switch(optionSelect){
            case "+" -> z = x + y;
            case "-" -> z = x - y;
            case "*" -> z = x * y;
            case "/" -> z = x / y;
            case "^" -> z = Math.pow(x, y);
            case "√" -> z = Math.pow(x, 1.0/y);
            case "sin(θ)","tan(θ)","log(x)","ln(x)" -> z = x;
            case "cos(θ)" -> z = x;
            default -> {return "NaN";}
         }
      }catch (Exception e){return "NaN";}
      return String.format("%.5f",z);
   }
}
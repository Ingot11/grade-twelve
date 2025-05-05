import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class swingCalculator{
   // Window Variables
   private JFrame mainFrame;
   private JPanel headerPanel;
   private JPanel controlPanel;
   private JPanel textPanel;
   // Calculator Variables
   private JLabel headerLabel;
   private JComboBox calculatorBox;
   private JTextField[] numberInput;
   private JComboBox operationBox;
   private JLabel equalsLabel;
   private String optionSelect;
   private final String[] calculatorOptions = {"Operations","Triginometry","Logarithims"};
   private final String[][] mathOptions = {{"+","-","*","/","^","√"},{"sin(θ)","cos(θ)","tan(θ)","sinh(θ)","cosh(θ)","tanh(θ)"},{"log(x)","ln(x)"}};
   public static void main(String[] args){// Create Window
      swingCalculator calc = new swingCalculator();
      calc.calcWindow();
   }public swingCalculator(){// Window Constructor
      // Set Main Frame
      mainFrame = new JFrame("Azeez's SWING Tester");
      mainFrame.setSize(400,200);
      mainFrame.setLayout(new GridLayout(3, 1));
      mainFrame.addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent windowEvent) {System.exit(0);}});
      // Add Panels
      headerPanel = new JPanel(new FlowLayout());
      controlPanel = new JPanel(new FlowLayout());
      textPanel = new JPanel();
      mainFrame.add(headerPanel);
      mainFrame.add(controlPanel);
      mainFrame.add(textPanel);
      mainFrame.setVisible(true);
   }private void calcWindow(){// Calculator Window
      // Set Header Panel
      headerLabel = new JLabel("Azeez's Calculator");
      calculatorBox = new JComboBox(calculatorOptions);
      calculatorBox.setSelectedIndex(0);
      calculatorBox.addActionListener((ActionEvent e) -> {
         operationBox.setModel(new DefaultComboBoxModel(mathOptions[calculatorBox.getSelectedIndex()]));
         // Checks what option is selected
         if(calculatorBox.getSelectedIndex() == 0) numberInput[1].setVisible(true);
         else numberInput[1].setVisible(false);
         optionSelect = "" + operationBox.getSelectedItem();
         mainFrame.revalidate(); // Resets Frame
      });
      // Set Control Panel
      numberInput = new JTextField[]{new JTextField(3),new JTextField(3)};
      operationBox = new JComboBox(mathOptions[0]);
      operationBox.setSelectedIndex(0);
      operationBox.addActionListener((ActionEvent e) -> {optionSelect=""+operationBox.getSelectedItem();});
      optionSelect="+";
      // Set Text Panel
      equalsLabel = new JLabel("NaN");
      JButton equalsButton = new JButton(" = ");
      equalsButton.addActionListener((ActionEvent e) -> {
         equalsLabel.setText(calculate());
         numberInput[0].setText("");
         numberInput[1].setText("");
      });
      // Add to Panels
      headerPanel.add(headerLabel);
      headerPanel.add(calculatorBox);
      controlPanel.add(numberInput[0]);
      controlPanel.add(operationBox);
      controlPanel.add(numberInput[1]);
      textPanel.add(equalsButton);
      textPanel.add(equalsLabel);
      mainFrame.setVisible(true);
   }private String calculate(){// Calculations
      double x,y=0,z;
      try {
         x = Float.parseFloat(numberInput[0].getText());
         if(numberInput[1].isVisible()) y = Float.parseFloat(numberInput[1].getText());
         switch(optionSelect){// Choose Operation
            case "+" -> z = x + y;
            case "-" -> z = x - y;
            case "*" -> z = x * y;
            case "/" -> z = x / y;
            case "^" -> z = Math.pow(x, y);
            case "√" -> z = Math.pow(x, 1.0/y);
            case "sin(θ)" -> z = Math.sin(x);
            case "cos(θ)" -> z = Math.cos(x);
            case "tan(θ)" -> z = Math.tan(x);
            case "sinh(θ)" -> z = Math.sinh(x);
            case "cosh(θ)" -> z = Math.cosh(x);
            case "tanh(θ)" -> z = Math.tanh(x);
            case "log(x)" -> z = Math.log10(x);
            case "ln(x)" -> z = Math.log(x);
            default -> {return "NaN";}
         }return "" + Math.round(z*10000.0)/10000.0;
      }catch (NumberFormatException | NullPointerException e){return "NaN";}
   }
}
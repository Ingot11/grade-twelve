import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class swingCalc{
   // Window Variables
    private JFrame mainFrame;
    private JPanel headerPanel;
    private JPanel middlePanel;
    private JPanel bottomPanel;
    public static void main(String[] args){// Call Window
        swingCalc calc = new swingCalc();
        calc.calcWindow();
    }public swingCalc(){// Window Constructor
        // Set Main Frame
        mainFrame = new JFrame("Azeez's SWING Tester");
        mainFrame.setSize(400,200);
        mainFrame.setLayout(new GridLayout(3, 1));
        mainFrame.addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent windowEvent) {System.exit(0);}});
        // Add Panels
        headerPanel = new JPanel(new FlowLayout());
        middlePanel = new JPanel(new FlowLayout());
        bottomPanel = new JPanel(new FlowLayout());
        mainFrame.add(headerPanel);
        mainFrame.add(middlePanel);
        mainFrame.add(bottomPanel);
    }// Calculator
    private double sum;
    private String equation;
    private String lastUsedOperation;
    private JTextField inputNumber;
    JLabel equalsLabel;
    private void calcWindow(){
        sum=0;
        equation="";
        lastUsedOperation = "";
        inputNumber = new JTextField(3);
        // Operations
        JButton plus = new JButton("+");
        plus.setActionCommand("+");
        plus.addActionListener(new Operation());
        JButton minus = new JButton("-");
        minus.setActionCommand("-");
        minus.addActionListener(new Operation());
        JButton multiply = new JButton("*");
        multiply.setActionCommand("*");
        multiply.addActionListener(new Operation());
        JButton divide = new JButton("/");
        divide.setActionCommand("/");
        divide.addActionListener(new Operation());
        // Add to Panel
        headerPanel.add(inputNumber);
        middlePanel.add(plus);
        middlePanel.add(minus);
        middlePanel.add(multiply);
        middlePanel.add(divide);
        // Equals Button and Text
        JButton equalsButton = new JButton("=");
        equalsLabel = new JLabel("Text");
        equalsButton.addActionListener(new Operation());
        equalsButton.setActionCommand("=");
        bottomPanel.add(equalsButton);
        bottomPanel.add(equalsLabel);
        mainFrame.setVisible(true);
    }private class Operation implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            try {
                calculate(lastUsedOperation, Double.parseDouble(inputNumber.getText()));
                equation+=lastUsedOperation+" "+inputNumber.getText();
                equalsLabel.setText(equation);
                if(e.getActionCommand().equals("=")) {
                    equalsLabel.setText(equation+" = "+sum);
                    sum=0;
                    equation="";
                }else lastUsedOperation = e.getActionCommand();
                inputNumber.setText("");
            }catch (Exception a) {System.out.print("ds");} 	
        }		
     }private String calculate(String option,double x){// Calculations
        double y=0,z=0;
        try {
           switch(option){// Choose Operation
              case "+" -> sum += x;
              case "-" -> sum -= x;
              case "*" -> sum *= x;
              case "/" -> sum = sum / x;
              case "^" -> z = Math.pow(x, 2);
              case "√" -> z = Math.pow(x, 1.0/2.0);
              case "sin(θ)" -> z = Math.sin(x);
              case "cos(θ)" -> z = Math.cos(x);
              case "tan(θ)" -> z = Math.tan(x);
              case "sinh(θ)" -> z = Math.sinh(x);
              case "cosh(θ)" -> z = Math.cosh(x);
              case "tanh(θ)" -> z = Math.tanh(x);
              case "log(x)" -> z = Math.log10(x);
              case "ln(x)" -> z = Math.log(x);
              case "" -> z = x;
              default -> {return "NaN";}
           }return "" + Math.round(z*10000.0)/10000.0;
        }catch (NumberFormatException | NullPointerException e){return "NaN";}
     }
}
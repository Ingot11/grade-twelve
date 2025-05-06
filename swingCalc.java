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
    private JTextField inputNumber;
    JLabel equalsLabel;
    private void calcWindow(){
        sum = 0;
        equation = "";
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
        equalsButton.setActionCommand("");
        bottomPanel.add(equalsButton);
        bottomPanel.add(equalsLabel);
        mainFrame.setVisible(true);
    }
    private class Operation implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            try {
                equation += inputNumber.getText() + " " + e.getActionCommand() + " ";
                equalsLabel.setText(equation);
                if(e.getActionCommand().equals("")) {
                    sum = calc(equation);
                    equalsLabel.setText(equation + "= " + sum);
                    sum = 0;
                    equation = "";
                }inputNumber.setText("");
            }catch (Exception a) {System.out.print("ds");} 	
        }		
     }private double calc(String equations){
        String[] eq = equations.split("\\s");
        double s=Double.parseDouble(eq[0]);
        for(int i=1;i<eq.length;i+=2){
            switch(eq[i]){
                case "*"->s*=Double.parseDouble(eq[i+1]);
                case "/"->s/=Double.parseDouble(eq[i+1]);
                case "+"->s+=Double.parseDouble(eq[i+1]);
                case "-"->s-=Double.parseDouble(eq[i+1]);
            }
        }
        return s;
     }
}
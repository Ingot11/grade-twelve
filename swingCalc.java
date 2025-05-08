import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
public class swingCalc{
   // Window Variables
    private final JFrame mainFrame;
    private final JPanel headerPanel;
    private final JPanel middlePanel;
    private final JPanel bottomPanel;
    public static void main(String[] args){// Call Window
        swingCalc calc = new swingCalc();
        calc.calcWindow();
    }public swingCalc(){// Window Constructor
        // Set Main Frame
        mainFrame = new JFrame("Azeez's SWING Tester");
        mainFrame.setSize(600,200);
        mainFrame.setLayout(new GridLayout(3, 1));
        mainFrame.addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent windowEvent) {System.exit(0);}});
        // Add Panels
        headerPanel = new JPanel(new FlowLayout());
        middlePanel = new JPanel(new FlowLayout());
        bottomPanel = new JPanel(new FlowLayout());
        mainFrame.add(headerPanel);
        mainFrame.add(middlePanel);
        mainFrame.add(bottomPanel);
    }
    // Calculator Variables
    private String equation;
    private boolean lastSymbol;
    private JLabel equalsLabel;
    private JTextField inputNumber;
    private void calcWindow(){// Calculator Constructor
        equation = "";
        lastSymbol = true;
        inputNumber = new JTextField(3);
        headerPanel.add(inputNumber);
        // Simple Operations : +, -, *, /, ^, √
        JButton plus = new JButton("+");
        JButton minus = new JButton("-");
        JButton multiply = new JButton("*");
        JButton divide = new JButton("/");
        JButton power = new JButton("^");
        JButton root = new JButton("√");
        plus.addActionListener((ActionEvent e) -> {mainOperation("+");});
        minus.addActionListener((ActionEvent e) -> {mainOperation("-");});
        multiply.addActionListener((ActionEvent e) -> {mainOperation("*");});
        divide.addActionListener((ActionEvent e) -> {mainOperation("/");});
        power.addActionListener((ActionEvent e) -> {mainOperation("^");});
        root.addActionListener((ActionEvent e) -> {mainOperation("√");});
        // Trigonometry : sin(θ), cos(θ), tan(θ), sinh(θ), cosh(θ), tanh(θ)
        JButton sin = new JButton("sin(θ)");
        JButton cos = new JButton("cos(θ)");
        JButton tan = new JButton("tan(θ)");
        JButton sinh = new JButton("sinh(θ)");
        JButton cosh = new JButton("cosh(θ)");
        JButton tanh = new JButton("tanh(θ)");
        sin.addActionListener((ActionEvent e) -> {symbolOperation("sin");});
        cos.addActionListener((ActionEvent e) -> {symbolOperation("cos");});
        tan.addActionListener((ActionEvent e) -> {symbolOperation("tan");});
        sinh.addActionListener((ActionEvent e) -> {symbolOperation("sinh");});
        cosh.addActionListener((ActionEvent e) -> {symbolOperation("cosh");});
        tanh.addActionListener((ActionEvent e) -> {symbolOperation("tanh");});
        sin.setVisible(false);
        cos.setVisible(false);
        tan.setVisible(false);
        sinh.setVisible(false);
        cosh.setVisible(false);
        tanh.setVisible(false);
        // Other Operations : log(x), ln(x)
        JButton log = new JButton("log(x)");
        JButton ln = new JButton("ln(x)");
        log.addActionListener((ActionEvent e) -> {symbolOperation("log");});
        ln.addActionListener((ActionEvent e) -> {symbolOperation("ln");});
        log.setVisible(false);
        ln.setVisible(false);
        // Choose type of Operation
        JComboBox operationSelector = new JComboBox(new String[]{"Operations","Trigonometry","Other"});
        operationSelector.setSelectedIndex(0);
        operationSelector.addActionListener((ActionEvent e) -> {
            // Hides all at start
            plus.setVisible(false);
            minus.setVisible(false);
            multiply.setVisible(false);
            divide.setVisible(false);
            power.setVisible(false);
            root.setVisible(false);
            sin.setVisible(false);
            cos.setVisible(false);
            tan.setVisible(false);
            sinh.setVisible(false);
            cosh.setVisible(false);
            tanh.setVisible(false);
            log.setVisible(false);
            ln.setVisible(false);
            switch(operationSelector.getSelectedIndex()){// Choose which is visible
                case 0 -> {// Operations
                    plus.setVisible(true);
                    minus.setVisible(true);
                    multiply.setVisible(true);
                    divide.setVisible(true);
                    power.setVisible(true);
                    root.setVisible(true);
                }case 1 -> {// Trigonometry
                    sin.setVisible(true);
                    cos.setVisible(true);
                    tan.setVisible(true);
                    sinh.setVisible(true);
                    cosh.setVisible(true);
                    tanh.setVisible(true);
                }case 2 -> {// Other
                    log.setVisible(true);
                    ln.setVisible(true);
                }
            }mainFrame.revalidate(); // Resets mainframe
        });
        // Add to Middle Panel
        middlePanel.add(operationSelector);
        middlePanel.add(plus);
        middlePanel.add(minus);
        middlePanel.add(multiply);
        middlePanel.add(divide);
        middlePanel.add(power);
        middlePanel.add(root);
        middlePanel.add(sin);
        middlePanel.add(cos);
        middlePanel.add(tan);
        middlePanel.add(sinh);
        middlePanel.add(cosh);
        middlePanel.add(tanh);
        middlePanel.add(log);
        middlePanel.add(ln);
        // Equals Button and Text
        JButton equalsButton = new JButton("=");
        equalsLabel = new JLabel("Text");
        equalsButton.addActionListener((ActionEvent e) -> {mainOperation("");});
        bottomPanel.add(equalsButton);
        bottomPanel.add(equalsLabel);
        mainFrame.setVisible(true);
    }private void mainOperation(String operation){
        try {// Input Operation into equation
            if(lastSymbol) equation += Double.valueOf(inputNumber.getText()) + " ";
            else lastSymbol = true;
            equalsLabel.setText(equation += operation + " ");
            if(operation.equals("")) {// When equals is pressed, gives final answer
                equalsLabel.setText(equation + "= " + calculate());
                equation = "";
            }inputNumber.setText("");
        }catch (NumberFormatException e) {}
    }private void symbolOperation(String operation){// Input Trig or other
        try{if(lastSymbol){
            equalsLabel.setText(equation += operation + " " + Double.valueOf(inputNumber.getText()) + " ");
            inputNumber.setText("");
            lastSymbol = false;
        }}catch (NumberFormatException e) {} 
    }private double calculate(){// Get Equation into sum
        ArrayList<String> answer = new ArrayList<>(Arrays.asList(equation.split("\\s")));
        for(String[] options : new String[][]{{"sin", "cos", "tan", "sinh", "cosh", "tanh" ,"log" ,"ln"}, {"√"}, {"^"}, {"*", "/"}, {"+", "-"}}) {
            boolean leave = true;
            while(leave){
                leave = false;
                // Multiple For loops to check each option
                for(int i=0; i<answer.size(); i++) for(String option : options) if(answer.get(i).equals(option)){
                    double x=Double.parseDouble(answer.get(i + 1)), y=0, sum=0;
                    if(!Arrays.equals(options, new String[]{"sin", "cos", "tan", "sinh", "cosh", "tanh" ,"log" ,"ln"})) y=Double.parseDouble(answer.get(i - 1));
                    switch(answer.get(i)){
                        // Normal Operations
                        case "+" -> sum = y + x;
                        case "-" -> sum = y - x;
                        case "*" -> sum = y * x;
                        case "/" -> sum = y / x;
                        case "^" -> sum = Math.pow(x, y);
                        case "√" -> sum = Math.pow(x, 1.0/y);
                        // Trig and Log
                        case "sin" -> sum = Math.sin(x);
                        case "cos" -> sum = Math.cos(x);
                        case "tan" -> sum = Math.tan(x);
                        case "sinh" -> sum = Math.sinh(x);
                        case "cosh" -> sum = Math.cosh(x);
                        case "tanh" -> sum = Math.tanh(x);
                        case "log" -> sum = Math.log10(x);
                        case "ln" -> sum = Math.log(x);
                    }answer.set(i, "" + sum);
                    answer.remove(i + 1);
                    if(!Arrays.equals(options, new String[]{"sin", "cos", "tan", "sinh", "cosh", "tanh" ,"log" ,"ln"})) answer.remove(i - 1);
                    break;
                }for(String i : options) if(answer.contains(i)) leave=true;
            }
        }return Double.parseDouble(answer.get(0)); 
    }
}
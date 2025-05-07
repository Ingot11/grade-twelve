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
    }// Calculator Variables
    private String equation;
    private JLabel equalsLabel;
    private JTextField inputNumber;
    private void calcWindow(){// Calculator Constructor
        equation = "";
        lastSymbol = false;
        inputNumber = new JTextField(3);
        headerPanel.add(inputNumber);
        // Simple Operations : +, -, *, /, ^, √
        JButton plus = new JButton("+");
        plus.addActionListener((ActionEvent e) -> {mainOperation("+");});
        JButton minus = new JButton("-");
        minus.addActionListener((ActionEvent e) -> {mainOperation("-");});
        JButton multiply = new JButton("*");
        multiply.addActionListener((ActionEvent e) -> {mainOperation("*");});
        JButton divide = new JButton("/");
        divide.addActionListener((ActionEvent e) -> {mainOperation("/");});
        JButton power = new JButton("^");
        power.addActionListener((ActionEvent e) -> {mainOperation("^");});
        JButton root = new JButton("√");
        root.addActionListener((ActionEvent e) -> {mainOperation("√");});
        // Trigonometry : sin(θ), cos(θ), tan(θ), sinh(θ), cosh(θ), tanh(θ)
        JButton sin = new JButton("sin(θ)");
        sin.addActionListener((ActionEvent e) -> {symbolOperation("sin");});
        sin.setVisible(false);
        JButton cos = new JButton("cos(θ)");
        cos.addActionListener((ActionEvent e) -> {symbolOperation("cos");});
        cos.setVisible(false);
        JButton tan = new JButton("tan(θ)");
        tan.addActionListener((ActionEvent e) -> {symbolOperation("tan");});
        tan.setVisible(false);
        JButton sinh = new JButton("sinh(θ)");
        sinh.addActionListener((ActionEvent e) -> {symbolOperation("sinh");});
        sinh.setVisible(false);
        JButton cosh = new JButton("cosh(θ)");
        cosh.addActionListener((ActionEvent e) -> {symbolOperation("cosh");});
        cosh.setVisible(false);
        JButton tanh = new JButton("tanh(θ)");
        tanh.addActionListener((ActionEvent e) -> {symbolOperation("tanh");});
        tanh.setVisible(false);
        // Other Operations : log(x), ln(x)
        JButton log = new JButton("log(x)");
        log.addActionListener((ActionEvent e) -> {symbolOperation("log");});
        log.setVisible(false);
        JButton ln = new JButton("ln(x)");
        ln.addActionListener((ActionEvent e) -> {symbolOperation("ln");});
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
                case 0 -> {
                    plus.setVisible(true);
                    minus.setVisible(true);
                    multiply.setVisible(true);
                    divide.setVisible(true);
                    power.setVisible(true);
                    root.setVisible(true);
                }case 1 -> {
                    sin.setVisible(true);
                    cos.setVisible(true);
                    tan.setVisible(true);
                    sinh.setVisible(true);
                    cosh.setVisible(true);
                    tanh.setVisible(true);
                }case 2 -> {
                    log.setVisible(true);
                    ln.setVisible(true);
                }
            }mainFrame.revalidate(); // Resets Frame
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
    }private boolean lastSymbol;
    private void mainOperation(String e){ try {// Input Operation into equation
        if(!lastSymbol) equation += Double.valueOf(inputNumber.getText()) + " ";
        else lastSymbol=false;
        equalsLabel.setText(equation += e + " ");
        if(e.equals("")) {// When equals is pressed, gives final answer
            equalsLabel.setText(equation + "= " + calc(equation));
            equation = "";
        }inputNumber.setText("");
        }catch (NumberFormatException a) {System.out.print("An error occured.");} 			
    }private void symbolOperation(String e){// Input Trig or other
        if(!lastSymbol){
            equalsLabel.setText(equation += e + " " + inputNumber.getText() + " ");
            inputNumber.setText("");
            lastSymbol=true;
        }
    }private double calc(String equations){// Get Equation into sum
        ArrayList<String> answer = new ArrayList<>(Arrays.asList(equations.split("\\s")));
        answer=doOperation(answer, new String[]{"sin", "cos", "tan", "sinh", "cosh", "tanh" ,"log" ,"ln"});
        answer=doOperation(answer, new String[]{"^","√"});
        answer=doOperation(answer, new String[]{"*", "/"});
        answer=doOperation(answer, new String[]{"+", "-"});
        return Double.parseDouble(answer.get(0));
    }private ArrayList<String> doOperation(ArrayList<String> list,String[] options){
        boolean leave=true;
        while(leave){
            leave = false;
            for(int i=0; i<list.size(); i++){
                for(String option : options)
                    if(list.get(i).equals(option)){
                        double x=Double.parseDouble(list.get(i + 1)), y=0;
                        if(!Arrays.equals(options, new String[]{"sin", "cos", "tan", "sinh", "cosh", "tanh" ,"log" ,"ln"})){
                            y=Double.parseDouble(list.get(i - 1));
                        }switch(list.get(i)){// Does Calculation
                            case "+" -> list.set(i, "" + (y + x));
                            case "-" -> list.set(i, "" + (y - x));
                            case "*" -> list.set(i, "" + (y * x));
                            case "/" -> list.set(i, "" + (y / x));
                            case "^" -> list.set(i, "" + Math.pow(x, y));
                            case "√" -> list.set(i, "" + Math.pow(x, 1.0/y));
                            case "sin" -> list.set(i, "" + Math.sin(x));
                            case "cos" -> list.set(i, "" + Math.cos(x));
                            case "tan" -> list.set(i, "" + Math.tan(x));
                            case "sinh" -> list.set(i, "" + Math.sinh(x));
                            case "cosh" -> list.set(i, "" + Math.cosh(x));
                            case "tanh" -> list.set(i, "" + Math.tanh(x));
                            case "log" -> list.set(i, "" + Math.log10(x));
                            case "ln" -> list.set(i, "" + Math.log(x));
                        }list.remove(i + 1);
                        if(!Arrays.equals(options, new String[]{"sin", "cos", "tan", "sinh", "cosh", "tanh" ,"log" ,"ln"})) list.remove(i - 1);
                        break;
                    }
            }for(String i : options) if(list.contains(i)) leave=true;
        }return list;
     }
}
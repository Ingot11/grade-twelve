import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.logging.*;
public class swingCalc{
   // Variables
    private final JFrame mainFrame;
    private final JPanel headerPanel, middlePanel, bottomPanel;
    private JLabel equalsLabel, equationLabel;
    private JTextField inputNumber;
    private int textCount, fails;
    private boolean lastSymbol;
    private String equation;
    private double[] v;
    public static void main(String[] args){ // Call Window
        swingCalc logs = new swingCalc();
        logs.loginWindow();
    }
    public swingCalc(){ // Window Constructor
        // Set Main Frame
        mainFrame = new JFrame("Azeez's SWING Tester");
        mainFrame.setSize(600,200);
        mainFrame.setLayout(new GridLayout(3, 1));
        mainFrame.addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent windowEvent) {System.exit(0);}});
        // Add Panels
        mainFrame.add(headerPanel = new JPanel(new FlowLayout()));
        mainFrame.add(middlePanel = new JPanel(new FlowLayout()));
        mainFrame.add(bottomPanel = new JPanel(new FlowLayout()));
    }
    // Login Details
    private void loginWindow(){
        mainFrame.setSize(400,250);
        ArrayList<String> users = new ArrayList<>();
        try(BufferedReader buffRead = new BufferedReader(new FileReader("users.txt"))){ // Add acccounts from users.txt
            String line;
            while ((line = buffRead.readLine()) != null) users.add(line);
            buffRead.close();
        }catch (FileNotFoundException e) {System.out.println("File not found");}
        catch (IOException ex) {Logger.getLogger(swingCalc.class.getName()).log(Level.SEVERE, null, ex);}
        // Username and Password
        JTextField username = new JTextField(4);
        JPasswordField password = new JPasswordField(4);
        fails = 1;
        // Login and New Account Button
        JButton login = new JButton("Login."), newAccount = new JButton("Create Account");
        JLabel loggedIn = new JLabel("Not logged in.");
        login.addActionListener((ActionEvent e) -> {
            if(fails == 0) return;
            for(String i:users){
                if(username.getText().equals(i)){
                    loggedIn.setText("Logged in.");
                    String[] options = {"Normal", "Themed", "Close"};
                    swingCalc calc = new swingCalc();
                    switch (JOptionPane.showOptionDialog(mainFrame, "Choose the Type of Calculator", "Calculator Select", 1, 1, null, options, options[2])) {
                        case 0 /*Normal*/ -> calc.normalCalculator();
                        case 1 /*Themed*/ -> calc.themedCalculator();
                        default -> System.exit(0);
                    }fails = 0;
                    break;
                }
            }if(fails != 0){
                loggedIn.setText((3-fails)+" attempts left.");
                if (fails++ >= 3){
                    JOptionPane.showMessageDialog(mainFrame,"You ran out of login attempts.\nPress OK to leave the program.");
                    System.exit(0);
                }
            }
        });
        newAccount.addActionListener((ActionEvent e) -> {
            if(fails == 0) return;
            for(String i:users) if(username.getText().equals(i)){
                loggedIn.setText(username.getText() + " is already an option.");
                return;
            }try(BufferedWriter buffWrite = new BufferedWriter(new FileWriter("users.txt"))){
                for (String item:users) buffWrite.write(item + "\n");
                buffWrite.write(username.getText());
                users.add(username.getText());
                buffWrite.close();
                loggedIn.setText(username.getText() + " was added.");
            }catch (FileNotFoundException a) {System.out.println("File not found");} 
            catch (IOException ex) {Logger.getLogger(swingCalc.class.getName()).log(Level.SEVERE, null, ex);}
        });
        middlePanel.add(new JLabel("Username:"));
        middlePanel.add(username);
        middlePanel.add(new JLabel("Password:"));
        middlePanel.add(password);
        bottomPanel.add(newAccount);
        bottomPanel.add(login);
        bottomPanel.add(loggedIn);
        mainFrame.setVisible(true);
    }
    // Normal Calculator
    private void normalCalculator(){
        equation = "";
        lastSymbol = true;
        mainFrame.setTitle("Azeez's Calculator");
        headerPanel.add(inputNumber = new JTextField(3));
        // Simple Operations : +, -, *, /, ^, √
        JButton plus = new JButton("+"),
        minus = new JButton("-"),
        multiply = new JButton("*"),
        divide = new JButton("/"),
        power = new JButton("^"),
        root = new JButton("√"),
        // Trigonometry : sin(θ), cos(θ), tan(θ), sinh(θ), cosh(θ), tanh(θ) and other operations : log(x), ln(x)
        sin = new JButton("sin(θ)"),
        cos = new JButton("cos(θ)"),
        tan = new JButton("tan(θ)"),
        sinh = new JButton("sinh(θ)"),
        cosh = new JButton("cosh(θ)"),
        tanh = new JButton("tanh(θ)"),
        log = new JButton("log(x)"),
        ln = new JButton("ln(x)");
        // Add action listeners to operators
        JButton[] operates= {plus, minus, multiply, divide, power, root};
        JButton[] trig = {sin, cos, tan, sinh, cosh, tanh};
        JButton[] logarithims = {log, ln};
        JButton[] allOperates = {plus, minus, multiply, divide, power, root, sin, cos, tan, sinh, cosh, tanh, log, ln};
        for(JButton i : operates) i.addActionListener((ActionEvent e) -> {mainOperation(i.getText());});
        for(JButton i : trig) i.addActionListener((ActionEvent e) -> {symbolOperation(i.getText().substring(0,i.getText().length()-3));});
        for(JButton i : logarithims) i.addActionListener((ActionEvent e) -> {symbolOperation(i.getText().substring(0,i.getText().length()-3));});
        // Choose type of operation
        JComboBox operationSelector = new JComboBox(new String[]{"Operations","Trigonometry","Other"});
        operationSelector.setSelectedIndex(0);
        operationSelector.addActionListener((ActionEvent e) -> { // Hides all at start
            for(JButton i : allOperates) i.setVisible(false);
            switch(operationSelector.getSelectedIndex()){ // Choose which is visible
                case 0 /*Operations*/ -> {for(JButton i : operates) i.setVisible(true);}
                case 1 /*Trigonometry*/ -> {for(JButton i : trig) i.setVisible(true);}
                case 2 /*Other*/ -> {for(JButton i : logarithims) i.setVisible(true);}
            }mainFrame.revalidate(); // Resets mainFrame
        });
        // Add to Middle Panel
        middlePanel.add(operationSelector);
        for(JButton i : allOperates) middlePanel.add(i);
        // Set all but default operations invisible
        for(Component component : middlePanel.getComponents()) component.setVisible(false);
        for(JButton i : operates) i.setVisible(true);
        operationSelector.setVisible(true);
        // Equals Button and Text
        JButton equalsButton = new JButton("=");
        equalsButton.addActionListener((ActionEvent e) -> {mainOperation("");});
        bottomPanel.add(equationLabel = new JLabel("Input a Number"));
        bottomPanel.add(equalsButton);
        bottomPanel.add(equalsLabel = new JLabel("Answer"));
        mainFrame.setVisible(true);
    }private void mainOperation(String operation){
        try { // Input Operation into equation
            if(lastSymbol) equation += Double.valueOf(inputNumber.getText()) + " ";
            else lastSymbol = true;
            equationLabel.setText(equation += operation + " ");
            if(operation.equals("")) { // When equals is pressed, gives final answer
                equalsLabel.setText("" + calculate());
                equation = "";
            }inputNumber.setText("");
        }catch (NumberFormatException e) {}
    }private void symbolOperation(String operation){ // Input Trig or other
        try{if(lastSymbol){
            equationLabel.setText(equation += operation + " " + Double.valueOf(inputNumber.getText()) + " ");
            inputNumber.setText("");
            lastSymbol = false;
        }}catch (NumberFormatException e) {} 
    }private double calculate(){ // Get Equation into Answer
        ArrayList<String> answer = new ArrayList<>(Arrays.asList(equation.split("\\s")));
        String[] symbolOperators = {"sin", "cos", "tan", "sinh", "cosh", "tanh" ,"log" ,"ln"};
        double tenToTen = Math.pow(10,10); // Used to round trig due to inconcistency
        for(String[] options : new String[][]{symbolOperators, {"√"}, {"^"}, {"*", "/"}, {"+", "-"}}) {
            boolean leave = true;
            while(leave){
                leave = false;
                // Multiple For loops to check each option
                for(int i=0; i<answer.size(); i++) for(String option : options) if(answer.get(i).equals(option)){
                    double x=Double.parseDouble(answer.get(i + 1)), y = 0, sum = 0;
                    if(!Arrays.equals(options, symbolOperators)) y = Double.parseDouble(answer.get(i - 1));
                    switch(answer.get(i)){
                        // Normal Operations
                        case "+" -> sum = y + x;
                        case "-" -> sum = y - x;
                        case "*" -> sum = y * x;
                        case "/" -> sum = y / x;
                        case "^" -> sum = Math.pow(y, x);
                        case "√" -> sum = Math.pow(x, 1.0/y);
                        // Trig and Log
                        case "sin" -> sum = Math.round(Math.sin(Math.toRadians(x))*tenToTen)/tenToTen;
                        case "cos" -> sum = Math.round(Math.cos(Math.toRadians(x))*tenToTen)/tenToTen;
                        case "tan" -> sum = Math.round(Math.tan(Math.toRadians(x))*tenToTen)/tenToTen;
                        case "sinh" -> sum = Math.sinh(x);
                        case "cosh" -> sum = Math.cosh(x);
                        case "tanh" -> sum = Math.tanh(x);
                        case "log" -> sum = Math.log10(x);
                        case "ln" -> sum = Math.log(x);
                    }answer.set(i, "" + sum);
                    answer.remove(i + 1);
                    if(!Arrays.equals(options, symbolOperators)) answer.remove(i - 1);
                    break;
                }for(String i : options) if(answer.contains(i)) leave = true;
            }
        }return Double.parseDouble(answer.get(0)); 
    }
    // Themed Calculator
    private void themedCalculator(){
        String[] textTiles = {"Starting Balance", "Interest Rate (In Decimals)", "k Property", "When did you start?", "c Property", "Complete"};
        v = new double[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};
        mainFrame.setTitle("Azeez's THEMED Calculator");
        textCount = 1;
        // Enter Button
        JButton enter = new JButton("ENTER");
        enter.addActionListener((ActionEvent e) -> {
            if(textCount >= 6){ // Reset Equation
                equalsLabel.setText("Input a number for: " + textTiles[0]);
                v = new double[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};
                equationLabel.setText("a(b)^(k(x - h)) + c");
                enter.setText("ENTER");
                textCount = 1;
                return;
            }for(int i = 0; i < v.length; i++){ // Adds each value to array
                if(v[i] == Integer.MAX_VALUE){
                    try{
                        v[i] = Double.parseDouble(inputNumber.getText());
                        inputNumber.setText("");
                        equalsLabel.setText("Input a number for: " + textTiles[textCount++]);
                    }catch(NumberFormatException a){}
                    break;
                }
            }if(v[v.length-1] != Integer.MAX_VALUE){ // When all values are inputted
                enter.setText("RESET");
                equationLabel.setText(String.format("%.2f(%.2f)^(%.2f(x - %.2f)) - %.2f", v[0], v[1], v[2], v[3], v[4])+ " = " + (v[0] * Math.pow(v[1], v[2] * (0.0 - v[3])) - v[4]));
            }
        });
        // Add to Panel
        headerPanel.add(equalsLabel = new JLabel("Input a number for: Starting Balance"));
        middlePanel.add(inputNumber = new JTextField(3));
        middlePanel.add(enter);
        bottomPanel.add(equationLabel = new JLabel("a(b)^(k(x - h)) + c"));
        mainFrame.setVisible(true);
    }
}
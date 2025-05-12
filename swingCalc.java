import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.logging.*;
public class swingCalc{
   // Window Variables
    private final JFrame mainFrame;
    private final JPanel headerPanel;
    private final JPanel middlePanel;
    private final JPanel bottomPanel;
    public static void main(String[] args){// Call Window
        swingCalc logs = new swingCalc();
        logs.loginWindow();
    }public swingCalc(){// Window Constructor
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
    private int fails = 1;
    private void loginWindow(){
        mainFrame.setSize(400,250);
        ArrayList<String> users = new ArrayList<>();
        String line;
        try(BufferedReader buffRead = new BufferedReader(new FileReader("users.txt"))){ // Add acccounts from users.txt
            while ((line=buffRead.readLine()) != null) users.add(line);
            buffRead.close();
        }catch (FileNotFoundException e) {System.out.println("File not found");}
        catch (IOException ex) {Logger.getLogger(swingCalc.class.getName()).log(Level.SEVERE, null, ex);}
        // Username and Password
        JTextField username = new JTextField(4);
        JPasswordField password = new JPasswordField(4);
        // Login and New Account Button
        JButton login = new JButton("Login.");
        JButton newAccount = new JButton("Create Account");
        JLabel loggedIn = new JLabel("Not logged in.");
        login.addActionListener((ActionEvent e) -> {
            if(fails == 0) return;
            for(String i:users){
                if(username.getText().equals(i)){
                    loggedIn.setText("Logged in.");
                    JOptionPane.showMessageDialog(mainFrame,"Press OK to open the calculator.");
                    swingCalc calc = new swingCalc();
                    calc.normalCalculator();
                    fails = 0;
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
                loggedIn.setText(username.getText() + " was added");
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
    }// Calculator Variables
    private String equation;
    private boolean lastSymbol;
    private JLabel equalsLabel;
    private JLabel equationLabel;
    private JTextField inputNumber;
    private void normalCalculator(){ // Calculator Constructor
        equation = "";
        lastSymbol = true;
        headerPanel.add(inputNumber = new JTextField(3));
        // Simple Operations : +, -, *, /, ^, √
        JButton plus = new JButton("+");
        JButton minus = new JButton("-");
        JButton multiply = new JButton("*");
        JButton divide = new JButton("/");
        JButton power = new JButton("^");
        JButton root = new JButton("√");
        // Trigonometry : sin(θ), cos(θ), tan(θ), sinh(θ), cosh(θ), tanh(θ) And Other Operations : log(x), ln(x)
        JButton sin = new JButton("sin(θ)");
        JButton cos = new JButton("cos(θ)");
        JButton tan = new JButton("tan(θ)");
        JButton sinh = new JButton("sinh(θ)");
        JButton cosh = new JButton("cosh(θ)");
        JButton tanh = new JButton("tanh(θ)");
        JButton log = new JButton("log(x)");
        JButton ln = new JButton("ln(x)");
        // Add action listeners to operators
        JButton[] operates= {plus, minus, multiply, divide, power, root};
        JButton[] trig = {sin, cos, tan, sinh, cosh, tanh};
        JButton[] logarithims = {log, ln};
        for(JButton i : operates) i.addActionListener((ActionEvent e) -> {mainOperation(i.getText());});
        for(JButton i : trig) i.addActionListener((ActionEvent e) -> {symbolOperation(i.getText().substring(0,i.getText().length()-3));});
        for(JButton i : logarithims) i.addActionListener((ActionEvent e) -> {symbolOperation(i.getText().substring(0,i.getText().length()-3));});
        // Choose type of Operation
        JComboBox operationSelector = new JComboBox(new String[]{"Operations","Trigonometry","Other"});
        operationSelector.setSelectedIndex(0);
        operationSelector.addActionListener((ActionEvent e) -> { // Hides all at start
            for(Component component : middlePanel.getComponents()) component.setVisible(false);
            operationSelector.setVisible(true);
            switch(operationSelector.getSelectedIndex()){ // Choose which is visible
                case 0 /*Operations*/ -> {for(JButton i : operates) i.setVisible(true);}
                case 1 /*Trigonometry*/ -> {for(JButton i : trig) i.setVisible(true);}
                case 2 /*Other*/ -> {for(JButton i : logarithims) i.setVisible(true);}
            }mainFrame.revalidate(); // Resets mainframe
        });
        // Add to Middle Panel
        middlePanel.add(operationSelector);
        for(JButton i : operates) middlePanel.add(i);
        for(JButton i : trig) middlePanel.add(i);
        for(JButton i : logarithims) middlePanel.add(i);
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
    }private void themedCalculator(){
        // Inputter
        equation = "";
        headerPanel.add(new JLabel("Input a number for: the starting money"));
        middlePanel.add(inputNumber = new JTextField(3));
        inputNumber.addActionListener((ActionEvent e) -> {});
        bottomPanel.add(equationLabel = new JLabel("a(b)^(k(x - h)) + c"));
    }private double calculate(){ // Get Equation into sum
        ArrayList<String> answer = new ArrayList<>(Arrays.asList(equation.split("\\s")));
        String[] symbolOperators = {"sin", "cos", "tan", "sinh", "cosh", "tanh" ,"log" ,"ln"};
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
                        case "sin" -> sum = Math.sin(Math.toRadians(x));
                        case "cos" -> sum = Math.cos(Math.toRadians(x));
                        case "tan" -> sum = Math.tan(Math.toRadians(x));
                        case "sinh" -> sum = Math.sinh(Math.toRadians(x));
                        case "cosh" -> sum = Math.cosh(Math.toRadians(x));
                        case "tanh" -> sum = Math.tanh(Math.toRadians(x));
                        case "log" -> sum = Math.log10(x);
                        case "ln" -> sum = Math.log(x);
                    }answer.set(i, "" + sum);
                    answer.remove(i + 1);
                    if(!Arrays.equals(options, symbolOperators)) answer.remove(i - 1);
                    break;
                }for(String i : options) if(answer.contains(i)) leave=true;
            }
        }return Double.parseDouble(answer.get(0)); 
    }
}
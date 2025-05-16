/*
 * Calculator using Java Swing
 * Coded by Azeez Bazara
*/
import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.logging.*;
public class swingCalc{
   // Variables
    private final JPanel headerPanel, middlePanel, bottomPanel;
    private final double tenToTen = Math.pow(10,10); // Used to round trig and other stuff due to inconcistency
    private final JFrame mainFrame;
    public static swingCalc logs;
    private JLabel equalsLabel, equationLabel;
    private JComboBox operationSelector;
    private double temp, previousAnswer;
    private int operationIndex, fails;
    private JTextField inputNumber;
    private boolean checkOperation;
    private String equation;
    private JButton enter;
    // Call Window
    public static void main(String[] args){
        logs = new swingCalc();
        logs.loginWindow();
    }
    // Window Constructor
    public swingCalc(){
        // Set Main Frame
        mainFrame = new JFrame("Azeez's SWING Tester");
        mainFrame.setSize(600,200);
        mainFrame.setLayout(new GridLayout(3, 1));
        // Add Panels
        mainFrame.add(headerPanel = new JPanel(new FlowLayout()));
        mainFrame.add(middlePanel = new JPanel(new FlowLayout()));
        mainFrame.add(bottomPanel = new JPanel(new FlowLayout()));
    }
    // Login Details
    private void loginWindow(){
        mainFrame.setSize(400,250);
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {System.exit(0);}});
        HashMap<String,String> users = new HashMap<>();
        // Add acccounts from users.txt
        try{
            BufferedReader buffReadUser = new BufferedReader(new FileReader("users.txt")),
            buffReadPass = new BufferedReader(new FileReader("pass.txt"));
            String line;
            while ((line = buffReadUser.readLine()) != null) users.put(line, buffReadPass.readLine());
            buffReadUser.close();
            buffReadPass.close();
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
            for(String i:users.keySet()){
                if(username.getText().equals(i) && !(new String(password.getPassword())).equals("")){
                    loggedIn.setText("Logged in.");
                    String[] options = {"Normal", "Conversion", "None"};
                    swingCalc calc = new swingCalc();
                    switch (JOptionPane.showOptionDialog(mainFrame, "Choose the Type of Calculator", "Calculator Select", 1, 1, null, options, options[0])) {
                        case 0 /*Normal*/ -> calc.normalCalculator();
                        case 1 /*Conversion*/ -> calc.conversionCalculator();
                        default /*End Program*/ -> System.exit(0);
                    }
                    fails = 0;
                    break;
                }
            }
            if(fails != 0){
                loggedIn.setText((3 - fails) + " attempts left.");
                if (fails++ >= 3){
                    if(JOptionPane.showOptionDialog(mainFrame, "You ran out of login attempts.\nPress OK to exit", "Login fail", 0, 1, null, new String[]{"OK","Cancel"}, "OK") == 0) System.exit(0);
                    fails = 0;
                }
            }
        });
        newAccount.addActionListener((ActionEvent e) -> {
            if(fails == 0) return;
            for(String i:users.keySet()){
                if(username.getText().equals(i)){
                    loggedIn.setText(username.getText() + " is already an option.");
                    return;
                }if(username.getText().equals("")||(new String(password.getPassword())).equals("")){
                    loggedIn.setText("Username and password can't be blank.");
                    return;
                }
            }
            // Write to users.txt/pass.txt
            try{
                BufferedWriter buffWriteUser = new BufferedWriter(new FileWriter("users.txt")),
                buffWritePass = new BufferedWriter(new FileWriter("pass.txt"));
                for (String item:users.keySet()) buffWriteUser.write(item + "\n");
                for (String item:users.values()) buffWritePass.write(item + "\n");
                buffWriteUser.write(username.getText());
                buffWritePass.write(new String(password.getPassword()));
                users.put(username.getText(),new String(password.getPassword()));
                buffWriteUser.close();
                buffWritePass.close();
                loggedIn.setText(username.getText() + " was added.");
                username.setText("");
                password.setText("");
            }catch (FileNotFoundException a) {System.out.println("File not found");} 
            catch (IOException t) {Logger.getLogger(swingCalc.class.getName()).log(Level.SEVERE, null, t);}
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
        checkOperation = true;
        equation = "";
        mainFrame.setTitle("Azeez's Calculator");
        // Makes login work when calculator is closed
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {logs.fails = 1;}
        });
        headerPanel.add(inputNumber = new JTextField(3));
        // ArrayLists to store operators
        ArrayList<JButton> operates = new ArrayList<>(),
        trig = new ArrayList<>(),
        logarithims = new ArrayList<>(),
        allOperates = new ArrayList<>();
        // Simple Operations : +, -, *, /, ^, √
        for(String i : new String[]{"+", "-", "*", "/", "^", "√"}) operates.add(new JButton(i));
        for(JButton i : operates) i.addActionListener((ActionEvent e) -> {mainOperation(i.getText());});
        // Trigonometry : sin(θ), cos(θ), tan(θ), sinh(θ), cosh(θ), tanh(θ)
        for(String i : new String[]{"sin(θ)", "cos(θ)", "tan(θ)", "sinh(θ)", "cosh(θ)", "tanh(θ)"}) trig.add(new JButton(i));
        for(JButton i : trig) i.addActionListener((ActionEvent e) -> {symbolOperation(i.getText());});
        // Other operations : log(x), ln(x)
        for(String i : new String[]{"log(x)","ln(x)"}) logarithims.add(new JButton(i));
        for(JButton i : logarithims) i.addActionListener((ActionEvent e) -> {symbolOperation(i.getText());});
        // Add to All Operator List
        allOperates.addAll(operates);
        allOperates.addAll(trig);
        allOperates.addAll(logarithims);
        // Add to middlePanel and set all but default operations invisible
        middlePanel.add(operationSelector = new JComboBox(new String[]{"Operations","Trigonometry","Other"}));
        for(JButton i : allOperates){
            middlePanel.add(i);
            if(operates.contains(i)) continue;
            i.setVisible(false);
        }
        // Add bottomPanel
        bottomPanel.add(equationLabel = new JLabel("Input a Number"));
        bottomPanel.add(enter = new JButton("="));
        bottomPanel.add(equalsLabel = new JLabel("Answer"));
        // Add Action Listeners
        enter.addActionListener((ActionEvent e) -> {mainOperation("");});
        operationSelector.addActionListener((ActionEvent e) -> {
            // Set all invisible
            for(JButton i : allOperates) i.setVisible(false);
            switch(operationSelector.getSelectedIndex()){ // Choose which is visible
                case 0 /*Operations*/ -> {for(JButton i : operates) i.setVisible(true);}
                case 1 /*Trigonometry*/ -> {for(JButton i : trig) i.setVisible(true);}
                case 2 /*Other*/ -> {for(JButton i : logarithims) i.setVisible(true);}
            }
            mainFrame.revalidate(); // Resets mainFrame
        });
        mainFrame.setVisible(true);
    }
    private void mainOperation(String operation){ // Input Operation into equation
        try {
            //Input previous answer in next calculation
            String input = inputNumber.getText();
            if(input.equals("ans") || input.equals("Ans")) input = "" + previousAnswer;
            // Adds calculation to equation
            if(checkOperation) equation += Double.valueOf(input) + " ";
            else checkOperation = true;
            equationLabel.setText(equation += operation + " ");
            // When equals is pressed, gives final answer
            if(operation.equals("")) {
                previousAnswer = calculate();
                equalsLabel.setText("" + previousAnswer);
                equation = "";
            }
            inputNumber.setText("");
        }catch (NumberFormatException e) {}
    }
    private void symbolOperation(String operation){ // Input Trig or other into equation
        try{if(checkOperation){
            //Input previous answer in next calculation
            String input = inputNumber.getText();
            if(input.equals("ans") || input.equals("Ans")) input = "" + previousAnswer;
            // Adds calculation to equation
            equationLabel.setText(equation += operation.substring(0,operation.length()-3) + " " + Double.valueOf(input) + " ");
            inputNumber.setText("");
            checkOperation = false;
        }}catch (NumberFormatException e) {} 
    }
    private double calculate(){ // Get Equation into Answer
        ArrayList<String> answer = new ArrayList<>(Arrays.asList(equation.split("\\s")));
        String[] symbolOperators = {"sin", "cos", "tan", "sinh", "cosh", "tanh" ,"log" ,"ln"};
        // Does Calculations based on BEDMAS
        for(String[] options : new String[][]{symbolOperators, {"√"}, {"^"}, {"*", "/"}, {"+", "-"}}){
            boolean leave = true;
            while(leave){
                leave = false;
                // Multiple For loops to check each option
                for(int i=0; i<answer.size(); i++) for(String option : options) if(answer.get(i).equals(option)){
                    double x=Double.parseDouble(answer.get(i + 1)), y = 0;
                    if(!Arrays.equals(options, symbolOperators)) y = Double.parseDouble(answer.get(i - 1));
                    switch(answer.get(i)){
                        // Normal Operations
                        case "+" -> y += x;
                        case "-" -> y -= x;
                        case "*" -> y *= x;
                        case "/" -> y /= x;
                        case "^" -> y = Math.pow(y, x);
                        case "√" -> y = Math.pow(x, 1.0/y);
                        // Trig and Log
                        case "sin" -> y = Math.round(Math.sin(Math.toRadians(x))*tenToTen)/tenToTen;
                        case "cos" -> y = Math.round(Math.cos(Math.toRadians(x))*tenToTen)/tenToTen;
                        case "tan" -> y = Math.round(Math.tan(Math.toRadians(x))*tenToTen)/tenToTen;
                        case "sinh" -> y = Math.sinh(x);
                        case "cosh" -> y = Math.cosh(x);
                        case "tanh" -> y = Math.tanh(x);
                        case "log" -> y = Math.log10(x);
                        case "ln" -> y = Math.log(x);
                    }
                    answer.set(i, "" + y);
                    answer.remove(i + 1);
                    if(!Arrays.equals(options, symbolOperators)) answer.remove(i - 1);
                    break;
                }
                for(String i : options) if(answer.contains(i)) leave = true;
            }
        }
        return Double.parseDouble(answer.get(0)); 
    }
    // Conversion Calculator
    private void conversionCalculator(){
        mainFrame.setTitle("Azeez's Conversion Calculator");
        // Makes login work when calculator is closed
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {logs.fails = 1;}
        });
        String[] normal = {"kg → lbs", "m → ft", "km → miles", "°C → °F", "tbsp → mL"}, flipped = {"kg ← lbs", "m ← ft", "km ← miles", "°C ← °F", "tbsp ← mL"};
        checkOperation = false;
        operationIndex = 0;
        // Add to Panel
        JButton reverser = new JButton("Reverse Operation");
        headerPanel.add(equalsLabel = new JLabel("Convert from"));
        headerPanel.add(operationSelector = new JComboBox(normal));
        headerPanel.add(reverser);
        middlePanel.add(inputNumber = new JTextField(3));
        middlePanel.add(enter = new JButton("ENTER"));
        bottomPanel.add(equationLabel = new JLabel("0.0"));
        // Action Listeners
        operationSelector.addActionListener((ActionEvent e) -> {operationIndex = operationSelector.getSelectedIndex();});
        enter.addActionListener((ActionEvent e) -> {
            try{temp = Double.parseDouble(inputNumber.getText());}
            catch(NumberFormatException a) {temp = 0;}
            if(checkOperation) switch(operationIndex){
                case 0 /*lbs to kg*/ -> temp /= 2.205;
                case 1 /*ft to meters*/ -> temp /= 3.281;
                case 2 /*miles to km*/ -> temp *= 1.609;
                case 3 /*Fahrenheit to Celsius*/ -> temp = (temp - 32) * (5.0/9.0);
                case 4 /*mL to tbsp*/ -> temp /= 14.787;
                default -> temp = 0;
            }
            else switch(operationIndex){
                case 0 /*kg to lbs*/ -> temp *= 2.205;
                case 1 /*meters to ft*/ -> temp *= 3.281;
                case 2 /*km to miles*/ -> temp /= 1.609;
                case 3 /*Celsius to Fahrenheit*/ -> temp = (temp * (9.0/5.0)) + 32;
                case 4 /*tbsp to mL*/ -> temp *= 14.787;
                default -> temp = 0;
            }
            equationLabel.setText("" + Math.round(temp*tenToTen)/tenToTen);
            mainFrame.revalidate(); // Resets mainFrame
        });
        reverser.addActionListener((ActionEvent e) -> {
            if(checkOperation = !checkOperation) operationSelector.setModel(new DefaultComboBoxModel(flipped));
            else operationSelector.setModel(new DefaultComboBoxModel(normal));
            operationSelector.setSelectedIndex(operationIndex);
            mainFrame.revalidate(); // Resets mainFrame
        });
        mainFrame.setVisible(true);
    }
}
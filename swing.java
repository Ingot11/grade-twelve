import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
public class swing{
   private JFrame mainFrame;
   private JLabel headerLabel;
   private JPanel controlPanel;
   //Calculator Variables
   private JTextField[] numberInput;
   private JSpinner calcOption;
   private JLabel equalsLabel;
   private final String[] options = {" + "," - "," * "," / "," ^ "," √ "};
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
      optionSelect=" + ";
      numberInput = new JTextField[]{new JTextField(3),new JTextField(3)};
      calcOption = new JSpinner(new SpinnerListModel(Arrays.asList(options).subList(0, 6)));
      calcOption.addChangeListener(new ChangeListener() {public void stateChanged(ChangeEvent e) {optionSelect=""+((JSpinner)e.getSource()).getValue();}});
      equalsLabel = new JLabel("NaN");
      // Equals Button
      JButton equalsButton = new JButton(" = ");
      equalsButton.setActionCommand("equals");
      equalsButton.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {equalsLabel.setText(calculate());}});
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
         switch(optionSelect){
            case " + " -> z = x + y;
            case " - " -> z = x - y ;
            case " * " -> z = x * y;
            case " / " -> z = x / y;
            case " ^ " -> z = Math.pow(x, y);
            case " √ " -> z = Math.pow(x, 1.0/y);
            default -> {return "NaN";}
         }
      }catch (Exception e){return "NaN";}
      return "" + z;
   }
}
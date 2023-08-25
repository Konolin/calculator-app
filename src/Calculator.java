import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator implements ActionListener, KeyListener {

    JFrame frame;
    JTextField textField;
    JButton[] numberButtons = new JButton[10];
    JButton[] functionButtons = new JButton[8];
    JButton addButton, subButton, mulButton, divButton, decButton, equButton, delButton, clrButton;
    JPanel panel;

    Font myFont = new Font("Futura", Font.BOLD, 30);

    float num1 = 0, num2 = 0, result = 0;
    char operator;

    Calculator() {
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 550);
        frame.setLayout(null);
        frame.addKeyListener(this);

        textField = new JTextField();
        textField.setBounds(50, 25, 300, 50);
        textField.setFont(myFont);
        textField.setEditable(false);

        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("*");
        divButton = new JButton("/");
        decButton = new JButton(".");
        equButton = new JButton("=");
        delButton = new JButton("Del");
        clrButton = new JButton("Clr");

        functionButtons[0] = addButton;
        functionButtons[1] = subButton;
        functionButtons[2] = mulButton;
        functionButtons[3] = divButton;
        functionButtons[4] = decButton;
        functionButtons[5] = equButton;
        functionButtons[6] = delButton;
        functionButtons[7] = clrButton;

        for (int i = 0; i < 8; i++) {
            functionButtons[i].addActionListener(this);
            functionButtons[i].setFont(myFont);
            functionButtons[i].setFocusable(false);
        }

        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setFont(myFont);
            numberButtons[i].setFocusable(false);
        }

        delButton.setBounds(50, 430, 145, 50);
        clrButton.setBounds(205, 430, 145, 50);

        panel = new JPanel();
        panel.setBounds(50, 100, 300, 300);
        panel.setLayout(new GridLayout(4, 4, 10, 10));

        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(addButton);
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(subButton);
        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(mulButton);
        panel.add(decButton);
        panel.add(numberButtons[0]);
        panel.add(equButton);
        panel.add(divButton);

        frame.add(panel);
        frame.add(delButton);
        frame.add(clrButton);
        frame.add(textField);
        frame.setFocusable(true);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Calculator calc = new Calculator();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 10; i++) {
            if (e.getSource() == numberButtons[i]) {
                textField.setText(textField.getText().concat(String.valueOf(i)));
            }
        }

        if (e.getSource() == decButton) {
            handleOperator('.');
        }

        if (e.getSource() == addButton) {
            handleOperator('+');
        }

        if (e.getSource() == subButton) {
            handleOperator('-');
        }

        if (e.getSource() == mulButton) {
            handleOperator('*');
        }

        if (e.getSource() == divButton) {
            handleOperator('/');
        }

        if (e.getSource() == equButton) {
            handleOperator('=');
        }

        if (e.getSource() == clrButton) {
            textField.setText("");
        }

        if (e.getSource() == delButton) {
            handleOperator('d');
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        char keyChar = e.getKeyChar();

        if (Character.isDigit(keyChar)) {
            textField.setText(textField.getText().concat(String.valueOf(keyChar)));
            return;
        }

        if (keyCode == KeyEvent.VK_ENTER || keyChar == '=') {
            handleOperator('=');
            return;
        }

        if (keyCode == KeyEvent.VK_BACK_SPACE) {
            handleOperator('d');
            return;
        }

        switch (keyChar) {
            case '+' -> handleOperator('+');
            case '-' -> handleOperator('-');
            case '*' -> handleOperator('*');
            case '/' -> handleOperator('/');
            case '.' -> handleOperator('.');
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void handleOperator(char input) {
        switch (input) {
            case '+' -> {
                num1 = Float.parseFloat(textField.getText());
                operator = '+';
                textField.setText("");
            }
            case '-' -> {
                String temp = textField.getText();
                if (temp.isEmpty()) {
                    textField.setText("-");
                } else {
                    num1 = Float.parseFloat(temp);
                    operator = '-';
                    textField.setText("");
                }
            }
            case '*' -> {
                num1 = Float.parseFloat(textField.getText());
                operator = '*';
                textField.setText("");
            }
            case '/' -> {
                num1 = Float.parseFloat(textField.getText());
                operator = '/';
                textField.setText("");
            }
            case '=' -> {
                num2 = Float.parseFloat(textField.getText());
                switch (operator) {
                    case '+' -> result = num1 + num2;
                    case '-' -> result = num1 - num2;
                    case '*' -> result = num1 * num2;
                    case '/' -> {
                        if (num2 == 0) {
                            textField.setText("Can't divide by zero");
                            return;
                        }
                        result = num1 / num2;
                    }
                }
                textField.setText(String.valueOf(result));
                num1 = result;
            }
            case '.' -> {
                if (textField.getText().isEmpty()) {
                    textField.setText(textField.getText().concat("0."));
                }
                if (!textField.getText().contains(".")) {
                    textField.setText(textField.getText().concat("."));
                }
            }
            case 'd' -> {
                String string = textField.getText();
                textField.setText("");

                if (!Character.isDigit(string.charAt(string.length() - 1)) && string.charAt(string.length() - 1) != 'E') {
                    return;
                }

                for (int i = 0; i < string.length() - 1; i++) {
                    textField.setText(textField.getText() + string.charAt(i));
                }
            }
        }
    }
}
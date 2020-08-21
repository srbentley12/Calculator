package calculator;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * Controller file for the calculator program. This file handles almost all the
 * functions of the calculator. It handles the interaction between the UI input,
 * the logic behind the functions, and the output. It also implements a custom
 * class called Operation.java which stores the operations and numbers and is
 * used in a linkedList.
 * @author Steve
 */
public class FXMLDocumentController implements Initializable {

    //Initiliizing objects and variables
    //if calculator is awaiting user input
    private boolean awaitingInput = true;
    
    //Output displayed on the calc screen
    @FXML private TextField screenText;
    
    //Linked list containing custom class Operation which stores input
    private LinkedList<Operation> operationList = new LinkedList();
    
    //First time input is taken the number is stored here
    private Double firstNumber;
    
    //Stores number for repeating operation with equals button
    private Double operatingNumber;
    
    //If first number of entry is expected
    private boolean isFirstNumber = true;
    
    //Stores last operator entered to be added to operations list
    private String nextOperator;
    
    //If last operator input was equals
    private boolean equalsPushedLast = false;

    /**
     *Handles the input when a number is button is pushed. Default output is "0"
     * while awaiting input. Checks for 0 or awaiting input status and sets the
     * screen text accordingly. If there is already a number or the calculator
     * is not awaiting input then the number pushed is appended to the screen 
     * output. Finally the awaiting input and equals pushed last flags are set
     * to false.
     * 
     * @param number String representing the number button that was pushed.
     */
    public void numberButtonPushed(String number) {
        if (screenText.getText().equals("0") || awaitingInput) {
            screenText.setText("");
        }
        screenText.setText(screenText.getText() + number);
        awaitingInput = false;
        equalsPushedLast = false;
    }

    //Methods for input of all the number buttons 0-9
    /**
     * Method for input of "0", calls numberButtonPushed with number of input.
     * @see #numberButtonPushed(java.lang.String)
     */
    public void zeroButtonPushed() {
        numberButtonPushed("0");
    }

    /**
     * Method for input of "1", calls numberButtonPushed with number of input.
     * @see #numberButtonPushed(java.lang.String)
     */
    public void oneButtonPushed() {
        numberButtonPushed("1");
    }

    /**
     * Method for input of "2", calls numberButtonPushed with number of input.
     * @see #numberButtonPushed(java.lang.String)
     */
    public void twoButtonPushed() {
        numberButtonPushed("2");
    }

    /**
     * Method for input of "3", calls numberButtonPushed with number of input.
     * @see #numberButtonPushed(java.lang.String)
     */
    public void threeButtonPushed() {
        numberButtonPushed("3");
    }

    /**
     * Method for input of "4", calls numberButtonPushed with number of input.
     * @see #numberButtonPushed(java.lang.String)
     */
    public void fourButtonPushed() {
        numberButtonPushed("4");
    }

    /**
     * Method for input of "5", calls numberButtonPushed with number of input.
     * @see #numberButtonPushed(java.lang.String)
     */
    public void fiveButtonPushed() {
        numberButtonPushed("5");
    }

    /**
     * Method for input of "6", calls numberButtonPushed with number of input.
     * @see #numberButtonPushed(java.lang.String)
     */
    public void sixButtonPushed() {
        numberButtonPushed("6");
    }

    /**
     * Method for input of "7", calls numberButtonPushed with number of input.
     * @see #numberButtonPushed(java.lang.String)
     */
    public void sevenButtonPushed() {
        numberButtonPushed("7");
    }

    /**
     * Method for input of "8", calls numberButtonPushed with number of input.
     * @see #numberButtonPushed(java.lang.String)
     */
    public void eightButtonPushed() {
        numberButtonPushed("8");
    }

    /**
     * Method for input of "9", calls numberButtonPushed with number of input.
     * @see #numberButtonPushed(java.lang.String)
     */
    public void nineButtonPushed() {
        numberButtonPushed("9");
    }

    /**
     * Method for input of ".", calls numberButtonPushed with decimal point.
     * Will exit method if there is already a decimal on screen.
     * @see #numberButtonPushed(java.lang.String)
     */
    public void pointButtonPushed() {
        if (screenText.getText().contains(".")) {
            return;
        }
        numberButtonPushed(".");
    }

    /**
     * Sets the screen text to the opposite (negative) value. Then formats the
     * screen to not contain a .0 at the end.
     * @see #formatScreenText() 
     */
    public void oppositeButtonPushed() {
        screenText.setText(String.valueOf(getScreenText() * -1));
        formatScreenText();
    }

    /**
     * Clears all input, operation list, resets flags, and sets screen to "0".
     */
    public void clearButtonPushed() {
        screenText.setText("0");
        operationList.clear();
        firstNumber = null;
        nextOperator = null;
        isFirstNumber = true;
        operatingNumber = null;
    }

    /**
     * Clears the current entry that is on the screen but retains all former
     * input.
     */
    public void clearEntryButtonPushed() {
        screenText.setText("0");
        awaitingInput = true;
    }

    //Delete button function
    /**
     * Deletes the last number on the screen. Will exit method if the calculator
     * is awaiting input. Checks for last number on screen to format screen to 
     * just display "0" if this is the case. Or else it just drops the last
     * number.
     */
    public void deleteButtonPushed() {
        //Check for zero value after delete.
        if (awaitingInput) {
            return;
        }
        if (screenText.getText().length() == 1) {
            screenText.setText("0");
            awaitingInput = true;
            return;
        }
        //else drop the last number
        screenText.setText(screenText.getText().substring(0, screenText.getText().length() - 1));
        formatScreenText();
    }

    /**
     * The operatorButtonPushed is the handling method for +, -, /, *, and =.
     * The working operations are passed to the operation class and an operation
     * object is made and added to the operationList. If there is an operation
     * operation in the list then the output screen is updated and displayed the
     * current running total of the operations.
     *
     * @param operator
     *
     */
    public void operatorButtonPushed(String operator) {
        //Checks if input is the first number to be operated on.
        if (isFirstNumber) {
            if (operator.equals("e")) {
                return;
            }
            firstNumber = getScreenText();
            //Queues operator to add to list
            nextOperator = operator;
            
        //If it's not the first number and equals was pushed
        } else if ("e".equals(operator)) {
            //if its the first time equals was pushed store the number input in
            //case equals is pushed multiple times in a row.
            if (operatingNumber == null) {
                operatingNumber = getScreenText();
            }
            operationList.add(new Operation(nextOperator, operatingNumber));
        
        //If input is not equals but instead an operator
        } else if (!"e".equals(operator)) {
            //If equals was pushed last treat the input as a whole new operation
            //set but using the running total as the first number input.
            if (equalsPushedLast) {
                firstNumber = getScreenText();
                operationList.clear();
                nextOperator = operator;
                equalsPushedLast = false;
                operatingNumber = null;
                
            //Default action for running input of operations
            } else {
                operatingNumber = null;
                operationList.add(new Operation(nextOperator, getScreenText()));
                nextOperator = operator;
            }
        }
        //Displays running total
        screenText.setText(String.valueOf(getTotal()));
        formatScreenText();
        isFirstNumber = false;
        awaitingInput = true;
    }

    /**
     * Adds an addition operation to the list and updates the running total.
     * 
     * @see #operatorButtonPushed(java.lang.String) 
     */
    public void additionButtonPushed() {
        if (awaitingInput && !equalsPushedLast) {
            return;
        }
        operatorButtonPushed("a");
    }

    /**
     * Adds a subtraction operation to the list and updates the running total.
     * 
     * @see #operatorButtonPushed(java.lang.String) 
     */
    public void subtractionButtonPushed() {
        if (awaitingInput && !equalsPushedLast) {
            return;
        }
        operatorButtonPushed("s");
    }

    /**
     * Adds a multiplication operation to the list and updates the running total.
     * 
     * @see #operatorButtonPushed(java.lang.String) 
     */
    public void multiplicationButtonPushed() {
        if (awaitingInput && !equalsPushedLast) {
            return;
        }
        operatorButtonPushed("m");
    }

    /**
     * Adds a division operation to the list and updates the running total.
     * 
     * @see #operatorButtonPushed(java.lang.String) 
     */
    public void divisionButtonPushed() {
        if (awaitingInput && !equalsPushedLast) {
            return;
        }
        operatorButtonPushed("d");
    }

    /**
     * Updates the running total and prepares for new operations.
     * 
     * @see #operatorButtonPushed(java.lang.String) 
     */
    public void equalButtonPushed() {
        operatorButtonPushed("e");
        equalsPushedLast = true;
    }

    /**
     * Iterates through the operations list and keeps track of the running
     * total. Utilizes the Operation class to perform the operations.
     * 
     * @return runningTotal is returned as a result of all the stored
     * operations.
     * @see Operation.java
     */
    public double getTotal() {
        double runningTotal = 0.0;
        runningTotal += firstNumber;
        for (Operation item : operationList) {
            runningTotal = item.operate(runningTotal);
        }
        awaitingInput = true;
        return runningTotal;
    }

    /**
     * Returns the text on the screen.
     * 
     * @return Screen Text, or output
     */
    public double getScreenText() {
        return Double.parseDouble(screenText.getText());
    }

    /**
     * Formats the screen text, drops ".0" from end of double.
     */
    public void formatScreenText() {
        String output;
        output = screenText.getText();
        if (output.substring(output.length() - 2).equals(".0")) {
            output = output.substring(0, output.length() - 2);
        }
        screenText.setText(output);
    }
    
    /**
     * Handles keyboard input of numbers and operators.
     * 
     * @param event Key code of key pressed. 
     */

    @FXML
    void keyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case DIGIT0:
            case NUMPAD0:
                zeroButtonPushed();
                break;
            case DIGIT1:
            case NUMPAD1:
                oneButtonPushed();
                break;
            case DIGIT2:
            case NUMPAD2:
                twoButtonPushed();
                break;
            case DIGIT3:
            case NUMPAD3:
                threeButtonPushed();
                break;
            case DIGIT4:
            case NUMPAD4:
                fourButtonPushed();
                break;
            case DIGIT5:
            case NUMPAD5:
                fiveButtonPushed();
                break;
            case DIGIT6:
            case NUMPAD6:
                sixButtonPushed();
                break;
            case DIGIT7:
            case NUMPAD7:
                sevenButtonPushed();
                break;
            case DIGIT8:
            case NUMPAD8:
                eightButtonPushed();
                break;
            case DIGIT9:
            case NUMPAD9:
                nineButtonPushed();
                break;
            case ENTER:
                equalButtonPushed();
                break;
            case PLUS:
            case ADD:
                additionButtonPushed();
                break;
            case MINUS:
            case SUBTRACT:
                subtractionButtonPushed();
                break;
            case ASTERISK:
            case MULTIPLY:
                multiplicationButtonPushed();
                break;
            case SLASH:
            case DIVIDE:
                divisionButtonPushed();
                break;
            case BACK_SPACE:
                deleteButtonPushed();
                break;
            default:
                break;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

}

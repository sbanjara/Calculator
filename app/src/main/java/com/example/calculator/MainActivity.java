package com.example.calculator;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String s;
    private String sHold;
    private String previousOperator;
    private String currentOperator;
    private String errorMessage;

    private int index;
    private int count;
    private int sqrtCount;
    private int restartCount;

    private BigDecimal lhs;
    private BigDecimal rhs;
    private BigDecimal intermediate;

    private TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        s = "";
        sHold = "";
        previousOperator = null;
        currentOperator = null;
        errorMessage = null;

        index = 0;
        count = 0;
        sqrtCount = 0;
        restartCount = 0;

        lhs = null;
        rhs = null;
        intermediate = null;

        output = (TextView) findViewById(R.id.outputView);
        output.setText(String.valueOf(0));

    }

    public void assignOperator(String buttonText) {

        if (s.length() >= 1) {

            if (count == 1) {
                previousOperator = buttonText;
            }
            else if (count > 1) {
                currentOperator = buttonText;
            }

        }
        else {
            count = 0;
        }

    }

    public void addOperator(String sign) {
        if(count >= 1) {
            s += sign;
        }
    }

    public void calculate() {

        switch (previousOperator) {

            case "+":
                intermediate = lhs.add(rhs);
                break;

            case "-":
                intermediate = lhs.subtract(rhs);
                break;

            case "÷":
                if( !(rhs.toString()).equals("0") ) {
                    intermediate = lhs.divide(rhs, 3, RoundingMode.CEILING);
                }
                else {
                    errorMessage = "ERROR: Cannot divide by zero";
                }
                break;


            case "×":
                intermediate = lhs.multiply(rhs);
                break;

            case "%":
                if( !(rhs.toString()).equals("0") ) {
                    intermediate = lhs.remainder(rhs);
                }
                else {
                    errorMessage = "ERROR: Cannot divide by zero";
                }
                break;

        }

    }

    public String negate(String number) {

        BigDecimal minusOne = new BigDecimal("-1");
        BigDecimal num = new BigDecimal(number);
        BigDecimal result = minusOne.multiply(num);
        return result.toString();

    }

    public String takeSqrt(String number) {

        double num = Double.parseDouble(number);
        Double sqrt = Math.pow(num, 0.5);
        return String.valueOf(sqrt);

    }

    public void reset() {

        previousOperator = null;
        currentOperator = null;
        rhs = null;
        lhs = null;
        intermediate = null;
        count = 0;

    }

    public void clearOldString() {

        if ( (restartCount == 1) && (count == 0) ) {
            s = "";
            restartCount = 0;
        }

    }

    public void clearSqrtString() {

        if ( (sqrtCount == 1) && (count == 0) ) {
            s = "";
            sqrtCount = 0;
        }
    }

    public void addDecimal(String str) {

        if ( s.length() == 0 ) {
            s += "0.";
        }

        else {

            if ( previousOperator == null ) {

                if( !s.contains(".") )
                    s += ".";

            }

            else if ( !(previousOperator == null) ) {

                int lengthRight = s.length();
                String right = s.substring(s.indexOf(previousOperator), lengthRight);
                if( !right.contains(".") )
                    s += ".";

            }

        }

    }

    public void buttonClick(View v) {

        String buttonText = ((Button) v).getText().toString();

        switch (buttonText) {

            case "√":
                count++;
                assignOperator("√");
                break;

            case "÷":
                count++;
                assignOperator("÷");
                addOperator("÷");
                break;

            case "%":
                count++;
                assignOperator("%");
                addOperator("%");
                break;

            case "×":
                count++;
                assignOperator("×");
                addOperator("×");
                break;

            case "-":
                count++;
                assignOperator("-");
                addOperator("-");
                break;

            case "\u00B1":
                count++;
                assignOperator("\u00B1");
                break;

            case "C":
                s = "";
                sHold = "0";
                reset();
                break;

            case "+":
                count++;
                assignOperator("+");
                addOperator("+");
                break;

            case "=":
                count++;
                assignOperator("=");
                count = 0;
                break;

            case ".":
                addDecimal(s);
                break;

            default:
                clearOldString();
                clearSqrtString();
                s += buttonText;
                break;

        }

        if ( (s.length() >= 1) && !(previousOperator == null) ) {

            index = s.length();

            switch (previousOperator) {

                case "=":
                    break;

                case "√":
                    String left = s.substring(0, index);
                    s = takeSqrt(left);
                    sqrtCount = 1;
                    previousOperator = "";
                    count = 0;
                    break;

                case "\u00B1":
                    String num = s.substring(0, index);
                    s = negate(num);
                    previousOperator = "";
                    count = 0;
                    break;

                case "-":
                    if(s.charAt(0) == '-') {
                        index = s.indexOf(previousOperator, 1);
                    }
                    else {
                        index = s.indexOf(previousOperator);
                    }
                    lhs = new BigDecimal(s.substring(0, index));
                    break;


                default:
                    index = s.indexOf(previousOperator);
                    lhs = new BigDecimal(s.substring(0, index));
                    break;

            }

        }

        if ( (s.length() >= 2) && !(currentOperator == null) ) {

            index = s.indexOf(previousOperator);
            int length = s.length();
            String specialCase = lhs.toString();
            int specialCaseLength = specialCase.length() + 2;


            if ( ( currentOperator.equals("\u00B1") ) && ( length == (specialCase.length()+1) ) ) {

                String num = lhs.toString();
                String opposite = negate(num);
                rhs = new BigDecimal(opposite);
                s = s + rhs;
                currentOperator = null;

            }

            else if ( ( currentOperator.equals("√") ) && ( length == (specialCase.length()+1) ) ) {

                String num = lhs.toString();
                String sqrt = takeSqrt(num);
                rhs = new BigDecimal(sqrt);
                s = s + rhs;
                currentOperator = null;

            }

            else if ( currentOperator.equals("\u00B1") ) {

                String num = s.substring(index + 1, length);
                String left = s.substring(0, index + 1);
                String replacement = negate(num);
                s = left + replacement;
                currentOperator = null;

            }

            else if ( currentOperator.equals("√") ) {

                String num = s.substring(index + 1, length);
                String left = s.substring(0, index + 1);
                String replacement = takeSqrt(num);
                s = left + replacement;
                currentOperator = null;
            }

            else if ( ( !(currentOperator.equals("=")) ) && ( length == specialCaseLength ) ) {
                rhs = lhs;
            }

            else if ( ( currentOperator.equals("=") ) && ( length == (specialCase.length() + 1) ) ) {
                rhs = lhs;
            }

            else {

                switch (currentOperator) {

                    case "=":
                        if(s.charAt(0) == '-') {
                            index = s.indexOf(previousOperator, 1);
                        }
                        else {
                            index = s.indexOf(previousOperator);
                        }
                        rhs = new BigDecimal(s.substring(index + 1, length));
                        break;

                    default:
                        rhs = new BigDecimal(s.substring(index + 1, length - 1));
                        break;

                }

            }

        }

        if( sHold.isEmpty() ) {
            output.setText(s);
        }
        else {
            output.setText(sHold);
            sHold = "";
        }

        if ( ( !(lhs == null) )  && ( !(rhs == null) ) && ( !(previousOperator == null) ) && ( !(currentOperator == null) ) ) {

            calculate();

            if( !(errorMessage == null) ) {

                s = errorMessage;
                errorMessage = null;
                output.setText(s);
                s = "";
                reset();

            }

            else {

                switch (currentOperator) {

                    case "=":
                        s = intermediate.toString();
                        output.setText(s);
                        restartCount = 1;
                        reset();
                        break;

                    default:
                        lhs = intermediate;
                        rhs = new BigDecimal("0");
                        s = intermediate + currentOperator;
                        output.setText(s);
                        previousOperator = currentOperator;
                        currentOperator = null;
                        break;

                }
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

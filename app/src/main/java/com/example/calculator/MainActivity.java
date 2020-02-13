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
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	private String s;
	private int index;
	private int indexTwo;
	private BigDecimal lhs;
	private BigDecimal rhs;
	private BigDecimal intermediate;
	private TextView output;
	private String operator;
	
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
		index = 0;
		indexTwo = 0;
		operator = "";
		lhs = new BigDecimal("0");
		rhs = new BigDecimal("0");
		intermediate = new BigDecimal("0");
        output = (TextView) findViewById(R.id.outputView);
        output.setText(String.valueOf(0));

    }

    public void buttonClick(View v) {

        String buttonText = ((Button) v).getText().toString();

        switch(buttonText) {

            case "7":
                s += "7";
                break;

            case "8":
                s += "8";
                break;

            case "9":
                s += "9";
                break;

            case "√":
                s += "√";
				index = s.indexOf("√");
				lhs = new BigDecimal(s.substring(0, index));
                break;
				
			case "4":
                s += "4";
                break;

            case "5":
                s += "5";
                break;

            case "6":
                s += "6";
                break;

            case "÷":
                s += "÷";
				operator += "÷";
                break;
				
			case "%":
				s = "%";
                operator += "%";
				break;
				
			case "1":
                s += "1";
                break;

            case "2":
                s += "2";
                break;

            case "3":
                s += "3";
                break;

            case "×":
                s += "×";
                operator += "×";
                break;
				
			case "-":
				s += "-";
                operator += "-";
				break;
				
			case "\u00B1":
                s += "\u00B1";
                break;
				
			case "0":
				s += "0";
				break;

            case ".":
                s += ".";
                break;

            case "+":
                s += "+";
                operator += "+";
                break;

            case "=":
                operator += "=";
                break;

            default:
                s = "";
                break;
				
        }

        if(operator.length() == 1) {
            lhs = new BigDecimal( s.substring(0, s.length() - 1) );
        }

        if(operator.length() > 2) {

            indexTwo = s.length();
            String ran = s.substring( index + 1, indexTwo );
            rhs = new BigDecimal(ran);

            switch(operator.substring(0,1)) {

                case "+":
                    intermediate = lhs.add(rhs);
                    break;

                case "-":
                    intermediate = lhs.subtract(rhs);
                    break;

                case "÷":  //Division
                    intermediate = lhs.divide(rhs);
                    break;

                case "×": //Multiplication
                    intermediate = lhs.multiply(rhs);
                    break;

                case "=":
                    s = lhs.toString();
                    break;

            }

            if(operator.substring(1,2).equals("=")) {
                s = intermediate.toString();
                operator = "";
            }
            else {
                lhs = intermediate;
            }

        }

        output.setText(s);

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

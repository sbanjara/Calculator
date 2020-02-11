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

public class MainActivity extends AppCompatActivity {

	private String s;
	private int index;
	private BigDecimal lhs;
	private BigDecimal rhs;
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
		index = 0;
		lhs = new BigDecimal("0");
		rhs = new BigDecimal("0");
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

            case "\u00F7":
                s += "\u00F7";
				index = s.indexOf("\u00F7");
				lhs = new BigDecimal(s.substring(0, index));
                break;
				
			case "%":
				s = "%";
				index = s.indexOf("%");
				lhs = new BigDecimal(s.substring(0, index));
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

            case "\u00D7":
                s += "\u00D7";
				index = s.indexOf("\u00D7");
				lhs = new BigDecimal(s.substring(0, index));
                break;
				
			case "-":
				s = "-";
				index = s.indexOf("-");
				lhs = new BigDecimal(s.substring(0, index));
				break;
				
			case "\u00B1":
                s += "\u00B1";
                break;
				
			case "0":
				s = "0";
				break;

            case ".":
                s += ".";
                break;

            case "+":
                s += "+";
				index = s.indexOf("+");
				lhs = new BigDecimal(s.substring(0, index));
                break;

            case "=":
                s += "=";
				index = s.indexOf("+");
				lhs = new BigDecimal(s.substring(0, index));
                break;

            default:
                s = "0";
                break;
				
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

package com.example.lengthunitconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText inputValue = findViewById(R.id.inputValue);
        Spinner inputUnit = findViewById(R.id.inputUnit);
        Spinner outputUnit = findViewById(R.id.outputUnit);
        Button convertButton = findViewById(R.id.convertButton);
        TextView result = findViewById(R.id.result);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputUnit.setAdapter(adapter);
        outputUnit.setAdapter(adapter);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double input = Double.parseDouble(inputValue.getText().toString());
                    String fromUnit = inputUnit.getSelectedItem().toString();
                    String toUnit = outputUnit.getSelectedItem().toString();

                    double output = convertUnits(input, fromUnit, toUnit);
                    result.setText(String.valueOf(output));
                } catch (NumberFormatException e) {
                    inputValue.setError("Please enter a valid number");
                }
            }
        });
    }

    private double convertUnits(double input, String fromUnit, String toUnit) {
        double baseValue = 0;

        // Convert from input unit to meters
        switch (fromUnit) {
            case "Metre":
                baseValue = input;
                break;
            case "Millimetre":
                baseValue = input / 1000;
                break;
            case "Mile":
                baseValue = input * 1609.34;
                break;
            case "Foot":
                baseValue = input * 0.3048;
                break;
        }

        // Convert from meters to output unit
        switch (toUnit) {
            case "Metre":
                return baseValue;
            case "Millimetre":
                return baseValue * 1000;
            case "Mile":
                return baseValue / 1609.34;
            case "Foot":
                return baseValue / 0.3048;
            default:
                return baseValue;
        }
    }
}

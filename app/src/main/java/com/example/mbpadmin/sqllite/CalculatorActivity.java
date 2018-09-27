package com.example.mbpadmin.sqllite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CalculatorActivity extends AppCompatActivity {
    EditText et1,et2;
    TextView hsl;
    Button plus,minus,divide,multiple,reset;
    float a1,a2,hasil=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator);

        et1=(EditText) findViewById(R.id.angka1);
        et2=(EditText) findViewById(R.id.angka2);

        hsl=(TextView) findViewById(R.id.hasil);

        plus=(Button)findViewById(R.id.plus);
        divide=(Button)findViewById(R.id.divide);
        minus=(Button)findViewById(R.id.minus);
        multiple=(Button)findViewById(R.id.multiple);

        reset=(Button)findViewById(R.id.reset);

        View.OnClickListener operasi=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et1.getText().toString().isEmpty()) {
                    Toast.makeText(CalculatorActivity.this, "Angka 1 Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                } else if (et2.getText().toString().isEmpty()) {
                    Toast.makeText(CalculatorActivity.this, "Angka 2 Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        a1 = Float.parseFloat(et1.getText().toString());
                    } catch (Exception e) {
                        Toast.makeText(CalculatorActivity.this, "Angka 1 Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                    }
                    try {
                        a2 = Float.parseFloat(et2.getText().toString());
                    } catch (Exception e) {
                        Toast.makeText(CalculatorActivity.this, "Angka 2 Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                    }
                    if(v==plus)
                    {
                        hasil=a1+a2;
                    }
                    else if(v==minus)
                    {
                        hasil=a1-a2;
                    }
                    else if(v==multiple)
                    {
                        hasil=a1*a2;
                    }
                    else if(v==divide)
                    {
                        if(a2!=0)
                        {
                            hasil=a1/a2;
                        }
                        else
                        {
                            Toast.makeText(CalculatorActivity.this, "Angka 2 Tidak Boleh 0", Toast.LENGTH_SHORT).show();
                        }
                    }
                    hsl.setText(String.valueOf(hasil));
                }
            }
        };
        plus.setOnClickListener(operasi);
        minus.setOnClickListener(operasi);
        divide.setOnClickListener(operasi);
        multiple.setOnClickListener(operasi);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent refresh = new Intent(CalculatorActivity.this, CalculatorActivity.class);
                startActivity(refresh);
                CalculatorActivity.this.finish();
            }
        });
    }
}

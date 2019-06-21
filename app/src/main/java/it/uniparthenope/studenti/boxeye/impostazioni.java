package it.uniparthenope.studenti.boxeye;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class impostazioni extends AppCompatActivity {
    EditText numero;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impostazioni);
        MainActivity.numeroditelefono="303200";

        numero = findViewById(R.id.numero);

        findViewById(R.id.salva).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences(MainActivity.numerosalv, MODE_PRIVATE).edit();
                editor.putString("Numero", numero.getText().toString());
                editor.apply();
                numero.setText("");
            }
        });
    }
}

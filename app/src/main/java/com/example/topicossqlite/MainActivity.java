package com.example.topicossqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText etID, etNombre, etApellido, etEdad;
    private Button btnBuscar, btnActualizar, btnAgregar, btnEliminar;
    private TextView tvMensaje;
    private ListView lvDatos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etID = (EditText) findViewById(R.id.etID);
        etNombre = (EditText) findViewById(R.id.etNombre);
        etApellido = (EditText) findViewById(R.id.etApellido);
        etEdad = (EditText) findViewById(R.id.etEdad);

        btnBuscar = (Button) = findViewById(R.id.btnBuscar);
        btnActualizar = (Button) = findViewById(R.id.btnActualizar);
        btnAgregar = (Button) = findViewById(R.id.btnAgregar);
        btnEliminar = (Button) = findViewById(R.id.btnEliminar);

        tvMensaje = (TextView) = findViewById(R.id.tvMensaje);
        lvDatos = (ListView) = findViewById(R.id.lvDatos);
    }
}

package com.example.topicossqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DAOHelper d;
    private EditText etID, etNombre, etApellido, etEdad;
    private Button btnBuscar, btnActualizar, btnAgregar, btnEliminar;
    private TextView tvMensaje;
    private ListView lvDatos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        d = new DAOHelper(this);

        etID = (EditText) findViewById(R.id.etID);
        etNombre = (EditText) findViewById(R.id.etNombre);
        etApellido = (EditText) findViewById(R.id.etApellido);
        etEdad = (EditText) findViewById(R.id.etEdad);

        btnBuscar = (Button)  findViewById(R.id.btnBuscar);
        btnActualizar = (Button)  findViewById(R.id.btnActualizar);
        btnAgregar = (Button)  findViewById(R.id.btnAgregar);
        btnEliminar = (Button)  findViewById(R.id.btnEliminar);

        tvMensaje = (TextView)  findViewById(R.id.tvMensaje);
        lvDatos = (ListView)  findViewById(R.id.lvDatos);

        buscarPersona();
        estadoArranque();
    }

    public void estadoArranque(){
        btnAgregar.setEnabled(false);
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
        tvMensaje.setVisibility(View.INVISIBLE);
        lvDatos.setVisibility(View.INVISIBLE);
    }

    public void buscarPersona(){
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etID.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this,"Proporsione un numero de ID", Toast.LENGTH_SHORT).show();
                }else{
                    int ID = Integer.parseInt(etID.getText().toString());
                    Persona p = d.buscarPersona(ID);
                    if (p == null){
                        Toast.makeText(MainActivity.this,"No se encuentran registros con ese ID. Puede almacenarlo", Toast.LENGTH_SHORT).show();
                        btnAgregar.setEnabled(true);
                        btnActualizar.setEnabled(false);
                        btnEliminar.setEnabled(false);
                        etNombre.requestFocus();
                    }else{
                        etNombre.setText(p.getNombre());
                        etApellido.setText(p.getApellido());
                        etEdad.setText(Integer.toString(p.getEdad()));
                        btnAgregar.setEnabled(false);
                        btnActualizar.setEnabled(true);
                        btnEliminar.setEnabled(true);
                    }
                }

            }
        });
    }
}

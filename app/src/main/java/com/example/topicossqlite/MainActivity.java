package com.example.topicossqlite;

import androidx.appcompat.app.AlertDialog;
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
        agregarPersona();
        estadoArranque();
    }

    public void estadoArranque(){
        btnAgregar.setEnabled(false);
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
        tvMensaje.setVisibility(View.INVISIBLE);
        lvDatos.setVisibility(View.INVISIBLE);
        Toast.makeText(MainActivity.this,"Aplicacion realizada para la materia: \nTopicos aplicados a la Telematica \n\n\t Bienvenido!", Toast.LENGTH_LONG).show();
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
                   // Toast.makeText(MainActivity.this,"ID: "+ID, Toast.LENGTH_SHORT).show();
                    if (p == null){
                        Toast.makeText(MainActivity.this,"No se encuentran registros para el ID: " +ID +"\nPuede almacenarlo", Toast.LENGTH_SHORT).show();
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

    public void agregarPersona(){
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etID.getText().toString().isEmpty() ||
                        etNombre.getText().toString().isEmpty() ||
                        etEdad.getText().toString().isEmpty() ||
                        etApellido.getText().toString().isEmpty() ){
                    Toast.makeText(MainActivity.this,"Datos faltantes, Por favor rellene todos los campos",Toast.LENGTH_LONG).show();
                }else{
                    Persona p = new Persona();
                    p.setID(Integer.parseInt(etID.getText().toString()));
                    p.setNombre(etNombre.getText().toString());
                    p.setApellido(etApellido.getText().toString());
                    p.setEdad(Integer.parseInt(etEdad.getText().toString()));
                    long res = d.agregarPersona(p);
                    if(res != -1){
                        StringBuffer buffer = new StringBuffer();
                        buffer.append("ID:\t\t\t"+p.getID()+"\n");
                        buffer.append("Nombre:\t"+p.getNombre()+"\n");
                        buffer.append("Apellido:\t"+p.getApellido()+"\n");
                        buffer.append("Edad:\t\t\t"+p.getEdad());
                        display("Se ha agregado el registro correctamente", buffer.toString());
//                        Toast.makeText(MainActivity.this,"Los datos han sido agregados correctamente",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(MainActivity.this,"El ID ya existe",Toast.LENGTH_LONG).show();
                        etID.setText("");
                        etID.requestFocus();
                    }
                }

            }
        });

    }

    public void display (String titulo, String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(titulo);
        builder.setMessage(mensaje);
        builder.setIcon(R.drawable.updatebutton);
        builder.show();

    }
}

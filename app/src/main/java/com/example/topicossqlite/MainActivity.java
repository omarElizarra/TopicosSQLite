package com.example.topicossqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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

        estadoArranque();
        buscarPersona();
        agregarPersona();
        eliminarPersona();
        modificarPersona();
    }

    public void limpiarCampos(){
        etID.setText("");
        etEdad.setText("");
        etNombre.setText("");
        etApellido.setText("");
        btnAgregar.setEnabled(false);
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }

    public void estadoArranque(){
        limpiarCampos();
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
                        buffer.append("ID:      "+p.getID()+"\n");
                        buffer.append("Nombre:  "+p.getNombre()+"\n");
                        buffer.append("Apellido:"+p.getApellido()+"\n");
                        buffer.append("Edad:    "+p.getEdad());
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
        listarDatos();
        limpiarCampos();
    }

    public void eliminarPersona(){
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etID.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this,"No ID ", Toast.LENGTH_LONG).show();
                }else{

                    int ID = Integer.parseInt(etID.getText().toString());
                    int res = d.eliminarPersona(ID);

                    //reciclado
                    Persona p = new Persona();
                    p.setID(Integer.parseInt(etID.getText().toString()));
                    p.setNombre(etNombre.getText().toString());
                    p.setApellido(etApellido.getText().toString());
                    p.setEdad(Integer.parseInt(etEdad.getText().toString()));
                    //int res = d.modificarPersona(p);


                    if(res == 1){
                        StringBuffer buffer = new StringBuffer();
                        buffer.append("ID:\t\t\t"+p.getID()+"\n");
                        buffer.append("Nombre:\t"+p.getNombre()+"\n");
                        buffer.append("Apellido:\t"+p.getApellido()+"\n");
                        buffer.append("Edad:\t\t\t"+p.getEdad());
                        display("El registro ha sido borrado exitosamente", buffer.toString());
                        //Toast.makeText(MainActivity.this, "El Registro fue eliminado!", Toast.LENGTH_SHORT).show();
                        //limpiarCampos();

                    }else{
                        Toast.makeText(MainActivity.this, "No se encontro un gesitro con el ID: " +ID, Toast.LENGTH_SHORT).show();
                        etID.setText("");
                        etID.requestFocus();
                    }
                }

            }
        });
    }

    public void modificarPersona(){
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etID.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "El ID esta vacio", Toast.LENGTH_SHORT).show();
                }else{
                    if(etNombre.getText().toString().isEmpty()
                            || etApellido.getText().toString().isEmpty()
                            || etEdad.getText().toString().isEmpty()){
                        Toast.makeText(MainActivity.this, "Hay campos sin valor", Toast.LENGTH_SHORT).show();
                        //mover el cursor al dato vacio.
                    }else{
                        Persona p = new Persona();
                        p.setID(Integer.parseInt(etID.getText().toString()));
                        p.setNombre(etNombre.getText().toString());
                        p.setApellido(etApellido.getText().toString());
                        p.setEdad(Integer.parseInt(etEdad.getText().toString()));
                        int res = d.modificarPersona(p);
                        if(res == 1){
                            StringBuffer buffer = new StringBuffer();
                            buffer.append("ID: " +p.getID() +"\n");
                            buffer.append("Nombre: " +p.getNombre() +"\n");
                            buffer.append("Apellido: " +p.getApellido() +"\n");
                            buffer.append("Edad: "+p.getEdad());
                            display("Se han actualizado los datos para el ID " +p.getID(),buffer.toString());
                        }else{
                            Toast.makeText(MainActivity.this, "No se encuntras coincidencias para el ID: "+p.getID() , Toast.LENGTH_SHORT).show();
                            etID.setText("");
                            etID.requestFocus();
                        }
                    }
                }
                
            }
        });
    }

    public void listarDatos(){
        tvMensaje.setVisibility(View.VISIBLE);
        lvDatos.setVisibility(View.VISIBLE);
        System.out.println("Mensaje");

        ArrayList<Persona> lista = d.obtenerPersonal();

        if(!lista.isEmpty()){

            ArrayAdapter<Persona> adapter = new ArrayAdapter<Persona>(this,android.R.layout.simple_list_item_1,lista);
            lvDatos.setAdapter(adapter);

        }else{
            // por implementar
        }



    }
}

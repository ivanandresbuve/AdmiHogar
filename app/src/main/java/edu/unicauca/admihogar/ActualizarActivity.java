package edu.unicauca.admihogar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActualizarActivity extends AppCompatActivity {
    /*22)creamos objetos de a edicion de texto para nuestros 3
    textos de adicion, todo esto lo vamos a hacer con el fin de  cuando el
     usuario de clic a el articulo o producto se le muestre la informacion ecxata del producto*/
    EditText entrada_nombre, entrada_categoria, entrada_precio;
    //agregamos un boton
    //27 agregamos el boto eliminar
    Button boton_actualizar, boton_eliminar;
    //23)creamos 4 cadenas diferentes, para el nombre, categoria y el precio
    // almacenamos la informacionde los datos en la intencion
    String id, nombre, categoria, precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar);
        //vamos a encontrar la idea de nuestros elementos dentro de onCreate
        entrada_nombre = findViewById(R.id.entrada_nombre2);
        entrada_categoria = findViewById(R.id.entrada_categoria2);
        entrada_precio = findViewById(R.id.entrada_precio2);
        boton_actualizar = findViewById(R.id.boton_actualizar);
        //27 tambien vamos encontramos la idea de nuestro boton, y justo abajo
        //ponemos el onClincklistener
        boton_eliminar = findViewById(R.id.boton_eliminar);

        //23)llamamos a nuestro metodo
        // con esto ya podemos visualizar nuestro producto al hacer clic en el RecyclerView
        getAndSetIntentData();
        //22)Creamos un nuevo metodo, y vamos a obtener los datos del itencion
        //que pasamos de nuestra actividad principal

        /*/26 (Part 5) Cambiar el nombre del action Bar: establecemos el nombre
        en la barra de accion despues de el metodo getAndsetIntentData, con el vamos
        a obtenenr el nombre, agregamos en nuestro archivo manifest en ActualizarActivity
        una actividad principal para mostrar automaticamente una flecha hacia atras*/
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(nombre);
        }

        boton_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //25)establecemos que queremos pasarlos dentro de nuestro nuevo metodo
                BaseDatos MiDB = new BaseDatos(ActualizarActivity.this);
                nombre = entrada_nombre.getText().toString().trim();
                categoria = entrada_categoria.getText().toString().trim();
                precio = entrada_precio.getText().toString().trim();
                //y solo entonces llamamos a esto
                MiDB.actualizarDatos(id, nombre, categoria, precio);
            }
        });
        /*27 llamamos a el metodo creado en la BaseDatos, creamos nuestro objeto
        auxiliar de base de datos, he inicializamos la clase
        para pasar el context*/
        boton_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Llamamos a el metodo de confirmacion creado mas abajo
                ConfirmacionDialogo();
            }
        });

    }
    /*sin numero)si tenemos intencion tiene una identificacion adicional, y si tienen un nombre
    de identificacion adicional, categoria y precio, los datos se tranfieren a
    a nuestra nueva actividad*/
    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("nombre") &&
             getIntent().hasExtra("categoria") && getIntent().hasExtra("precio")){
            //23)obtener datos de la intención
            id = getIntent().getStringExtra("id");
            nombre = getIntent().getStringExtra("nombre");
            categoria = getIntent().getStringExtra("categoria");
            precio = getIntent().getStringExtra("precio");
            //establecer datos de intención
            entrada_nombre.setText(nombre);
            entrada_categoria.setText(categoria);
            entrada_precio.setText(precio);
            // si ese no es el caso entonces, mostramos un mensaje que no hay datos
        }else{
            Toast.makeText(this, "No hay datos", Toast.LENGTH_SHORT).show();
        }
    }
    /*28)Creamos un metodo, para confirmar el dialogo, cuando hacemos clicl en nuestro boton eliminar
    preguntara si desea eliminar esto  */
    void ConfirmacionDialogo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //vamos a configurar el el titulo de nuestro dialogo de alerta
        builder.setTitle("Eliminar " + nombre + " ?");
        //configuramos el mensaje
        builder.setMessage("Esta seguro de que desea eliminar " + nombre + " ?");
        //Agregamos los botones, uno positivo y otro negativo
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Usamos este objeto para acceder a el metodo eliminarUnaFila
                BaseDatos MiDB = new BaseDatos(ActualizarActivity.this);
                MiDB.eliminarUnaFila(id);
                /* Agregamos un metodo de acabado, lo que hara es cerrar
                la actividad actual y que volvamos a nuestra
                actividad principal cuando precionemos Si*/
                finish();

            }
        });
        //Agregamos el boton de alerta negativo
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        //Agregamos lo siguiente para poder mostrar
        builder.create().show();

    }
}

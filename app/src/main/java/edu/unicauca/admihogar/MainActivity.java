package edu.unicauca.admihogar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //9)objeto de vista de reciclaje
    RecyclerView recyclerView;
    FloatingActionButton boton_agregar;
    ImageView vacio_imageView;
    TextView no_datos;

    //15)Inicializamos la clase auxiliar de mi base de datos
    BaseDatos MiDB;
    /*Creamos 4 listas de matrices diferentes, que contienen cadenas
    el 1 nombre de identificacion, nombre del producto, categoria y precio*/
    ArrayList<String> id_producto, nombre_producto, categoria_producto, precio_producto;
    //20)Creamos un objeto adaptador personalizado
    AdaptadorPersonalizado adaptadorPersonalizado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //tiempo para el splash_screen
        try {
            Thread.sleep(5000);
        }catch (Exception ex){}

        //Poner el icono en el action Bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //10)agregamos un conjunto de clics pra escuchar nuestro boton flotante
        //y este nos llevara a la siguiente actividad
        recyclerView = findViewById(R.id.recyclerView);
        boton_agregar = findViewById(R.id.boton_agregar);
        vacio_imageView = findViewById(R.id.vacio_imageView);
        no_datos = findViewById(R.id.no_datos_txt);
        boton_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //11)llamamos intent, nuevas rutas intent, para que comience la actividad y la corra
                Intent intent = new Intent( MainActivity.this, AgregarActivity.class );
                startActivity(intent);
            }
        });
        //16)ahora inicializamos la clase
        MiDB = new BaseDatos((MainActivity.this));
        //inicializamos esas listas de matrices
        id_producto = new ArrayList<>();
        nombre_producto = new ArrayList<>();
        categoria_producto = new ArrayList<>();
        precio_producto = new ArrayList<>();

        AlmacenarDatosEnMatrices();
        //20)inicializamos, ademas usamos un adaptador que debe tener cinco parametros
        //diferentes, y usamos esas matrices, y podremoos tomar los datos almacenados dentro de
        //nuestras matrices y es entonces ue podremos pasarlos en un
        //adaptador personalizado
        adaptadorPersonalizado = new AdaptadorPersonalizado(MainActivity.this, this, id_producto, nombre_producto,
                categoria_producto, precio_producto);
        recyclerView.setAdapter(adaptadorPersonalizado);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }
    //26)Vasmo a configurar si solicita un resultado
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }
    /*16)(#17 en clase AdaptadorPersonalizado)
    Creamos un nuevo metodo de nuestra actividad principal
    En resumen tomamos los datos de nuestro metodo de lectura de
    datos antiguaos y almacenamos ese resultado en nuestras matrices*/
    void AlmacenarDatosEnMatrices(){
        //almacenamos el resultado de nuestra lectura de todos los datos
        Cursor cursor = MiDB.LeerTodosDatos();
        //Si nuestro cursor es igual a cero, es decir no hay datos, vamos a mostrar
        //un simple mensaje
        if (cursor.getCount() == 0 ){
          vacio_imageView.setVisibility(View.VISIBLE);
          no_datos.setVisibility(View.VISIBLE);
        }else{
            /*vamos a leer todos los datos  de nuestro cursor, para ello usamos
            la cadena get y vamos a pasar un entero el cero significa que vamos
            a leer la primera columna que es la identificacion del producto,
            el segundo el nombre,categoria y titulo*/
            while (cursor.moveToNext()){
                id_producto.add(cursor.getString(0));
                nombre_producto.add(cursor.getString(1));
                categoria_producto.add(cursor.getString(2));
                precio_producto.add(cursor.getString(3));
            }
            vacio_imageView.setVisibility(View.GONE);
            no_datos.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //29) vamos a inflar nuestro diseño manual ese objeto
        MenuInflater inflader = getMenuInflater();
        inflader.inflate(R.menu.mi_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //este metodo activara el onclicklistener para nuestras opciones de menu
    //o elementos de menu, el 30 esta en BaseDatos
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        /*Si el elemento get item es igual a esto elimine
        todo lo que es la Id, y mostramso un mensaje*/
        if (item.getItemId() == R.id.eliminar_todo) {
            ConfirmacionDialogo();
        }
        return  super.onOptionsItemSelected(item);
    }
    /*31)Creamos un metodo, para confirmar el dialogo, cuando hacemos clicl en nuestro boton eliminar todo
   preguntara si desea eliminar esto  */
    void ConfirmacionDialogo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //vamos a configurar el el titulo de nuestro dialogo de alerta
        builder.setTitle("Eliminar Todo ?");
        //configuramos el mensaje
        builder.setMessage("Esta seguro de que desea eliminar todos los datos ?");
        //Agregamos los botones, uno positivo y otro negativo
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //31 creamos la clase auxiliar de mi base de datos, la inicializamos
                //para que acceda a nuestro metodo para eliminar todos los fatos
                BaseDatos MiDB = new BaseDatos(MainActivity.this);

                MiDB.eliminarTodosDatos();
            /*Al dar clic en eliminar todos aparece una pantalla negra
            para quitarla vamos a hacer lo siguiente, basicaente vamos
            a decir que navege desde nuestra MainActivy a nuestra MainActivity*/
                //Refrescamos la Actividad
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
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

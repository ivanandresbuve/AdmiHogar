package edu.unicauca.admihogar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //9)objeto de vista de reciclaje
    RecyclerView recyclerView;
    FloatingActionButton boton_agregar;

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
        adaptadorPersonalizado = new AdaptadorPersonalizado(MainActivity.this, id_producto, nombre_producto,
                categoria_producto, precio_producto);
        recyclerView.setAdapter(adaptadorPersonalizado);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
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
            Toast.makeText(this, "No Hay Datos", Toast.LENGTH_SHORT).show();
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
        }
    }

}

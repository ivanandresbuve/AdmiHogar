package edu.unicauca.admihogar;

import androidx.appcompat.app.AppCompatActivity;

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
    Button boton_actualizar;
    //23)creamos 4 cadenas diferentes, para el nombre, categoria y el precio
    // almacenamos la informacionde los datos en la intencion
    String id, nombre, categoria, precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar);
        //vamos a encontrar la icdea de nuestros elementos dentro de onCreate
        entrada_nombre = findViewById(R.id.entrada_nombre2);
        entrada_categoria = findViewById(R.id.entrada_categoria2);
        entrada_precio = findViewById(R.id.entrada_precio2);
        boton_actualizar = findViewById(R.id.boton_actualizar);
        //23)llamamos a nuestro metodo
        // con esto ya podemos visualizar nuestro producto al hacer clic en el RecyclerView
        getAndSetIntentData();
        //22)Creamos un nuevo metodo, y vamos a obtener los datos del itencion
        //que pasamos de nuestra actividad principal
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

    }
    /*si tenemos intencion tiene una identificacion adicional, y si tienen un nombre
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

}

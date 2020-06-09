package edu.unicauca.admihogar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AgregarActivity extends AppCompatActivity {
     /*13)Agregamos los TextView (entrada_nombre,entrada_categoria,entrada_precio)
      y el button de la activity_agregar*/
    EditText entrada_nombre, entrada_categoria, entrada_precio;
    //Ahora agregamos el boton
    Button boton_agregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);
        /*Encontramos la idea de nuestrso elemntos y configuramos al hacer clic*/
        entrada_nombre = findViewById(R.id.entrada_nombre);
        entrada_categoria = findViewById(R.id.entrada_categoria);
        entrada_precio = findViewById(R.id.entrada_precio);
        boton_agregar = findViewById(R.id.boton_agregar);
        //llamamos al metodo de nuestra clase auxiliar de mi base de datos
        boton_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*creamos la base de datos del objeto auxiliar,inicializamos
                la clase, pasamos un parametro, lo llamamos el metodo*/
                BaseDatos MiDB = new BaseDatos(AgregarActivity.this);
                /*Llamamos a esto en el metod libro, y pasamos los tres parametros aqui
                Y obtendremos el texto, de nuestro tres textos de edicion y los
                convertimos en cadena y pasamos esto a nuestro metodo de agregaproducto
                que luego procesara nuestros datos e insertar nuestra base de datos SQLite
                el tercer parametro es un entero por lo que convertimos la cadena a entero*/
                MiDB.agregarproducto(entrada_nombre.getText().toString().trim(),
                        entrada_categoria.getText().toString().trim(),
                        Integer.valueOf(entrada_precio.getText().toString().trim()));
            }
        });
    }
}

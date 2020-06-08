package edu.unicauca.admihogar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    //9)objeto de vista de reciclaje
    RecyclerView recyclerView;
    FloatingActionButton boton_agregar;


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


    }
}

package edu.unicauca.admihogar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//17) Adaptador para nuestra vista de recyclerView, su funcion es
//extender el RecyclerView, pasaremos aqui el adaptador personalizado
//MiSoporteDeVista es una clse interna
public class AdaptadorPersonalizado extends RecyclerView.Adapter<AdaptadorPersonalizado.MiSoporteDeVista> {
    //18)Creamos un constructor, para agregar algunas propiedades
    //ponemos un context y 4 matrices diferentes que estaran en nuestro constructor como parametros
    //para cuando iniciañicemos nuestra actividad principal pasara todas las listas
    //de matrices
    private Context context;
    //26)Agregamos actividad aqui y en el constructor
    Activity activity;
    private ArrayList id_producto, nombre_producto, categoria_producto, precio_producto;


    AdaptadorPersonalizado(Activity activity, Context context,
                           ArrayList id_producto,
                           ArrayList nombre_producto,
                           ArrayList categoria_producto,
                           ArrayList precio_producto ){
        this.activity = activity;
        this.context = context;
        //vamos a establecer esos valores a nuestras variables globales para
        //acceder a esos objetos en toda nuestra clase
        this.id_producto = id_producto;
        this.nombre_producto = nombre_producto;
        this.categoria_producto = categoria_producto;
        this.precio_producto = precio_producto;

    }

    /*17Implementamos tre diferentes metodos para nuestro RecyclerView*/
    @NonNull
    @Override
    public MiSoporteDeVista onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //18)Iinflamos nuestro diseño de fila para nuestro RecyclerView, usamos el
        //metodo inflar y aqui pasamos nuestro diseño
        LayoutInflater inflater = LayoutInflater.from(context);
        //el segundo parametro se vera el grupo de padre, y el tecer parametro de falso
        View view = inflater.inflate(R.layout.my_fila, parent, false);
        //Devolvemos un nuevo titular de mi vista, pasamos la View, en el inflador
        //dentro de nuestro objeto de View
        return new MiSoporteDeVista(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MiSoporteDeVista holder, final int position) {

    /*19) Configuramos el texto, usamos el objeto titular y configuramos el texto
        aqui obtenemos la cadena  de nuestra matriz, usamos el metodo get, y pasamos
        el numero entero de posicion, basicamente estamos obteniendo todos los datos
        de nuestro arreglo*/
        holder.producto_id_txt.setText(String.valueOf(id_producto.get(position)));
        holder.producto_nombre_txt.setText(String.valueOf(nombre_producto.get(position)));
        holder.producto_categoria_txt.setText(String.valueOf(categoria_producto.get(position)));
        holder.producto_precio_txt.setText(String.valueOf(precio_producto.get(position)));
        /*20)creanos Onclicklistener para este diseño linea, lo que siginifica que cuando
        hacemos clic, en algunos de nuestros articculos en el RecyclerView
        se activira el Onclicklistener*/
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //21) creamos el Intem y pasamos el context de parametro y la segunda
                //actualizacion
                Intent intent = new Intent(context, ActualizarActivity.class);
                //usamos el contexto pra comenzar la actividad, basicamente estamos
                //navegando en nuestra ctividad principal donde tenemos el >RecyclerView
                //y vamos a redirigir a nuestra ActualizarActivity
                intent.putExtra("id",String.valueOf(id_producto.get(position)));
                intent.putExtra("nombre",String.valueOf(nombre_producto.get(position)));
                intent.putExtra("categoria",String.valueOf(categoria_producto.get(position)));
                intent.putExtra("precio",String.valueOf(precio_producto.get(position)));
                activity.startActivityForResult(intent, 1);


            }
        });
    }

    @Override
    public int getItemCount() {
        //19)agregamos un metodo mas para que podamos usar un metodo de tamaño cualquiera
        //de nuestra matriz
        //de aqui pasamos a la MainActivity punto 20)
        return id_producto.size();
    }

    /*17)La clase extiende la vista de RecyclerView de la vista de puntos, luego de esto
    creamos una nuevo diseño para nuestra fila de RecyclerView, para agregar
    una vista de tarjeta*/
    public class MiSoporteDeVista extends RecyclerView.ViewHolder {
        //18)Creamos un texto diferente
        TextView producto_id_txt, producto_nombre_txt, producto_categoria_txt, producto_precio_txt;
        //20) (PART 4)Agregamos el diseño lineal y encontramos la identificacion
        LinearLayout mainLayout;

        public MiSoporteDeVista(@NonNull View itemView) {
            super(itemView);
            //Obtenenmos la identificacion de esas vistas de texto
            producto_id_txt = itemView.findViewById(R.id.producto_id_txt);
            producto_nombre_txt = itemView.findViewById(R.id.producto_nombre_txt);
            producto_categoria_txt = itemView.findViewById(R.id.producto_categoria_txt);
            producto_precio_txt = itemView.findViewById(R.id.producto_precio_txt);
            //20) la encontramos aqui
            mainLayout = itemView.findViewById(R.id.mainLayout);

        }
    }
}

package edu.unicauca.admihogar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

//1) Esta clase ampliara la clase de capital abierto SQLite
class BaseDatos extends SQLiteOpenHelper {
    //3)Creamos un constructor para esta clase
    /*4)declaramos las variables  para agregar el nombre de la tabla y la version
    * primero agregamos un context y luego una public static */
    private Context context;
    //5)nombramos nuestra base de datos
    private static final String NOMBRE_BASEDATOS = "AdministracionProductos.db";
    //la version de la base de datos que es 1, por tanto es un int
    private  static final int VERSION_BASEDATOS = 1;
    //Nombre de la tabla
    private static String NOMBRE_TABLA = "mis_productos";
    //6)agregamos las columnas (4) por lo que tendremos 4 conos diferentes
    //el primero sera el ID, que aumentara de manera automatica
    private static final String COLUMNA_ID = "_id";
    //El segundo sera el nombre del producto
    private static final String COLUMNA_NOMBRE = "nombre_producto";
    //el tercero sera la categoria del producto
    private static final String COLUMNA_CATEGORIA = "categoria_producto";
    //el cuarto sera el precio del producto
    private static final String  COLUMNA_PRECIO = "precio_producto";

    public BaseDatos(@Nullable Context context) {
        //7)pegamos ahora NOMBRE_BASEDATOS y la VERSION_BASE DATOS
        super(context, NOMBRE_BASEDATOS , null, VERSION_BASEDATOS);
        this.context = context;
    }

    //2)Implementados 2 metodos en crear y actualizar
    @Override
    public void onCreate(SQLiteDatabase db) {
        //8)Tipo de cadena consulta + declaracion SQLite (ESTO ES JAVA)
        String consulta = "CREATE TABLE " + NOMBRE_TABLA +
                        " (" + COLUMNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMNA_NOMBRE + " TEXT, " +
                        COLUMNA_CATEGORIA + " TEXT, " +
                        COLUMNA_PRECIO + " INTEGER); "; //Aqui cerramos la consulta
         db.execSQL(consulta);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*9)Creacion segunda consulta: Es simple "la tabla desplegable si existe
        +  el nombre de la tabla, luego se llamam al metodo onCreate*/
        db.execSQL(" DROP TABLE IF EXISTS " + NOMBRE_TABLA);
        onCreate(db);

    }
}

package edu.unicauca.admihogar;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

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
    //12)Metodo agregar producto,agregamos tres parametros, nombre,categoria,precio que es
    //de tipo entero
    void agregarproducto(String nombre, String categoria, int precio){
        /*Creamos un objeto de base de datos SQLite
        asignamos un nombre, y usamos una palabra clave
        para hacer referencia nuestra clase de ayuda abierta SQLite
        y se usa su metodo de escritura*/
        SQLiteDatabase db = this.getWritableDatabase();
        //Objeto de valores de contenido para almacenar todos los datos de la aplicacion
        //se pasara nuestra tabla de base de datos
        ContentValues cv = new ContentValues();
        /*le asigne el nombre correcto, el 1 parametro es la clave de nombre
        su valor seran datos,por lo que la clave sera la COLUMNA_NOMBRE, no
        necesitamos la COLUMNA_ID porque se incrementa automaticamente,
        el 2 parametro seran los nombres y se repite lo mismo para categoria y precio*/
        cv.put(COLUMNA_NOMBRE, nombre);
        cv.put(COLUMNA_CATEGORIA, categoria);
        cv.put(COLUMNA_PRECIO, precio);
        /*uso de SQLite para insertar los datos dentro de la tabla de base de datos
        se inserta el nombre de la tabla (db)
        vamos a decirle que almacene el resultaod dentro de nuestor metodo de inserccion
        dentro de variable de resultado*/
        long resultado = db.insert(NOMBRE_TABLA, null, cv);
        //Vamos a decir si este resultaod es igual a -1, lo que significa que nuestra
        //aplicacion no pudo insertar los datos, sera el primer parametro para nuestro mensaje
        if (resultado == 1){
            Toast.makeText(context, "Falló", Toast.LENGTH_SHORT).show();
        }else {
            //Se mostrara un mensaje que nuestra tarea agregar un producto a sido exitoso
            //Ahora llamamos a nuestro metodo de agregar activida en AgregarActivity
            Toast.makeText(context, "Agregado con Exito", Toast.LENGTH_SHORT).show();
        }

    }

}

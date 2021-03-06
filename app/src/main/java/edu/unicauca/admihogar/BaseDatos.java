package edu.unicauca.admihogar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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

    BaseDatos(@Nullable Context context) {
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
        vamos a decirle que almacene el resultado dentro de nuestro metodo de inserccion
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
    /*14) (PART #3 VIDEO)Creamos un nuevo metodo, para devolver un objeto cursor
    para leer los datos de nuestra tabla de base de datos,el cursor contendra todos
    //los datos de nuestra tabla, todos los datos se llamaran dentro de nuestra actividad
    //principal donde utilizaremos el RecyclerView
*/
    Cursor LeerTodosDatos(){
        //Primero creamos una consulta, para seleccionar todos los dados
        //de la tabla de base de datos
        String query = " SELECT * FROM " + NOMBRE_TABLA;
        //Creamos un objeto de base de datos, va a utilizar la base de datos get
        SQLiteDatabase db = this.getReadableDatabase();
        //Creo un cursor y se configura
        Cursor cursor = null;
        if (db != null){//almacenamos el resultado dentro del cursor
           cursor = db.rawQuery(query, null);

        }//devolvemos un objeto cursor
        return cursor;
    }
    //24)metodo
    void actualizarDatos(String fila_id, String nombre, String categoria, String precio){
        //vamos a obtenenr una base de datos grabable
        //para escribir nuestra tabla de base de datos
        SQLiteDatabase db = this.getWritableDatabase();
        //usamos el ell objeto de valores de contenido y almacenamos los valores
        //dentro de esdte objeto
        ContentValues cv = new ContentValues();
        //ahora uso su metod put  y usamos una clave y un valor, para la clave sera
        //el la COLUMNA_NOMBRE, y el segundo sera COLUMNA_CATEGORIA, el tercero sera
        //COLUMNA_PRECIO y agregamos lo diferentes valores
        cv.put(COLUMNA_NOMBRE,nombre);
        cv.put(COLUMNA_CATEGORIA,categoria);
        cv.put(COLUMNA_PRECIO,precio);

        //Queremos pasar esos datos aqui, los resultados deben guardarse en
        //este valor largo, estos datos de actualizacion los llamamos
        // dentro de la ActualizarActivity >25
        long result = db.update(NOMBRE_TABLA, cv, "_id=?", new String[]{fila_id});
        // si el resultado es igual a -1 (lo que significa que no hay datos o es un error)
        if(result == -1){
            Toast.makeText(context, "Error al actualizar", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Actualizacion Exitosa", Toast.LENGTH_SHORT).show();

        }

    }
    /*27) Creamos un metodo vacio, es decir no devolvera nada, este metodo
   solo eliminara una fila de la tabla de nuestra base de datos*/
    void eliminarUnaFila(String fila_id){
        /*creamos un objeto de base de datos SQLite y hacemos uso del
        metodo de eliminacion, le decimos que elimine el nombre de la tabla
        en la que estamos y la ID, alamacenamos el resultado de nuestro
        metodo en la variable along*/
        SQLiteDatabase db = this.getWritableDatabase ();
        long resultado = db.delete(NOMBRE_TABLA, "_id=?", new String[]{fila_id});
        /*Si resultado es igual a -1, significa que hay algunos errores, sino
        diremos que se elimino con exito, ahora debemos llamar este metodo
        desde nuestra activida ActualizarActivity*/
        if (resultado == -1){
            Toast.makeText(context, "No se pudo eliminar", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Eliminado", Toast.LENGTH_SHORT).show();
        }
    }
    // 30) creamos un nuevo metodo, este metodo eliminara todos los elementos de la tabla
    //de base de datos (31 ESTA EN MainActivity)
    void eliminarTodosDatos(){
        //agregamos un objeto de la base de datos SQLite
        SQLiteDatabase db = this.getWritableDatabase();
        //nuestra consulta lo hara
        db.execSQL("DELETE FROM " + NOMBRE_TABLA);

    }

}

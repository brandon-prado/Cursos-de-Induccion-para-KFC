package com.bapc.cursos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDCursos extends SQLiteOpenHelper {
    public static final String TABLA = "Usuarios";
    private static final String NOMBRE_BD = "Cursos.db";
    private static final int VERSION = 1;

    public BDCursos(Context context) {
        super(context, NOMBRE_BD, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLA + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "                                                                   nombres TEXT, " +
                "                                                                   apellidos TEXT, " +
                "                                                                   genero TEXT, " +
                "                                                                   correo TEXT UNIQUE, " +
                "                                                                   contrasena TEXT)";
        db.execSQL(createTableQuery);

        //Creación del usuario ROOT
        String correoUsuario = "admin@soporte.com";

        // Verificar si el usuario ya existe en la base de datos
        String consultaUsuarioExistente = "SELECT * FROM " + TABLA + " WHERE correo = ?";
        Cursor cursor = db.rawQuery(consultaUsuarioExistente, new String[]{correoUsuario});

        if (cursor.getCount() == 0) {
            // El usuario no existe, crearlo
            ContentValues valoresUsuario = new ContentValues();
            valoresUsuario.put("nombres", "Administrador");
            valoresUsuario.put("apellidos", "No aplica");
            valoresUsuario.put("genero", "No aplica");
            valoresUsuario.put("correo", correoUsuario);
            valoresUsuario.put("contrasena", "admin");
            db.insert(TABLA, null, valoresUsuario);
        }
        cursor.close();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLA;
        db.execSQL(dropTableQuery);
        onCreate(db);
    }
}

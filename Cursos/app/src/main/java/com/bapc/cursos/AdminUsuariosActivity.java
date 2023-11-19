package com.bapc.cursos;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class AdminUsuariosActivity extends AppCompatActivity {
    //Variables para acceder a la BD
    private BDCursos bdCursos;
    private SQLiteDatabase dbSQLite;
    //Declaración de los elementos que se encuentran en la interfaz y serán usados en la programación
    private View mensaje;
    private Button btnListarRegistros, btnAgregar, btnModificar, btnEliminar, btnLimpiar;
    private EditText txtNombres, txtApellidos, txtCorreoR, txtContrasenaR;
    private Spinner spinnerGenero;

    //Variables utilizadas para insertar, modificar y eliminar
    private int datoID = 0;
    private String datoNombres = "";
    private String datoApellidos = "";
    private String datoGenero = "";
    private String datoCorreo = "";
    private String datoContrasena = "";
    private boolean modoModificar;//Apuntador para saber cuando insertar o modificar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrar_usuarios);

        // Se inicializan las variables definidas previamente con referencia
        // a los elementos de la interfaz de usuario correspondiente.
        // Esto se logra a través de los métodos findViewById(), que buscan
        // los elementos de la interfaz de usuario por su ID y devuelven una referencia a ellos.
        txtNombres = findViewById(R.id.txtNombres);
        txtApellidos = findViewById(R.id.txtApellidos);
        spinnerGenero = findViewById(R.id.spinnerGenero);
        txtCorreoR = findViewById(R.id.txtCorreoR);
        txtContrasenaR = findViewById(R.id.txtContrasenaR);

        btnListarRegistros = findViewById(R.id.btnListarRegistros);
        btnAgregar = findViewById(R.id.btnAgregar);
        btnModificar = findViewById(R.id.btnModificar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnLimpiar = findViewById(R.id.btnLimpiar);
        mensaje = findViewById(android.R.id.content);

        //Se le asignan los valores String al spinnerGenero
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this, R.array.genders_array, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenero.setAdapter(genderAdapter);

        //Acción al presionar el botón de listar
        btnListarRegistros.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Se abre una ventana con todos los registros
                ventanaConTodosRegistros();
            }
        });

        //Acción al presionar el botón de Agregar
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    //Se obtienes los valores de los txt y son asignados a las variables
                    String datoNombres = txtNombres.getText().toString();
                    String datoApellidos = txtApellidos.getText().toString();
                    String datoGenero = spinnerGenero.toString();
                    String datoCorreo = txtCorreoR.getText().toString();
                    String datoContrasena = txtContrasenaR.getText().toString();

                    //Se valida que los datos esten completos
                    if (validacionDatos()) {
                        //Si estamos en modo de modificar, se manda hablar el método de modificar, en caso contrario se
                        //inserta un nuevo registro en la BD
                        if (modoModificar) {
                            //Se realiza la actualización de datos en la DB
                            modificarRegistro(datoID, datoNombres, datoApellidos, datoGenero, datoCorreo, datoContrasena);
                            //Se avisa al usuario
                            Snackbar.make(mensaje, "Usuario actualizado correctamente", Snackbar.LENGTH_LONG).show();
                        } else {
                            //Se verifica que no este registrado el correo ya que es un campo unico
                            if (!verificarExistenciaCorreo(datoCorreo)) {
                                insertarRegistro(datoNombres, datoApellidos, datoGenero, datoCorreo, datoContrasena);
                                Snackbar.make(mensaje, "Usuario registrado exitosamente", Snackbar.LENGTH_LONG).show();
                            } else {
                                Snackbar.make(mensaje, "El correo ya se encuentra registrado", Snackbar.LENGTH_LONG).show();
                            }
                        }
                        //Se limpian los edit text
                        limpiar("Todo");
                    } else {
                        Snackbar.make(mensaje, "Faltan datos por ingresar (*)", Snackbar.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Snackbar.make(mensaje, "Error al registrar el usuario: " + e.toString(), Snackbar.LENGTH_LONG).show();
                }
            }
        });

        //Acción al presionar el botón de Modififcar
        btnModificar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Se abre la ventana para ingresar el registro a modificar
                ventanaSeleccionarRegistro("Modificar");
            }
        });

        //Acción al presionar el botón de Eliminar
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Se abre la ventana para ingresar el registro a eliminar
                ventanaSeleccionarRegistro("Eliminar");
            }
        });

        //Acción al presionar el botón de Limpiar
        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Se manda a hablar el método limpiar con el fltro correspondiente y se avisa al usuario
                limpiar("Todo");
                Snackbar.make(mensaje, "Se han limpiado los campos", Snackbar.LENGTH_LONG).show();
            }
        });

        // Crear instancia de DBCursos
        bdCursos = new BDCursos(this);

        // Obtener instancia de la base de datos
        dbSQLite = bdCursos.getWritableDatabase();
    }

    // Método que limpia los EditText
    private void limpiar(String filtro) {
        if (filtro.equals("Todo")) {
            modoModificar = false;
            txtNombres.setText("");
            txtApellidos.setText("");
            txtCorreoR.setText("");
            txtContrasenaR.setText("");
            txtCorreoR.setEnabled(true); // Habilita el EditText
        }
    }

    //Método que permite limpiar el valor de las variables que son utilizadas para eliminar
    //ó modificar un registro, esto para futuras acciones
    private void limpiarVariables() {
        datoID = 0;
        datoNombres = "";
        datoApellidos = "";
        datoCorreo = "";
        datoContrasena = "";
    }

    //Método que obtiene todos los registros de la tabla en la BD y es usado por el método ObtenerRegistros()
    private Cursor getRegistros() {
        return dbSQLite.query(bdCursos.TABLA, null, null, null, null, null, null);
    }

    //Método que obtiene los registros de acuerdo al filtro requerido
    //Todos o uno en particular (modificación o eliminación)
    private String ObtenerRegistros(String filtro, int idBuscar) {
        // Obtener datos de la tabla de acuerdo al filtro
        Cursor cursor = getRegistros();
        String registros = "";
        if (filtro.equals("Todo")) {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    @SuppressLint("Range") int idD = cursor.getInt(cursor.getColumnIndex("id"));
                    @SuppressLint("Range") String datoNombresD = cursor.getString(cursor.getColumnIndex("nombres"));
                    @SuppressLint("Range") String datoApellidosD = cursor.getString(cursor.getColumnIndex("apellidos"));
                    @SuppressLint("Range") String datoGeneroD = cursor.getString(cursor.getColumnIndex("genero"));
                    @SuppressLint("Range") String datoCorreoD = cursor.getString(cursor.getColumnIndex("correo"));
                    @SuppressLint("Range") String datoContrasenaD = cursor.getString(cursor.getColumnIndex("contrasena"));

                    registros += ("ID: " + idD + "\nNombre (s): " + datoNombresD + "\nApellido (s): " + datoApellidosD + "\nCorreo: " + datoCorreoD + "\n\n");
                }
                cursor.close();
            }
        } else if (filtro.equals("id") && idBuscar != -1) {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    @SuppressLint("Range") int idD = cursor.getInt(cursor.getColumnIndex("id"));
                    @SuppressLint("Range") String datoNombresD = cursor.getString(cursor.getColumnIndex("nombres"));
                    @SuppressLint("Range") String datoApellidosD = cursor.getString(cursor.getColumnIndex("apellidos"));
                    @SuppressLint("Range") String datoGeneroD = cursor.getString(cursor.getColumnIndex("genero"));
                    @SuppressLint("Range") String datoCorreoD = cursor.getString(cursor.getColumnIndex("correo"));
                    @SuppressLint("Range") String datoContrasenaD = cursor.getString(cursor.getColumnIndex("contrasena"));

                    if (idD == idBuscar) {
                        //Se asigna valor a las variables para poder modificar o eliminar
                        datoID = idD;
                        datoNombres = datoNombresD;
                        datoApellidos = datoApellidosD;
                        datoGenero = datoGeneroD;
                        datoCorreo = datoCorreoD;
                        datoContrasena = datoContrasenaD;

                        registros += ("ID: " + idD + ", Nombre (s): " + datoNombresD + ",  Apellido (s): " + datoApellidosD + ",  Correo: " + datoCorreoD);
                    }
                }
                cursor.close();
            }
        }
        return registros;
    }

    //Método que valida que los campos tengan información antes de insertar un registro
    private boolean validacionDatos() {
        if (txtNombres.getText().toString().isEmpty() ||
                txtApellidos.getText().toString().isEmpty() ||
                spinnerGenero.toString().isEmpty() ||
                txtCorreoR.getText().toString().isEmpty() ||
                txtContrasenaR.getText().toString().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    //Método que nos permite saber de la existencia de un registro en la BD
    private boolean verificarExistenciaCorreo(String correo) {
        String[] projection = {"correo"};
        String selection = "correo = ?";
        String[] selectionArgs = {String.valueOf(correo)};
        Cursor cursor = dbSQLite.query(bdCursos.TABLA, projection, selection, selectionArgs, null, null, null);

        boolean existe = (cursor != null && cursor.getCount() > 0);

        if (cursor != null) {
            cursor.close();
        }

        return existe;
    }

    //Método que nos permite saber de la existencia de un registro en la BD
    private boolean verificarExistenciaID(int idRegistro) {
        String[] projection = {"id"};
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(idRegistro)};
        Cursor cursor = dbSQLite.query(bdCursos.TABLA, projection, selection, selectionArgs, null, null, null);

        boolean existe = (cursor != null && cursor.getCount() > 0);

        if (cursor != null) {
            cursor.close();
        }

        return existe;
    }

    //Método de inserción de datos en la BD
    private void insertarRegistro(String datoNombres, String datoApellidos, String datoGenero, String datoCorreo, String datoContrasena) {
        ContentValues values = new ContentValues();
        values.put("nombres", datoNombres);
        values.put("apellidos", datoApellidos);
        values.put("genero", datoGenero);
        values.put("correo", datoCorreo);
        values.put("contrasena", datoContrasena);

        dbSQLite.insert(bdCursos.TABLA, null, values);
    }

    //Método de modificación de datos en la BD
    private void modificarRegistro(int idRegistro, String datoNombres, String datoApellidos, String datoGenero, String datoCorreo, String datoContrasena) {
        ContentValues values = new ContentValues();
        values.put("nombres", datoNombres);
        values.put("apellidos", datoApellidos);
        values.put("genero", datoGenero);
        values.put("correo", datoCorreo);
        values.put("contrasena", datoContrasena);

        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(idRegistro)};
        dbSQLite.update(bdCursos.TABLA, values, whereClause, whereArgs);
    }


    //Método de eliminación de datos en la BD
    private void eliminarRegistro(int idRegistro) {
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(idRegistro)};
        dbSQLite.delete(bdCursos.TABLA, whereClause, whereArgs);
        limpiarVariables(); //Limpiar de procesos anteriores
    }

    //Cierra la BD al cerrar la APP
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cerrar la base de datos al destruir la actividad
        dbSQLite.close();
    }

    //Ventana emergente que nos permite ver todos los registros de la BD
    private void ventanaConTodosRegistros() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Usuarios dados de alta en el Sistema:\n");
        builder.setMessage("");
        if (ObtenerRegistros("Todo", -1).equals("")) {
            builder.setMessage("Sin registros");
        } else {
            builder.setMessage(ObtenerRegistros("Todo", -1));
        }

        builder.setNegativeButton("Salir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //Ventana emergente que nos permite seleccionar por medio del ID el registro de la BD
    //para poder modificar o eliminar
    private void ventanaSeleccionarRegistro(String accion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText editText = new EditText(this);

        //Evento permite que cada vez que se modifique el editText con el ID
        //se muestre en la parte inferior los datos principales del registro
        //para poder saber que registro vamos a eliminar o modificar
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                // Ejecutar la función cada vez que se ingrese información en el EditText
                if (!editText.getText().toString().isEmpty()) {
                    String datoUsuario = ObtenerRegistros("id", Integer.parseInt(editText.getText().toString()));
                    if (!datoUsuario.equals("")) {
                        Snackbar.make(mensaje, datoUsuario, Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(mensaje, "No existe, puedes 'Ver los registros' para conocer su ID", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });

        if (accion.equals("Modificar")) {
            editText.setHint("Ingresa el ID del usuario que deseas modificar");
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(editText);

            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Realizar acciones con el texto ingresado por el usuario
                    if (!editText.getText().toString().isEmpty()) {
                        //Se obtiene el dato del ID
                        datoID = Integer.parseInt(editText.getText().toString());
                        //Se asigna el dato de la BD a los txt
                        txtNombres.setText(datoNombres.toString());
                        txtApellidos.setText(datoApellidos.toString());
                        //Bloquear campo de correo, ese campo no se puede modificar, en caso que sea el admin
                        if (datoCorreo.equals("admin@soporte.com")) {
                            txtCorreoR.setEnabled(false); // Bloquea el EditText
                        } else {
                            txtCorreoR.setEnabled(true); // Habilita el EditText
                        }
                        txtCorreoR.setText(datoCorreo.toString());
                        txtContrasenaR.setText(datoContrasena.toString());
                        //Se activa el modo modificar
                        modoModificar = true;
                    } else {
                        Snackbar.make(mensaje, "No se ingreso ningún ID", Snackbar.LENGTH_LONG).show();
                    }
                }
            });
        } else if (accion.equals("Eliminar")) {
            editText.setHint("Ingresa el ID del usuario que deseas eliminar");
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(editText);

            builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Realizar acciones con el texto ingresado por el usuario
                    if (!editText.getText().toString().isEmpty()) {
                        int idEliminar = Integer.parseInt(editText.getText().toString());
                        if (verificarExistenciaID(idEliminar)) {
                            if (datoCorreo.equals("admin@soporte.com")) {
                                Snackbar.make(mensaje, "Este usuario de administrador no se puede eliminar", Snackbar.LENGTH_LONG).show();
                            } else {
                                eliminarRegistro(idEliminar);
                                Snackbar.make(mensaje, "Usuario eliminado", Snackbar.LENGTH_LONG).show();
                            }

                        } else {
                            Snackbar.make(mensaje, "No se elimino ningún usuario porque el ID no existe", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(mensaje, "No se ingreso ningún ID", Snackbar.LENGTH_LONG).show();
                    }
                }
            });
        }

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
    }
}

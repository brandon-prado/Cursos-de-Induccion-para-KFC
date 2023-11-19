package com.bapc.cursos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class RegistrarUsuarioActivity extends AppCompatActivity {
    //Declaración de los elementos que se encuentran en la interfaz y serán usados en la programación
    private EditText txtNombres, txtApellidos, txtCorreoR, txtContrasenaR;
    private Button btnRegistrarse;
    private TextView accesoInicioSesion;
    private Spinner spinnerGenero;
    private View mensaje;

    //Variables para acceder a la BD
    private BDCursos bdCursos;
    private SQLiteDatabase dbSQLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_de_usuario);

        // Se inicializan las variables definidas previamente con referencia
        // a los elementos de la interfaz de usuario correspondiente.
        // Esto se logra a través de los métodos findViewById(), que buscan
        // los elementos de la interfaz de usuario por su ID y devuelven una referencia a ellos.
        txtNombres = findViewById(R.id.txtNombres);
        txtApellidos = findViewById(R.id.txtApellidos);
        spinnerGenero = findViewById(R.id.spinnerGenero);
        txtCorreoR = findViewById(R.id.txtCorreoR);
        txtContrasenaR = findViewById(R.id.txtContrasenaR);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);
        accesoInicioSesion = findViewById(R.id.accesoInicioSesion);
        mensaje = findViewById(android.R.id.content);

        //Se le asignan los valores String al spinnerGenero
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this, R.array.genders_array, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenero.setAdapter(genderAdapter);

        // Crear instancia de DBLibros
        bdCursos = new BDCursos(this);

        // Obtener instancia de la base de datos
        dbSQLite = bdCursos.getWritableDatabase();

        //Acción al presionar el botón de 'Registrarse'
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
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
                        //Se verifica que no este registrado el correo ya que es un campo unico
                        if (!verificarExistencia(datoCorreo)) {
                            insertarRegistro(datoNombres, datoApellidos, datoGenero, datoCorreo, datoContrasena);
                            Toast.makeText(RegistrarUsuarioActivity.this, "Usuario registrado exitosamente, ahora puedes iniciar sesión", Toast.LENGTH_SHORT).show();
                            finish(); // Cierra la actividad de registro y vuelve a la actividad anterior
                        } else {
                            Snackbar.make(mensaje, "El correo ya se encuentra registrado, si perdiste la contraseña contacta a soporte KFC", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(mensaje, "Faltan datos por ingresar (*)", Snackbar.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Snackbar.make(mensaje, "Error al registrar el usuario: " + e.toString(), Snackbar.LENGTH_LONG).show();
                }
            }

        });

        //Acceso directo que abre la actividad de inicio de sesión y cierra la actividad de registro de usuario
        accesoInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Cierra la actividad de registro y vuelve a la actividad de inicio de sesión
            }
        });
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
    private boolean verificarExistencia(String correo) {
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
}


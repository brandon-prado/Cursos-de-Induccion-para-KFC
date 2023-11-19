package com.bapc.cursos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class InicioSesionActivity extends AppCompatActivity {
    //Declaración de los elementos que se encuentran en la interfaz y serán usados en la programación
    private View mensaje;
    private TextView accesoRegistrarUsuario;
    private EditText txtCorreo, txtContrasena;
    private Button btnIniciarSesion;

    //Variables para acceder a la BD
    private BDCursos bdCursos;
    private SQLiteDatabase dbSQLite;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        // Se inicializan las variables definidas previamente con referencia
        // a los elementos de la interfaz de usuario correspondiente.
        // Esto se logra a través de los métodos findViewById(), que buscan
        // los elementos de la interfaz de usuario por su ID y devuelven una referencia a ellos.
        txtCorreo = findViewById(R.id.txtCorreo);
        txtContrasena = findViewById(R.id.txtContrasenaR);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        accesoRegistrarUsuario = findViewById(R.id.accesoRegistarUsuario);
        mensaje = findViewById(android.R.id.content);

        // Crear instancia de DBLibros
        bdCursos = new BDCursos(this);

        // Obtener instancia de la base de datos
        dbSQLite = bdCursos.getWritableDatabase();

        //Acción al presionar el botón de iniciar sesión
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Se obtienes los valores de los txt y son asignados a las variables
                String datoCorreo = txtCorreo.getText().toString();
                String datoContrasena = txtContrasena.getText().toString();

                //Se valida que los datos esten completos
                if (validacionDatos()) {
                    //Se verifica la existencia del correo
                    if (verificarExistenciaCorreo(datoCorreo)) {
                        //Si existe el correo se valida que el correo y la contraseña sean correctas
                        if (verificarContrasena(datoCorreo, datoContrasena)) {
                            if(datoCorreo.equals("admin@soporte.com")){
                                // Si el correo corresponde al unico ususario administrador, inicia en pantalla de ADMIN
                                Intent intent = new Intent(InicioSesionActivity.this, MenuPrincipalAdminActivity.class);
                                startActivity(intent);
                            }else{
                                // Si el inicio de sesión es exitoso, navega a la pantalla de bienvenida
                                Intent intent = new Intent(InicioSesionActivity.this, MenuPrincipalActivity.class);
                                startActivity(intent);
                            }

                            //Se borra la contraseña del txtContraseña
                            txtContrasena.setText("");
                        } else {
                            Snackbar.make(mensaje, "Algún dato ingresado esta incorrecto", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(mensaje, "El correo no se encuentra registrado, puedes crear una cuenta nueva en 'Regístrate aquí'", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(mensaje, "Faltan datos por ingresar, comprueba y vuelva intentar", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        //Si se preciona el acceso directo 'regístrate aqui' se abre el activity de registro de usuario
        accesoRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre la actividad de registro
                Intent intent = new Intent(InicioSesionActivity.this, RegistrarUsuarioActivity.class);
                startActivity(intent);
                //Se borra el correo y la contraseña
                txtCorreo.setText("");
                txtContrasena.setText("");
            }
        });
    }

    //Método que valida que los campos tengan información antes de consultar la existencia de un registro
    private boolean validacionDatos() {
        if (txtCorreo.getText().toString().isEmpty() ||
                txtContrasena.getText().toString().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    //Método que nos permite saber de la existencia de un usuario en la BD
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

    //Método que nos permite el usuario y contraseña de un usuario en la BD
    private boolean verificarContrasena(String correo, String contrasena) {
        String[] projection = {"correo"};
        String selection = "correo = ? AND contrasena = ?";
        String[] selectionArgs = {correo, contrasena};
        Cursor cursor = dbSQLite.query(bdCursos.TABLA, projection, selection, selectionArgs, null, null, null);

        boolean existe = (cursor != null && cursor.getCount() > 0);

        if (cursor != null) {
            cursor.close();
        }

        return existe;
    }

}

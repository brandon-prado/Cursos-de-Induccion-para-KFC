package com.bapc.cursos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MenuPrincipalActivity extends AppCompatActivity {
    //Declaración de los elementos que se encuentran en la interfaz y serán usados en la programación
    private ImageButton imgBtnMaterial, imgBtnAcercaDe;
    private Button btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        // Se inicializan las variables definidas previamente con referencia
        // a los elementos de la interfaz de usuario correspondiente.
        // Esto se logra a través de los métodos findViewById(), que buscan
        // los elementos de la interfaz de usuario por su ID y devuelven una referencia a ellos.
        imgBtnMaterial = findViewById(R.id.imgBtnMaterial);
        imgBtnAcercaDe = findViewById(R.id.imgBtnAcercaDe);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        //Acción del imageButton Ver material
        imgBtnMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipalActivity.this, MaterialActivity.class);
                startActivity(intent);
            }
        });

        // Configuración del botón "Acerca de"
        imgBtnAcercaDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipalActivity.this, AcercaDeActivity.class);
                startActivity(intent);
            }
        });

        //Acción de boton de cerrar sesión
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VentanaConfirmacion();
            }
        });
    }

    //Ventana de confirmación para confirmar la acción de cerrar sesión o al presional la tecla de back
    private void VentanaConfirmacion() {
        // Crear un diálogo de alerta de confirmación
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmación");
        builder.setMessage("¿Estás seguro (a) de cerrar sesión?");

        // Cerrar
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MenuPrincipalActivity.this, "Cerrando sesión", Toast.LENGTH_SHORT).show();
                finish(); // Cierra la actividad de registro y vuelve a la actividad anterior
            }
        });

        // Cancelar, no se hace nada
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // Mostrar el diálogo de alerta
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    //Al momento de presionar la tecla de back se pregunta si se quiere cerrar sesión
    @Override
    public void onBackPressed() {
        VentanaConfirmacion();
    }

}

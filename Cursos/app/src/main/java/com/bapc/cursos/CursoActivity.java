package com.bapc.cursos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CursoActivity extends AppCompatActivity {
    //Declaración de los elementos que se encuentran en la interfaz y serán usados en la programación
    private ImageView imgCurso;
    private Button btnRetroceder, btnAvanzar, btnInicio, btnFin;
    private TextView txtNumeroDeImagen;
    private int currentIndex = 0;  // Índice actual de la imagen
    //Arreglo de recursos que contiene el material de cada curso
    private int[] recursosCurso1 = {R.drawable.bisquet1, R.drawable.bisquet2, R.drawable.bisquet3, R.drawable.bisquet4, R.drawable.bisquet5, R.drawable.bisquet6, R.drawable.bisquet7, R.drawable.bisquet8, R.drawable.bisquet9};  // Lista de recursos de imágenes
    private int[] recursosCurso2 = {R.drawable.cruji1, R.drawable.cruji2, R.drawable.cruji3, R.drawable.cruji4, R.drawable.cruji5, R.drawable.cruji6, R.drawable.cruji7, R.drawable.cruji8, R.drawable.cruji9, R.drawable.cruji10, R.drawable.cruji11, R.drawable.cruji12, R.drawable.cruji13, R.drawable.cruji14, R.drawable.cruji15, R.drawable.cruji16, R.drawable.cruji17, R.drawable.cruji18, R.drawable.cruji19, R.drawable.cruji20, R.drawable.cruji21, R.drawable.cruji22, R.drawable.cruji23, R.drawable.cruji24, R.drawable.cruji25, R.drawable.cruji26, R.drawable.cruji27, R.drawable.cruji28, R.drawable.cruji29, R.drawable.cruji30, R.drawable.cruji31, R.drawable.cruji32};
    private int[] recursosCurso3 = {R.drawable.ensalada1, R.drawable.ensalada2, R.drawable.ensalada3, R.drawable.ensalada4, R.drawable.ensalada5, R.drawable.ensalada6, R.drawable.ensalada7};
    private int[] recursosCurso4 = {R.drawable.ke_cono1, R.drawable.ke_cono2, R.drawable.ke_cono3, R.drawable.ke_cono4};
    private int[] recursosCurso5 = {R.drawable.popcorn1, R.drawable.popcorn2, R.drawable.popcorn3, R.drawable.popcorn4, R.drawable.popcorn5, R.drawable.popcorn6};
    private int[] recursosCurso6 = {R.drawable.pure1, R.drawable.pure2, R.drawable.pure3, R.drawable.pure4, R.drawable.pure5, R.drawable.pure6, R.drawable.pure7};
    private int[] recursosCurso7 = {R.drawable.salsa_morena1, R.drawable.salsa_morena2, R.drawable.salsa_morena3, R.drawable.salsa_morena4, R.drawable.salsa_morena5};
    private int[] recursosCurso8 = {R.drawable.pay_de_manzana1,R.drawable.pay_de_manzana2,R.drawable.pay_de_manzana3,R.drawable.pay_de_manzana4,R.drawable.pay_de_manzana5,R.drawable.pay_de_manzana6,R.drawable.pay_de_manzana7};
    private int[] recursosCurso9 = {R.drawable.churro_bites1,R.drawable.churro_bites2,R.drawable.churro_bites3,R.drawable.churro_bites4,R.drawable.churro_bites5,R.drawable.churro_bites6,R.drawable.churro_bites7,R.drawable.churro_bites8,R.drawable.churro_bites9,R.drawable.churro_bites10,R.drawable.churro_bites11,R.drawable.churro_bites2,R.drawable.churro_bites13};
    private int[] recursosCurso10 = {R.drawable.kreamball1,R.drawable.kreamball2,R.drawable.kreamball3,R.drawable.kreamball4,R.drawable.kreamball5,R.drawable.kreamball6,R.drawable.kreamball7,R.drawable.kreamball8,R.drawable.kreamball9};
    private int[] recursosCurso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curso);

        // Se inicializan las variables definidas previamente con referencia
        // a los elementos de la interfaz de usuario correspondiente.
        // Esto se logra a través de los métodos findViewById(), que buscan
        // los elementos de la interfaz de usuario por su ID y devuelven una referencia a ellos.
        imgCurso = findViewById(R.id.imgCurso);
        txtNumeroDeImagen = findViewById(R.id.txtNumeroDeImagen);
        btnRetroceder = findViewById(R.id.btnRetroceder);
        btnAvanzar = findViewById(R.id.btnAvanzar);
        btnInicio = findViewById(R.id.btnInicio);
        btnFin = findViewById(R.id.btnFin);

        // Obtener el Intent que inició esta actividad
        Intent intent = getIntent();

        // Verificar si se enviaron extras con el Intent
        if (intent != null && intent.hasExtra("curso")) {
            // Obtener el valor del dato "curso"
            String curso = intent.getStringExtra("curso");

            if (curso.equals("1")) {
                recursosCurso = recursosCurso1; // Asignar arreglo de recursos para curso 1
            } else if (curso.equals("2")) {
                recursosCurso = recursosCurso2; // Asignar arreglo de recursos para curso 2
            } else if (curso.equals("3")) {
                recursosCurso = recursosCurso3; // Asignar arreglo de recursos para curso 3
            } else if (curso.equals("4")) {
                recursosCurso = recursosCurso4; // Asignar arreglo de recursos para curso 4
            } else if (curso.equals("5")) {
                recursosCurso = recursosCurso5; // Asignar arreglo de recursos para curso 5
            } else if (curso.equals("6")) {
                recursosCurso = recursosCurso6; // Asignar arreglo de recursos para curso 6
            } else if (curso.equals("7")) {
                recursosCurso = recursosCurso7; // Asignar arreglo de recursos para curso 7
            } else if (curso.equals("8")) {
                recursosCurso = recursosCurso8; // Asignar arreglo de recursos para curso 8
            } else if (curso.equals("9")) {
                recursosCurso = recursosCurso9; // Asignar arreglo de recursos para curso 9
            } else if (curso.equals("10")) {
                recursosCurso = recursosCurso10; // Asignar arreglo de recursos para curso 10
            } else {
                // Valor de curso no válido y se muestra la imagen por defecto
                return; // Salir del método o realizar acciones correspondientes
            }

            // Establecer la imagen inicial
            imgCurso.setImageResource(recursosCurso[currentIndex]);
            actualizarNumeroImagen();
            // Configurar acción al presionar el botón de retroceder
            btnRetroceder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentIndex > 0) {
                        currentIndex--;
                        imgCurso.setImageResource(recursosCurso[currentIndex]);
                        actualizarNumeroImagen();
                    }
                }
            });

            // Configurar acción al presionar el botón de avanzar
            btnAvanzar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentIndex < recursosCurso.length - 1) {
                        currentIndex++;
                        imgCurso.setImageResource(recursosCurso[currentIndex]);
                        actualizarNumeroImagen();
                    }
                }
            });

            // Acción al presionar el botón de inicio
            btnInicio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentIndex = 0;
                    imgCurso.setImageResource(recursosCurso[currentIndex]);
                    actualizarNumeroImagen();
                }
            });

            // Acción al presionar el botón de final
            btnFin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentIndex = recursosCurso.length - 1;
                    imgCurso.setImageResource(recursosCurso[currentIndex]);
                    actualizarNumeroImagen();
                }
            });

        }
    }

    // Método para actualizar el número de imagen en el TextView txtNumeroDeCurso
    private void actualizarNumeroImagen() {
        int numeroImagen = currentIndex + 1;
        int totalImagenes = recursosCurso.length;
        String texto = "Imagen " + numeroImagen + " de " + totalImagenes;
        txtNumeroDeImagen.setText(texto);
    }
}

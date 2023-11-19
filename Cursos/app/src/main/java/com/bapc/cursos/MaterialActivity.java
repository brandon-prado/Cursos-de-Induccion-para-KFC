package com.bapc.cursos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MaterialActivity extends AppCompatActivity {
    //Declaración de los elementos que se encuentran en la interfaz y serán usados en la programación
    private ImageButton imgBtnCurso1, imgBtnCurso2, imgBtnCurso3, imgBtnCurso4, imgBtnCurso5, imgBtnCurso6, imgBtnCurso7, imgBtnCurso8, imgBtnCurso9, imgBtnCurso10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);

        // Se inicializan las variables definidas previamente con referencia
        // a los elementos de la interfaz de usuario correspondiente.
        // Esto se logra a través de los métodos findViewById(), que buscan
        // los elementos de la interfaz de usuario por su ID y devuelven una referencia a ellos.
        imgBtnCurso1 = findViewById(R.id.imgBtnCurso1);
        imgBtnCurso2 = findViewById(R.id.imgBtnCurso2);
        imgBtnCurso3 = findViewById(R.id.imgBtnCurso3);
        imgBtnCurso4 = findViewById(R.id.imgBtnCurso4);
        imgBtnCurso5 = findViewById(R.id.imgBtnCurso5);
        imgBtnCurso6 = findViewById(R.id.imgBtnCurso6);
        imgBtnCurso7 = findViewById(R.id.imgBtnCurso7);
        imgBtnCurso8 = findViewById(R.id.imgBtnCurso8);
        imgBtnCurso9 = findViewById(R.id.imgBtnCurso9);
        imgBtnCurso10 = findViewById(R.id.imgBtnCurso10);

        //Acción del imageButton Curso1
        imgBtnCurso1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MaterialActivity.this, CursoActivity.class);
                //startActivity(intent);

                // Enviar el dato al iniciar la actividad CursoActivity
                Intent intent = new Intent(getApplicationContext(), CursoActivity.class);
                intent.putExtra("curso", "1");
                startActivity(intent);
            }
        });

        //Acción del imageButton Curso2
        imgBtnCurso2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MaterialActivity.this, CursoActivity.class);
                //startActivity(intent);

                // Enviar el dato al iniciar la actividad CursoActivity
                Intent intent = new Intent(getApplicationContext(), CursoActivity.class);
                intent.putExtra("curso", "2");
                startActivity(intent);
            }
        });

        //Acción del imageButton Curso3
        imgBtnCurso3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MaterialActivity.this, CursoActivity.class);
                //startActivity(intent);

                // Enviar el dato al iniciar la actividad CursoActivity
                Intent intent = new Intent(getApplicationContext(), CursoActivity.class);
                intent.putExtra("curso", "3");
                startActivity(intent);
            }
        });

        //Acción del imageButton Curso4
        imgBtnCurso4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MaterialActivity.this, CursoActivity.class);
                //startActivity(intent);

                // Enviar el dato al iniciar la actividad CursoActivity
                Intent intent = new Intent(getApplicationContext(), CursoActivity.class);
                intent.putExtra("curso", "4");
                startActivity(intent);
            }
        });

        //Acción del imageButton Curso5
        imgBtnCurso5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MaterialActivity.this, CursoActivity.class);
                //startActivity(intent);

                // Enviar el dato al iniciar la actividad CursoActivity
                Intent intent = new Intent(getApplicationContext(), CursoActivity.class);
                intent.putExtra("curso", "5");
                startActivity(intent);
            }
        });

        //Acción del imageButton Curso6
        imgBtnCurso6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MaterialActivity.this, CursoActivity.class);
                //startActivity(intent);

                // Enviar el dato al iniciar la actividad CursoActivity
                Intent intent = new Intent(getApplicationContext(), CursoActivity.class);
                intent.putExtra("curso", "6");
                startActivity(intent);
            }
        });

        //Acción del imageButton Curso7
        imgBtnCurso7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MaterialActivity.this, CursoActivity.class);
                //startActivity(intent);

                // Enviar el dato al iniciar la actividad CursoActivity
                Intent intent = new Intent(getApplicationContext(), CursoActivity.class);
                intent.putExtra("curso", "7");
                startActivity(intent);
            }
        });

        //Acción del imageButton Curso8
        imgBtnCurso8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MaterialActivity.this, CursoActivity.class);
                //startActivity(intent);

                // Enviar el dato al iniciar la actividad CursoActivity
                Intent intent = new Intent(getApplicationContext(), CursoActivity.class);
                intent.putExtra("curso", "8");
                startActivity(intent);
            }
        });

        //Acción del imageButton Curso9
        imgBtnCurso9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MaterialActivity.this, CursoActivity.class);
                //startActivity(intent);

                // Enviar el dato al iniciar la actividad CursoActivity
                Intent intent = new Intent(getApplicationContext(), CursoActivity.class);
                intent.putExtra("curso", "9");
                startActivity(intent);
            }
        });

        //Acción del imageButton Curso10
        imgBtnCurso10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MaterialActivity.this, CursoActivity.class);
                //startActivity(intent);

                // Enviar el dato al iniciar la actividad CursoActivity
                Intent intent = new Intent(getApplicationContext(), CursoActivity.class);
                intent.putExtra("curso", "10");
                startActivity(intent);
            }
        });
    }
}

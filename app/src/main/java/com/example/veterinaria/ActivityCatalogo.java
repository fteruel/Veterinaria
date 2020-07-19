package com.example.veterinaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.veterinaria.data.MascotasHelper;
import com.example.veterinaria.data.VeterinariaContracto.MascotasEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ActivityCatalogo extends AppCompatActivity {

    private MascotasHelper mMascotaHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catalogo_activity);

        // setup FAB
        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityCatalogo.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mMascotaHelper = new MascotasHelper(this);

        MostrarInfoBD();



    }



    private  void MostrarInfoBD() {
        MascotasHelper mDbHelper = new MascotasHelper(this);

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        //Hace la SQL query "SELECT * FROM pets"
        //para sacar un cursor para navergar todos los elementos


       // Cursor cursor = db.rawQuery("SELECT * FROM " + MascotasEntry.NOMBRE_TABLA, null);

        String[] projeccion = {
                MascotasEntry._ID,
                MascotasEntry.COLUMNA_MASCOTA_NOMBRE,
                MascotasEntry.COLUMNA_MASCOTA_RAZA,
                MascotasEntry.COLUMNA_MASCOTA_GENERO,
                MascotasEntry.COLUMNA_MASCOTA_PESO,
        };

        Cursor cursor = db.query(MascotasEntry.NOMBRE_TABLA,
                projeccion,
                null,
                null,
                null,
                null,
                null);

        try {
            // Muestro la cantidad de filas en la tabla(que serian la cantidad de mascotas)
            TextView displayView = findViewById(R.id.text_view_pet);
            displayView.setText("Cantidad de Masctotas en la DB: " + cursor.getCount() + "pets.\n\n");
            displayView.append(MascotasEntry._ID + "-" +
                            MascotasEntry.COLUMNA_MASCOTA_NOMBRE + "-" +
                            MascotasEntry.COLUMNA_MASCOTA_RAZA + "-" +
                            MascotasEntry.COLUMNA_MASCOTA_GENERO + "-" +
                            MascotasEntry.COLUMNA_MASCOTA_PESO + "-" );


            //genero los ids de cada columna

            int columnaIDIndex = cursor.getColumnIndex(MascotasEntry._ID);
            int columnaNombreIndex = cursor.getColumnIndex(MascotasEntry.COLUMNA_MASCOTA_NOMBRE);
            int columnaRazaIndex = cursor.getColumnIndex(MascotasEntry.COLUMNA_MASCOTA_RAZA);
            int columnaGeneroIndex = cursor.getColumnIndex(MascotasEntry.COLUMNA_MASCOTA_GENERO);
            int columnaPesoIndex = cursor.getColumnIndex(MascotasEntry.COLUMNA_MASCOTA_PESO);

            //itero el por el indice hata que Move next sea false que es cuando llegue al ultimo

            while (cursor.moveToNext()){

                int IDActual = cursor.getInt(columnaIDIndex);
                String nombreActual = cursor.getString(columnaNombreIndex);
                String  razaActual = cursor.getString(columnaRazaIndex);
                int  generoActual = cursor.getInt(columnaGeneroIndex);
                int  pesoActual  = cursor.getInt(columnaPesoIndex);

                displayView.append("\n" + IDActual + "-" +
                        nombreActual + "-" +
                        razaActual + "-" +
                        generoActual + "-" +
                        pesoActual);
            }


        } finally {

            //cierro cursor
            cursor.close();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalogo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Repondo a seleccionar
            case R.id.insertar_dummy_data:

                //pongo una mascota solo para mostrar
                InsertarMascota();

                return true;
            // Respondo a borrar todo
            case R.id.borrar_todo:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void InsertarMascota() {

        SQLiteDatabase db = mMascotaHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(MascotasEntry.COLUMNA_MASCOTA_NOMBRE, "Toto");
        values.put(MascotasEntry.COLUMNA_MASCOTA_RAZA, "Terrier");
        values.put(MascotasEntry.COLUMNA_MASCOTA_GENERO, MascotasEntry.GENERO_MACHO);
        values.put(MascotasEntry.COLUMNA_MASCOTA_PESO, 2);

        long NuevaIDFila = db.insert(MascotasEntry.NOMBRE_TABLA, null,values);

        Log.v("Catalogo", "nuevo ID Fila :" + NuevaIDFila);

    }

    @Override
    protected void onStart() {

        MostrarInfoBD();

        super.onStart();
    }
}
package com.example.veterinaria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.veterinaria.data.MascotasHelper;
import com.example.veterinaria.data.VeterinariaContracto.MascotasEntry;

public class EditorActivity extends AppCompatActivity {


    private EditText NombreET;
    private EditText RazaET;
    private EditText PesoET;
    private Spinner SpinnerGenero;

    /**
    genero de la mascota, 0 para desconocido, 1 macho, 2 hembra
     */
    private int Genero = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        //inicializo
        NombreET = findViewById(R.id.editar_nombre_mascota);
        RazaET = findViewById(R.id.editar_raza_mascota);
        PesoET = findViewById(R.id.editar_peso_mascota);
        SpinnerGenero = findViewById(R.id.spinner_genero);

        setupSpinner();

    }

    /**
     * Seteo Spinner
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_opciones_genero, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        SpinnerGenero.setAdapter(genderSpinnerAdapter);
        SpinnerGenero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.genero_macho))) {
                        Genero = MascotasEntry.GENERO_MACHO; // Macho
                    } else if (selection.equals(getString(R.string.gendero_hembra))) {
                        Genero = MascotasEntry.GENERO_HEMBRA; // Hembra
                    } else {
                        Genero = MascotasEntry.GENERO_DESCONOCIDO; // Desconocido
                    }
                }
            }

            // AdapterView, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Genero = 0; //Desconocido
            }
        });
    }


    private void GuardarMascota(){

        String nombreActual = NombreET.getText().toString().trim();
        String razaActual = RazaET.getText().toString().trim();
        String pesoActual = PesoET.getText().toString().trim();
        int pesoInt = Integer.parseInt(pesoActual);

        MascotasHelper mMascotaHelper = new MascotasHelper(this);

        SQLiteDatabase db = mMascotaHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(MascotasEntry.COLUMNA_MASCOTA_NOMBRE, nombreActual);
        values.put(MascotasEntry.COLUMNA_MASCOTA_RAZA, razaActual);
        values.put(MascotasEntry.COLUMNA_MASCOTA_GENERO, Genero);
        values.put(MascotasEntry.COLUMNA_MASCOTA_PESO, pesoInt);

        long NuevaIDFila = db.insert(MascotasEntry.NOMBRE_TABLA, null,values);

        if (NuevaIDFila == -1){
            Toast.makeText(this, "Error guardando mascota", Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(this, "Se guardo una nueva mascota con el ID : "+ NuevaIDFila , Toast.LENGTH_LONG).show();
        }






    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                GuardarMascota();

                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

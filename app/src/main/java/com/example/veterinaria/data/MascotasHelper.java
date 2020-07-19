package com.example.veterinaria.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.veterinaria.data.VeterinariaContracto.MascotasEntry;

public class MascotasHelper extends SQLiteOpenHelper {

    private static String NOMBRE_DB = "veterinaria.db";

    private static final int VERSION_DB = 1;

    public MascotasHelper(Context context){
        super(context, NOMBRE_DB, null, VERSION_DB);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        //creo DB usando los strings declarado en el contracto

        String SQL_CREAR_TABLA_MASCOTAS = "CREATE TABLE " + MascotasEntry.NOMBRE_TABLA + "("
                + MascotasEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MascotasEntry.COLUMNA_MASCOTA_NOMBRE + " TEXT NOT NULL, "
                + MascotasEntry.COLUMNA_MASCOTA_RAZA + " TEXT, "
                + MascotasEntry.COLUMNA_MASCOTA_GENERO + " INTEGER NOT NULL, "
                + MascotasEntry.COLUMNA_MASCOTA_PESO + " INTEGER NOT NULL DEFAULT 0"
                + ");";

        db.execSQL(SQL_CREAR_TABLA_MASCOTAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

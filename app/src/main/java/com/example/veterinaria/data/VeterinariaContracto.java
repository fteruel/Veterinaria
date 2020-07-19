package com.example.veterinaria.data;

import android.provider.BaseColumns;

public final class VeterinariaContracto {

    private VeterinariaContracto() {
    }

    public static final class MascotasEntry implements BaseColumns {

        /** Nombre de la tabla */
        public final static String NOMBRE_TABLA = "mascotas";

        /**
         * ID de cada mascota (uso interno de la tabla).
         *
         * Tipo: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Nombre de la mascota.
         *
         * Tipo: TEXT
         */
        public final static String COLUMNA_MASCOTA_NOMBRE ="nombre";

        /**
         * raza de la mascota.
         *
         * Tipo: TEXT
         */
        public final static String COLUMNA_MASCOTA_RAZA = "raza";

        /**
         * Genero de la mascota.
         *
         *
         * Tipo: INTEGER
         */
        public final static String COLUMNA_MASCOTA_GENERO = "genero";

        /**
         *Peso de la mascota.
         *
         * Tipo: INTEGER
         */
        public final static String COLUMNA_MASCOTA_PESO = "peso";

        /**
         * Possible values for the gender of the pet.
         */
        public static final int GENERO_DESCONOCIDO = 0;
        public static final int GENERO_MACHO = 1;
        public static final int GENERO_HEMBRA = 2;
    }




}

package com.acostaivan.entucasaoenlamia.util;

import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class SubidaImagenUtil {

    private static final String CARPETA = "img/espacios/";

    public static String guardar(Part part, String rutaBase) throws IOException {

        // Si no se subió ningún archivo
        if (part == null || part.getSize() == 0) return null;

        // Generar nombre único para evitar colisiones
        String extension = obtenerExtension(part.getSubmittedFileName());
        String nombreArchivo = UUID.randomUUID().toString() + "." + extension;

        // Ruta absoluta donde se guarda
        String rutaCompleta = rutaBase + File.separator +
                CARPETA.replace("/", File.separator);

        // Crear carpeta si no existe
        File carpeta = new File(rutaCompleta);
        if (!carpeta.exists()) carpeta.mkdirs();

        // Guardar el archivo
        part.write(rutaCompleta + File.separator + nombreArchivo);

        return CARPETA + nombreArchivo; // ruta relativa para guardar en BD
    }

    private static String obtenerExtension(String nombreOriginal) {
        if (nombreOriginal == null || !nombreOriginal.contains(".")) return "jpg";
        return nombreOriginal.substring(nombreOriginal.lastIndexOf(".") + 1);
    }
}
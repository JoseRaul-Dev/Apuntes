/*Código realizado por José Raúl Álvarez Rodríguez */
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ArchivoApp2 {
    public static void main(String[] args) {

        // Ruta del fichero que vamos a leer en este caso es nuestro programa.
        File fichero = new File("./src/ArchivoApp2.java");

        // Comprobamos si el fichero existe (En este caso si existe ya que estamos mirando nuestro programa).
        if (!fichero.exists()) {
            System.out.println("El fichero no existe");
            return;
        }
        // Comprobamos si es un fichero (En este caso si es un fichero).
        if (!fichero.isFile()) {
            System.out.println("No es un fichero");
            return;
        }
        // Comprobamos si se puede leer el fichero (En este caso si se puede leer).
        if (!fichero.canRead()) {
            System.out.println("No se puede leer el fichero");
            return;
        }

        // StringBuffer almacena los caracteres que vamos leyendo.
        StringBuffer contenido = new StringBuffer();

        // FileReader permite que se pueda leer el fichero carácter a carácter.
        try (FileReader lector = new FileReader(fichero)) {
            int caracter;

            // Mediante este bucle se lee el fichero hasta llegar al final del mismo.
            while ((caracter = lector.read()) != -1) {

                // Se añade cada carácter que se haya leído realizando un casting y añadiendo el contenido al StringBuffer.
                contenido.append((char) caracter);
            }

            // Se muestra el contenido del fichero por pantalla.
            System.out.println(contenido);

            // Mediante este try-catch se controla la excepción que puede producirse al leer el fichero.
        } catch (IOException e) {
            System.out.println("Error al leer el fichero " + e.getMessage());
        }

    }

}

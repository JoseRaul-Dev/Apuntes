/* Código realizado por José Raúl Álvarez Rodríguez */
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class CursosAleatorio {

    // Esta constante indica el número máximo de cursos que puede contener el fichero.
    private static final int MAXIMO_CURSOS = 15;

    // Esta constante indica el número máximo de caracteres de un curso.
    private static final int LONGITUD_NOMBRE = 15;

    // Esta constante indica cuál es el tamaño total de cada registro.
    private static final int TAMAÑO_FINAL_REGISTRO = 42;

    // Estas variables definen los datos de los cursos que vamos a introducir en el fichero.
    private static int[] numeros = { 10, 2, 1, 5 };

    private static String[] nombres = { "Curso10", "Curso2", "Curso1", "Curso5" };

    private static double[] costes = { 4000, 5000, 6000, 3000 };

    public static void main(String[] args) {

        // Creamos el fichero binario de acceso aleatorio.
        File fichero = new File("CursosAleatorio.dat");

        // Abrimos el fichero de forma aleatoria en modo lectura y escritura.
        try (RandomAccessFile archivo = new RandomAccessFile(fichero, "rw")) {

            // Inicializamos los registros totales del fichero(en nuestro caso como hemos definido una constante con 15 cursos máximos, nos aparecerán 15 registros).
            iniciarFichero(archivo);

            // Escribimos los cursos en sus posiciones correspondientes.
            for(int i= 0;i <numeros.length;i++){
                escribirCurso(archivo, numeros[i], nombres[i], costes[i]);
            }

            // Leemos y mostramos todos los cursos por pantalla.
            mostrarCursos(archivo);

        // Mediante esta excepción controlamos los posibles errores que puedan ocurrir al trabajar con el fichero.
        } catch (IOException e) {
            System.out.println("Ha habido un problema al trabajar con el fichero " + e.getMessage());
        }
    }

    // Este método inicia todos los registros de los cursos como vacíos.
    private static void iniciarFichero(RandomAccessFile archivo) {
        try {
            for (int i = 1; i <= MAXIMO_CURSOS; i++) {

                // Calculamos la posición de cada registro.
                long posicion = (long) (i - 1) * TAMAÑO_FINAL_REGISTRO;

                // Movemos el puntero hacia la posición calculada.
                archivo.seek(posicion);

                // -1 indica que el registro está vacío
                archivo.writeInt(-1);

                // Mediante esta llamada a este método reservamos el espacio que corresponde al nombre.
                escribirNombre(archivo, "");

                // Iniciamos el coste a 0.
                archivo.writeDouble(0.0);

            }

        // Mediante esta excepción controlamos los posibles errores que puedan ocurrir al iniciar el fichero.
        } catch (IOException e) {
            System.out.println("Ha habido un problema al iniciar el fichero " + e.getMessage());
        }
    }

    // Escribe el curso en la posición que corresponda.
    private static void escribirCurso(RandomAccessFile archivo, int numero, String nombre, double coste) {

        try {

            // Con este if controlamos que el número del curso sea válido.
            if (numero < 1 || numero > MAXIMO_CURSOS) {
                System.out.println("Numero de curso no valido " + numero);
                return;
            }

            // Mediante esta variable calculamos la posición del registro según su número.
            long posicion = (long) (numero - 1) * TAMAÑO_FINAL_REGISTRO;

            // Apuntamos mediante este método hasta esa posición.
            archivo.seek(posicion);

            // Escribimos los datos del curso.
            archivo.writeInt(numero);

            escribirNombre(archivo, nombre);

            archivo.writeDouble(coste);

        // Mediante esta excepción controlamos los posibles errores que puedan ocurrir al escribir el curso.
        } catch (IOException e) {
            System.out.println("Ha habido un problema al escribir el curso" + e.getMessage());
        }
    }

    // Este método escribe el nombre del registro ocupando siempre el número máximo de caracteres.
    private static void escribirNombre(RandomAccessFile archivo, String nombre) {
        StringBuilder nombreCompleto = new StringBuilder(nombre);
        try {

            // Comprobamos que el nombre no supere el número máximo de caracteres.
            if (nombreCompleto.length() > LONGITUD_NOMBRE) {
                System.out.println("Nombre demasiado largo");
                return;
            }

            // Rellenamos el nombre hasta alcanzar el número máximo de caracteres.
            while (nombreCompleto.length() < LONGITUD_NOMBRE) {
                nombreCompleto.append("\0");
            }

            // Escribimos los caracteres en el fichero.
            archivo.writeChars(nombreCompleto.toString());

        // Mediante esta excepción controlamos los posibles errores que puedan ocurrir al escribir el nombre.
        } catch (IOException e) {
            System.out.println("Ha habido un problema al escribir el nombre " + e.getMessage());
        }
    }

    // Mediante este método se leen los registros del fichero y muestra su contenido.
    private static void mostrarCursos(RandomAccessFile archivo){
    try {

        // Mostramos mediante la opción printf la cabecera de la tabla para alinear correctamente las columnas.
        System.out.printf("%18s | %-5s | %-6s | %-15s | %-10s%n","Posición (Bytes)","Curso","Número","Nombre","Coste");

        // Con este bucle se recorre el máximo de registros del fichero.
        for(int i= 1; i<= MAXIMO_CURSOS;i++){

            // Calculamos la posición exacta del registro.
            long posicion= (long) (i-1) * TAMAÑO_FINAL_REGISTRO;

            // Movemos el puntero hasta esa posición.
            archivo.seek(posicion);

            // Leemos el número del curso.
            int numero=archivo.readInt();

            // Leemos los caracteres que habíamos reservado previamente para el nombre.
            StringBuilder nombre = new StringBuilder();

            for(int j = 0; j < LONGITUD_NOMBRE;j++){
                nombre.append(archivo.readChar());
            }

            // Eliminamos los caracteres que hemos definido previamente como nulos al final del nombre.
            String nombreCur= nombre.toString().replace("\0","").trim();

            // Leemos el coste del curso.
            double coste = archivo.readDouble();

            // Si el número es -1, el registro está vacío.
            if(numero == -1){
                System.out.printf("%18s | %-5s | %-6s | %-15s | %-10s%n",posicion,"-",-1,"(vacío)","-");

            }else{

                // En el caso de que no este vacio,mostramos los datos del curso.
                 System.out.printf("%18s | %-5s | %-6s | %-15s | %-10s%n",posicion,i,numero,nombreCur,coste);
            }

        }

     // Mediante esta excepción controlamos los posibles errores que puedan ocurrir al mostrar los cursos.
    } catch (IOException e) {
        System.out.println("Error al mostrar los cursos " + e.getMessage());
    }
}
}

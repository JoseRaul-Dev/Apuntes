//Código realizado por José Raúl Álvarez Rodríguez
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ListarContenidoDir {

    //Este Scanner se utiliza para leer la opción del menú que pediremos al usuario.
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Bienvenido al programa del ejercicio 1 de la clase File");
        menu();
    }

    //Este metodo es el que se encarga de mostrar el menú y llamar a los métodos según la opción que elija el usuario.
    public static void menu() {
        String opcion;
        do {
            System.out.println("Menú");
            System.out.println("1. Listar contenido del directorio");
            System.out.println("2. Crear dos ficheros .txt");
            System.out.println("3. Listar ficheros .txt");
            System.out.println("4. Salir");
            opcion = sc.nextLine();
                switch (opcion) {
                    case "1":
                        listarContenido();
                        break;
                    case "2":
                        crearFicheros();
                        break;
                    case "3":
                        listarFicherosTxt();
                        break;
                    case "4":
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
        } while (!opcion.equals("4"));
    }

    //Este método se encarga de listar el contenido del directorio.
    public static void listarContenido() {

        //"." representa el directorio actual.
        File directorio = new File(".");

        //Se comprueba si el directorio existe(En este caso como el directorio es el actual siempre va a existir).
        if (!directorio.exists()) {
            System.out.println("El directorio no existe.");
        return;
        }

        //Se comprueba si la ruta que se ha pasado por la variable es un directorio(En este caso como es el directorio actual, es un directorio).
        if (!directorio.isDirectory()) {
            System.out.println("La ruta actual no es un directorio.");
        return;
        }

        //Se obtiene los archivos y directorios que hay dentro del directorio.
        File[] ficheros = directorio.listFiles();

        //En el caso de que la función listFiles() no pueda acceder devolvera un null.Mediante este if lo controlamos.
        if (ficheros == null) {
            System.out.println("No se puede acceder al directorio.");
        return;
    }

    //Recorremos todos los elementos que se han encontrado y los mostramos por pantalla.
        for (File fichero : ficheros) {
            System.out.println("Nombre: " + fichero.getName());
            System.out.println("Se puede leer: " + fichero.canRead());
            System.out.println("Se puede escribir: " + fichero.canWrite());
            System.out.println("Tamaño: " + fichero.length() + " bytes");
            System.out.println("Ruta absoluta: " + fichero.getAbsolutePath());
        }

    }

    //Este método se encarga de crear los dos ficheros .txt en el directorio.
    public static void crearFicheros() {
        File fichero1 = new File("fichero1.txt");
        File fichero2 = new File("fichero2.txt");

        //Controlamos mediante un try-catch los posibles errores al crear los ficheros.
        try {
            if (fichero1.createNewFile()) {
                System.out.println("Fichero1.txt creado.");
            } else {
                System.out.println("El fichero1.txt ya existe.");
            }
            if (fichero2.createNewFile()) {
                System.out.println("Fichero2.txt creado.");
            } else {
                System.out.println("El fichero2.txt ya existe.");
            }
        } catch (IOException e) {
            System.out.println("Error al crear los ficheros " + e.getMessage());
        }
    }

    //Este método se encarga de listar todos los ficheros que terminen en .txt de el directorio.
    public static void listarFicherosTxt() {
        File directorio = new File(".");
        if (!directorio.exists()) {
            System.out.println("El directorio no existe.");
            return;
        }
        if (!directorio.isDirectory()) {
            System.out.println("La ruta actual no es un directorio.");
            return;
        }
        File[] ficheros = directorio.listFiles();

        if (ficheros == null) {
            System.out.println("No se puede acceder al directorio.");
            return;
        }

        boolean hayTxt=false;

        //Recorremos todos los elementos del directorio.
        for (File fichero : ficheros) {

            //Comprobamos que el fichero sea un archivo y que termine en .txt.
            if (fichero.isFile() && fichero.getName().toLowerCase().endsWith(".txt")) {
                System.out.println(fichero.getName());
                hayTxt=true;
            }
        }

        //Si no se ha encontrado ningún fichero .txt se indicara por consola.
        if (!hayTxt) {
            System.out.println("No hay ficheros .txt en el directorio.");
        }
    }
}

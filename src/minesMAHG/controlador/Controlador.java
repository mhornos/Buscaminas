package minesMAHG.controlador;

import minesMAHG.model.Model;
import minesMAHG.vista.Vista;

import java.util.Scanner;

/**
 * Respon a les accions de l'usuari i les passa a Model.
 */
public class Controlador {

    //VARIABLES GLOBALS:
    /**
     * Guarda informació introduida per l'usuari
     */
    static Scanner scan = new Scanner(System.in);
    /**
     * Guarda la fila i la columna, donades per l'usuari, juntes per després separarles per a un millor sistema de scanneig.
     */
    static String filaColumna;
    /**
     * Separa la columna del String "filaColumna".
     */
    static String columnaString;
    /**
     * Agafa el primer caracter de "filaColumna" i ho pasa a int.
     */
    static int fila;
    /**
     * Pasa el valor del String "columnaString" a int.
     */
    static int columna;
    /**
     * Nombre de files del tauler.
     */
    static int files;
    /**
     * Nombre de columnes del tauler.
     */
    static int columnes;
    /**
     * Nombre de bombes al tauler.
     */
    static int bombes;

    //FUNCIONS:

    /**
     * Pregunta en quin nivell es vol jugar i ajusta el joc.
     */
    public static void escollirNivell(){
        char nivellUsuari;
        Vista.mostrarMissatge("Escolleix el nivell:\n" +
                "   1 - 8x8 (10 bombes)\n" +
                "   2 - 16x16 (40 bombes)\n" +
                "   3 - 16x30 (99 bombes)\n" +
                "   S - Sortir del programa");
        nivellUsuari = scan.next().toUpperCase().charAt(0);
        scan.nextLine();
        switch (nivellUsuari){
            case '1':
                files = 8;
                columnes = 8;
                bombes = 10;
                jugar();
                break;
            case '2':
                files = 16;
                columnes = 16;
                bombes = 40;
                jugar();
                break;
            case '3':
                files = 16;
                columnes = 30;
                bombes = 99;
                jugar();
                break;
            case 'S':
                System.exit(0);
                break;
        }
    }

    /**
     * Crida la funció que inicialitza el joc amb la configuració deseada.
     */
    public static void jugar(){
        String accioUsuari;
        Model.inicialitzarElJoc(files,columnes,bombes);

        do {
            System.out.println("Que vols fer? Trepitjar(T), Bandera(B) o Acabar(A): ");
            accioUsuari = scan.next().toUpperCase().trim();
            scan.nextLine();
            switch(accioUsuari){
                case "T":
                    scanearPosicio();
                    Model.trepitjar(fila, columna);
                    break;

                case "B":
                    scanearPosicio();
                    Model.posarBandera(fila, columna);
                    break;
                case "A":
                    System.out.println("Has acabat correctament.");
                    return;
                default:
                    System.out.println("Opció incorrecta, prova de nou.");
            }

        } while(!Model.consultarSiHaAcabat());
    }

    /**
     * Pregunta la posició amb la cual es vol interactuar per comprimir el codi.
     */
    private static void scanearPosicio(){
        System.out.println("Selecciona FILA(A,B,C...) i COLUMNA(1,2,3...): ");
        filaColumna = scan.nextLine().trim().toUpperCase();
        fila = filaColumna.charAt(0);
        fila -= 65 ;
        String filaColumnaEspacios = filaColumna.replace("","");
        columnaString = filaColumnaEspacios.substring(1);
        columna = Integer.parseInt(columnaString);
        columna -= 1;
    }
}


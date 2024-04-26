package minesMAHG.vista;

/**
 * Mostra a l'usuari les dades que s'han solicitat, rebudes per Model.
 */
public class Vista {

    /**
     * Mostra el tauler per al jugador.
     * @param campDeMines conjunt de caracter que serveixen per determinar el tamany del mapa.
     */
    public static void mostrarCampDeMines(char[][] campDeMines){
        char lletra = 'A';
        int numero = 1;
        int fila = campDeMines.length;
        int columna = campDeMines[0].length;
        System.out.print("   ");
        for (int i = 0; i < columna; i++) {
            if (numero > 9){
                System.out.print(numero + " ");
                numero++;
            } else {
                System.out.print(numero + "  ");
                numero++;
            }
        }
        System.out.println();
        for (int i = 0; i < fila; i++) {
            System.out.print(lletra + "  ");
            lletra++;
            for (int j = 0; j < columna; j++) {
                System.out.print(campDeMines[i][j] + "  ");
            }
            System.out.println();
        }
    }

    /**
     * Mostra un missatge.
     * @param missatge text que es vol mostrar.
     */
    public static void mostrarMissatge(String missatge){
        System.out.println(missatge);
    }
}

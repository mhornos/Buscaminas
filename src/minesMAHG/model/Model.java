package minesMAHG.model;

import minesMAHG.vista.Vista;

/**
 * Rep les peticions de Controlador i envia els resultats a Vista.
 */
public class Model {

    //VARIABLES GLOBALS:
    /**
     *Nombre de files
     */
    static int files;
    /**
     * Nombre de columnes
     */
    static int columnes;
    /**
     * Nombre de bombes
     */
    static int bombes;
    /**
     * Mentre sigui false el bucle do per fer accions estarà actiu, si es true, el joc finalitzarà.
     */
    static boolean jocFinalitzat;
    /**
     *  Emmagatzema la ubicació de les bombes i la informació de les caselles.
     */
    static char[][] minesOcult;
    /**
     * Reflecteix l'estat actual vist pel jugador
     */
    static char[][] minesVisible;

    //FUNCIONS:

    /**
     * Crida totes les funcions para inicialitzar el joc.
     * @param f defineix el numero de files al inicialitzar el joc.
     * @param c defineix el numero de columnes al inicialitzar el joc.
     * @param b defineix el numero de bombes al inicialitzar el joc.
     */
    public static void inicialitzarElJoc(int f, int c, int b) {
        files = f;
        columnes = c;
        bombes = b;
        minesOcult = new char[f][c];
        minesVisible = new char[f][c];
        jocFinalitzat = false;

        inicialitzarCampDeMines(minesOcult, ' ');
        inicialitzarCampDeMines(minesVisible, '·');
        posarBombesAleatories();
        comptarBombes();
        Vista.mostrarCampDeMines(minesOcult);
        System.out.println();
        Vista.mostrarCampDeMines(minesVisible);
    }

    /**
     * Verifica les files y columnes existents.
     * @param fila numero de fila
     * @param columna numero de columna
     * @return false si el numero de fila o columna es passa del limit.
     */
    public static boolean verificarFilesYColumnes(int fila, int columna){
        if (fila < 0 || columna < 0 || fila >= files || columna >= columnes){
            return false;
        }
        else return true;
    }

    /**
     * Crea el tauler del joc amb les seves caselles.
     * @param campDeMines conjunt de caracter que serveixen per determinar el tamany del mapa.
     * @param casella serveix per definir quin caracter s'asigna a cada posició
     */
    private static void inicialitzarCampDeMines(char[][] campDeMines, char casella){
        int fila = campDeMines.length;
        int columna = campDeMines[0].length;
        for (int i = 0; i < fila; i++) {
            for (int j = 0; j < columna; j++) {
                campDeMines[i][j] = casella;
           }
        }
    }

    /**
     * Posa les bombes aleatòriament en el camp.
     */
    private static void posarBombesAleatories(){
        char bomba = 'B';

        for (int i = 0; i < bombes; i++) {
            int posicioFila = (int)(Math.random() * files);
            int posicioColumna = (int)(Math.random() * columnes);
            while(minesOcult[posicioFila][posicioColumna] == bomba){
                posicioFila = (int)(Math.random() * files);
                posicioColumna = (int)(Math.random() * columnes);
            }
            minesOcult[posicioFila][posicioColumna] = bomba;
        }

    }

    /**
     * Compta quantes bombes té cada casella al voltant.
     */
    private static void comptarBombes(){
        int comptadorBombes = 0;
        for (int i = 0; i < files; i++) {
            for (int j = 0; j < columnes; j++) {
                if (minesOcult[i][j] != 'B'){
                    if(verificarFilesYColumnes(i-1,j-1) && minesOcult[i-1][j-1] == 'B'){
                        comptadorBombes++;
                    }
                    if(verificarFilesYColumnes(i,j-1) && minesOcult[i][j-1] == 'B'){
                        comptadorBombes++;
                    }
                    if(verificarFilesYColumnes(i+1,j-1) && minesOcult[i+1][j-1] == 'B'){
                        comptadorBombes++;
                    }
                    if(verificarFilesYColumnes(i-1,j) && minesOcult[i-1][j] == 'B'){
                        comptadorBombes++;
                    }
                    if(verificarFilesYColumnes(i,j+1) && minesOcult[i][j+1] == 'B'){
                        comptadorBombes++;
                    }
                    if(verificarFilesYColumnes(i-1,j+1) && minesOcult[i-1][j+1] == 'B'){
                        comptadorBombes++;
                    }
                    if(verificarFilesYColumnes(i+1,j) && minesOcult[i+1][j] == 'B'){
                        comptadorBombes++;
                    }
                    if(verificarFilesYColumnes(i+1,j+1) && minesOcult[i+1][j+1] == 'B'){
                        comptadorBombes++;
                    }
                    minesOcult[i][j] = (char) (comptadorBombes + '0');
                    comptadorBombes = 0;
                }
            }
        }
    }

    /**
     * Destapa la casella i sabem si hem perdut per una bomba o es mostren les caselles mes properes sense bombes.
     * @param fila numero de fila
     * @param columna numero de columna
     */
    public static void trepitjar(int fila, int columna){
        if (!verificarFilesYColumnes(fila, columna) || minesVisible[fila][columna] != '·') {
            return;
        }
        trepitjarRecursivament(fila, columna);

        minesVisible[fila][columna] = minesOcult[fila][columna];
        if (minesVisible[fila][columna] == 'B'){
            Vista.mostrarMissatge("HAS PERDUT...");
            mostrarSolucioIErrors();
            jocFinalitzat = true;
        }
        else {
            Vista.mostrarCampDeMines(minesVisible);
        }
        if (comprovarSiHaGuanyat()) {
            Vista.mostrarMissatge("HAS GUANYAT. ENHORABONA!!!");
            jocFinalitzat = true;
        }
    }

    /**
     * Coloca una bandera a la casilla o la treu si ja havia una.
     * @param fila numero de fila
     * @param columna numero de columna
     */
    public static void posarBandera(int fila, int columna){
        if (minesVisible[fila][columna] == minesOcult[fila][columna])
            Vista.mostrarMissatge("No es pot col·locar la bandera, ja s'ha treptijat aquesta casella");
        if (minesVisible[fila][columna] != 'B') {
            minesVisible[fila][columna] = 'B' ;
            }
         else {
             minesVisible[fila][columna] = '·';
        }
        if (comprovarSiHaGuanyat()) {
            Vista.mostrarMissatge("HAS GUANYAT. ENHORABONA!!!");
            Vista.mostrarCampDeMines(minesVisible);
            jocFinalitzat = true;
        }
         Vista.mostrarCampDeMines(minesVisible);
    }

    /**
     * Consulta si el joc ha finalitzat.
     * @return si el joc ha acabat retorna true, si no false.
     */
    public static boolean consultarSiHaAcabat(){
        return jocFinalitzat;
    }

    /**
     * Consulta si ha guanyat
     * @return si el camp ocult és igual al camp visible retorna true, si no false.
     */
    private static boolean comprovarSiHaGuanyat(){
        for (int i = 0; i < files; i++) {
            for (int j = 0; j < columnes; j++) {
                if (minesOcult[i][j] != minesVisible[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Mostra el camp ocult pel jugador que té la solució.
     */
    private static void mostrarSolucioIErrors(){
        Vista.mostrarCampDeMines(minesOcult);
    }

    /**
     * Destapa les caselles del voltant d'una casella destapada depenent de si hi han bombes al voltant.
     * @param fila numero de fila
     * @param columna numero de columna
     */
    private static void trepitjarRecursivament(int fila, int columna) {
        if (!verificarFilesYColumnes(fila, columna) || minesVisible[fila][columna] != '·') {
            return;
        }

        minesVisible[fila][columna] = minesOcult[fila][columna];

        if (minesVisible[fila][columna] == '0') {
            trepitjarRecursivament(fila - 1, columna - 1);
            trepitjarRecursivament(fila, columna - 1);
            trepitjarRecursivament(fila + 1, columna - 1);
            trepitjarRecursivament(fila - 1, columna);
            trepitjarRecursivament(fila + 1, columna);
            trepitjarRecursivament(fila - 1, columna + 1);
            trepitjarRecursivament(fila, columna + 1);
            trepitjarRecursivament(fila + 1, columna + 1);
        }
    }
}


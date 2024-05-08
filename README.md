# BUSCAMINES en Java 💣
¡Benvingut al meu projecte de Buscamines en Java! Aquesta aplicació implementa el clàssic joc de Buscamines amb diverses funcionalitats interessants i una interfície d'usuari intuïtiva:
- El Buscamines en Java és una aplicació de terminal que permet als jugadors gaudir del clàssic joc de Buscamines en el seu ordinador. Està desenvolupat en Java i utilitza una arquitectura MVC (Model-Vista-Controlador) per a una organització clara i modular del codi.

## Comença a jugar 🕹
Per començar a jugar al Buscamines en Java, simplement segueix aquests passos:
(Des de la consola)
1. Descarregar el projecte i descomprimir-ho.
1. Obrir el terminal (cmd.exe)
1. Anar al directori on s'ha creat l'arxiu JAR, per exemple, `cd C:\Users\Miguel Ángel\Downloads\Buscaminas-master`.
1. Executar l'arxiu JAR amb la comanda `java -jar MinesMAHG.jar`.

## Contribucions 🤗
- Les contribucions són benvingudes! Si tens alguna idea per millorar el joc o trobes algun error, si us plau, obre un "**issue**" o envia una "**pull request**".


## Funcionalitats destacades 👩‍💻
### Selecció de Nivells
- Els jugadors poden triar entre tres nivells de dificultat: 8x8, 16x16 i 16x30, amb el nombre corresponent de bombes. Aquesta funcionalitat ofereix una experiència personalitzada per a cada jugador.
```java
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
```

### Interacció intuïtiva
- El programa captura les entrades de l'usuari de manera clara i intuïtiva, permetent-los seleccionar les caselles i realitzar accions com destapar o posar banderes de forma fàcil.
```java
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
```

### Algorismes intel·ligents
- El programa utilitza algorismes per calcular automàticament el nombre de bombes en les cel·les adjacents, creant una experiència de joc més fluida i autònoma.
```java
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
```

### Destapat recursiu de caselles
- El mètode `trepitjarRecursivament` s'encarrega de destapar de forma recursiva les caselles adjacents a una casella específica al tauler del Buscamines. Aquest mètode s'utilitza quan un jugador destapa una casella sense bombes adjacents, cosa que permet revelar automàticament totes les caselles al voltant sense bombes.
```java
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
```

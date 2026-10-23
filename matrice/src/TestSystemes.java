import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;

public class TestSystemes {

    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    private static Class classe = Matrice.class;
    private static Field data;
    private static Field nbLignes;
    private static Field nbColonnes;
    private static Method permuter ;
    private static Method remplacer ;
    private static final Scanner scanner = new Scanner(System.in);
    private static final String[] NOMS_METHODES = {
            "de la méthode permuter(int l1, int l2)",
            "de la méthode remplacer(int l1, double a, int l2)",
            "de la méthode echelonner()",
            "de la méthode estEchelonnee()",
            "de la méthode resoudreSysteme()" };

    public static void main(String[] args) throws IllegalAccessException  {
        Field[] champs = classe.getDeclaredFields();
        for (Field f : champs) {
            if (f.getType() == double[][].class) {
                data = f;
                data.setAccessible(true);
                if (!Modifier.isPrivate(f.getModifiers())) {
                    System.out.println("\nLE TABLEAU " + f.getName()
                            + " DOIT ÊTRE MIS EN PRIVATE !!!");
                    return;
                }
            } else if (f.getType() == int.class) {
                if (f.getName().equals("nbLignes")) {
                    nbLignes = f;
                    nbLignes.setAccessible(true);
                } else if (f.getName().equals("nbColonnes")) {
                    nbColonnes = f;
                    nbColonnes.setAccessible(true);
                }
                if (!Modifier.isPrivate(f.getModifiers())) {
                    System.out.println("\nVOUS AURIEZ DU DECLARER LE CHAMP "
                            + f.getName() + " EN PRIVATE!!!");
                    return;
                }

            } else {
                System.out.println("QUE REPRESENTE LE CHAMP " + f.getName()
                        + " DE VOTRE CLASSE Matrice?");
                return;
            }
        }
        if (data == null) {
            System.out
                    .println("Vous n'avez pas créé de champ de type double[][]");
            return;
        }
        if (nbLignes == null) {
            System.out
                    .println("Vous avez enlevé le champ pour le nombre de lignes.");
            return;
        }
        if (nbColonnes == null) {
            System.out
                    .println("Vous avez enlevé le champ pour le nombre de colonnes.");
            return;
        }
        Method[] methods = classe.getDeclaredMethods() ;
        for (Method m : methods) {
            if (m.getName().equals("permuter")) {
                permuter = m ;
                permuter.setAccessible(true);
            } else if (m.getName().equals("remplacer")) {
                remplacer = m ;
                remplacer.setAccessible(true);
            }
        }

        System.out.println("**************************************");
        System.out.println("Programme Test pour la classe Matrice");
        System.out.println("**************************************");

        int choix = 1;
        while (choix>0 && choix <6) {
            for (int i = 0; i < NOMS_METHODES.length; i++) {
                System.out.println((i + 1) + " -> Test " + NOMS_METHODES[i]);
            }
            System.out.println("autre -> Quitter");

            choix = scanner.nextInt();
            boolean testOK = true ;
            switch (choix) {
                case 1:
                    testOK = testPermuter();
                    break;
                case 2:
                    testOK = testRemplacer();
                    break;
                case 3:
                    testOK = testEchelonner();
                    break;
                case 4:
                    testOK = testEstEchelonnee();
                    break;
                case 5:
                    testOK = testResoudreSysteme();
                    break;
                default: return ;
            }
            if (testOK)
                System.out.println("Les tests " + NOMS_METHODES[choix - 1]
                        + " ont réussi.");
            else
                System.out.println("Les tests " + NOMS_METHODES[choix - 1]
                        + " ont échoué.");
            System.out.println();
        }
    }

    private static boolean testConstructeur1() throws IllegalAccessException {
        Matrice m;
        boolean testOK = true;
        m = new Matrice(5, 3);
        double[][] coefficients = (double[][]) data.get(m);
        int nbLignes = TestSystemes.nbLignes.getInt(m);
        int nbColonnes = TestSystemes.nbColonnes.getInt(m);
        if (coefficients == null) {
            System.out.println("L'attribut " + data.getName()
                    + " a été initialisé à null.");
            testOK = false;
        } else if (coefficients.length != 5) {
            System.out.println("Le tableau " + data.getName()
                    + " n'a pas le bon nombre de lignes.");
            testOK = false;
        } else {
            for (int i = 0; i < 5; i++) {
                if (coefficients[i] == null) {
                    System.out.println("Le tableau d'indice " + i
                            + " de l'attribut " + data.getName()
                            + " a été initialisé à null !");
                    testOK = false;
                    break;
                } else if (coefficients[i].length != 3) {
                    System.out.println("Le tableau d'indice " + i
                            + " de l'attribut " + data.getName()
                            + " n'a pas la bonne dimension !");
                    testOK = false;
                    break;
                }
            }
        }
        if (testOK) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 3; j++) {
                    if (coefficients[i][j] != 0) {
                        System.out
                                .println("On a créé la matrice nulle de taille (5,3)");
                        System.out
                                .println("Les éléments de la matrice ne sont pas tous à 0.");
                        testOK = false;
                        break;
                    }
                }
                if (!testOK)
                    break;
            }
        }
        if (nbLignes != 5) {
            System.out.println("L'attribut " + TestSystemes.nbLignes.getName()
                    + " est mal initialisé.");
            testOK = false;
        }
        if (nbColonnes != 3) {
            System.out.println("L'attribut " + TestSystemes.nbColonnes.getName()
                    + " est mal initialisé.");
            testOK = false;
        }
        try {
            new Matrice(0, 6);
            System.out
                    .println("Création d'une matrice ayant 0 ligne --> Il fallait lancer une IllegalArgumentException");
            testOK = false;
        } catch (IllegalArgumentException e) {
        }
        try {
            new Matrice(-1, 6);
            System.out
                    .println("Création d'une matrice ayant -1 ligne --> Il fallait lancer une IllegalArgumentException");
            testOK = false;
        } catch (IllegalArgumentException e) {
        }
        try {
            new Matrice(3, 0);
            System.out
                    .println("Création d'une matrice ayant 0 colonne  --> Il fallait lancer une IllegalArgumentException");
            testOK = false;
        } catch (IllegalArgumentException e) {
        }
        try {
            new Matrice(3, -2);
            System.out
                    .println("Création d'une matrice ayant -2 colonne  --> Il fallait lancer une IllegalArgumentException");
            testOK = false;
        } catch (IllegalArgumentException e) {
        }
        return testOK;
    }

    private static boolean testPermuter() throws IllegalAccessException {
        if (!testConstructeur1()) {
            System.out
                    .println("Il faut d'abord écrire le constructeur Matrice(double[][] tab) de manière correcte.");
            return false;
        }

        boolean testOK =true ;

        // test1
        System.out.println(" -------- ");
        System.out.println("| Test 1 |");
        System.out.println(" -------- ");
        Matrice m = new Matrice(4,3) ;
        double[][] tab = new double[][] {{1,-1,0},{1,2,0},{4,2,-1},{0,-2,3}} ;
        double[][] tabInit = new double[][] {{1,-1,0},{1,2,0},{4,2,-1},{0,-2,3}} ;
        double[][] tabAttendue = new double[][] {{1,-1,0},{0,-2,3},{4,2,-1},{1,2,0}} ;
        try {
            data.set(m, tab);
            permuter.invoke(m,3,1) ;
            if (!Arrays.deepEquals(tabAttendue,(double[][])data.get(m))) {
                testOK = false ;
                System.out.println("KO");
                System.out.println("Pour la matrice ");
                System.out.println(toStringTableau(tabInit));
                System.out.println("On demandait de faire l_4 <--> l_2");
                System.out.println("Matrice Attendue : ");
                System.out.println(toStringTableau(tabAttendue));
                System.out.println("Matrice reçue : ");
                System.out.println(toStringTableau((double[][])data.get(m)));
            } else if (nbLignes.getInt(m)!=4) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m)!=3) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch(Exception e) {
            testOK = false ;
            System.out.println("Il ne fallait pas d'exception : ") ;
            System.out.println(RED) ;
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        // test2
        System.out.println(" -------- ");
        System.out.println("| Test 2 |");
        System.out.println(" -------- ");
        m = new Matrice(4,3) ;
        tab = new double[][] {{1,-1,0},{1,2,0},{4,2,-1},{0,-2,3}} ;
        tabInit = new double[][] {{1,-1,0},{1,2,0},{4,2,-1},{0,-2,3}} ;
        tabAttendue = new double[][] {{0,-2,3},{1,2,0},{4,2,-1},{1,-1,0}} ;
        try {
            data.set(m, tab);
            permuter.invoke(m,0,3) ;
            if (!Arrays.deepEquals(tabAttendue,(double[][])data.get(m))) {
                testOK = false ;
                System.out.println("KO");
                System.out.println("Pour la matrice ");
                System.out.println(toStringTableau(tabInit));
                System.out.println("On demandait de faire l_1 <--> l_4");
                System.out.println("Matrice Attendue : ");
                System.out.println(toStringTableau(tabAttendue));
                System.out.println("Matrice reçue : ");
                System.out.println(toStringTableau((double[][])data.get(m)));
            } else if (nbLignes.getInt(m)!=4) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m)!=3) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch(Exception e) {
            testOK = false ;
            System.out.println("Il ne fallait pas d'exception : ") ;
            System.out.println(RED) ;
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        // test3
        System.out.println(" -------- ");
        System.out.println("| Test 3 |");
        System.out.println(" -------- ");
        m = new Matrice(4,3) ;
        tab = new double[][] {{1,-1,0},{1,2,0},{4,2,-1},{0,-2,3}} ;
        tabInit = new double[][] {{1,-1,0},{1,2,0},{4,2,-1},{0,-2,3}} ;
        tabAttendue = new double[][] {{1,-1,0},{1,2,0},{4,2,-1},{0,-2,3}} ;
        try {
            data.set(m, tab);
            permuter.invoke(m,2,2) ;
            if (!Arrays.deepEquals(tabAttendue,(double[][])data.get(m))) {
                testOK = false ;
                System.out.println("KO");
                System.out.println("Pour la matrice ");
                System.out.println(toStringTableau(tabInit));
                System.out.println("On demandait de faire l_3 <--> l_3");
                System.out.println("Matrice Attendue : ");
                System.out.println(toStringTableau(tabAttendue));
                System.out.println("Matrice reçue : ");
                System.out.println(toStringTableau((double[][])data.get(m)));
            } else if (nbLignes.getInt(m)!=4) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m)!=3) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch(Exception e) {
            testOK = false ;
            System.out.println("Il ne fallait pas d'exception : ") ;
            System.out.println(RED) ;
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }
        System.out.println();
        return testOK ;
    }

    private static boolean testRemplacer() throws IllegalAccessException {
        if (!testConstructeur1()) {
            System.out
                    .println("Il faut d'abord écrire le constructeur Matrice(double[][] tab) de manière correcte.");
            return false;
        }

        boolean testOK = true;

        // test1
        System.out.println(" -------- ");
        System.out.println("| Test 1 |");
        System.out.println(" -------- ");
        Matrice m = new Matrice(4, 3);
        double[][] tab = new double[][]{{1, -1, 0}, {1, 2, 0}, {4, 2, -1}, {0, -2, 3}};
        double[][] tabInit = new double[][] {{1, -1, 0}, {1, 2, 0}, {4, 2, -1}, {0, -2, 3}} ;
        double[][] tabAttendue = new double[][]{{1, -1, 0}, {1, 2, 0}, {4, 2, -1}, {-3, -8, 3}};
        try {
            data.set(m, tab);
            remplacer.invoke(m,3,3,1) ;
            if (!Arrays.deepEquals(tabAttendue, (double[][]) data.get(m))) {
                testOK = false;
                System.out.println("KO");
                System.out.println("Pour la matrice ");
                System.out.println(toStringTableau(tabInit));
                System.out.println("On demandait de faire l_4 <-- l_4 - 3*l_2");
                System.out.println("Matrice Attendue : ");
                System.out.println(toStringTableau(tabAttendue));
                System.out.println("Matrice reçue : ");
                System.out.println(toStringTableau((double[][]) data.get(m)));
            } else if (nbLignes.getInt(m) != 4) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m) != 3) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch (Exception e) {
            testOK = false;
            System.out.println("Il ne fallait pas d'exception : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        // test2
        System.out.println(" -------- ");
        System.out.println("| Test 2 |");
        System.out.println(" -------- ");
        m = new Matrice(3, 5);
        tab = new double[][]{{1, -1, 0, 2, 3}, {1, 2, 0,-4, 2},{0, 2, 3, -1, 1 }};
        tabInit = new double[][]    {{1, -1, 0, 2, 3}, {1, 2, 0,-4, 2},{0, 2, 3, -1, 1 }} ;
        tabAttendue = new double[][]{{1, 0, 1.5, 1.5, 3.5}, {1, 2, 0,-4, 2},{0, 2, 3, -1, 1 }};
        try {
            data.set(m, tab);
            remplacer.invoke(m,0,-0.5,2) ;
            if (!Arrays.deepEquals(tabAttendue, (double[][]) data.get(m))) {
                testOK = false;
                System.out.println("KO");
                System.out.println("Pour la matrice ");
                System.out.println(toStringTableau(tabInit));
                System.out.println("On demandait de faire l_4 <-- l_4 - 3*l_2");
                System.out.println("Matrice Attendue : ");
                System.out.println(toStringTableau(tabAttendue));
                System.out.println("Matrice reçue : ");
                System.out.println(toStringTableau((double[][]) data.get(m)));
            } else if (nbLignes.getInt(m) != 3) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m) != 5) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch (Exception e) {
            testOK = false;
            System.out.println("Il ne fallait pas d'exception : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        // test3
        System.out.println(" -------- ");
        System.out.println("| Test 3 |");
        System.out.println(" -------- ");
        m = new Matrice(3, 5);
        tab = new double[][]{{1, -1, 0, 2, 3}, {1, 2, 0,-4, 2},{0, 2, 3, -1, 1 }};
        tabInit = new double[][]    {{1, -1, 0, 2, 3}, {1, 2, 0,-4, 2},{0, 2, 3, -1, 1 }} ;
        tabAttendue = new double[][] {{1, -1, 0, 2, 3}, {1, 2, 0,-4, 2},{0, 2, 3, -1, 1 }} ;
        try {
            data.set(m, tab);
            remplacer.invoke(m,0,0,2) ;
            if (!Arrays.deepEquals(tabAttendue, (double[][]) data.get(m))) {
                testOK = false;
                System.out.println("KO");
                System.out.println("Pour la matrice ");
                System.out.println(toStringTableau(tabInit));
                System.out.println("On demandait de faire l_4 <-- l_4 - 3*l_2");
                System.out.println("Matrice Attendue : ");
                System.out.println(toStringTableau(tabAttendue));
                System.out.println("Matrice reçue : ");
                System.out.println(toStringTableau((double[][]) data.get(m)));
            } else if (nbLignes.getInt(m) != 3) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m) != 5) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch (Exception e) {
            testOK = false;
            System.out.println("Il ne fallait pas d'exception : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        // test4
        System.out.println(" -------- ");
        System.out.println("| Test 4 |");
        System.out.println(" -------- ");
        m = new Matrice(3, 5);
        tab = new double[][]{{1, -1, 0, 2, 3}, {1, 2, 0,-4, 2},{0, 2, 3, -1, 1 }};
        tabInit = new double[][]{{1, -1, 0, 2, 3}, {1, 2, 0,-4, 2},{0, 2, 3, -1, 1 }} ;
        tabAttendue = new double[][] {{1, -1, 0, 2, 3}, {0.75, 1.5, 0,-3, 1.5},{0, 2, 3, -1, 1 }} ;
        try {
            data.set(m, tab);
            remplacer.invoke(m,1,0.25,1) ;
            if (!Arrays.deepEquals(tabAttendue, (double[][]) data.get(m))) {
                testOK = false;
                System.out.println("KO");
                System.out.println("Pour la matrice ");
                System.out.println(toStringTableau(tabInit));
                System.out.println("On demandait de faire l_4 <-- l_4 - 3*l_2");
                System.out.println("Matrice Attendue : ");
                System.out.println(toStringTableau(tabAttendue));
                System.out.println("Matrice reçue : ");
                System.out.println(toStringTableau((double[][]) data.get(m)));
            } else if (nbLignes.getInt(m) != 3) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m) != 5) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch (Exception e) {
            testOK = false;
            System.out.println("Il ne fallait pas d'exception : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        System.out.println();
        return testOK;
    }

    private static boolean testEchelonner() throws IllegalAccessException {
        if (!testConstructeur1()) {
            System.out
                    .println("Il faut d'abord écrire le constructeur Matrice(double[][] tab) de manière correcte.");
            return false;
        }

        boolean testOK = true;

        // test 1
        System.out.println(" -------- ");
        System.out.println("| Test 1 |");
        System.out.println(" -------- ");
        Matrice m = new Matrice(3, 4);
        double[][] tab = new double[][]{{1,-1,1,1},{-4,2,2,2},{2,1,1,-1}} ;
        double[][] tabInit = new double[][]{{1,-1,1,1},{-4,2,2,2},{2,1,1,-1}} ;
        double[][] tableAttendue = new double[][]{{-4,2,2,2},{0,2,2,0},{0,0,2,1.5}} ;
        try {
            data.set(m, tab);
            m.echelonner();
            if (!Arrays.deepEquals(tableAttendue,(double[][]) data.get(m))) {
                System.out.println("On demandait d'échelonner la matrice ");
                System.out.println(toStringTableau(tabInit));
                System.out.println("Matrice attendue : ");
                System.out.println(toStringTableau(tableAttendue));
                System.out.println("Matrice reçue : ");
                System.out.println(toStringTableau((double[][]) data.get(m)));
                testOK = false ;
            } else if (nbLignes.getInt(m)!=3) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m)!=4) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch(Exception e) {
            testOK = false ;
            System.out.println("Il ne fallait pas d'exception : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        // test 2
        System.out.println(" -------- ");
        System.out.println("| Test 2 |");
        System.out.println(" -------- ");
        m = new Matrice(3, 6);
        tab = new double[][]{{4,2,-2,5,5,6},{-1,-1,-1,2, 3,0 },{-2,3,5,0,1,-1}} ;
        tabInit = new double[][]{{4,2,-2,5,5,6},{-1,-1,-1,2, 3,0 },{-2,3,5,0,1,-1}} ;
        tableAttendue = new double[][]{{4,2,-2,5,5,6},{0,4,4,2.5, 3.5, 2.0 },{0,0,-1,3.5625,4.6875,1.75}} ;
        try {
            data.set(m, tab);
            m.echelonner();
            if (!Arrays.deepEquals(tableAttendue,(double[][]) data.get(m))) {
                System.out.println("On demandait d'échelonner la matrice ");
                System.out.println(toStringTableau(tabInit));
                System.out.println("Matrice attendue : ");
                System.out.println(toStringTableau(tableAttendue));
                System.out.println("Matrice reçue : ");
                System.out.println(toStringTableau((double[][]) data.get(m)));
                testOK = false ;
            } else if (nbLignes.getInt(m)!=3) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m)!=6) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch(Exception e) {
            testOK = false ;
            System.out.println("Il ne fallait pas d'exception : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        // test 3
        System.out.println(" -------- ");
        System.out.println("| Test 3 |");
        System.out.println(" -------- ");
        m = new Matrice(6, 3);
        tab = new double[][]{{4,-1, -2},{2,-2,3},{-2, -1,5},{4,2,0} ,{5,4, 1}, {1, 0, -1}} ;
        tabInit = new double[][]{{4,-1, -2},{2,-2,3},{-2, -1,5},{4,2,0} ,{5,4, 1}, {1, 0, -1}} ;
        tableAttendue = new double[][]{{5,4,1},{0,-4.2,-2.8},{0,0,5},{0,0,0}, {0,0,0}, {0,0,0}} ;
        try {
            data.set(m, tab);
            m.echelonner();
            if (!Arrays.deepEquals(tableAttendue,(double[][]) data.get(m))) {
                System.out.println("On demandait d'échelonner la matrice ");
                System.out.println(toStringTableau(tabInit));
                System.out.println("Matrice attendue : ");
                System.out.println(toStringTableau(tableAttendue));
                System.out.println("Matrice reçue : ");
                System.out.println(toStringTableau((double[][]) data.get(m)));
                testOK = false ;
            } else if (nbLignes.getInt(m)!=6) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m)!=3) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch(Exception e) {
            testOK = false ;
            System.out.println("Il ne fallait pas d'exception : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        // test 4
        System.out.println(" -------- ");
        System.out.println("| Test 4 |");
        System.out.println(" -------- ");
        m = new Matrice(6, 1);
        tab = new double[][]{{4},{2},{-2},{7} ,{5}, {1}} ;
        tabInit = new double[][]{{4},{2},{-2},{7} ,{5}, {1}} ;
        tableAttendue = new double[][]{{7},{0},{0},{0} ,{0}, {0}} ;
        try {
            data.set(m, tab);
            m.echelonner();
            if (!Arrays.deepEquals(tableAttendue,(double[][]) data.get(m))) {
                System.out.println("On demandait d'échelonner la matrice ");
                System.out.println(toStringTableau(tabInit));
                System.out.println("Matrice attendue : ");
                System.out.println(toStringTableau(tableAttendue));
                System.out.println("Matrice reçue : ");
                System.out.println(toStringTableau((double[][]) data.get(m)));
                testOK = false ;
            } else if (nbLignes.getInt(m)!=6) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m)!=1) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch(Exception e) {
            testOK = false ;
            System.out.println("Il ne fallait pas d'exception : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        // test 5
        System.out.println(" -------- ");
        System.out.println("| Test 5 |");
        System.out.println(" -------- ");
        m = new Matrice(1, 6);
        tab = new double[][]{{4,2,-2,7,5,1}} ;
        tabInit = new double[][]{{4,2,-2,7,5,1}} ;
        tableAttendue = new double[][]{{4,2,-2,7,5,1}} ;
        try {
            data.set(m, tab);
            m.echelonner();
            if (!Arrays.deepEquals(tableAttendue,(double[][]) data.get(m))) {
                System.out.println("On demandait d'échelonner la matrice ");
                System.out.println(toStringTableau(tabInit));
                System.out.println("Matrice attendue : ");
                System.out.println(toStringTableau(tableAttendue));
                System.out.println("Matrice reçue : ");
                System.out.println(toStringTableau((double[][]) data.get(m)));
                testOK = false ;
            } else if (nbLignes.getInt(m)!=1) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m)!=6) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch(Exception e) {
            testOK = false ;
            System.out.println("Il ne fallait pas d'exception : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        System.out.println();

        // test 6
        System.out.println(" -------- ");
        System.out.println("| Test 6 |");
        System.out.println(" -------- ");
        m = new Matrice(4, 5);
        tab = new double[][]{{-2, -0.5, 3.5, 4,  4.5}, {-1, -1.25, 1.75, 2.5, 0.25},
                {3, 1.25, 3.5, -2.75, -0.25},{-4, 1, 2, 5, 3}};
        tabInit = new double[][]{{-2, -0.5, 3.5, 4,  4.5}, {-1, -1.25, 1.75, 2.5, 0.25},
                {3, 1.25, 3.5, -2.75, -0.25},{-4, 1, 2, 5, 3}};
        tableAttendue = new double[][]{{-4, 1, 2, 5, 3}, {0, 2, 5, 1, 2}, {0, 0, 5, 2, 1}, {0, 0, 0, 0, 3} } ;
        try {
            data.set(m, tab);
            m.echelonner();
            if (!Arrays.deepEquals(tableAttendue,(double[][]) data.get(m))) {
                System.out.println("On demandait d'échelonner la matrice ");
                System.out.println(toStringTableau(tabInit));
                System.out.println("Matrice attendue : ");
                System.out.println(toStringTableau(tableAttendue));
                System.out.println("Matrice reçue : ");
                System.out.println(toStringTableau((double[][]) data.get(m)));
                testOK = false ;
            } else if (nbLignes.getInt(m)!=4) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m)!=5) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch(Exception e) {
            testOK = false ;
            System.out.println("Il ne fallait pas d'exception : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        System.out.println();

        // test 7
        System.out.println(" -------- ");
        System.out.println("| Test 7 |");
        System.out.println(" -------- ");
        m = new Matrice(3, 4);
        tab = new double[][]{{-1, 4, 2, 6}, {-2, -4, 5, 2}, {1, 2, -2.5, -1}} ;
        tabInit = new double[][]{{-1, 4, 2, 6}, {-2, -4, 5, 2}, {1, 2, -2.5, -1}} ;
        tableAttendue = new double[][]{{-2, -4, 5, 2}, {0, 6, -0.5,  5.0}, {0, 0, 0, 0}} ;
        try {
            data.set(m, tab);
            m.echelonner();
            if (!Arrays.deepEquals(tableAttendue,(double[][]) data.get(m))) {
                System.out.println("On demandait d'échelonner la matrice ");
                System.out.println(toStringTableau(tabInit));
                System.out.println("Matrice attendue : ");
                System.out.println(toStringTableau(tableAttendue));
                System.out.println("Matrice reçue : ");
                System.out.println(toStringTableau((double[][]) data.get(m)));
                testOK = false ;
            } else if (nbLignes.getInt(m)!=3) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m)!=4) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch(Exception e) {
            testOK = false ;
            System.out.println("Il ne fallait pas d'exception : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        System.out.println();

        // test 8
        System.out.println(" -------- ");
        System.out.println("| Test 8 |");
        System.out.println(" -------- ");
        m = new Matrice(4, 5);
        tab = new double[][]{{0, -1, 0, 2, 3}, {0, 2, 0,-4, 2},{0, 2, 3, -1, 1 }, {0, -1, 3, 4, 5}};
        tabInit = new double[][] {{0, -1, 0, 2, 3}, {0, 2, 0,-4, 2},{0, 2, 3, -1, 1 }, {0, -1, 3, 4, 5}};
        tableAttendue = new double[][]{{0, 2, 0, -4, 2},{0, 0, 3, 3, -1}, {0, 0, 0, -1, 7},{0, 0, 0, 0, 4}} ;
        try {
            data.set(m, tab);
            m.echelonner();
            if (!Arrays.deepEquals(tableAttendue,(double[][]) data.get(m))) {
                System.out.println("On demandait d'échelonner la matrice ");
                System.out.println(toStringTableau(tabInit));
                System.out.println("Matrice attendue : ");
                System.out.println(toStringTableau(tableAttendue));
                System.out.println("Matrice reçue : ");
                System.out.println(toStringTableau((double[][]) data.get(m)));
                testOK = false ;
            } else if (nbLignes.getInt(m)!=4) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m)!=5) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch(Exception e) {
            testOK = false ;
            System.out.println("Il ne fallait pas d'exception : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        System.out.println();

        // test 9
        System.out.println(" -------- ");
        System.out.println("| Test 9 |");
        System.out.println(" -------- ");
        m = new Matrice(3, 4);
        tab = new double[][]{{4, 4, -2, 2}, {-2, -2, 3, -3},{1, 1, 2, 1}};
        tabInit = new double[][]{{4, 4, -2, 2}, {-2, -2, 3, -3},{1, 1, 2, 1}};
        tableAttendue = new double[][]{{4, 4, -2, 2}, {0, 0, 2.5, 0.5}, {0, 0, 0, -2.4} } ;
        try {
            data.set(m, tab);
            m.echelonner();
            if (!Arrays.deepEquals(tableAttendue,(double[][]) data.get(m))) {
                System.out.println("On demandait d'échelonner la matrice ");
                System.out.println(toStringTableau(tabInit));
                System.out.println("Matrice attendue : ");
                System.out.println(toStringTableau(tableAttendue));
                System.out.println("Matrice reçue : ");
                System.out.println(toStringTableau((double[][]) data.get(m)));
                testOK = false ;
            } else if (nbLignes.getInt(m)!=3) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m)!=4) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch(Exception e) {
            testOK = false ;
            System.out.println("Il ne fallait pas d'exception : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        System.out.println();

        // test 10
        System.out.println(" --------- ");
        System.out.println("| Test 10 |");
        System.out.println(" --------- ");
        m = new Matrice(4, 5);
        tab = new double[][]{{4,1,2,3,-1},{-1,1.75,-1.5,2.25, 1.25},{-2,-1.5,-0.5,-1,-1},{2,1.5,0.5,7,-2}} ;
        tabInit = new double[][]{{4,1,2,3,-1},{-1,1.75,-1.5,2.25, 1.25},{-2,-1.5,-0.5,-1,-1},{2,1.5,0.5,7,-2}} ;
        tableAttendue = new double[][]{{4, 1, 2, 3, -1},{0, 2, -1, 3, 1}, {0, 0, 0, 4, -2},{0, 0, 0, 0, 0}} ;
        try {
            data.set(m, tab);
            m.echelonner();
            if (!Arrays.deepEquals(tableAttendue,(double[][]) data.get(m))) {
                System.out.println("On demandait d'échelonner la matrice ");
                System.out.println(toStringTableau(tabInit));
                System.out.println("Matrice attendue : ");
                System.out.println(toStringTableau(tableAttendue));
                System.out.println("Matrice reçue : ");
                System.out.println(toStringTableau((double[][]) data.get(m)));
                testOK = false ;
            } else if (nbLignes.getInt(m)!=4) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m)!=5) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch(Exception e) {
            testOK = false ;
            System.out.println("Il ne fallait pas d'exception : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        System.out.println();

        // test 11
        System.out.println(" --------- ");
        System.out.println("| Test 11 |");
        System.out.println(" --------- ");
        m = new Matrice(3, 7);
        tab = new double[][]{{0, 2, 0, 0, 2, 1, 4}, {0, 4, 0, 0, -6, 3, 9}, {0, 2, 0, 0, 5, 3, 7}};
        tabInit = new double[][]{{0, 2, 0, 0, 2, 1, 4}, {0, 4, 0, 0, -6, 3, 9}, {0, 2, 0, 0, 5, 3, 7}};
        tableAttendue = new double[][]{{0, 4, 0, 0, -6, 3, 9}, {0, 0, 0, 0, 8, 1.5, 2.5},  {0, 0, 0, 0, 0, -1.4375, -2.0625}};
        try {
            data.set(m, tab);
            m.echelonner();
            if (!Arrays.deepEquals(tableAttendue,(double[][]) data.get(m))) {
                System.out.println("On demandait d'échelonner la matrice ");
                System.out.println(toStringTableau(tabInit));
                System.out.println("Matrice attendue : ");
                System.out.println(toStringTableau(tableAttendue));
                System.out.println("Matrice reçue : ");
                System.out.println(toStringTableau((double[][]) data.get(m)));
                testOK = false ;
            } else if (nbLignes.getInt(m)!=3) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m)!=7) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch(Exception e) {
            testOK = false ;
            System.out.println("Il ne fallait pas d'exception : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        System.out.println();

        return testOK ;
    }

    private static boolean testEstEchelonnee() throws IllegalAccessException {
        if (!testConstructeur1()) {
            System.out
                    .println("Il faut d'abord écrire le constructeur Matrice(double[][] tab) de manière correcte.");
            return false;
        }

        boolean testOK = true;

        // test 1
        System.out.println(" -------- ");
        System.out.println("| Test 1 |");
        System.out.println(" -------- ");

        Matrice m = new Matrice(3, 3);
        double[][] tab = new double[][]{{1, 0, 1}, {0, -2, 2}, {0, 0, -5}};
        double[][] tabInit = new double[][]{{1, 0, 1}, {0, -2, 2}, {0, 0, -5}};
        try {
            data.set(m, tab);
            if (!m.estEchelonnee()) {
                System.out.println("La matrice ");
                System.out.println(toStringTableau(tabInit));
                System.out.println("est échelonnée mais votre méthode dit qu'elle ne l'est pas");
                testOK = false;
            } else if (!Arrays.deepEquals(tabInit,(double[][])data.get(m))) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié la matrice et il ne fallait pas.");
            } else if (nbLignes.getInt(m) != 3) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m) != 3) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch (Exception e) {
            testOK = false;
            System.out.println("Il ne fallait pas d'exception : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }
        System.out.println();

        // test 2
        System.out.println(" -------- ");
        System.out.println("| Test 2 |");
        System.out.println(" -------- ");

        m = new Matrice(3, 3);
        tab = new double[][]{{1, 0, 0}, {0, 4, -3}, {0, 0, 0}};
        tabInit = new double[][]{{1, 0, 0}, {0, 4, -3}, {0, 0, 0}};
        try {
            data.set(m, tab);
            if (!m.estEchelonnee()) {
                System.out.println("La matrice ");
                System.out.println(toStringTableau(tabInit));
                System.out.println("est échelonnée mais votre méthode dit qu'elle ne l'est pas");
                testOK = false;
            } else if (!Arrays.deepEquals(tabInit,(double[][])data.get(m))) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié la matrice et il ne fallait pas.");
            } else if (nbLignes.getInt(m) != 3) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m) != 3) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch (Exception e) {
            testOK = false;
            System.out.println("Il ne fallait pas d'exception : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        System.out.println();

        // test 3
        System.out.println(" -------- ");
        System.out.println("| Test 3 |");
        System.out.println(" -------- ");

        m = new Matrice(1, 1);
        tab = new double[][]{{-2}};
        tabInit = new double[][]{{-2}};
        try {
            data.set(m, tab);
            if (!m.estEchelonnee()) {
                System.out.println("La matrice ");
                System.out.println(toStringTableau(tabInit));
                System.out.println("est échelonnée mais votre méthode dit qu'elle ne l'est pas");
                testOK = false;
            } else if (!Arrays.deepEquals(tabInit,(double[][])data.get(m))) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié la matrice et il ne fallait pas.");
            } else if (nbLignes.getInt(m) != 1) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m) != 1) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch (Exception e) {
            testOK = false;
            System.out.println("Il ne fallait pas d'exception : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        System.out.println();

        // test 4
        System.out.println(" -------- ");
        System.out.println("| Test 4 |");
        System.out.println(" -------- ");

        m = new Matrice(3, 4);
        tab = new double[][]{{-1, -2, 0,1}, {0, 4, -3, 2}, {0, 0, 3, -3}};
        tabInit = new double[][]{{-1, -2, 0, 1}, {0, 4, -3, 2}, {0, 0, 3, -3}};
        try {
            data.set(m, tab);
            if (!m.estEchelonnee()) {
                System.out.println("La matrice ");
                System.out.println(toStringTableau(tabInit));
                System.out.println("est échelonnée mais votre méthode dit qu'elle ne l'est pas");
                testOK = false;
            } else if (!Arrays.deepEquals(tabInit,(double[][])data.get(m))) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié la matrice et il ne fallait pas.");
            } else if (nbLignes.getInt(m) != 3) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m) != 4) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch (Exception e) {
            testOK = false;
            System.out.println("Il ne fallait pas d'exception : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        System.out.println();

        // test 5
        System.out.println(" -------- ");
        System.out.println("| Test 5 |");
        System.out.println(" -------- ");

        m = new Matrice(2, 4);
        tab = new double[][]{{-1, -2, 0,1}, {0, 0, -3, 2}};
        tabInit = new double[][]{{-1, -2, 0, 1}, {0, 0, -3, 2}};
        try {
            data.set(m, tab);
            if (!m.estEchelonnee()) {
                System.out.println("La matrice ");
                System.out.println(toStringTableau(tabInit));
                System.out.println("est échelonnée mais votre méthode dit qu'elle ne l'est pas");
                testOK = false;
            } else if (!Arrays.deepEquals(tabInit,(double[][])data.get(m))) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié la matrice et il ne fallait pas.");
            } else if (nbLignes.getInt(m) != 2) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m) != 4) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch (Exception e) {
            testOK = false;
            System.out.println("Il ne fallait pas d'exception : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        System.out.println();

        // test 6
        System.out.println(" -------- ");
        System.out.println("| Test 6 |");
        System.out.println(" -------- ");

        m = new Matrice(5, 3);
        tab = new double[][]{{0, -2, 0}, {0, 0, 1}, {0,0,0}, {0,0,0}, {0,0,0}};
        tabInit = new double[][]{{0, -2, 0}, {0, 0, 1}, {0,0,0}, {0,0,0}, {0,0,0}};
        try {
            data.set(m, tab);
            if (!m.estEchelonnee()) {
                System.out.println("La matrice ");
                System.out.println(toStringTableau(tabInit));
                System.out.println("est échelonnée mais votre méthode dit qu'elle ne l'est pas");
                testOK = false;
            } else if (!Arrays.deepEquals(tabInit,(double[][])data.get(m))) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié la matrice et il ne fallait pas.");
            } else if (nbLignes.getInt(m) != 5) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m) != 3) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch (Exception e) {
            testOK = false;
            System.out.println("Il ne fallait pas d'exception : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        System.out.println();

        // test 7
        System.out.println(" -------- ");
        System.out.println("| Test 7 |");
        System.out.println(" -------- ");

        m = new Matrice(5, 1);
        tab = new double[][]{{-3}, {0}, {0}, {0}, {0}};
        tabInit = new double[][]{{-3}, {0}, {0}, {0}, {0}};
        try {
            data.set(m, tab);
            if (!m.estEchelonnee()) {
                System.out.println("La matrice ");
                System.out.println(toStringTableau(tabInit));
                System.out.println("est échelonnée mais votre méthode dit qu'elle ne l'est pas");
                testOK = false;
            } else if (!Arrays.deepEquals(tabInit,(double[][])data.get(m))) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié la matrice et il ne fallait pas.");
            } else if (nbLignes.getInt(m) != 5) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m) != 1) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch (Exception e) {
            testOK = false;
            System.out.println("Il ne fallait pas d'exception : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        System.out.println();

        // test 8
        System.out.println(" -------- ");
        System.out.println("| Test 8 |");
        System.out.println(" -------- ");

        m = new Matrice(3, 3);
        tab = new double[][]{{0,0,0}, {0,0,0}, {0,0,0}};
        tabInit = new double[][]{{0,0,0}, {0,0,0}, {0,0,0}};
        try {
            data.set(m, tab);
            if (!m.estEchelonnee()) {
                System.out.println("La matrice ");
                System.out.println(toStringTableau(tabInit));
                System.out.println("est échelonnée mais votre méthode dit qu'elle ne l'est pas");
                testOK = false;
            } else if (!Arrays.deepEquals(tabInit,(double[][])data.get(m))) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié la matrice et il ne fallait pas.");
            } else if (nbLignes.getInt(m) != 3) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m) != 3) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch (Exception e) {
            testOK = false;
            System.out.println("Il ne fallait pas d'exception : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        System.out.println();

        // test 9
        System.out.println(" -------- ");
        System.out.println("| Test 9 |");
        System.out.println(" -------- ");

        m = new Matrice(4, 4);
        tab = new double[][]{{1, -2, 0, 1}, {0, 1, 1, 2}, {0,-1,-1,0}, {0,0,4,0}};
        tabInit = new double[][]{{1, -2, 0, 1}, {0, 1, 1, 2}, {0,-1,-1,0}, {0,0,4,0}};
        try {
            data.set(m, tab);
            if (m.estEchelonnee()) {
                System.out.println("La matrice ");
                System.out.println(toStringTableau(tabInit));
                System.out.println("n'est pas échelonnée mais votre méthode dit qu'elle l'est.");
                testOK = false;
            } else if (!Arrays.deepEquals(tabInit,(double[][])data.get(m))) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié la matrice et il ne fallait pas.");
            } else if (nbLignes.getInt(m) != 4) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m) != 4) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch (Exception e) {
            testOK = false;
            System.out.println("Il ne fallait pas d'exception : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        System.out.println();

        // test 10
        System.out.println(" --------- ");
        System.out.println("| Test 10 |");
        System.out.println(" --------- ");

        m = new Matrice(3, 4);
        tab = new double[][]{{1, -2, 0, 1}, {0, 1, 1, 2}, {1,0,-2,0}};
        tabInit = new double[][]{{1, -2, 0, 1}, {0, 1, 1, 2}, {1,0,-2,0}};
        try {
            data.set(m, tab);
            if (m.estEchelonnee()) {
                System.out.println("La matrice ");
                System.out.println(toStringTableau(tabInit));
                System.out.println("n'est pas échelonnée mais votre méthode dit qu'elle l'est.");
                testOK = false;
            } else if (!Arrays.deepEquals(tabInit,(double[][])data.get(m))) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié la matrice et il ne fallait pas.");
            } else if (nbLignes.getInt(m) != 3) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m) != 4) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch (Exception e) {
            testOK = false;
            System.out.println("Il ne fallait pas d'exception : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        System.out.println();

        // test 11
        System.out.println(" -------- ");
        System.out.println("| Test 11 |");
        System.out.println(" -------- ");

        m = new Matrice(5, 5);
        tab = new double[][]{{1, -2, 0, 1,-3}, {0, 1, 1, 2,- 1}, {0,0,-2,1,3}, {0,0,0,1,5}, {-2,0,0,0,0}};
        tabInit = new double[][]{{1, -2, 0, 1,-3}, {0, 1, 1, 2,- 1}, {0,0,-2,1,3}, {0,0,0,1,5}, {-2,0,0,0,0}};
        try {
            data.set(m, tab);
            if (m.estEchelonnee()) {
                System.out.println("La matrice ");
                System.out.println(toStringTableau(tabInit));
                System.out.println("n'est pas échelonnée mais votre méthode dit qu'elle l'est.");
                testOK = false;
            } else if (!Arrays.deepEquals(tabInit,(double[][])data.get(m))) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié la matrice et il ne fallait pas.");
            } else if (nbLignes.getInt(m) != 5) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m) != 5) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch (Exception e) {
            testOK = false;
            System.out.println("Il ne fallait pas d'exception : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        System.out.println();

        // test 12
        System.out.println(" -------- ");
        System.out.println("| Test 12 |");
        System.out.println(" -------- ");

        m = new Matrice(4, 5);
        tab = new double[][]{{0, -2, 0, 1,-3}, {0, 1, 1, 2,- 1}, {0,0,-2,1,3}, {0,0,0,1,5}};
        tabInit = new double[][]{{0, -2, 0, 1,-3}, {0, 1, 1, 2,- 1}, {0,0,-2,1,3}, {0,0,0,1,5}};
        try {
            data.set(m, tab);
            if (m.estEchelonnee()) {
                System.out.println("La matrice ");
                System.out.println(toStringTableau(tabInit));
                System.out.println("n'est pas échelonnée mais votre méthode dit qu'elle l'est.");
                testOK = false;
            } else if (!Arrays.deepEquals(tabInit,(double[][])data.get(m))) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié la matrice et il ne fallait pas.");
            } else if (nbLignes.getInt(m) != 4) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m) != 5) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch (Exception e) {
            testOK = false;
            System.out.println("Il ne fallait pas d'exception : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        System.out.println();

        // test 13
        System.out.println(" -------- ");
        System.out.println("| Test 13 |");
        System.out.println(" -------- ");

        m = new Matrice(4, 1);
        tab = new double[][]{{5}, {0}, {0}, {1}};
        tabInit = new double[][]{{5}, {0}, {0}, {1}};
        try {
            data.set(m, tab);
            if (m.estEchelonnee()) {
                System.out.println("La matrice ");
                System.out.println(toStringTableau(tabInit));
                System.out.println("n'est pas échelonnée mais votre méthode dit qu'elle l'est.");
                testOK = false;
            } else if (!Arrays.deepEquals(tabInit,(double[][])data.get(m))) {
                testOK = false ;
                System.out.println("Attention ! Vous avez modifié la matrice et il ne fallait pas.");
            } else if (nbLignes.getInt(m) != 4) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m) != 1) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch (Exception e) {
            testOK = false;
            System.out.println("Il ne fallait pas d'exception : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        System.out.println();

        return testOK;
    }

    private static boolean testResoudreSysteme() throws IllegalAccessException {
        if (!testConstructeur1()) {
            System.out
                    .println("Il faut d'abord écrire le constructeur Matrice(double[][] tab) de manière correcte.");
            return false;
        }

        boolean testOK = true;

        // test 1
        System.out.println(" -------- ");
        System.out.println("| Test 1 |");
        System.out.println(" -------- ");
        Matrice m = new Matrice(3, 1);
        double[][] tab = new double[][]{{-4}, {0}, {0}};
        double[][] tabInit = new double[][]{{-4}, {0}, {0}};

        try {
            data.set(m, tab);
            m.resoudreSysteme();
            testOK = false;
            System.out.println("Attention ! Il fallait lancer une MathException");
            System.out.println("La matrice ");
            System.out.println(toStringTableau(tabInit));
            System.out.println("ne représente pas un système !");
        } catch (MathException e) {
            if (!Arrays.deepEquals(tabInit, (double[][]) data.get(m))) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié la matrice et il ne fallait pas.");
            } else if (nbLignes.getInt(m) != 3) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m) != 1) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch (Exception e) {
            testOK = false;
            System.out.println("Mauvais type d'exception : Attendu MathException mais reçu : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        System.out.println();

        // test 2
        System.out.println(" -------- ");
        System.out.println("| Test 2 |");
        System.out.println(" -------- ");
        m = new Matrice(2, 4);
        tab = new double[][]{{-4, 2, 2, 2}, {0, 2, 2, 0}};
        tabInit = new double[][]{{-4, 2, 2, 2}, {0, 2, 2, 0}};

        try {
            data.set(m, tab);
            m.resoudreSysteme();
            testOK = false;
            System.out.println("Attention ! Il fallait lancer une MathException");
            System.out.println("Le système représenté par la matrice ");
            System.out.println(toStringTableau(tabInit));
            System.out.println("n'a pas de solution unique (pas assez d'équations) !");
        } catch (MathException e) {
            if (!Arrays.deepEquals(tabInit, (double[][]) data.get(m))) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié la matrice et il ne fallait pas.");
            } else if (nbLignes.getInt(m) != 2) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m) != 4) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch (Exception e) {
            testOK = false;
            System.out.println("Mauvais type d'exception : Attendu MathException mais reçu : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        System.out.println();

        // test 3
        System.out.println(" -------- ");
        System.out.println("| Test 3 |");
        System.out.println(" -------- ");
        m = new Matrice(3, 4);
        tab = new double[][]{{-2, 3, 3.5, 1}, {-4, 2, 1, 0}, {-1, 1.5, 1.75, 0.75}};
        tabInit = new double[][]{{-2, 3, 3.5, 1}, {-4, 2, 1, 0}, {-1, 1.5, 1.75, 0.75}};
        try {
            data.set(m, tab);
            m.resoudreSysteme();
            testOK = false;
            System.out.println("Attention ! Il fallait lancer une MathException");
            System.out.println("Le système représenté par la matrice ");
            System.out.println(toStringTableau(tabInit));
            System.out.println("n'a pas de solution unique (échelonnage par pivotage partiel impossible) !");
        } catch (MathException e) {
            if (!Arrays.deepEquals(tabInit, (double[][]) data.get(m))) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié la matrice et il ne fallait pas.");
            } else if (nbLignes.getInt(m) != 3) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m) != 4) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch (Exception e) {
            testOK = false;
            System.out.println("Mauvais type d'exception : Attendu MathException mais reçu : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        System.out.println();

        // test 4
        System.out.println(" -------- ");
        System.out.println("| Test 4 |");
        System.out.println(" -------- ");
        m = new Matrice(3, 4);
        tab = new double[][]{{4, 4, -2, 2}, {-2, -2, 3, -3},{1, 1, 2, 1}};
        tabInit = new double[][]{{4, 4, -2, 2}, {-2, -2, 3, -3},{1, 1, 2, 1}};
        try {
            data.set(m, tab);
            m.resoudreSysteme();
            testOK = false;
            System.out.println("Attention ! Il fallait lancer une MathException");
            System.out.println("Le système représenté par la matrice ");
            System.out.println(toStringTableau(tabInit));
            System.out.println("n'a pas de solution unique (échelonnage par pivotage partiel impossible) !");
        } catch (MathException e) {
            if (!Arrays.deepEquals(tabInit, (double[][]) data.get(m))) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié la matrice et il ne fallait pas.");
            } else if (nbLignes.getInt(m) != 3) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m) != 4) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch (Exception e) {
            testOK = false;
            System.out.println("Mauvais type d'exception : Attendu MathException mais reçu : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        System.out.println();

        // test 5
        System.out.println(" -------- ");
        System.out.println("| Test 5 |");
        System.out.println(" -------- ");
        m = new Matrice(5, 4);
        tab = new double[][]{{-1, 4, 2, 6}, {-2, -4, 5, 2}, {-3, 0, 7, 8}, {-4, 4, 9, 14}, {-2,8,4,12}} ;
        tabInit = new double[][]{{-1, 4, 2, 6}, {-2, -4, 5, 2}, {-3, 0, 7, 8}, {-4, 4, 9, 14}, {-2,8,4,12}} ;
        try {
            data.set(m, tab);
            m.resoudreSysteme();
            testOK = false;
            System.out.println("Attention ! Il fallait lancer une MathException");
            System.out.println("Le système représenté par la matrice ");
            System.out.println(toStringTableau(tabInit));
            System.out.println("n'a pas de solution unique (résolution finale impossible) !");
        } catch (MathException e) {
            if (!Arrays.deepEquals(tabInit, (double[][]) data.get(m))) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié la matrice et il ne fallait pas.");
            } else if (nbLignes.getInt(m) != 5) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m) != 4) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch (Exception e) {
            testOK = false;
            System.out.println("Mauvais type d'exception : Attendu MathException mais reçu : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        System.out.println();

        // test 6
        System.out.println(" -------- ");
        System.out.println("| Test 6 |");
        System.out.println(" -------- ");
        m = new Matrice(3, 4);
        tab = new double[][]{{-1, 4, 2, 6}, {-2, -4, 5, 2}, {1, 2, -2.5, -1}} ;
        tabInit = new double[][]{{-1, 4, 2, 6}, {-2, -4, 5, 2}, {1, 2, -2.5, -1}} ;

        try {
            data.set(m, tab);
            m.resoudreSysteme();
            testOK = false;
            System.out.println("Attention ! Il fallait lancer une MathException");
            System.out.println("Le sytème représenté par la matrice ");
            System.out.println(toStringTableau(tabInit));
            System.out.println("n'a pas de solution unique (résolution finale impossible) !");
        } catch (MathException e) {
            if (!Arrays.deepEquals(tabInit, (double[][]) data.get(m))) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié la matrice et il ne fallait pas.");
            } else if (nbLignes.getInt(m) != 3) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m) != 4) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch (Exception e) {
            testOK = false;
            System.out.println("Mauvais type d'exception : Attendu MathException mais reçu : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        System.out.println();

        // test 7
        System.out.println(" -------- ");
        System.out.println("| Test 7 |");
        System.out.println(" -------- ");
        m = new Matrice(3, 3);
        tab = new double[][]{{-4, 8, 10}, {1, -4, 1}, {-2,8,8}};
        tabInit = new double[][]{{-4, 8, 10}, {1, -4, 1}, {-2,8,8}};

        try {
            data.set(m, tab);
            m.resoudreSysteme();
            testOK = false;
            System.out.println("Attention ! Il fallait lancer une MathException");
            System.out.println("Le sytème représenté par la matrice ");
            System.out.println(toStringTableau(tabInit));
            System.out.println("n'a pas de solution unique (résolution finale impossible) !");
        } catch (MathException e) {
            if (!Arrays.deepEquals(tabInit, (double[][]) data.get(m))) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié la matrice et il ne fallait pas.");
            } else if (nbLignes.getInt(m) != 3) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m) != 3) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch (Exception e) {
            testOK = false;
            System.out.println("Mauvais type d'exception : Attendu MathException mais reçu : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        System.out.println();

        // test 8
        System.out.println(" -------- ");
        System.out.println("| Test 8 |");
        System.out.println(" -------- ");
        m = new Matrice(1, 2);
        tab = new double[][]{{-4, 2}};
        tabInit = new double[][]{{-4, 2}};
        double[][] tableAttendue = new double[][]{{-0.5}};
        try {
            data.set(m, tab);
            Matrice solution = m.resoudreSysteme();
            if (m == null) {
                System.out.println("Matrice attendue : ");
                System.out.println(toStringTableau(tableAttendue));
                System.out.println("Matrice reçue : null");
                testOK = false;
            } else if (!Arrays.deepEquals(tableAttendue, (double[][]) data.get(solution))) {
                System.out.println("Matrice attendue : ");
                System.out.println(toStringTableau(tableAttendue));
                System.out.println("Matrice reçue : ");
                System.out.println(toStringTableau((double[][]) data.get(solution)));
                testOK = false;
            } else if (!Arrays.deepEquals(tabInit, (double[][]) data.get(m))) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié la matrice et il ne fallait pas.");
            } else if (nbLignes.getInt(m) != 1) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m) != 2) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch (Exception e) {
            testOK = false;
            System.out.println("Il ne fallait pas d'exception : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        System.out.println();

        // test 9
        System.out.println(" -------- ");
        System.out.println("| Test 9 |");
        System.out.println(" -------- ");
        m = new Matrice(3, 4);
        tab = new double[][]{{1, -1, 1, 1},{-4, 2, 2, 2}, {2, 1, 1, -1}};
        tabInit = new double[][]{{1, -1, 1, 1},{-4, 2, 2, 2}, {2, 1, 1, -1}};
        tableAttendue = new double[][]{{-0.5},{-0.75},{0.75}};
        try {
            data.set(m, tab);
            Matrice solution = m.resoudreSysteme();
            if (m == null) {
                System.out.println("Matrice attendue : ");
                System.out.println(toStringTableau(tableAttendue));
                System.out.println("Matrice reçue : null");
                testOK = false;
            } else if (!Arrays.deepEquals(tableAttendue, (double[][]) data.get(solution))) {
                System.out.println("Matrice attendue : ");
                System.out.println(toStringTableau(tableAttendue));
                System.out.println("Matrice reçue : ");
                System.out.println(toStringTableau((double[][]) data.get(solution)));
                testOK = false;
            } else if (!Arrays.deepEquals(tabInit, (double[][]) data.get(m))) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié la matrice et il ne fallait pas.");
            } else if (nbLignes.getInt(m) != 3) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m) != 4) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch (Exception e) {
            testOK = false;
            System.out.println("Il ne fallait pas d'exception : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        System.out.println();

        // test 10
        System.out.println(" --------- ");
        System.out.println("| Test 10 |");
        System.out.println(" --------- ");
        m = new Matrice(4, 5);
        tab = new double[][]{{1, 2, 3, 4, -9},{2, -1, 1, -3, -2}, {3, 1, -2, 1, -5}, {4, -2, -1, -1, -6}};
        tabInit = new double[][]{{1, 2, 3, 4, -9},{2, -1, 1, -3, -2}, {3, 1, -2, 1, -5}, {4, -2, -1, -1, -6}};
        tableAttendue = new double[][]{{-2},{0},{-1},{-1}};
        try {
            data.set(m, tab);
            Matrice solution = m.resoudreSysteme();
            if (m == null) {
                System.out.println("Matrice attendue : ");
                System.out.println(toStringTableau(tableAttendue));
                System.out.println("Matrice reçue : null");
                testOK = false;
            } else if (!Arrays.deepEquals(tableAttendue, (double[][]) data.get(solution))) {
                System.out.println("Matrice attendue : ");
                System.out.println(toStringTableau(tableAttendue));
                System.out.println("Matrice reçue : ");
                System.out.println(toStringTableau((double[][]) data.get(solution)));
                testOK = false;
            } else if (!Arrays.deepEquals(tabInit, (double[][]) data.get(m))) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié la matrice et il ne fallait pas.");
            } else if (nbLignes.getInt(m) != 4) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m) != 5) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch (Exception e) {
            testOK = false;
            System.out.println("Il ne fallait pas d'exception : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        System.out.println();

        // test 11
        System.out.println(" --------- ");
        System.out.println("| Test 11 |");
        System.out.println(" --------- ");
        m = new Matrice(6, 5);
        tab = new double[][]{{1, 1, 2, 0, -1},{0, 3, -6, 4, -2},{-2, 1, -6, -8, -1},{0, 0, 2, -4, 0},{1, 4, -4, 4, -3},{-1, -0.25, -2.5, -2, 0.25}};
        tabInit = new double[][]{{1, 1, 2, 0, -1},{0, 3, -6, 4, -2},{-2, 1, -6, -8, -1},{0, 0, 2, -4, 0},{1, 4, -4, 4, -3},{-1, -0.25, -2.5, -2, 0.25}};
        tableAttendue = new double[][]{{-2},{0},{0.5},{0.25}};
        try {
            data.set(m, tab);
            Matrice solution = m.resoudreSysteme();
            if (m == null) {
                System.out.println("Matrice attendue : ");
                System.out.println(toStringTableau(tableAttendue));
                System.out.println("Matrice reçue : null");
                testOK = false;
            } else if (!Arrays.deepEquals(tableAttendue, (double[][]) data.get(solution))) {
                System.out.println("Matrice attendue : ");
                System.out.println(toStringTableau(tableAttendue));
                System.out.println("Matrice reçue : ");
                System.out.println(toStringTableau((double[][]) data.get(solution)));
                testOK = false;
            } else if (!Arrays.deepEquals(tabInit, (double[][]) data.get(m))) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié la matrice et il ne fallait pas.");
            } else if (nbLignes.getInt(m) != 6) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbLignes et il ne fallait pas.");
            } else if (nbColonnes.getInt(m) != 5) {
                testOK = false;
                System.out.println("Attention ! Vous avez modifié l'attribut nbColonnes et il ne fallait pas.");
            } else {
                System.out.println("OK");
            }
        } catch (Exception e) {
            testOK = false;
            System.out.println("Il ne fallait pas d'exception : ");
            System.out.println(RED);
            e.printStackTrace(System.out);
            System.out.println(RESET);
        }

        System.out.println();

        return testOK;
    }

    private static String toStringTableau(double[][] tab) {
        int lg = 2+ Stream.of(tab).mapToInt( (t) -> Arrays.stream(t).mapToInt((d)->Double.toString(d).length()).max().getAsInt()).max().getAsInt();
        StringBuilder st = new StringBuilder("");
        Stream.of(tab).forEach((t)-> {
            Arrays.stream(t).forEach((d)-> st.append(String.format("%-"+lg+"s", (d<0)?d:" "+d)));
            st.append('\n');
        });
        return st.substring(0, st.length() - 1);
    }
}

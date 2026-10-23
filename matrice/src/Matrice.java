import java.text.BreakIterator;

public class Matrice {
    private final int nbLignes;              // nombre de lignes
    private final int nbColonnes;            // nombre de colonnes
    private final double[][] data;           // matrice (nbLignes,nbColonnes)

    // ce constructeur cree la matrice nulle de genre (a,b)
    public Matrice(int a, int b) throws IllegalArgumentException {
        //TODO : Les lignes ci-dessous sont là uniquement pour qu'il n'y ait pas d'erreur. Elles doivent être modifiées
        if(a <= 0 || b <= 0) {
            throw new IllegalArgumentException();
        }
        nbLignes=a ;
        nbColonnes=b;
        data = new double[nbLignes][nbColonnes] ;
    }

    //  Ce constructeur permet de construire la matrice correspondant
    //  au tableau en parametre.
    public Matrice(double[][] tab)  throws IllegalArgumentException {
        //TODO : La ligne suivante est là uniquement pour qu'il n'y ait pas d'erreur. Elle doit être modifiée ou supprimée
        if (tab == null || tab.length <= 0) {
            throw new IllegalArgumentException();
        }


        for (int i = 0; i < tab.length; i++) {
            if (tab[i] == null || tab[i].length <= 0) {
                throw new IllegalArgumentException();
            }
        }

        nbLignes = tab.length;
        nbColonnes = tab[0].length;
        data = new double[nbLignes][nbColonnes];

        for (int i = 0; i < nbLignes; i++) {
            if(tab[i].length != nbColonnes) {
                throw new IllegalArgumentException();
            }
            for (int j = 0; j < nbColonnes; j++) {
                data[i][j] = tab[i][j];
            }
        }


    }

    // constructeur par recopie
    public Matrice(Matrice a)  throws IllegalArgumentException {
        //TODO : La ligne suivante est là uniquement pour qu'il n'y ait pas d'erreur. Elle doit être modifiée ou supprimée
        if (a == null) {
            throw new IllegalArgumentException();
        }

        nbLignes = a.nbLignes;
        nbColonnes = a.nbColonnes;
        data = new double[nbLignes][nbColonnes];

        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                data[i][j] = a.data[i][j];
            }
        }
    }

    // cree la matrice identite d'ordre a
    public static Matrice identite(int a)  throws IllegalArgumentException {
        //TODO
        if (a <= 0) {
            throw new IllegalArgumentException();
        }
        Matrice matrice = new Matrice(a,a);
        for (int i = 0; i < matrice.nbLignes; i++) {
            for (int j = 0; j < matrice.nbColonnes; j++) {
                if (i != j) {
                    matrice.data[i][j] = 0;
                } else {
                    matrice.data[i][j]= 1;
                }
            }
        }

        return matrice;
    }

    //Cette methode renvoie l'element de la ligne numLigne et de la
    //colonne numColonne de la matrice. Si cet element n'existe pas, la
    //methode lance une IllegalArgumentException
    public double getElement(int numLigne, int numColonne)
            throws IllegalArgumentException {
        //TODO
        if (numColonne <= 0 || numLigne <= 0 || numColonne > nbColonnes || numLigne > nbLignes) {
            throw new IllegalArgumentException();
        }

        return data[numLigne-1][numColonne-1];
    }

    // Calcule la somme this+b de la matrice courante avec b si c'est possible
    public Matrice somme(Matrice b)  throws IllegalArgumentException {
        //TODO
        if (b == null || b.nbLignes != nbLignes || b.nbColonnes != nbColonnes) {
            throw new IllegalArgumentException();
        }

        Matrice somme = new Matrice(b);

        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                somme.data[i][j] += data[i][j];
            }
        }
        return somme;
    }

    // calcule le produit scalaire.this de la matrice courante avec scalaire
    public Matrice produitParScalaire(double scalaire){
        //TODO

        Matrice produitScalaire = new Matrice(data);

        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                produitScalaire.data[i][j] *= scalaire;
            }

        }
        return produitScalaire;
    }

    // calcule le produit this*b de la matrice courante avec b si possible
    public Matrice produitAGauche(Matrice b)  throws IllegalArgumentException {
        //TODO
        if (b == null || nbColonnes != b.nbLignes) {
            throw new IllegalArgumentException();
        }

        Matrice produitAGauche = new Matrice(nbLignes, b.nbColonnes);
        double tmp = 0;

        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < b.nbColonnes; j++) {
                for (int k = 0; k < nbColonnes; k++) {
                    tmp += data[i][k] * b.data[k][j];
                }
                produitAGauche.data[i][j] = tmp;
                tmp = 0;
            }
        }

        return produitAGauche;
    }

    // calcule le produit b*this de b avec la matrice courante si possible
    public Matrice produitADroite(Matrice b)  throws IllegalArgumentException {
        //TODO
        if (b == null || b.nbColonnes != nbLignes) {
            throw new IllegalArgumentException();
        }
        Matrice produitADroite = new Matrice(b.nbLignes, nbColonnes);
        double tmp = 0;
        for (int i = 0; i < b.nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                for (int k = 0; k < nbLignes; k++) {
                    tmp += b.data[i][k] * data[k][j];
                }
                produitADroite.data[i][j] = tmp;
                tmp = 0;
            }

        }
        return produitADroite;
    }

    // renvoie true si la matrice courante est carrée
    public boolean carree(){
        //TODO
        if (nbLignes == nbColonnes) {
            return true;
        }
        return false;
    }

    // Calcule this^n.
    // Lance une Mathexception si this n'est pas carrée
    // Lance une IllegalArgumentException en cas de paramètre invalide
    public Matrice puissance(int n) throws  IllegalArgumentException {
        //TODO
        if (n < 0) {
            throw new IllegalArgumentException();
        }

        if(!carree()) {
            throw new MathException();
        }
        Matrice puissance = new Matrice(data);
        if (n == 0) {
            return identite(nbLignes);
        }
        for (int i = 2; i <= n; i++) {
            puissance = puissance.produitAGauche(this);
        }
        return puissance;
    }

    //Calcule this^T : la tranposée de this
    public Matrice transposee() {
        //TODO
        Matrice transposee = new Matrice(nbColonnes,nbLignes);
        for (int i = 0; i < nbColonnes; i++) {
            for (int j = 0; j < nbLignes; j++) {
                transposee.data[i][j] = data[j][i];
            }
        }
        return transposee ;
    }

    // affiche la matrice en format standard
    public String toString(){
        //TODO
        return null;
    }


    // Permute les lignes d'indice l1 et l2
    private void permuter(int l1, int l2) {
        // A FAIRE QUAND LES SYSTÈMES AURONT ÉTÉ VUS
        //TODO
        double tmp;
        for (int i = 0; i < nbColonnes; i++) {
            tmp = data[l1][i];
            data[l1][i] = data[l2][i];
            data[l2][i] = tmp;
        }
    }

    // Remplace la ligne l1 par la ligne l1 - a * l2
    private void remplacer(int l1, double a, int l2) {
        // A FAIRE QUAND LES SYSTÈMES AURONT ÉTÉ VUS
        //TODO
        for (int i = 0; i < nbColonnes; i++) {
            data[l1][i] = data[l1][i] - a * data[l2][i];
        }
    }

    // Echelonne la matrice courrante
    public void echelonner() {
        // A FAIRE QUAND LES SYSTÈMES AURONT ÉTÉ VUS
        //TODO

    }

    // Renvoie true si la matrice est échelonnée et false sinon
    public boolean estEchelonnee() {
       int nbzeros = -1;
        for (int i = 0; i < nbLignes; i++) {
            int j = 0;                                         // 0 2 9 3
            int zeroParLigne = 0;                              // 0 2 2 2
            while(j<nbColonnes && this.data[i][j] == 0){       // 0 0 2 2
                zeroParLigne++;                                // 0 0 0
                j++;
            }
            if(zeroParLigne<nbzeros || (nbzeros == zeroParLigne && nbzeros<nbColonnes )) {
                return false;
            }
            nbzeros = zeroParLigne;
        }
        return true;
    }

    // Résoud le système représenté par la matrice et renvoie son unique solution, sous forme d'une matrice colonne, si elle existe
    // Lance une MathException si la matrice ne représente pas un système
    // Lance une MathException si le système représenté par la matrice n'admet pas une solution unique
    public Matrice resoudreSysteme() {
        // A FAIRE QUAND LES SYSTÈMES AURONT ÉTÉ VUS
        //TODO

        return null ;
    }


    public Matrice pageRank() {
        // A FAIRE QUAND MARKOV AURA ÉTÉ VU
        //TODO
        return null ;
    }

    public Matrice sommeMatrice(Matrice a) {
        if (a == null) {
            throw new IllegalArgumentException();
        }

        if (a.nbLignes != a.nbColonnes) {
            throw new IllegalArgumentException();
        }

        int x = a.nbLignes;
        int y = a.nbColonnes * 2;

        Matrice identite = new Matrice(x, x);

        // Création de l'identité
        for (int i = 0; i < x; i++) {
            identite.data[i][i] = 1;
        }

        Matrice matrice = new Matrice(x, y);

        // Copie de A à gauche
        for (int i = 0; i < a.nbLignes; i++) {
            for (int j = 0; j < a.nbColonnes; j++) {
                matrice.data[i][j] = a.data[i][j];
            }
        }

        // Copie de l'identité à droite
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < identite.nbColonnes; j++) {
                matrice.data[i][j + a.nbColonnes] = identite.data[i][j];
            }
        }

        return matrice;
    }

}
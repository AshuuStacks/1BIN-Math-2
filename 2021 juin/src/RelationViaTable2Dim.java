

public class RelationViaTable2Dim {

	
	private static final int MAX = Elt.MAXELT.val();
	
	private boolean[][] table2Dim;
	private int taille; //nombre de couples
	// Ne pas ajouter d'attribut

	//NE PAS MODIFIER : va servir pour les tests
	public RelationViaTable2Dim(boolean[][] tableARecopier) {
		table2Dim = new boolean[MAX+1][MAX+1];
		for (int i = 0; i < tableARecopier.length; i++) {
			for (int j = 0; j < tableARecopier.length; j++) {
				table2Dim[i][j]=tableARecopier[i][j];
				if(table2Dim[i][j]) 
					taille++;
			}
		}
	}
	
	
	/** Construit la Relation vide  */
	public RelationViaTable2Dim() {
		table2Dim = new boolean[MAX+1][MAX+1];
		taille=0;
	}
	
	//Renvoie le nombre de couple contenu dans la relation
	public int taille(){
		return taille;
	}
	
	// Renvoie true si la relation est vide
	//         false sinon
	public boolean estVide(){
		return taille==0;
	}

	// Ajoute le couple c à la relation si celui-ci n'est pas déjà contenu dans la relation.
	public void ajouter(Couple c){
		if(c==null)
			throw new IllegalArgumentException();
        if(!table2Dim[c.getX().val()][c.getY().val()]){
            table2Dim[c.getX().val()][c.getY().val()]=true;
            taille++;

        }

	}

	// renvoie true si le couple c appartient à la Relation courante
	//         false sinon
	public boolean contient(Couple c){
		if(c==null)
			throw new IllegalArgumentException();
       return  table2Dim[c.getX().val()][c.getY().val()];

    }
	
	// Clôture la Relation courante pour la symétrie
	public void cloSym(){

        for (int i = 0; i <= MAX; i++) {
            for (int j = 0; j <= MAX; j++) {
                if(table2Dim[i][j] && !table2Dim[j][i]){
                  table2Dim[j][i]=true;
                    taille++;
                }
            }
        }

	}
	
	// Renvoie true si la relation courante est réflexive
	//         false sinon
	public boolean reflexive(){
        for (int i = 0; i <= MAX; i++) {
            boolean estPresent=false;
            for (int j = 0; j <= MAX; j++) {
                if(table2Dim[i][j] || table2Dim[j][i]){
                    estPresent=true;
                }
                if(estPresent){
                    if(!table2Dim[i][i]){
                        return false;
                    }
                }
            }

        }
            return true;
	}
	
	// Renvoie true si la relation courante est transitive
	//         false sinon
	public boolean transitive(){
        for (int i = 0; i <= MAX; i++) {
            for (int j = 0; j <= MAX; j++) {
                for (int k = 0; k <= MAX; k++) {
                    if(table2Dim[i][j] && table2Dim[j][k] && !table2Dim[i][k]){
                        return  false;
                    }
                }
            }
        }

		return true ;
	}


	// A ne pas modifier, va servir pour les tests
		public String toString(){
			String s = "";
			for (int i = 0; i <= MAX; i++) {
				for (int j = 0; j <= MAX; j++) {
					if(table2Dim[i][j])
						s = s + " T";
					else
						s = s + " F";
				}
				s += "\n";
			}
			return s;
		}


} 

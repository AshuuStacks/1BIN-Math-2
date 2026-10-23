/** Classe Relation héritant de RelationDeBase
	 Fournit des outils de manipulation des relations entre sous-ensembles de l'Univers
 */

public class Relation extends RelationDeBase {

	/** Valeur numérique de MAXELT */
	private static final int MAX = Elt.MAXELT.val();

	/** Construit la Relation vide sur l'ensemble vide */
	public Relation() {
		super();
	}

	/** Construit la Relation vide de d vers a */
	public Relation(EnsembleAbstrait d, EnsembleAbstrait a) {
		super(d, a);
	}

	/** Clone */
	public Relation clone() {
		return (Relation) super.clone();
	}
	
	//Ex1
	//renvoie le domaine de la relation courante
	public EnsembleAbstrait domaine() {
        EnsembleAbstrait e = new Ensemble();
        for (Elt x : this.depart()) {
            for (Elt elt : this.arrivee()) {
                if(contient(x,elt)) {
                    e.ajouter(x);
                }
            }
        }
		return e;
	}
	
	//renvoie l'image de la relation courante
	public EnsembleAbstrait image() {
        EnsembleAbstrait e = new Ensemble();
        for (Elt x : this.depart()) {
            for (Elt elt : this.arrivee()) {
                if(contient(x,elt)) {
                    e.ajouter(elt);
                }
            }
        }
        return e;
	}
	
	// EX 2
	// renvoie la complémentaire de la relation courante
	public Relation complementaire() {
        Relation r = new Relation(this.depart(), this.arrivee());
        for (Elt x : this.depart()) {
            for (Elt elt : this.arrivee()) {
                if(!contient(x,elt)) {
                    r.ajouter(x,elt);
                }
            }
        }
		//TODO
		return r;
	}

	// renvoie la réciproque de la relation courante
	public Relation reciproque() {
        Relation r = new Relation(this.arrivee(), this.depart());
        for (Elt x : this.depart()) {
            for (Elt elt : this.arrivee()) {
                if(contient(x,elt)) {
                    r.ajouter(elt,x);
                }
            }
        }
		//TODO
		return r;
	}

	// si possible, remplace la relation courante par son union avec r
	//sinon, lance une IllegalArgumentException
	public void ajouter(RelationInterface r) {
        if(r == null) throw new IllegalArgumentException();
        if(!this.depart().equals(r.depart())) {
            throw new IllegalArgumentException();
        }
        if(!this.arrivee().equals(r.arrivee())) {
            throw new IllegalArgumentException();
        }
        for (Couple couple : r) {
            if(!this.contient(couple)) {
                this.ajouter(couple);
            }
        }
		//TODO
	}

	// si possible, remplace this par sa différence avec r
	//sinon, lance une IllegalArgumentException
    public void enlever(RelationInterface r) {
        if (r == null)
            throw new IllegalArgumentException();

        if (!this.depart().equals(r.depart()) ||
                !this.arrivee().equals(r.arrivee()))
            throw new IllegalArgumentException();
       Relation r2 = this.clone();


        for (Couple couple : r2) {
            if(r.contient(couple)) {
                this.enlever(couple);
            }
        }

    }



    // si possible, remplace this par son intersection avec r
	//sinon, lance une IllegalArgumentException
	public void intersecter(RelationInterface r) {

		//TODO
	}

	// si possible, renvoie la composée : this après r
	//sinon, lance une IllegalArgumentException
	public Relation apres(RelationInterface r) {
		//TODO
		return null;
	}


	
	/*Les exercices 4 et 5 ne concernent que les relations sur un ensemble.
	 * Les méthodes demandées génèreront donc une MathException lorsque l'ensemble de départ
	 * ne coïncide pas avec l'ensemble d'arrivée.
	 */
	
	/* Ex 4 */
		
	// Clôture la Relation courante pour la réflexivité
	public void cloReflex() {
        if(!this.arrivee().equals(this.depart())) { throw new MathException();}
        for (Elt elt : this.depart()) {
            if(!this.contient(elt,elt) && this.arrivee().contient(elt))
                this.ajouter(elt,elt);
            }

		//TODO
	}

	// Clôture la Relation courante pour la symétrie
	public void cloSym() {
        if(!this.arrivee().equals(this.depart())) { throw new MathException();}
        if(!this.arrivee().equals(this.depart())) { throw new MathException();}

        for (Elt elt : this.depart()) {
            for (Elt elt1 : this.arrivee()) {
                if(this.contient(elt1,elt)) {
                    this.ajouter(elt,elt1);
                }
            }
        }

        //TODO
	}

	// Clôture la Relation courante pour la transitivité (Warshall)
	public void cloTrans() {
        if (!this.depart().equals(this.arrivee())) {
            throw new MathException();
        }
        for (Elt x : this.depart()) {
            for (Elt y : this.depart()) {
                if(this.contient(y,x)) {
                    for (Elt z : this.depart()) {
                        if(this.contient(x,z)) {
                            this.ajouter(y,z);
                        }
                    }
                }
            }
        }

		//TODO
	}
	
	
	//Ex 5
	/*Les questions qui suivent ne concernent que les relations sur un ensemble.
	 * Les méthodes demandées génèreront donc une MathException lorsque l'ensemble de départ
	 * ne coïncide pas avec l'ensemble d'arrivée.
	 */
	// renvoie true ssi this est réflexive
	public boolean reflexive(){
        if(!this.arrivee().equals(this.depart())) { throw new MathException();}
        if(!this.arrivee().equals(this.depart())) { throw new MathException();}
        for (Elt e  : this.domaine()) {
            if(!this.contient(e,e)){
                return false;
            }
        }
		return true;
	}

	// renvoie true ssi this est antiréflexive
	public boolean antireflexive(){
        if(!this.arrivee().equals(this.depart())) { throw new MathException();}
        if(!this.arrivee().equals(this.depart())) { throw new MathException();}
        for (Elt e  : this.domaine()) {
            if(this.contient(e,e)){
                return false;
            }
        }
        return true;
	}

	// renvoie true ssi this est symétrique
	public boolean symetrique(){
        if(!this.arrivee().equals(this.depart())) { throw new MathException();}
        for (Couple c : this ) {
            if(this.contient(c.getX(),c.getY()) && !this.contient(c.getY(),c.getX())) {
                return false;
            }
        }
        return true;
    }

	// renvoie true ssi this est antisymétrique
	public boolean antisymetrique(){
        if(!this.arrivee().equals(this.depart())) { throw new MathException();}
        for (Elt x : this.depart()) {
            for (Elt y : this.depart()) {
                if(this.contient(x,y)) {
                    if(this.contient(y,x)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
	}

	// renvoie true ssi  this est transitive
	public boolean transitive(){
        if(!this.arrivee().equals(this.depart())) { throw new MathException();}
        for (Elt elt : this.depart()) {
            for (Elt elt1 : this.arrivee()) {
                if(this.contient(elt,elt1)) {
                    for (Elt elt2 : this.arrivee()) {
                        if(this.contient(elt1,elt2)) {
                            if(!this.contient(elt,elt2)) {
                                return false;
                            }
                        }
                    }
                }

            }
        }

		return true;
	}
	
	// Ex 6
	//Construit une copie de la relation en paramètre
	//lance une IllegalArgumentException en cas de paramètre invalide
	public Relation(RelationInterface r) {
		if(r == null) throw new IllegalArgumentException();

         super(r.depart(), r.arrivee());
        for (Couple c : r) {
            this.ajouter(c);
        }


	}

	//renvoie l'identité sur e
	//lance une IllegalArgumentException en cas de paramètre invalide
	public static Relation identite(EnsembleAbstrait e) {
		Relation relation = new Relation(e,e);
            for (Elt elt1 : e) {
                relation.ajouter(elt1,elt1);
        }
		return relation;
	}

	//renvoie le produit cartésien de a et b
	//lance une IllegalArgumentException en cas de paramètre invalide
	public static Relation produitCartesien(EnsembleAbstrait a, EnsembleAbstrait b) {
        if(a == null || b == null) { throw new IllegalArgumentException();}
        Relation relation = new Relation(a,b);
        for (Elt elt : a) {
            for (Elt elt1 : b) {
                relation.ajouter(elt,elt1);
            }
        }
        
        return relation;
	}

} // class Relation

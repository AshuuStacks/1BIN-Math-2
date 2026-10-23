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
        Ensemble ensemble = new Ensemble();
        for (Couple couple : this) {
            ensemble.ajouter(couple.getX());
        }
		return ensemble;
	}
	
	//renvoie l'image de la relation courante
	public EnsembleAbstrait image() {
        Ensemble ensemble = new Ensemble();
        for (Couple couple : this) {
            ensemble.ajouter(couple.getY());
        }
        return ensemble;
	}
	
	// EX 2
	// renvoie la complémentaire de la relation courante
	public Relation complementaire() {
         Relation ensemble = new Relation(this.depart(),this.arrivee());
        for (Elt elt : this.depart()) {
            for (Elt elt1 : this.arrivee()) {
                if(!this.contient(elt,elt1)){
                    ensemble.ajouter(new Couple(elt,elt1));
                }
            }
        }
		return ensemble;
	}

	// renvoie la réciproque de la relation courante
	public Relation reciproque() {
		Relation relation = new Relation(this.arrivee(),this.depart());
        for (Couple couple : this) {
            relation.ajouter(new Couple(couple.getY(),couple.getX()));
        }
		return relation;
	}

	// si possible, remplace la relation courante par son union avec r
	//sinon, lance une IllegalArgumentException
	public void ajouter(RelationInterface r) {
        if(r == null) throw new IllegalArgumentException();

        if(!r.depart().equals(this.depart())) throw new IllegalArgumentException();
        if(!r.arrivee().equals(this.arrivee())) throw new IllegalArgumentException();
        for (Couple couple : r) {
            if(!this.contient(couple)){
                this.ajouter(couple);
            }
        }
	}

	// si possible, remplace this par sa différence avec r
	//sinon, lance une IllegalArgumentException
	public void enlever(RelationInterface r) {
        if (r == null) throw new IllegalArgumentException();

        if (!r.depart().equals(this.depart())) throw new IllegalArgumentException();
        if (!r.arrivee().equals(this.arrivee())) throw new IllegalArgumentException();
         Relation relation = this.clone();

        for (Couple couple : relation) {
            if(r.contient(couple)){
                this.enlever(couple);
            }
        }
	}

	// si possible, remplace this par son intersection avec r
	//sinon, lance une IllegalArgumentException
	public void intersecter(RelationInterface r) {
        if (r == null) throw new IllegalArgumentException();
        if (!r.depart().equals(this.depart())) throw new IllegalArgumentException();
        if (!r.arrivee().equals(this.arrivee())) throw new IllegalArgumentException();
        Relation relation = this.clone();

        for (Couple couple : relation) {
            if(!r.contient(couple)){
                this.enlever(couple);
            }
        }
	}

	// si possible, renvoie la composée : this après r
	//sinon, lance une IllegalArgumentException
	public Relation apres(RelationInterface r) {
        if (r == null) throw new IllegalArgumentException();
        if (!r.arrivee().equals(this.depart())) throw new IllegalArgumentException();
         Relation x  = new Relation(r.depart(),this.arrivee());
        for (Couple couple : r) {
            for (Couple couple1 : this) {
                if(couple.getY().equals(couple1.getX())){
                    x.ajouter(new Couple(couple.getX(),couple1.getY()));
                }
            }
        }

		return x;
	}



	/*Les exercices 4 et 5 ne concernent que les relations sur un ensemble.
	 * Les méthodes demandées génèreront donc une MathException lorsque l'ensemble de départ
	 * ne coïncide pas avec l'ensemble d'arrivée.
	 */
	
	/* Ex 4 */
		
	// Clôture la Relation courante pour la réflexivité
	public void cloReflex() {
        if(!this.depart().equals(this.arrivee())) throw new MathException();
        for (Elt elt : this.depart()) {
            this.ajouter(elt,elt);
        }
	}

	// Clôture la Relation courante pour la symétrie
	public void cloSym() {
        if(!this.depart().equals(this.arrivee())) throw new MathException();
        for (Elt elt : this.depart()) {
            for (Elt elt1 : this.arrivee()) {
                if(this.contient(elt,elt1)){
                    this.ajouter(elt1,elt);
                }
            }
        }
        //TODO
	}

	// Clôture la Relation courante pour la transitivité (Warshall)
	public void cloTrans() {
        if(!this.depart().equals(this.arrivee())) throw new MathException();

        for (Elt i : this.depart()) {
            for (Elt k : this.depart()) {
                for (Elt j : this.arrivee()) {
                    if(this.contient(k,i) && this.contient(i,j)){
                     this.ajouter(k,j);

                }
                }
            }
        }

    }
	
	
	//Ex 5
	/*Les questions qui suivent ne concernent que les relations sur un ensemble.
	 * Les méthodes demandées génèreront donc une MathException lorsque l'ensemble de départ
	 * ne coïncide pas avec l'ensemble d'arrivée.
	 */
	// renvoie true ssi this est réflexive
	public boolean reflexive(){
        if(!this.depart().equals(this.arrivee())) throw new MathException();
        for (Elt elt : this.depart()) {
            if(!this.contient(elt,elt)){
                return false;
            }
        }
		return true;
	}

	// renvoie true ssi this est antiréflexive
	public boolean antireflexive(){
        if(!this.depart().equals(this.arrivee())) throw new MathException();
                boolean trouve = false;
        for (Elt elt : this.depart()) {
            if(this.contient(elt,elt)){
                trouve =true;
            }
        }
           if(trouve){
               return false;
           }
        return true;
	}

	// renvoie true ssi this est symétrique
	public boolean symetrique(){
        if(!this.depart().equals(this.arrivee())) throw new MathException();

        for (Elt elt : this.depart()) {
            for (Elt elt1 : this.depart()) {
                if (this.contient(elt, elt1) && !this.contient(elt1, elt)) {
                    return false;
                }
            }
        }
		return true;
	}

	// renvoie true ssi this est antisymétrique
	public boolean antisymetrique(){
        if(!this.depart().equals(this.arrivee())) throw new MathException();
        boolean trueOrfalse = false;
        for (Elt elt : this.depart()) {
            for (Elt elt1 : this.depart()) {
                if (this.contient(elt, elt1) && this.contient(elt1, elt) && !elt.equals(elt1)) {
                    trueOrfalse = true;
                }
            }
        }
        if(trueOrfalse){
            return false;
        }
        return true;
	}

	// renvoie true ssi  this est transitive
	public boolean transitive(){
        if(!this.depart().equals(this.arrivee())) throw new MathException();

        for (Elt i : this.depart()) {
            for (Elt k : this.depart()) {
                for (Elt j : this.depart()) {
                    if(this.contient(k,i) && this.contient(i,j) && !this.contient(k,j)){
                        return false;
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
        this((r).depart().clone(), (r).arrivee().clone());
        for(Couple c: r){
            this.ajouter(new Couple(c.getX(),c.getY()));
        }


    }

	//renvoie l'identité sur e
	//lance une IllegalArgumentException en cas de paramètre invalide
	public static Relation identite(EnsembleAbstrait e) {
		Relation relation = new Relation(e,e);
        for (Elt elt : e) {
            relation.ajouter(elt,elt);
        }
		return relation;
	}

	//renvoie le produit cartésien de a et b
	//lance une IllegalArgumentException en cas de paramètre invalide
	public static Relation produitCartesien(EnsembleAbstrait a, EnsembleAbstrait b) {
        if(a== null|| b== null|| b.estVide()||a.estVide())
            throw new IllegalArgumentException();
			Relation relation = new Relation(a,b);

        for (Elt elt : a) {
            for (Elt elt1 : b) {
                relation.ajouter(elt,elt1);
            }
        }
		return relation;
	}

} // class Relation

import java.util.Iterator;

public class Ordre extends RelationAbstraite {

	private Relation couples;

	//construit l'identité sur e
	//lance une IllegalArgumentException en cas de paramètre invalide
	public Ordre(EnsembleAbstrait e) {
        if( e == null) throw new IllegalArgumentException();
		this.couples =  Relation.identite(e);
	}

	//construit le plus petit ordre contenant r
	//lance une IllegalArgumentException si cette construction n'est pas possible
	public Ordre(Relation r) {
        if(r==null || !r.arrivee().equals(r.depart()) || !r.antisymetrique()){
            throw new IllegalArgumentException();
        }
        Relation relation = r.clone();
        relation.cloTrans();
        relation.cloReflex();
        if(!relation.antisymetrique()){
            throw new IllegalArgumentException();
        }
        this.couples = relation;

	}
	
	//constructeur pas recopie
	//lance une IllegalArgumentException en cas de paramètre invalide
	public Ordre(Ordre or) {
		if(or == null ) throw new IllegalArgumentException();

        this.couples = or.couples.clone();

    }

	//ajoute x à l'ensemble sous-jacent de la relation d'ordre
	//ne fait rien si x est déjà dans l'ensemble sous-jacent
	//lance une IllegalArgumentException en cas de paramètre invalide
	public void ajouterAuSousJacent(Elt x) {
		if(x == null) throw new IllegalArgumentException();
         couples.ajouterDepart(x);
         couples.ajouterArrivee(x);
         couples.cloReflex();
	}

	//enlève x de l'ensemble sous-jacent de la relation d'ordre
	//ainsi que toutes les flêches liées à x
	//ne fait rien si x n'est pas dans l'ensemble sous-jacent 
	//lance une IllegalArgumentException en cas de paramètre invalide
	public void enleverDuSousJacent(Elt x) {
        if(x == null) throw new IllegalArgumentException();
        couples.supprimerDepart(x);
        couples.supprimerArrivee(x);
	}
	
	@Override
	public Iterator<Couple> iterator() {
		return couples.iterator();
	}

	@Override
	public boolean estVide() {
		return couples.estVide();
	}

	@Override
	public boolean contient(Couple c) {
		if (c== null) throw new IllegalArgumentException();
		if (!couples.depart().contient(c.getX())||!couples.arrivee().contient(c.getY())) throw new IllegalArgumentException();
		return couples.contient(c.getX(), c.getY());
	}

	@Override
	//crée (si possible) le plus petit ordre contenant this et c
	//lance une IllegalArgumentException en cas de paramètre invalide
	public void ajouter(Couple c) {
        if(c == null) throw new IllegalArgumentException();
        if(!this.depart().contient(c.getX()) || !this.arrivee().contient(c.getY())) throw new IllegalArgumentException();
        Relation ordre = this.couples.clone();
         ordre.ajouter(c);
        if(!ordre.antisymetrique()) throw new IllegalArgumentException();
        ordre.cloTrans();
        ordre.reflexive();
        this.couples = ordre;
	}


	@Override
	//Enlève (si possible) l'arête de Hasse c du la relation d'ordre
	//lance une IllegalArgumentException en cas de si le paramètre est invalide ou si c n'est pas une arête de Hasse
	//ne fait rien sinon
	public void enlever(Couple c) {
		if (c== null) throw new IllegalArgumentException();
		if (!this.depart().contient(c.getX())) throw new IllegalArgumentException();
		if (!this.depart().contient(c.getY())) throw new IllegalArgumentException();
		if (!this.contient(new Couple(c.getX(), c.getY()))) return;
		if (!estUneAreteDeHasse(c.getX(), c.getY()))
			throw new IllegalArgumentException();
		Ensemble plusPttX = this.plusPetitQue(c.getX());
		Ensemble plusGrdY = this.plusGrandQue(c.getY());
		for (Elt eX : plusPttX) {
			for (Elt eY : plusGrdY) {
				this.couples.enlever(eX, eY);
			}
		}
		this.couples.cloTrans();
	}
	
	private Ensemble plusPetitQue(Elt e){
		Ensemble min = new Ensemble();
		for (Elt eC : couples.depart()){
			if (couples.contient(eC, e)) min.ajouter(eC);
		}
		return min;
	}
	
	private Ensemble plusGrandQue(Elt e){
		Ensemble maj = new Ensemble();
		for (Elt eC : couples.depart()){
			if (couples.contient(e,eC)) maj.ajouter(eC);
		}
		return maj;
	}

	private boolean estUneAreteDeHasse(Elt x, Elt y) {
		if (!this.contient(new Couple(x, y)))
			return false;
		if (x.equals(y))
			return false;
		EnsembleAbstrait aParcourir = this.depart();
		aParcourir.enlever(x);
		aParcourir.enlever(y);
		for (Elt e : aParcourir) {
			if (this.contient(new Couple(x, e)) && this.contient(new Couple(e, y)))
				return false;
		}
		return true;
	}

	public boolean estUneAreteDeHasse(Couple c) {
		if (c== null) throw new IllegalArgumentException();
		return estUneAreteDeHasse(c.getX(), c.getY());
	}

	@Override
	public EnsembleAbstrait depart() {
		return couples.depart();
	}

	@Override
	public EnsembleAbstrait arrivee() {
		return couples.arrivee();
	}

	//renvoie true ssi x et y sont comparables pour l'ordre courant
	//lance une IllegalArgumentException en cas de paramètre invalide
	public boolean comparables(Elt x, Elt y) {
        if(x == null || y == null) throw new IllegalArgumentException();
		if(!this.depart().contient(x) || !this.depart().contient(y)) throw new IllegalArgumentException();
        return this.couples.contient(x,y) || this.couples.contient(y,x);
	}

	// Renvoie l'ensemble des éléments minimaux de b
	//lance une IllegalArgumentException en cas de paramètre invalide
	public EnsembleAbstrait minimaux(EnsembleAbstrait b) {
        if(b== null|| !b.inclusDans(this.arrivee())){
            throw new IllegalArgumentException();
        }
        EnsembleAbstrait minimaux= new Ensemble();
        for(Elt elt1: b) {
            boolean trouve = true;
            for (Elt elt : b) {
                if(!elt.equals(elt1)){
                    if(this.couples.contient(elt,elt1)){
                        trouve = false;
                        break;
                    }
                }
            }
            if(trouve){
                minimaux.ajouter(elt1);
            }
        }
        return minimaux;
	}
	
	// Renvoie l'ensemble des éléments maximaux de b
	//lance une IllegalArgumentException en cas de paramètre invalide
	public EnsembleAbstrait maximaux(EnsembleAbstrait b) {
		if(b == null || !b.inclusDans(this.depart())) throw new IllegalArgumentException();
            EnsembleAbstrait maximauxx = new Ensemble();
        for (Elt elt : b) {
            boolean trouve = true;
            for (Elt elt1 : b) {
                if(!elt.equals(elt1)){
                    if(this.couples.contient(elt,elt1)){
                        trouve = false;
                    }
                }
            }
            if(trouve) maximauxx.ajouter(elt);
        }
		return maximauxx;
	}

	// Renvoie le minimum de b s'il existe; renvoie null sinon
	//lance une IllegalArgumentException en cas de paramètre invalide
	public Elt minimum(EnsembleAbstrait b) {
        if(b == null || !b.inclusDans(this.depart())) throw new IllegalArgumentException();
		EnsembleAbstrait ensemble = minimaux(b);
         int x = 0;
        for (Elt elt : ensemble) {
            x++;
        }
        if(x>1){
            return null;
        }

		return ensemble.unElement();
	}
	
	// Renvoie le maximum de b s'il existe; renvoie null sinon
	//lance une IllegalArgumentException en cas de paramètre invalide
	public Elt maximum(EnsembleAbstrait b) {
        if(b == null || !b.inclusDans(this.depart())) throw new IllegalArgumentException();
        EnsembleAbstrait ensemble = maximaux(b);
        int x = 0;
        for (Elt elt : ensemble) {
            x++;
        }
        if(x>1){
            return null;
        }

        return ensemble.unElement();
	}

	// Renvoie l'ensemble des minorants de b
	//lance une IllegalArgumentException en cas de paramètre invalide
	public EnsembleAbstrait minor(EnsembleAbstrait b) {
        if(b== null|| !b.inclusDans(this.arrivee())){
            throw new IllegalArgumentException();
        }
        if(b.estVide()){ //si b est vide minor = ensemble arrivee/sousjacent
            return  couples.arrivee();
        }
		EnsembleAbstrait minorant = new Ensemble();

        for (Elt x : this.arrivee()) {
            boolean trouve = true;
            for (Elt y : b) {
                      if(!this.couples.contient(x,y)){
                       trouve = false;
                       break;

                }
            }
            if(trouve){
                minorant.ajouter(x);
            }
        }
		return minorant;
	}
	
	// Renvoie l'ensemble des majorants de b
	//lance une IllegalArgumentException en cas de paramètre invalide
	public EnsembleAbstrait major(EnsembleAbstrait b) {
        if(b== null|| !b.inclusDans(this.arrivee())){
            throw new IllegalArgumentException();
        }
        if(b.estVide()){ //si b est vide minor = ensemble arrivee/sousjacent
            return  couples.arrivee();
        }
        EnsembleAbstrait minorant = new Ensemble();

        for (Elt x : this.arrivee()) {
            boolean trouve = true;
            for (Elt y : b) {
                if(!this.couples.contient(y,x)){
                    trouve = false;
                    break;

                }
            }
            if(trouve){
                minorant.ajouter(x);
            }
        }
        return minorant;
	}

	// Renvoie l'infimum de b s'il existe; renvoie null sinon
	//lance une IllegalArgumentException en cas de paramètre invalide
	public Elt infimum(EnsembleAbstrait b) {
        if(b== null || !b.inclusDans(this.arrivee())){
            throw new IllegalArgumentException();
        }
		return maximum(minor(b));
	}
	
	// Renvoie le supremum de b s'il existe; renvoie null sinon
	//lance une IllegalArgumentException en cas de paramètre invalide
	public Elt supremum(EnsembleAbstrait b) {
        if(b== null || !b.inclusDans(this.arrivee())){
            throw new IllegalArgumentException();
        }
		return minimum((major(b)));
	}

	//Renvoie true ssi this est un treillis
	//lance une IllegalArgumentException en cas de paramètre invalide
	public boolean treillis() {
        for (Elt elt : this.depart()) {
            for (Elt elt1 : this.depart()) {
                if(!this.comparables(elt,elt1)){
                    Ensemble ensemble  = new Ensemble();
                    ensemble.ajouter(elt);
                    ensemble.ajouter(elt1);
                    if(supremum(ensemble) == null ||infimum(ensemble) == null ){
                        return false;
                    }
                }
            }
        }
        return true;
    }

	public String toString() {
		return couples.toString();
	}
	
}

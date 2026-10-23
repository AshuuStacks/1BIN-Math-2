public abstract class EnsembleAbstrait implements EnsembleInterface {

	/**
	* Renvoie une Relation représentant this + a
	* 
	* @parameter a : l'ensemble a "sommer" avec this
	* @return une Relation représentant la somme disjointe de this et a
	* @throws IllegalArgumentException en cas de paramètre invalide
	**/
	public Relation sommeDisjointe(EnsembleAbstrait a) {
		if( a== null) throw new IllegalArgumentException();
        Elt un = new Elt(1);
        Elt deux = new Elt(2);
        
        EnsembleAbstrait depart = new Ensemble();
        depart.ajouter(un);
        depart.ajouter(deux);
        EnsembleAbstrait arrivee = new Ensemble();

        for (Elt elt : this) {
            arrivee.ajouter(elt);
        }
        for (Elt elt : a) {
            arrivee.ajouter(elt);
        }

        Relation relation = new Relation(depart,arrivee);

        for (Elt elt : a) {
            relation.ajouter(deux,elt);
        }
        for (Elt elt : this) {
            relation.ajouter(un,elt);
        }
        
		return relation ;
	}

	public boolean inclusDans (EnsembleAbstrait e) {
		if (e==null)
			return false ;
		for (Elt el:this) {
			if (!e.contient(el)) {
				return false ;
			}
		}
		return true ;
	}

	// renvoie true ssi this est égal à  o
	public final boolean equals(Object o) {
		if (o == null)
			return false;
		if (o == this)
			return true;
		if (!(o instanceof EnsembleAbstrait))
			return false ;
	
		EnsembleAbstrait e = (EnsembleAbstrait) o ;
		return this.inclusDans(e) && e.inclusDans(this) ;
	}

	@Override
	public final int hashCode() {
		int result = 1;
		int prime = 31;
		for (int i = 1; i <= MAX; i++) {
			Elt ei = new Elt(i);
			if (this.contient(ei))
				result = result * prime + ei.hashCode();
		}
		return result;
	}

	@Override
	public EnsembleAbstrait clone()  {
		try {
			return (EnsembleAbstrait) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
	}
	
}

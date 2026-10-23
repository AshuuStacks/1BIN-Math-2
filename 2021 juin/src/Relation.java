public class Relation extends RelationDeBase {

	/** Valeur numérique de MAXELT */
	private static final int MAX = Elt.MAXELT.val();
	// Ne pas ajouter d'attribut

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
	
	//renvoie l'ensemble des éléments isolés de la relation
    public EnsembleAbstrait ensembleDesIsoles() {
        if (!depart().equals(arrivee()))
            throw new MathException();

        EnsembleAbstrait isoles = new Ensemble();

        for (Elt elt : this.depart()) {
            boolean isIsole = true;
            for (Couple couple : this) {
                if(!couple.getX().equals(couple.getY())) {
                    if(couple.getX().equals(elt) || couple.getY().equals(elt)) {
                        isIsole = false;
                        break;
                    }
                }
            }
            if(isIsole) {
                isoles.ajouter(elt);
            }
        }
        return isoles;
    }


} // class Relation

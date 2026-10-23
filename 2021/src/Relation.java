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
	
	public static Relation identite(EnsembleAbstrait ens) {
		if (ens==null)
			throw new IllegalArgumentException() ;
		Relation r = new Relation(ens,ens) ;
		for (Elt e:ens) {
			r.ajouter(e,e);
		}
		return r ;
	}

	// Si vous avez besoin d'autres méthodes de la classe Relation vous devez les implémenter.

} // class Relation

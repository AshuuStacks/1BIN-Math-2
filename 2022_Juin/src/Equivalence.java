

public class Equivalence{
	
	/** Valeur numerique de MAXELT */
	private static final int MAX = Elt.MAXELT.val();

	private EnsembleAbstrait sousJac; // ensemble sous-jacent
	private Elt[] tabRep; // tableau des representants
	// Ne pas ajouter d'attribut

	// si possible, construit une relation d'equivalence
	// sinon, lance une IllegalArgumentException
	public Equivalence(EnsembleAbstrait[] classesEquivalence) {
        if(classesEquivalence == null) throw new IllegalArgumentException();
		tabRep = new Elt[MAX+1];
       sousJac = new Ensemble();
        for (int i = 0; i < classesEquivalence.length; i++) {
            if(classesEquivalence[i] == null || classesEquivalence[i].estVide()) throw new IllegalArgumentException();
            Elt rep = classesEquivalence[i].unElement();
                for (Elt elt : classesEquivalence[i]) {
                    if(sousJac.contient(elt)) throw new IllegalArgumentException();
                    sousJac.ajouter(elt);
                    tabRep[elt.val()]=rep;

            }

        }

	}
	
} 

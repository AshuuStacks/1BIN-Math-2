

public class Equivalence{
	
	/** Valeur numerique de MAXELT */
	private static final int MAX = Elt.MAXELT.val();

	private EnsembleAbstrait sousJac; // ensemble sous-jacent
	private Elt[] tabRep; // tableau des representants
	// Ne pas ajouter d'attribut

	// si possible, construit une relation d'equivalence
	// sinon, lance une IllegalArgumentException
	public Equivalence(EnsembleAbstrait[] classesEquivalence) {
         if(classesEquivalence == null)throw new IllegalArgumentException("classesEquivalence can't be null");
         sousJac = new Ensemble();
         tabRep = new Elt[MAX+1];

        for (int i = 0; i < classesEquivalence.length; i++) {
             if(classesEquivalence[i] == null || classesEquivalence[i].cardinal() == 0) throw new IllegalArgumentException("classesEquivalence can't be null");
            Elt rep = classesEquivalence[i].unElement();
            for (Elt x  : classesEquivalence[i]) {
              if(sousJac.contient(x)) throw new IllegalArgumentException();
              sousJac.ajouter(x);
              tabRep[x.val()] = rep;
            }
        }



	}
	
} 

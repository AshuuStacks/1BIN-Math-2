/** Classe Equivalence
	 Chaque instance de cette classe est une relation d'�quivalence sur un sous-ensemble de l'Univers
 */

import java.util.*;

public class Equivalence extends RelationAbstraite {

	private EnsembleAbstrait sousJac; // ensemble sous-jacent
	private Elt[] tabRep; // tableau des repr�sentants
	private int numVersion; // num�ro de version


	// Construit l�identit� sur e
	// Lance une IllegalArgumentException en cas de param�tre invalide 
    public Equivalence(EnsembleAbstrait e) {
        if (e == null)
            throw new IllegalArgumentException();

        sousJac = e.clone();
        tabRep = new Elt[MAX+1];

        for (Elt elt : sousJac) {
            tabRep[elt.val()] = elt;
        }

        numVersion = 0;
    }
	
	
	// ajoute (si n�cessaire) l�ar�te x-y au diagramme de classes de
	// l�Equivalence courante ; autrement dit, fusionne les classes de
	// c.getx() et de c.gety(). 
	// Lance une IllegalArgumentException en cas de param�tre invalide 
	public void ajouter(Couple c) {
        if(c == null || !sousJac.contient(c.getY()) || !sousJac.contient(c.getX())) throw new IllegalArgumentException();
        Elt x = tabRep[c.getX().val()];
        Elt y = tabRep[c.getY().val()];
      if(!y.equals(x)){
          for (int i = 0; i < MAX; i++) {
              if(tabRep[i]!=null && tabRep[i].equals(y)){
                  tabRep[i] = x;
              }
          }
          numVersion++;
      }


		//TODO
	}

	// Construit la cl�ture �quivalente de r, pour autant que celle-ci soit une relation sur un ensemble.
	// lance une IllegalArgumentException sinon
	public Equivalence(Relation r) {
		if(r == null) throw new IllegalArgumentException();
         tabRep = new Elt[MAX+1];
         sousJac = r.depart();
        for (Elt elt : sousJac) {
            tabRep[elt.val()] = elt;
        }
        for (Couple couple : r) {
            this.ajouter(couple);
        }


	}

	
	// renvoie true si c.getx() et c.gety() sont dans la m�me classe et false sinon
	// Lance une IllegalArgumentException en cas de param�tre invalide 
	public boolean contient(Couple c) {
   if(c == null) throw new IllegalArgumentException();
   if(!sousJac.contient(c.getX()) || !sousJac.contient(c.getY())) throw new IllegalArgumentException();

		return tabRep[c.getX().val()].equals(tabRep[c.getY().val()]);
	}

	// renvoie la classe d'�quivalence de x, ou g�n�re une IllegalArgumentException
	// si e est null ou si e n'appartient pas � l'ensemble sous-jacent
	public EnsembleAbstrait classe(Elt e) {
		if(e == null  || !sousJac.contient(e)) throw new IllegalArgumentException();
        EnsembleAbstrait ebe = new Ensemble();
        for (Elt elt : sousJac) {
            if(tabRep[elt.val()].equals(tabRep[e.val()])) ebe.ajouter(elt);
        }

		return ebe;
	}

	// Si c.getx()et c.gety() sont distincts et si la classe commune
	// de c.getx() et c.gety() est {c.getx(),c.gety()}, alors cette classe
	// sera scind�e en deux classes.
	// g�n�re une IllegalArgumentException si le param�tre est invalide,
	// ou si c.getx(), c.gety() sont dans la m�me classe  mais qu'on n'est pas 
	// dans le cas o� on peut scind�e cette classe.
    public void enlever(Couple c) {
        //TODO
        if(c==null || !sousJac.contient(c.getX()) ||!sousJac.contient(c.getY())){
            throw new IllegalArgumentException();
        }
        if(contient(c)) {
            if (c.getX().equals(c.getY())) {
                throw new IllegalArgumentException();
            }
            if (!tabRep[c.getX().val()].equals(tabRep[c.getY().val()])) {
                throw new IllegalArgumentException();
            }
            if (classe(c.getX()).cardinal() != 2) {
                throw new IllegalArgumentException();
            }
            tabRep[c.getX().val()] = c.getX();
            tabRep[c.getY().val()] = c.getY();
            numVersion++;
        }

    }


    // renvoie le nombre de classes de l'Equivalence courante
	public int nbreClasses() {
		int nbmdeRep = 0;
         EnsembleAbstrait ebe = new Ensemble();
        for (Elt elt : sousJac) {
            if(!ebe.contient(tabRep[elt.val()])) {
                ebe.ajouter(tabRep[elt.val()]);
                nbmdeRep++;
            }
        }
        return nbmdeRep;
	}

	// renvoie le quotient de l�ensemble sous-jacent par l'Equivalence
	// courante
	public EnsembleAbstrait[] quotient() {
		EnsembleAbstrait[] quot = new EnsembleAbstrait[nbreClasses()];
         EnsembleAbstrait ebe = new Ensemble();
        int j = 0;
        for (Elt elt : sousJac) {
            if(!ebe.contient(tabRep[elt.val()]) ) {
                ebe.ajouter(tabRep[elt.val()]);
                EnsembleAbstrait classe = classe(elt);
                quot[j++]=classe;
            }
        }
		return quot;
	}


	public boolean estVide() {
		return sousJac.estVide();
	}

	@Override
	public EnsembleAbstrait depart() {
		return sousJac.clone();
	}

	@Override
	public EnsembleAbstrait arrivee() {
		return sousJac.clone();
	}
	
	/** renvoie un it�rateur sur l'Equivalence courante */
	public Iterator<Couple> iterator() {
		return new EquivalenceIterator();
	}

	private class EquivalenceIterator implements java.util.Iterator<Couple> {
		private Iterator<Couple> itC;
		private int version;

		public EquivalenceIterator() {
			version = numVersion;
			Relation r = new Relation(sousJac, sousJac);
			EnsembleInterface reste = sousJac.clone();
			while (!reste.estVide()) {
				Elt e = reste.unElement();
				EnsembleAbstrait classeE = classe(e);
				Iterator<Elt> itClasseE = classeE.iterator();
				while (itClasseE.hasNext()) {
					Elt next = itClasseE.next();
					r.ajouter(e, next);
					r.ajouter(next, e);
					r.ajouter(next, next);
				}
				reste.enlever(classeE);
			}
			r.cloTrans();
			itC = r.iterator();
		}

		@Override
		public boolean hasNext() {
			return itC.hasNext();
		}

		@Override
		public Couple next() {
			if (numVersion != this.version)
				throw new ConcurrentModificationException();
			if (!hasNext())
				throw new NoSuchElementException();
			return itC.next();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

} // Equivalence

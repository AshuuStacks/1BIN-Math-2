/** Classe Equivalence
	 Chaque instance de cette classe est une relation d'�quivalence sur un sous-ensemble de l'Univers
 */

import com.sun.nio.file.ExtendedWatchEventModifier;
import com.sun.source.tree.EnhancedForLoopTree;
import jdk.dynalink.beans.StaticClass;

import java.util.*;

public class Equivalence extends RelationAbstraite {

	private EnsembleAbstrait sousJac; // ensemble sous-jacent
	private Elt[] tabRep; // tableau des repr�sentants
	private int numVersion; // num�ro de version


	// Construit l�identit� sur e
	// Lance une IllegalArgumentException en cas de param�tre invalide 
	public Equivalence(EnsembleAbstrait e) {
        if(e== null){
            throw new IllegalArgumentException();
        }
        this.sousJac = e.clone();
        //System.out.println(sousJac)
        tabRep = new Elt[MAX+1];
        //System.out.println(tabRep.length);
        for (Elt element : sousJac){
            tabRep[element.val()]= element;
        }
	}
	
	
	// ajoute (si n�cessaire) l�ar�te x-y au diagramme de classes de
	// l�Equivalence courante ; autrement dit, fusionne les classes de
	// c.getx() et de c.gety(). 
	// Lance une IllegalArgumentException en cas de param�tre invalide 
	public void ajouter(Couple c) {
        if(c== null||!sousJac.contient(c.getY()) ||!sousJac.contient(c.getX())){
            throw new IllegalArgumentException();
        }        Elt x = tabRep[c.getX().val()];
        Elt y = tabRep[c.getY().val()];

        if(!x.equals(y)){
            for (Elt elt : sousJac) {
                if(tabRep[elt.val()].equals(y)){
                    tabRep[elt.val()] = x;
                }
            }
            numVersion++;
        }
	}

	// Construit la cl�ture �quivalente de r, pour autant que celle-ci soit une relation sur un ensemble.
	// lance une IllegalArgumentException sinon
    public Equivalence(Relation r) {
        if (r == null) throw new IllegalArgumentException();
        if (!r.depart().equals(r.arrivee())) throw new IllegalArgumentException();

        sousJac = new Ensemble(r.depart());
        tabRep = new Elt[MAX + 1];

        for (Elt elt : sousJac) {
            tabRep[elt.val()] = elt;
        }

        for (Couple c : r) {
            ajouter(c);
        }
    }
	
	// renvoie true si c.getx() et c.gety() sont dans la m�me classe et false sinon
	// Lance une IllegalArgumentException en cas de param�tre invalide 
	public boolean contient(Couple c) {
        if(c == null || !this.contient(c)) throw new IllegalArgumentException();
        Elt x = tabRep[c.getX().val()];
        Elt y = tabRep[c.getY().val()];
		return x.equals(y);
	}

	// renvoie la classe d'�quivalence de x, ou g�n�re une IllegalArgumentException
	// si e est null ou si e n'appartient pas � l'ensemble sous-jacent
	public EnsembleAbstrait classe(Elt e) {
		if(e == null || !sousJac.contient(e)) throw new IllegalArgumentException();
        EnsembleAbstrait x = new Ensemble();
        Elt repX = tabRep[e.val()];
        for (Elt elt : sousJac) {
            if(tabRep[elt.val()].equals(repX)){
                x.ajouter(elt);
            }
        }

		return x;
	}

	// Si c.getx()et c.gety() sont distincts et si la classe commune
	// de c.getx() et c.gety() est {c.getx(),c.gety()}, alors cette classe
	// sera scind�e en deux classes.
	// g�n�re une IllegalArgumentException si le param�tre est invalide,
	// ou si c.getx(), c.gety() sont dans la m�me classe  mais qu'on n'est pas 
	// dans le cas o� on peut scind�e cette classe.
	public void enlever(Couple c) {
	}

	// renvoie le nombre de classes de l'Equivalence courante
	public int nbreClasses() {
        Ensemble ensemble = new Ensemble();
        for (Elt elt : sousJac) {
            if(!ensemble.contient(tabRep[elt.val()])){
                ensemble.ajouter(tabRep[elt.val()]);
            }
        }
		return ensemble.cardinal() ;
	}

	// renvoie le quotient de l�ensemble sous-jacent par l'Equivalence
	// courante
	public EnsembleAbstrait[] quotient() {
		EnsembleAbstrait[] x = new EnsembleAbstrait[nbreClasses()];
        for (Elt elt : sousJac) {
            Elt rep = tabRep[elt.val()];
           if(!x[elt.val()].contient(rep)){
               x[elt.val()]
           }
        }

		return x;
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

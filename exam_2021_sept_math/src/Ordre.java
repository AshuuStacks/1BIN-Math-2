import java.util.Iterator;

public class Ordre implements Iterable<Couple> {

	private Relation couples;

	// construit l'identité sur e
	// lance une IllegalArgumentException en cas de paramêtre invalide
	public Ordre(EnsembleAbstrait e) {
		couples = Relation.identite(e);
	}

	// constructeur par recopie
	// lance une IllegalArgumentException en cas de paramêtre invalide
	public Ordre(Ordre or) {
		if (or == null)
			throw new IllegalArgumentException();
		couples = or.couples.clone() ;
	}

	public Iterator<Couple> iterator() {
		return couples.iterator();
	}

	public boolean estVide() {
		return couples.estVide();
	}

	public boolean contient(Couple c) {
		if (c == null)
			throw new IllegalArgumentException();
		if (!couples.depart().contient(c.getX()) || !couples.arrivee().contient(c.getY()))
			throw new IllegalArgumentException();
		return couples.contient(c.getX(), c.getY());
	}

	public EnsembleAbstrait depart() {
		return couples.depart();
	}

	public EnsembleAbstrait arrivee() {
		return couples.arrivee();
	}

	public int cardinal() {
		return couples.cardinal();
	}

	public String toString() {
		return couples.toString();
	}

	/**
	 * Renvoie true  si l'ordre courant est un ordre total 
	 *         false sinon 
	 * 
	 * @parameter l'Ordre ordre
	 * @return true si l'ordre courant est un ordre total
	 *         false sinon
	 **/
	public boolean estTotal() {

        for (Elt elt : this.depart()) {
            for (Elt elt1 : this.depart()) {
               if(!this.contient(new Couple(elt,elt1)) && !this.contient(new Couple(elt1,elt) )){
                   return false;
               }
            }
        }
		//TODO
		return true ;
	}
	
	
	
	/**
	 * Renvoie true  si l'ordre courant est un tri topologique de l'Ordre ordre 
	 *         false sinon 
	 * 
	 * @parameter l'Ordre ordre
	 * @return true si l'ordre courant est un tri topologique de l'Ordre ordre 
	 *         false sinon
	 * @throws IllegalArgumentException en cas de paramêtre invalide
	 **/
	public boolean estUnTriTopologiqueDe(Ordre ordre) {
        if(ordre == null) throw new IllegalArgumentException("balbalbal");
        if(!ordre.depart().equals(this.depart())) return false;

        if(!estTotal()) return false;

        for (Couple couple : ordre) {
            if(!this.contient(couple)){
                return false;
            }
        }

        //TODO
		// Pensez à utiliser la méthode estTotal
		return true ;
	}

}
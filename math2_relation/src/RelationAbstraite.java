

public abstract class RelationAbstraite implements RelationInterface {
	
	// Ex 3
	// renvoie true ssi this est inclus dans r
	public boolean inclusDans(RelationAbstraite r) {
        if(r==null) return false;
        if(!this.depart().equals(r.depart())) return false;
        if(!this.arrivee().equals(r.arrivee())) return false;

        for (Couple couple : this) {
            if(!r.contient(couple)) {
                return false;
            }
        }
		return true;
	}

	// renvoie true ssi this est égale à o
	public boolean equals(Object o) {
		if (o== null) return false;
		if (o==this) return true;
		if (! (o instanceof RelationAbstraite)) return false;
		RelationAbstraite r = (RelationAbstraite) o;
        if(!this.depart().equals(r.depart())) return false;
        if(!this.arrivee().equals(r.arrivee())) return false;

        return this.inclusDans(r) && r.inclusDans(this);
	}
	
	//renvoie un hashCode associé à la relation
	
	public int hashCode(){
		int hash = this.depart().hashCode();
		hash = hash * 31 + this.arrivee().hashCode();
		for (int i = 1; i <= MAX; i++) {
			Elt d = new Elt(i);
			if (this.depart().contient(d)){
				for (int j = 1; j <=MAX; j++ ){
					Elt a = new Elt(j);
					Couple c = new Couple(d,a);
					if (this.contient(c))
						hash = hash *31 + c.hashCode();
				}
			}
		}
		return hash;
	}
}

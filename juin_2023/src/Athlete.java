
public class Athlete implements Comparable<Athlete> {
	private final String nom;
	private int nbSautEffectue = 0;
	private int[] sauts = new int[3];

	public Athlete(String nom) {
		this.nom = nom;
	}

	public String getNom() {
		return nom;
	}

	public void ajouterSaut(int longueur) {
		if (longueur < 0)
			throw new IllegalArgumentException();
		if (nbSautEffectue == 3)
			throw new RuntimeException();
		sauts[nbSautEffectue] = longueur;
		nbSautEffectue++;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Athlete other = (Athlete) obj;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		return true;
	}



    @Override
    public int compareTo(Athlete other) {
        int OtherSautMax = 0;
        int thisSautMax = 0;
        for (int i = 0; i < 3; i++) {
            if (OtherSautMax < other.sauts[i]) {
                OtherSautMax = other.sauts[i];
            }
            if (thisSautMax < this.sauts[i]) {
                thisSautMax = this.sauts[i];
            }
        }

        if (OtherSautMax != thisSautMax) {
            return OtherSautMax - thisSautMax;
        }

        return  this.nom.compareTo(other.nom);

    }
}

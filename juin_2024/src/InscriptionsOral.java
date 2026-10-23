import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class InscriptionsOral {


    HashMap<Etudiant,PlageHoraire> map;
	// Attributs � ajouter ici
	
	public InscriptionsOral() {
        map = new HashMap<>();
		// Initialiser les attributs ici
	}
	
	// Inscrit l'�tudiant � une plage horaire. Plusieurs �tudiants peuvent s'inscrire � la m�me plage horaire.
	// Cette m�thode v�rifie si l'�tudiant est d�j� inscrit � une autre plage horaire.
    // Si oui, une RuntimeException est lanc�e
	public void inscrireEtudiant(Etudiant e, PlageHoraire p) {
        if (map.containsKey(e)) throw new RuntimeException();
        map.put(e,p);
		// TODO
	}
	
	// Affiche l'horaire des examens en ordre chronologique.
    // Elle affiche les �tudiants inscrits pour chaque plage horaire. 
	// L'ordre des �tudiants au sein d'une plage n'a pas d'importance
    public void afficherHoraire() {

        ArrayList<PlageHoraire> list = new ArrayList<>(new HashSet<>(map.values()));

        Collections.sort(list);

        for (PlageHoraire horaire : list) {
            System.out.print("Horaire " + horaire);
            for (Etudiant etudiant : map.keySet()) {
                if(map.get(etudiant).equals(horaire)){
                    System.out.print("/"+etudiant + " ");
                }
            }
            System.out.println();
        }
    }
	
	// Echange les plages horaires de deux �tudiants
    // Si l'un des deux �tudiants n'est pas inscrit, une RuntimeException est lanc�e
	public void echanger(Etudiant e1, Etudiant e2) {
        PlageHoraire p1 = map.get(e1);
		if(map.get(e1) == null || map.get(e2) == null ) throw new IllegalArgumentException();
        map.put(e1,map.get(e2));
        map.put(e2,p1);

	}
	
}

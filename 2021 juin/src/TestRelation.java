public class TestRelation {

	public static void main(String[] args) {
		System.out.println("*********************************************************************");
		System.out.println("Programme Test pour la methode ensembleIsoles() de la classe Relation");
		System.out.println("*********************************************************************");
		
		boolean testOK = true;
		Relation re1 = Io.chargerRelation("dg1.rel");
		EnsembleAbstrait e1 = re1.ensembleDesIsoles();
		Ensemble result1 = new Ensemble("{2,4,16,17}");	
		if (!e1.equals(result1)){
			System.out.println("L'ensemble des isoles de la relation re1 (donnee en exemple) vaut " + result1);
			System.out.println("Votre methode a renvoye : " + e1);
			testOK = false;
		}
		Relation re2 = Io.chargerRelation("dg2.rel");
		EnsembleAbstrait e2 = re2.ensembleDesIsoles();
		Ensemble result2 = new Ensemble("{}");	
		if (!e2.equals(result2)){
			System.out.println("L'ensemble des isoles de la relation re2 (donnee en exemple) vaut " + result2);
			System.out.println("Votre methode a renvoye : " + e2);
			testOK = false;
		}
		if(testOK){
			System.out.println("Les tests avec les relations dg1 et dg2 ont reussi !");
		}		
	}	
}

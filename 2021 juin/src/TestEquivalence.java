public class TestEquivalence {

	public static void main(String[] args) {


		System.out.println("************************************************************************");
		System.out.println("Programme Test pour la methode ensembleIsoles() de la classe Equivalence");
		System.out.println("************************************************************************");

		boolean tousReussi = true;

		// eq1

		Elt[] tab1 = new Elt[Elt.MAXELT.val()+1];

		tab1[1]=new Elt(7);
		tab1[3]=new Elt(3);
		tab1[4]=new Elt(4);
		tab1[5]=new Elt(9);
		tab1[7]=new Elt(7);
		tab1[8]=new Elt(7);
		tab1[9]=new Elt(9);
		tab1[10]=new Elt(9);
		tab1[11]=new Elt(9);
		tab1[12]=new Elt(3);	


		Equivalence eq1 = new Equivalence(tab1 );
		EnsembleAbstrait e1 = eq1.ensembleDesIsoles();
		Ensemble result1 = new Ensemble("{4}");	
		if (!e1.equals(result1)){
			System.out.println("L'ensemble des isoles de la relation d'equivalence eq1 donne " + result1);
			System.out.println("Votre methode a renvoye : " + e1);
			tousReussi = false;
		}

		// eq2 

		Elt[] tab2 = new Elt[Elt.MAXELT.val()+1];

		tab2[1]=new Elt(7);
		tab2[3]=new Elt(3);
		tab2[4]=new Elt(4);
		tab2[5]=new Elt(9);
		tab2[7]=new Elt(7);
		tab2[8]=new Elt(8);
		tab2[9]=new Elt(9);
		tab2[10]=new Elt(9);
		tab2[11]=new Elt(9);
		tab2[12]=new Elt(3);	

		
		Equivalence eq2 = new Equivalence(tab2 );
		EnsembleAbstrait e2 = eq2.ensembleDesIsoles();
		Ensemble result2 = new Ensemble("{4,8}");	
		if (!e2.equals(result2)){
			System.out.println("L'ensemble des isoles de la relation d'equivalence eq2 donne " + result2);
			System.out.println("Votre methode a renvoye : " + e2);
			tousReussi = false;
		}

		Elt[] tab3 = new Elt[Elt.MAXELT.val()+1];

		tab3[1]=new Elt(7);
		tab3[3]=new Elt(3);
		tab3[5]=new Elt(9);
		tab3[7]=new Elt(7);
		tab3[8]=new Elt(7);
		tab3[9]=new Elt(9);
		tab3[10]=new Elt(9);
		tab3[11]=new Elt(9);
		tab3[12]=new Elt(3);
		
		Equivalence eq3 = new Equivalence(tab3);
		EnsembleAbstrait e3 = eq3.ensembleDesIsoles();
		Ensemble result3 = new Ensemble("{}");	
		if (!e3.equals(result3)){
			System.out.println("L'ensemble des isoles de la relation d'equivalence eq3 donne " + result3);
			System.out.println("Votre methode a renvoye : " + e3);
			tousReussi = false;
		}

		if(tousReussi){
			System.out.println("Les tests avec les relations eq1, eq2 et eq3 ont reussi !");
		}

	}
}



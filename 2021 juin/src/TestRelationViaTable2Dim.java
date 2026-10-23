import java.util.Scanner;

public class wTestRelationViaTable2Dim {
	private final static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.println("**************************************************");
		System.out.println("Programme Test pour la classe RelationViaTable2Dim");
		System.out.println("**************************************************");
		int choix = 0;

		// Pour les tests MAX = 6
		Elt.MAXELT = new Elt(6);

		do{
			System.out.println();
			System.out.println("1 -> Tester la methode 'ajouter'");
			System.out.println("2 -> Tester la methode 'contient'");	
			System.out.println("3 -> Tester la methode 'clotureSymetrique'");
			System.out.println("4 -> Tester la methode 'reflexive'");
			System.out.println("5 -> Tester la methode 'transitive'");
			System.out.println();
			System.out.print("Entrez votre choix : ");

			choix = scanner.nextInt();

			switch (choix) {
			case 1 :
				testAjouter();
				break;

			case 2:
				testContient();
				break;

			case 3:
				testCloSymetrique();
				break;
				
			case 4:
				testReflexive();
				break;
				
			case 5:
				testTransitive();
				break;
			default:
				break;


			} 
		}while (choix>0 && choix<6);

	}

	private static void testAjouter() {
	
		// ajout (4,5) dans r1
		RelationViaTable2Dim r = r1();
		r.ajouter(new Couple(4,5));
		RelationViaTable2Dim rAttendu = r1Avec4_5();
		if (!r.toString().equals(rAttendu.toString())){
			System.out.println();
			System.out.println("Attention, apres ajout du couple (4,5) dans r1, la table est ko ");
			System.out.println();
			System.out.println("Voici la table attendue : ");
			System.out.println(rAttendu.toString());
			System.out.println("Voici la table obtenue : ");
			System.out.println(r.toString());
			System.out.println("Revoyez votre methode !");
			return;
		}
		if (r.taille()!=rAttendu.taille()){
			System.out.println();
			System.out.println("Apres ajout du couple (4,5) dans r1, la table est ok");
			System.out.println("Mais le nombre de couples (taille) est ko");
			System.out.print("Voici la taille attendue : ");
			System.out.println(rAttendu.taille());
			System.out.print("Voici la taille obtenue : ");
			System.out.println(r.taille());
			System.out.println("Revoyez votre methode !");
			return;
		}
		
		
		// ajout (4,3) deja present dans r1
		r = r1();
		r.ajouter(new Couple(4,3));
		rAttendu = r1();
		if (!r.toString().equals(rAttendu.toString())){
			System.out.println();
			System.out.println("Attention, apres ajout du couple (4,3) (deja existant) dans r1, la table est ko ");
			System.out.println();
			System.out.println("Voici la table attendue : ");
			System.out.println(rAttendu.toString());
			System.out.println("Voici la table obtenue : ");
			System.out.println(r.toString());
			System.out.println("Revoyez votre methode !");
			return;
		}
		if (r.taille()!=rAttendu.taille()){
			System.out.println();
			System.out.println("Apres ajout du couple (4,3) (deja existant) dans r1, la table est ok");
			System.out.println("Mais le nombre de couples (taille) est ko");
			System.out.print("Voici la taille attendue : ");
			System.out.println(rAttendu.taille());
			System.out.print("Voici la taille obtenue : ");
			System.out.println(r.taille());
			System.out.println("Revoyez votre methode !");
			return;
		}
		
		System.out.println();
		System.out.println("Tous les tests avec la relation r1 ont reussi !");
		
	}

	private static void testContient() {
	
		RelationViaTable2Dim r = r1();
		
		// r1 contient le couple (5,3)
		if(!r.contient(new Couple(5, 3))){
			System.out.print("Attention, votre methode annonce que r1 ne contient pas le couple (5,3) ");
			System.out.println("Revoyez votre methode !");		
			return;
		}
		
		// r1 ne contient pas le couple (4,6)
		if(r.contient(new Couple(4, 6))){
			System.out.print("Attention, votre methode annonce que r1 contient le couple (4,6) ");
			System.out.println("Revoyez votre methode !");
			return;			
		}
		
		System.out.println();
		System.out.println("Tous les tests avec la relation r1 ont reussi !");
		
	}

	
	private static void testCloSymetrique() {
		
		//cloture symetrique de r1
		
		RelationViaTable2Dim r = r1();
		r.cloSym();
		RelationViaTable2Dim rAttendu = r1CloSym();
		
		if (!r.toString().equals(rAttendu.toString())){
			System.out.println();
			System.out.println("Attention, apres cloture symetrique de r1, la table est ko ");
			System.out.println();
			System.out.println("Voici la table attendue : ");
			System.out.println(rAttendu.toString());
			System.out.println("Voici la table obtenue : ");
			System.out.println(r.toString());
			System.out.println("Revoyez votre methode !");
			return;	
		}

		if (r.taille()!=rAttendu.taille()){
			System.out.println();
			System.out.println("Apres la cloture reflexive de r1, la table est ok");
			System.out.println("Mais le nombre de couples (taille) est ko");
			System.out.print("Voici la taille attendue : ");
			System.out.println(rAttendu.taille());
			System.out.print("Voici la taille obtenue : ");
			System.out.println(r.taille());
			System.out.println("Revoyez votre methode !");
			return;
		}
		System.out.println();
		System.out.println("Le test avec la relation r1 a reussi !");
	}


	private static void testReflexive() {
		
		RelationViaTable2Dim r = r1();
		if(r.reflexive()){
			System.out.print("Attention, votre methode annonce que r1 est reflexive ");
			System.out.println("Revoyez votre methode !");		
			return;
		}
		r = r2();
		if(!r.reflexive()){
			System.out.print("Attention, votre methode annonce que r2 n'est pas reflexive ");
			System.out.println("Revoyez votre methode !");		
			return;
		}
		r = r3();
		if(r.reflexive()){
			System.out.print("Attention, votre methode annonce que r3 est reflexive ");
			System.out.println("Revoyez votre methode !");		
			return;
		}
		System.out.println();
		System.out.println("Les tests avec les relations r1, r2 et r3 ont reussi !");
	}

	private static void testTransitive() {
		RelationViaTable2Dim r = r1();
		if(r.transitive()){
			System.out.print("Attention, votre methode annonce que r1 est transitive ");
			System.out.println("Revoyez votre methode !");		
			return;
		}
		
		r = r2();
		if(r.transitive()){
			System.out.print("Attention, votre methode annonce que r2 est transitive ");
			System.out.println("Revoyez votre methode !");		
			return;
		}
		
		r = r3();
		if(!r.transitive()){
			System.out.print("Attention, votre methode annonce que r3 n'est pas transitive ");
			System.out.println("Revoyez votre methode !");		
			return;
		}
		
		System.out.println();
		System.out.println("Les tests avec les relations r1, r2 et r3 ont reussi !");
		
	}

	
	private static RelationViaTable2Dim r1(){
		boolean[][] t = new boolean[7][7];
		t[3][1]=true;
		t[3][4]=true;
		t[4][3]=true;
		t[4][4]=true;
		t[5][3]=true;
		t[6][4]=true;
		return new RelationViaTable2Dim(t);		
	}
	
	private static RelationViaTable2Dim r1CloSym(){
		boolean[][] t = new boolean[7][7];
		t[3][1]=true;
		t[1][3]=true;
		t[3][4]=true;
		t[4][3]=true;
		t[4][4]=true;
		t[5][3]=true;
		t[3][5]=true;
		t[6][4]=true;
		t[4][6]=true;
		return new RelationViaTable2Dim(t);		
	}
	
	private static RelationViaTable2Dim r1Avec4_5(){
		boolean[][] t = new boolean[7][7];
		t[3][1]=true;
		t[3][4]=true;
		t[4][3]=true;
		t[4][4]=true;
		t[5][3]=true;
		t[6][4]=true;
		
		t[4][5]=true;
		
		return new RelationViaTable2Dim(t);		
	}
	
	private static RelationViaTable2Dim r2(){
		boolean[][] t = new boolean[7][7];
		t[1][1]=true;
		t[2][2]=true;
		t[3][3]=true;
		t[4][4]=true;
		t[5][5]=true;
		t[6][6]=true;	
		t[2][3]=true;
		t[2][5]=true;
		t[3][5]=true;
		t[4][2]=true;
		t[5][3]=true;
		return new RelationViaTable2Dim(t);		
	}
	
	
	private static RelationViaTable2Dim r3(){
		boolean[][] t = new boolean[7][7];
		t[2][2]=true;
		t[3][3]=true;	
		t[1][4]=true;
		t[2][3]=true;
		t[2][4]=true;
		t[2][5]=true;
		t[5][3]=true;	
		t[5][4]=true;
		return new RelationViaTable2Dim(t);		
	}

}

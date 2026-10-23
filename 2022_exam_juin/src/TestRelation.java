public class TestRelation {

	public static void main(String[] args) {
		System.out.println("*****************************************************************************");
		System.out.println("Programme Test pour la methode sommeDisjointe() de la classe EnsembleAbstrait");
		System.out.println("*****************************************************************************");
		int temps = 300 ;

		boolean testOK = true;
		EnsembleAbstrait a = new Ensemble("{1,3,4}");
		EnsembleAbstrait aInit = new Ensemble("{1,3,4}");
		EnsembleAbstrait b = new Ensemble("{4,5}") ;
		EnsembleAbstrait bInit = new Ensemble("{4,5}") ;

		System.out.print("Test 1 : ") ;

		Relation reAttendue = Io.chargerRelation("reEx1.rel");
		try {
			Relation reRecue = a.sommeDisjointe(b);
			if (!sontEgales(reAttendue, reRecue)) {
				System.out.println("KO");
				System.out.println("Calcul de " + a + "+" + b + ":");
				System.out.println("==================");
				System.out.println("Relation obtenue :");
				System.out.println("==================");
				System.out.println(reRecue);
				System.out.println("==================");
				System.out.println("Relation attendue :");
				System.out.println("==================");
				System.out.println(reAttendue);
				testOK = false;
			} else if (!sontEgaux(a,aInit)) {
				System.out.println("KO");
				System.out.println("Vous avez modifié l'ensemble courant !") ;
				testOK = false;
			} else if (!sontEgaux(b,bInit)) {
				System.out.println("KO");
				System.out.println("Vous avez modifié l'ensemble passé en paramètre !") ;
				testOK = false;
			} else {
				System.out.println("OK");
			}
		} catch(Exception e) {
			System.out.println("KO");
			System.out.println("Il ne fallait pas d'exception") ;
			e.printStackTrace();
			try {
				Thread.sleep(temps);
			} catch(InterruptedException ex) {

			}
			testOK = false;
		}
		System.out.println();

		System.out.print("Test 2 : ") ;

		a = new Ensemble("{1,3,4}");
		b = new Ensemble("{4,5}") ;
		reAttendue = Io.chargerRelation("reEx2.rel");
		try {
			Relation reRecue = b.sommeDisjointe(a);
			if (!sontEgales(reAttendue, reRecue)) {
				System.out.println("KO");
				System.out.println("Calcul de " + b + "+" + a + ":");
				System.out.println("==================");
				System.out.println("Relation obtenue :");
				System.out.println("==================");
				System.out.println(reRecue);
				System.out.println("==================");
				System.out.println("Relation attendue :");
				System.out.println("==================");
				System.out.println(reAttendue);
				testOK = false;
			} else if (!sontEgaux(b,bInit)) {
				System.out.println("KO");
				System.out.println("Vous avez modifié l'ensemble courant !") ;
				testOK = false;
			} else if (!sontEgaux(a,aInit)) {
				System.out.println("KO");
				System.out.println("Vous avez modifié l'ensemble passé en paramètre !") ;
				testOK = false;
			} else {
				System.out.println("OK");
			}
		} catch(Exception e) {
			System.out.println("KO");
			System.out.println("Il ne fallait pas d'exception") ;
			e.printStackTrace();
			try {
				Thread.sleep(temps);
			} catch(InterruptedException ex) {

			}
			testOK = false;
		}
		System.out.println();

		System.out.print("Test 3 : ") ;

		a = new Ensemble("{1,3,4}");
		b = new Ensemble() ;
		bInit = new Ensemble() ;
		reAttendue = Io.chargerRelation("reEx3.rel");
		try {
			Relation reRecue = a.sommeDisjointe(b);
			if (!sontEgales(reAttendue, reRecue)) {
				System.out.println("KO");
				System.out.println("Calcul de " + a + "+" + b + ":");
				System.out.println("==================");
				System.out.println("Relation obtenue :");
				System.out.println("==================");
				System.out.println(reRecue);
				System.out.println("==================");
				System.out.println("Relation attendue :");
				System.out.println("==================");
				System.out.println(reAttendue);
				testOK = false;
			} else if (!sontEgaux(a,aInit)) {
				System.out.println("KO");
				System.out.println("Vous avez modifié l'ensemble courant !") ;
				testOK = false;
			} else if (!sontEgaux(b,bInit)) {
				System.out.println("KO");
				System.out.println("Vous avez modifié l'ensemble passé en paramètre !") ;
				testOK = false;
			} else {
				System.out.println("OK");
			}
		} catch(Exception e) {
			System.out.println("KO");
			System.out.println("Il ne fallait pas d'exception") ;
			e.printStackTrace();
			try {
				Thread.sleep(temps);
			} catch(InterruptedException ex) {

			}
			testOK = false;
		}
		System.out.println();

		System.out.print("Test 4 : ") ;

		a = new Ensemble();
		aInit = new Ensemble() ;
		b = new Ensemble("{4,5}") ;
		bInit = new Ensemble("{4,5}") ;
		reAttendue = Io.chargerRelation("reEx4.rel");
		try {
			Relation reRecue = a.sommeDisjointe(b);
			if (!sontEgales(reAttendue, reRecue)) {
				System.out.println("KO");
				System.out.println("Calcul de " + a + "+" + b + ":");
				System.out.println("==================");
				System.out.println("Relation obtenue :");
				System.out.println("==================");
				System.out.println(reRecue);
				System.out.println("==================");
				System.out.println("Relation attendue :");
				System.out.println("==================");
				System.out.println(reAttendue);
				testOK = false;
			} else if (!sontEgaux(a,aInit)) {
				System.out.println("KO");
				System.out.println("Vous avez modifié l'ensemble courant !") ;
				testOK = false;
			} else if (!sontEgaux(b,bInit)) {
				System.out.println("KO");
				System.out.println("Vous avez modifié l'ensemble passé en paramètre !") ;
				testOK = false;
			} else {
				System.out.println("OK");
			}
		} catch(Exception e) {
			System.out.println("KO");
			System.out.println("Il ne fallait pas d'exception") ;
			e.printStackTrace();
			try {
				Thread.sleep(temps);
			} catch(InterruptedException ex) {

			}
			testOK = false;
		}
		System.out.println();

		System.out.print("Test 5 : ") ;

		a = new Ensemble();
		aInit = new Ensemble() ;
		b = new Ensemble() ;
		bInit = new Ensemble() ;
		reAttendue = new Relation(new Ensemble("{1,2}"),new Ensemble());
		try {
			Relation reRecue = a.sommeDisjointe(b);
			if (!sontEgales(reAttendue, reRecue)) {
				System.out.println("KO");
				System.out.println("Calcul de " + a + "+" + b + ":");
				System.out.println("==================");
				System.out.println("Relation obtenue :");
				System.out.println("==================");
				System.out.println(reRecue);
				System.out.println("==================");
				System.out.println("Relation attendue :");
				System.out.println("==================");
				System.out.println(reAttendue);
				testOK = false;
			} else if (!sontEgaux(a,aInit)) {
				System.out.println("KO");
				System.out.println("Vous avez modifié l'ensemble courant !") ;
				testOK = false;
			} else if (!sontEgaux(b,bInit)) {
				System.out.println("KO");
				System.out.println("Vous avez modifié l'ensemble passé en paramètre !") ;
				testOK = false;
			} else {
				System.out.println("OK");
			}
		} catch(Exception e) {
			System.out.println("KO");
			System.out.println("Il ne fallait pas d'exception") ;
			e.printStackTrace();
			try {
				Thread.sleep(temps);
			} catch(InterruptedException ex) {

			}
			testOK = false;
		}
		System.out.println();
		if (testOK) {
			System.out.println("Tous les tests de la méthode sommeDisjointe ont réussis !") ;
		} else {
			System.out.println("Les tests de la méthode sommeDisjointe ont échoués !") ;
		}

	}

	public static boolean sontEgales(Relation r1, Relation r2) {
		//System.out.println("ici1") ;
		if (r1==null||r2==null)
			return false;
		//System.out.println("ici2") ;
		if (r1.cardinal()!=r2.cardinal())
			return false ;
		//System.out.println("ici3") ;
		if (!sontEgaux(r1.depart(),r2.depart()))
			return false ;
		//System.out.println("ici4") ;
		if (!sontEgaux(r1.arrivee(),r2.arrivee()))
			return false ;
		//System.out.println("ici5") ;
		for (Couple c:r1) {
			if (!r2.contient(c)) {
				return false;
			}
		}
		return true ;
	}

	public static boolean sontEgaux(EnsembleAbstrait e1, EnsembleAbstrait e2) {
		if (e1==null||e2==null)
			return false;
		if (e1.cardinal()!=e2.cardinal())
			return false ;
		for (Elt e:e1) {
			if (!e2.contient(e)) {
				return false;
			}
		}
		return true ;
	}

	public static String printRel(Relation r) {
		String re = "{";
		for (Couple c:r) {
			re = re+c+", ";
		}
		if (!r.estVide()) {
			re = re.substring(0,re.length()-2);
		}
		re = re+"}";
		return re ;
	}

}

public class TestOrdre {
	private final static java.util.Scanner scanner = new java.util.Scanner(System.in);
	private static final String[] NOMS_METHODE = { "estTotal","estUnTriTopologiqueDe" };

	public static void main(String[] args) {
		System.out.println("***************************************");
		System.out.println("* Programme Test pour la classe Ordre *");
		System.out.println("***************************************");
		int choix = 0;
		while (true) {
			for (int i = 0; i < NOMS_METHODE.length; i++) {
				System.out.println((i + 1) + " -> Tester la méthode '" + NOMS_METHODE[i] + "'");
			}

			choix = scanner.nextInt();
			System.out.println();
			boolean testOK;
			switch (choix) {
				case 1:
					testOK = testEstTotal(); 
					break;
				case 2:
					testOK = testEstUnTriTopologiqueDe();
					break;
				default:
					return;
			}
			if (testOK)
				System.out.println("Le test de la méthode " + NOMS_METHODE[choix - 1] + " a réussi.");
			else
				System.out.println("Le test de la méthode " + NOMS_METHODE[choix - 1] + " a échoué.");
			System.out.println();
		}
	}
	
	private static boolean testEstTotal() {
		boolean testOK = true;

		System.out.print("Test 1 : ");
		Relation r = Io.chargerRelation("orExAout1.rel");
		Ordre or = UtilOrdre.creerOrdre(r);
		try {
			if (or.estTotal()) {
				System.out.println("Echoué");
				System.out.println("Votre test dit que l'ordre");
				System.out.println(or);
				System.out.println("est un ordre total mais c'est faux");
				System.out.println(or);
				testOK = false;
			} else {
				System.out.println("OK");
			}
		} catch(Exception e) {
			System.out.println("Echoué : Il ne fallait pas d'exception.") ;
			e.printStackTrace();
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException ex) {
				System.out.println();
			}
		}
		System.out.println();
		
		System.out.print("Test 2 : ");
		r = Io.chargerRelation("orExAout6.rel");
		or = UtilOrdre.creerOrdre(r);
		try {
			if (!or.estTotal()) {
				System.out.println("Echoué");
				System.out.println("Votre test dit que l'ordre");
				System.out.println(or);
				System.out.println("n'est pas total. Or il l'est.");
				System.out.println(or);
				testOK = false;
			} else {
				System.out.println("OK");
			}
		} catch(Exception e) {
			System.out.println("Echoué : Il ne fallait pas d'exception.") ;
			e.printStackTrace();
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException ex) {
				System.out.println();
			}
		}
		System.out.println();
		
		System.out.print("Test 3 : ");
		r = Io.chargerRelation("orExAout3.rel");
		or = UtilOrdre.creerOrdre(r);
		try {
			if (!or.estTotal()) {
				System.out.println("Echoué");
				System.out.println("Votre test dit que l'ordre");
				System.out.println(or);
				System.out.println("n'est pas total. Or il l'est.");
				System.out.println(or);
				testOK = false;
			} else {
				System.out.println("OK");
			}
		} catch(Exception e) {
			System.out.println("Echoué : Il ne fallait pas d'exception.") ;
			e.printStackTrace();
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException ex) {
				System.out.println();
			}
		}
		System.out.println();
		
		System.out.print("Test 4 : ");
		r = new Relation();
		or = UtilOrdre.creerOrdre(r);
		try {
			if (!or.estTotal()) {
				System.out.println("Echoué");
				System.out.println("Votre test dit que l'ordre");
				System.out.println(or);
				System.out.println("n'est pas total. Or il l'est.");
				System.out.println(or);
				testOK = false;
			} else {
				System.out.println("OK");
			}
		} catch(Exception e) {
			System.out.println("Echoué : Il ne fallait pas d'exception.") ;
			e.printStackTrace();
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException ex) {
				System.out.println();
			}
		}
		System.out.println(); 
		
		System.out.print("Test 5 : ");
		r =  Io.chargerRelation("orExAout7.rel");
		or = UtilOrdre.creerOrdre(r);
		try {
			if (or.estTotal()) {
				System.out.println("Echoué");
				System.out.println("Votre test dit que l'ordre");
				System.out.println(or);
				System.out.println("est total mais il ne l'est pas.");
				System.out.println(or);
				testOK = false;
			} else {
				System.out.println("OK");
			}
		} catch(Exception e) {
			System.out.println("Echoué : Il ne fallait pas d'exception.") ;
			e.printStackTrace();
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException ex) {
				System.out.println();
			}
		}
		System.out.println();
		
		return testOK;
	}

	private static boolean testEstUnTriTopologiqueDe() {
		boolean testOK = true;
		Relation r = Io.chargerRelation("orExAout1.rel");
		Ordre or = UtilOrdre.creerOrdre(r);

		System.out.print("Test 1 : ");
		r = Io.chargerRelation("orExAout3.rel");
		Ordre or2 = UtilOrdre.creerOrdre(r);
		try {
			if (or2.estUnTriTopologiqueDe(or)) {
				System.out.println("Echoué");
				System.out.println("Votre test dit que l'ordre");
				System.out.println(or2);
				System.out.println("est un tri topolopique de l'ordre partiel");
				System.out.println(or);
				System.out.println("Or c'est faux");
				System.out.println("N'oubliez pas de tester l'égalité des ensembles !") ;
				testOK = false;
			} else {
				System.out.println("OK");
			}
		} catch(Exception e) {
			System.out.println("Echoué : Il ne fallait pas d'exception.") ;
			e.printStackTrace();
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException ex) {
				System.out.println();
			}
		}
		System.out.println();

		System.out.print("Test 2 : ");
		r = Io.chargerRelation("orExAout4.rel");
		Ordre or3 = UtilOrdre.creerOrdre(r);
		try {
			if (or3.estUnTriTopologiqueDe(or)) {
				System.out.println("Echoué");
				System.out.println("Votre test dit que l'ordre");
				System.out.println(or3);
				System.out.println("est un tri topolopique de l'ordre partiel");
				System.out.println(or);
				System.out.println("Or c'est faux");
				testOK = false;
			} else {
				System.out.println("OK");
			}
		} catch(Exception e) {
			System.out.println("Echoué : Il ne fallait pas d'exception.") ;
			e.printStackTrace();
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException ex) {
				System.out.println();
			}
		}
		System.out.println();

		System.out.print("Test 3 : ");
		r = Io.chargerRelation("orExAout6.rel");
		Ordre or4 = UtilOrdre.creerOrdre(r);
		try {
			if (or4.estUnTriTopologiqueDe(or)) {
				System.out.println("Echoué");
				System.out.println("Votre test dit que l'ordre");
				System.out.println(or4);
				System.out.println("est un tri topolopique de l'ordre partiel");
				System.out.println(or);
				System.out.println("Or c'est faux");
				testOK = false;
			} else {
				System.out.println("OK");
			}
		} catch(Exception e) {
			System.out.println("Echoué : Il ne fallait pas d'exception.") ;
			e.printStackTrace();
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException ex) {
				System.out.println();
			}
		}
		System.out.println();

		System.out.print("Test 4 : ");
		try {
			if (or.estUnTriTopologiqueDe(or)) {
				System.out.println("Echoué");
				System.out.println("Votre test dit que l'ordre");
				System.out.println(or);
				System.out.println("est un tri topologique de lui-même");
				System.out.println("Or c'est faux car c'est un ordre partiel");
				testOK = false;
			} else {
				System.out.println("OK");
			}
		} catch(Exception e) {
			System.out.println("Echoué : Il ne fallait pas d'exception.") ;
			e.printStackTrace();
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException ex) {
				System.out.println();
			}
		}
		System.out.println();

		System.out.print("Test 5 : ");
		r = Io.chargerRelation("orExAout2.rel");
		Ordre or5 = UtilOrdre.creerOrdre(r);
		try {
			if (!or5.estUnTriTopologiqueDe(or)) {
				System.out.println("Echoué");
				System.out.println("Votre test dit que l'ordre");
				System.out.println(or5);
				System.out.println("n'est pas un tri topolopique de l'ordre partiel");
				System.out.println(or);
				System.out.println("Or c'en est un");
				testOK = false;
			} else {
				System.out.println("OK");
			}
		} catch(Exception e) {
			System.out.println("Echoué : Il ne fallait pas d'exception.") ;
			e.printStackTrace();
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException ex) {
				System.out.println();
			}
		}
		System.out.println();

		System.out.print("Test 6 : ");
		r = Io.chargerRelation("orExAout5.rel");
		Ordre or6 = UtilOrdre.creerOrdre(r);
		try {
			if (!or6.estUnTriTopologiqueDe(or)) {
				System.out.println("Echoué");
				System.out.println("Votre test dit que l'ordre");
				System.out.println(or6);
				System.out.println("n'est pas un tri topolopique de l'ordre partiel");
				System.out.println(or);
				System.out.println("Or c'en est un");
				testOK = false;
			} else {
				System.out.println("OK");
			}
		} catch(Exception e) {
			System.out.println("Echoué : Il ne fallait pas d'exception.") ;
			e.printStackTrace();
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException ex) {
				System.out.println();
			}
		}
		System.out.println();

		System.out.print("Test 7 : ");
		try {
			if (!or3.estUnTriTopologiqueDe(or3)) {
				System.out.println("Echoue");
				System.out.println("Votre test dit que l'ordre");
				System.out.println(or3);
				System.out.println("n'est pas un tri topolopique de lui-même");
				System.out.println("Or c'en est un car c'est un ordre total");
				testOK = false;
			} else {
				System.out.println("OK");
			}
		} catch(Exception e) {
			System.out.println("Echoué : Il ne fallait pas d'exception.") ;
			e.printStackTrace();
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException ex) {
				System.out.println();
			}
		}
		System.out.println();

		System.out.print("Test 8 : ");
		r = new Relation();
		Ordre or7 = UtilOrdre.creerOrdre(r);
		try {
			if (!or7.estUnTriTopologiqueDe(or7)) {
				System.out.println("Echoué");
				System.out.println("Votre test dit que l'ordre");
				System.out.println(or7);
				System.out.println("n'est pas un tri topolopique de l'ordre");
				System.out.println(or7);
				System.out.println("Or c'en est un");
				testOK = false;
			} else {
				System.out.println("OK");
			}
		} catch(Exception e) {
			System.out.println("Echoué : Il ne fallait pas d'exception.") ;
			e.printStackTrace();
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException ex) {
				System.out.println();
			}
		}
		System.out.println();
		return testOK;
	}

}

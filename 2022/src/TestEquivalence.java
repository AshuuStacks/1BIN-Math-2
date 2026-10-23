import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Scanner;

public class TestEquivalence {
	private final static Scanner scanner = new Scanner(System.in);
	private static final Class cl = Equivalence.class;
	private static Field fieldSsJac;
	private static Field fieldRep;


	public static void main(String[] args) throws NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {
		fieldSsJac = cl.getDeclaredField("sousJac");
		fieldSsJac.setAccessible(true);
		fieldRep = cl.getDeclaredField("tabRep");
		fieldRep.setAccessible(true);

		System.out.println("*****************************************");
		System.out.println("Programme Test pour la classe Equivalence");
		System.out.println("*****************************************");
		System.out.println();
		testEquivalenceEns();

	}

	private static boolean testEquivalenceEns() throws IllegalArgumentException, IllegalAccessException {
		EnsembleAbstrait[] classes;
		boolean testOK = true;
		Equivalence eq;
		System.out.println("Test1 : IllegalException (parametre null)");
		classes = null;
		try {
			new Equivalence(classes);
			testOK = false;
			System.out.println("Test1 ko");
			System.out.println("Le parametre est null --> Il fallait lancer une IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			System.out.println("Test1 ok");
		} catch (Exception e) {
			testOK = false;
			System.out.println("Test1 ko");
			System.out.println("Le parametre est null --> Il fallait lancer une IllegalArgumentException.");
		}
		System.out.println();
		System.out.println("Test2 : exemple de l'enonce");
		classes = new EnsembleAbstrait[4];
		classes[0] = new Ensemble("4");
		classes[1] = new Ensemble("5,9,10,11");
		classes[2] = new Ensemble("1,7,8");
		classes[3] = new Ensemble("3,12");
		try {
			eq = new Equivalence(classes);
			Ensemble sousJac = (Ensemble) fieldSsJac.get(eq);
			Ensemble sousJacAttendu = new Ensemble("1,3,4,5,7,8,9,10,11,12");
			if (sousJac == null) {
				System.out.println("Test2 ko");
				testOK = false;
				System.out.println("L'ensemble sous-jacent n'a pas ete cree (new).");

			} else if (!sousJac.equals(sousJacAttendu)) {
				System.out.println("Test2 ko");
				testOK = false;
				System.out.println("L'ensemble sous-jacent ne contient pas les bons elements");
				System.out.println("Voici le contenu de votre ensemble : ");
				System.out.println(sousJac);
			} else {
				Elt[] tabRep = (Elt[]) fieldRep.get(eq);
				if (tabRep == null) {
					System.out.println("Test2 ko");
					testOK = false;
					System.out.println("Vous n'avez pas cree (new) la table des representants");
				} else if (tabRep.length != Elt.MAXELT.val() + 1) {
					System.out.println("Test2 ko");
					testOK = false;
					System.out.println("La table des representants n'a pas la bonne dimension");
				} else if (tabRep[2] != null || tabRep[6] != null || tabRep[13] != null) {
					System.out.println("Test2 ko");
					testOK = false;
					System.out.println("La table des representants n'a pas ete bien initialisee.");
					System.out.println("Voici le contenu de votre table :");
					System.out.println(Arrays.toString(tabRep));
				} else if (tabRep[1] == null || tabRep[3] == null || tabRep[4] == null || tabRep[5] == null || tabRep[7] == null || tabRep[8] == null || tabRep[9] == null || tabRep[10] == null || tabRep[11] == null || tabRep[12] == null) {
					System.out.println("Test2 ko");
					testOK = false;
					System.out.println("La table des representants n'a pas ete bien initialisee.");
					System.out.println("Voici le contenu de votre table :");
					System.out.println(Arrays.toString(tabRep));
				} else if (tabRep[1].val() != 1 && tabRep[1].val() != 7 && tabRep[1].val() != 8) {
					System.out.println("Test2 ko");
					testOK = false;
					System.out.println("La table des representants n'a pas ete bien initialisee.");
					System.out.println("Voici le contenu de votre table :");
					System.out.println(Arrays.toString(tabRep));
				} else if (tabRep[1].val() != tabRep[7].val() || tabRep[1].val() != tabRep[8].val()) {
					System.out.println("Test2 ko");
					testOK = false;
					System.out.println("La table des representants n'a pas ete bien initialisee.");
					System.out.println("Voici le contenu de votre table :");
					System.out.println(Arrays.toString(tabRep));
				} else if (tabRep[5].val() != 5 && tabRep[5].val() != 9 && tabRep[5].val() != 10 && tabRep[5].val() != 11) {
					System.out.println("Test2 ko");
					testOK = false;
					System.out.println("La table des representants n'a pas ete bien initialisee.");
					System.out.println("Voici le contenu de votre table :");
					System.out.println(Arrays.toString(tabRep));
				} else if (tabRep[5].val() != tabRep[9].val() || tabRep[5].val() != tabRep[10].val() || tabRep[5].val() != tabRep[11].val()) {
					System.out.println("Test2 ko");
					testOK = false;
					System.out.println("La table des representants n'a pas ete bien initialisee.");
					System.out.println("Voici le contenu de votre table :");
					System.out.println(Arrays.toString(tabRep));
				} else if (tabRep[3].val() != 3 && tabRep[3].val() != 12) {
					System.out.println("Test2 ko");
					testOK = false;
					System.out.println("La table des representants n'a pas ete bien initialisee.");
					System.out.println("Voici le contenu de votre table :");
					System.out.println(Arrays.toString(tabRep));
				} else if (tabRep[3].val() != tabRep[12].val()) {
					System.out.println("Test2 ko");
					testOK = false;
					System.out.println("La table des representants n'a pas ete bien initialisee.");
					System.out.println("Voici le contenu de votre table :");
					System.out.println(Arrays.toString(tabRep));
				} else if (tabRep[4].val() != 4) {
					System.out.println("Test2 ko");
					testOK = false;
					System.out.println("La table des representants n'a pas ete bien initialisee.");
					System.out.println("Voici le contenu de votre table :");
					System.out.println(Arrays.toString(tabRep));
				}else{
					System.out.println("Test2 ok");
				}
			}
		}
		catch (IllegalArgumentException e) {
			testOK = false;
			System.out.println("Test2 ko");
			System.out.println("Il ne fallait pas lancer une IllegalArgumentException.");
		}
		catch (NullPointerException e) {
			testOK = false;
			System.out.println("Test2 ko");
			System.out.println("Il y a eu une NullPointerException");
			System.out.println("Verifiez si vous avez bien cree (new) l'ensemble sous-jacent et la table des representants");
		}
		catch (Exception e) {
			testOK = false;
			System.out.println("Test2 ko");
			System.out.println("Il y a eu une exception inattendue");
			e.printStackTrace();
		}

		System.out.println();
		System.out.println("Test3 : Exemple de l'enonce moins la classe {1,7,8}");
		classes = new EnsembleAbstrait[3];
		classes[0] = new Ensemble("4");
		classes[1] = new Ensemble("5,9,10,11");
		classes[2] = new Ensemble("3,12");
		try {
			eq = new Equivalence(classes);
			Ensemble sousJac = (Ensemble) fieldSsJac.get(eq);
			Ensemble sousJacAttendu = new Ensemble("3,4,5,9,10,11,12");
			if (sousJac == null) {
				System.out.println("Test3 ko");
				testOK = false;
				System.out.println("L'ensemble sous-jacent n'a pas ete cree (new)");
			} else if (!sousJac.equals(sousJacAttendu)) {
				System.out.println("Test3 ko");
				testOK = false;
				System.out.println("L'ensemble sous-jacent ne contient pas les bons elements.");
				System.out.println("Voici le contenu de votre ensemble : ");
				System.out.println(sousJac);
			} else {
				Elt[] tabRep = (Elt[]) fieldRep.get(eq);
				if (tabRep == null) {
					System.out.println("Test3 ko");
					testOK = false;
					System.out.println("Vous n'avez pas cree (new) la table des representants.");
				} else if (tabRep.length != Elt.MAXELT.val() + 1) {
					System.out.println("Test3 ko");
					testOK = false;
					System.out.println("La table des representants n'a pas la bonne dimension");
				} else if (tabRep[1] != null || tabRep[2] != null || tabRep[6] != null || tabRep[7] != null || tabRep[8] != null || tabRep[13] != null) {
					System.out.println("Test3 ko");
					testOK = false;
					System.out.println("La table des representants n'a pas ete bien initialisee.");
					System.out.println("Voici le contenu de votre table :");
					System.out.println(Arrays.toString(tabRep));
				}  else if (tabRep[3] == null || tabRep[4] == null || tabRep[5] == null || tabRep[9] == null || tabRep[10] == null || tabRep[11] == null || tabRep[12] == null) {
					System.out.println("Test3 ko");
					testOK = false;
					System.out.println("La table des representants n'a pas ete bien initialisee.");
					System.out.println("Voici le contenu de votre table :");
					System.out.println(Arrays.toString(tabRep));
				} else if (tabRep[5].val() != 5 && tabRep[5].val() != 9 && tabRep[5].val() != 10 && tabRep[5].val() != 11) {
					System.out.println("Test3 ko");
					testOK = false;
					System.out.println("La table des representants n'a pas ete bien initialisee.");
					System.out.println("Voici le contenu de votre table :");
					System.out.println(Arrays.toString(tabRep));
				} else if (tabRep[5].val() != tabRep[9].val() || tabRep[5].val() != tabRep[10].val() || tabRep[5].val() != tabRep[11].val()) {
					System.out.println("Test3 ko");
					testOK = false;
					System.out.println("La table des representants n'a pas ete bien initialisee.");
					System.out.println("Voici le contenu de votre table :");
					System.out.println(Arrays.toString(tabRep));
				} else if (tabRep[3].val() != 3 && tabRep[3].val() != 12) {
					System.out.println("Test3 ko");
					testOK = false;
					System.out.println("La table des representants n'a pas ete bien initialisee.");
					System.out.println("Voici le contenu de votre table :");
					System.out.println(Arrays.toString(tabRep));
				} else if (tabRep[3].val() != tabRep[12].val()) {
					System.out.println("Test3 ko");
					testOK = false;
					System.out.println("La table des representants n'a pas ete bien initialisee.");
					System.out.println("Voici le contenu de votre table :");
					System.out.println(Arrays.toString(tabRep));
				} else if (tabRep[4].val() != 4) {
					System.out.println("Test3 ko");
					testOK = false;
					System.out.println("La table des representants n'a pas ete bien initialisee.");
					System.out.println("Voici le contenu de votre table :");
					System.out.println(Arrays.toString(tabRep));
				}else{
					System.out.println("Test3 ok");
				}
			}
		}
		catch (IllegalArgumentException e) {
			testOK = false;
			System.out.println("Test3 ko");
			System.out.println("Il ne fallait pas lancer une IllegalArgumentException.");
		}
		catch (NullPointerException e) {
			testOK = false;
			System.out.println("Test3 ko");
			System.out.println("Il y a eu une NullPointerException");
			System.out.println("Verifiez si vous avez bien cree (new) l'ensemble sous-jacent et la table des representants");
		}
		catch (Exception e) {
			testOK = false;
			System.out.println("Test3 ko");
			System.out.println("Il y a eu une exception inattendue");
			e.printStackTrace();
		}

		System.out.println();
		System.out.println("Test4 : IllegalException : une des classes d'equivalence est vide");
		classes = new EnsembleAbstrait[3];
		classes[0] = new Ensemble("4");
		classes[1] = new Ensemble();
		classes[2] = new Ensemble("3,12");
		try {
			new Equivalence(classes);
			testOK = false;
			System.out.println("Test4 ko");
			System.out.println("Une des classes d'equivalence est vide --> Il fallait lancer une IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			System.out.println("Test4 ok");
		} catch (NullPointerException e) {
			testOK = false;
			System.out.println("Test4 ko");
			System.out.println("Il y a eu une NullPointerException");
			System.out.println("Verifiez si vous avez bien cree (new) l'ensemble sous-jacent et la table des representants");
		}

		catch (Exception e) {
			testOK = false;
			System.out.println("Test4 ko");
			System.out.println("Il y a eu une exception inattendue");
			e.printStackTrace();
		}

		System.out.println();
		System.out.println("Test5 : IllegalException : un element se trouve dans 2 classes d'equivalence differentes");
		classes = new EnsembleAbstrait[3];
		classes[0] = new Ensemble("4");
		classes[1] = new Ensemble("7");
		classes[2] = new Ensemble("3,4,12");
		try {
			new Equivalence(classes);
			testOK = false;
			System.out.println("Test5 ko");
			System.out.println("Un element se trouve dans 2 classes d'equivalence differentes --> Il fallait lancer une IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			System.out.println("Test5 ok");
		}
		catch (NullPointerException e) {
			testOK = false;
			System.out.println("Test5 ko");
			System.out.println("Il y a eu une NullPointerException");
			System.out.println("Verifiez si vous avez bien cree (new) l'ensemble sous-jacent et la table des representants");
		}
		catch (Exception e) {
			testOK = false;
			System.out.println("Test5 ko");
			System.out.println("Il y a eu une exception inattendue");
			e.printStackTrace();
		}

		System.out.println();
		System.out.println("Test6 : relation vide (aucune classe de communication)");
		classes = new EnsembleAbstrait[0];

		try {
			eq = new Equivalence(classes);
			Ensemble sousJac = (Ensemble) fieldSsJac.get(eq);
			if (sousJac == null) {
				System.out.println("Test6 ko");
				testOK = false;
				System.out.println("L'ensemble sous-jacent n'a pas ete cree (new).");
			} else if (!sousJac.estVide()) {
				System.out.println("Test6 ko");
				testOK = false;
				System.out.println("L'ensemble sous-jacent devrait etre vide");
				System.out.println("Voici le contenu de votre ensemble : ");
				System.out.println(sousJac);
			}else {
				Elt[] tabRep = (Elt[]) fieldRep.get(eq);
				if (tabRep == null) {
					System.out.println("Test6 ko");
					testOK = false;
					System.out.println("Vous n'avez pas cree (new) la table des representants.");
				} else if (tabRep.length != Elt.MAXELT.val() + 1) {
					System.out.println("Test6 ko");
					testOK = false;
					System.out.println("La table des representants n'a pas la bonne dimension");
				}else if (tabRep[1] != null ||tabRep[2] != null||tabRep[3] != null) {
					System.out.println("Test6 ko");
					testOK = false;
					System.out.println("La table des representants n'a pas ete bien initialisee.");
					System.out.println("Voici le contenu de votre table :");
					System.out.println(Arrays.toString(tabRep));
				}else{
					System.out.println("Test6 ok");
				}


			}
		} catch (IllegalArgumentException e) {
			testOK = false;
			System.out.println("Test6 ko");
			System.out.println("Il ne fallait pas lancer une IllegalArgumentException.");
			System.out.println("Une relation peut etre vide.");
		}
		catch (NullPointerException e) {
			testOK = false;
			System.out.println("Test6 ko");
			System.out.println("Il y a eu une NullPointerException");
			System.out.println("Verifiez si vous avez bien cree (new) l'ensemble sous-jacent et la table des representants");
		}
		catch (Exception e) {
			testOK = false;
			System.out.println("Test6 ko");
			System.out.println("Il y a eu une exception inattendue");
			e.printStackTrace();
		}

		System.out.println();
		System.out.println("Test7 : relation avec la classe de communication {50}");
		classes = new EnsembleAbstrait[1];
		classes[0]=new Ensemble("50");

		try {
			eq = new Equivalence(classes);
			Ensemble sousJac = (Ensemble) fieldSsJac.get(eq);
			if (sousJac == null) {
				System.out.println("Test7 ko");
				testOK = false;
				System.out.println("L'ensemble sous-jacent n'a pas ete cree (new).");
			} else if (sousJac.cardinal()!=1||!sousJac.contient(new Elt(50))) {
				System.out.println("Test7 ko");
				testOK = false;
				System.out.println("L'ensemble sous-jacent devrait contenir 50");
				System.out.println("Voici le contenu de votre ensemble : ");
				System.out.println(sousJac);
			}else {
				Elt[] tabRep = (Elt[]) fieldRep.get(eq);
				if (tabRep == null) {
					System.out.println("Test7 ko");
					testOK = false;
					System.out.println("Vous n'avez pas cree (new) la table des representants.");
				} else if (tabRep.length != Elt.MAXELT.val() + 1) {
					System.out.println("Test7 ko");
					testOK = false;
					System.out.println("La table des representants n'a pas la bonne dimension");
				}else if (tabRep[1] != null||tabRep[2] != null||tabRep[3] != null) {
					System.out.println("Test7 ko");
					testOK = false;
					System.out.println("La table des representants n'a pas ete bien initialisee.");
					System.out.println("Voici le contenu de votre table :");
					System.out.println(Arrays.toString(tabRep));
				}else if ( tabRep[50] == null||tabRep[50].val() != 50) {
					System.out.println("Test7 ko");
					testOK = false;
					System.out.println("La table des representants n'a pas ete bien initialisee.");
					System.out.println("Voici le contenu de votre table :");
					System.out.println(Arrays.toString(tabRep));
				}else{
					System.out.println("Test7 ok");
				}
			}
		} catch (IllegalArgumentException e) {
			testOK = false;
			System.out.println("Test7 ko");
			System.out.println("Il ne fallait pas lancer une IllegalArgumentException.");
		}
		catch (NullPointerException e) {
			testOK = false;
			System.out.println("Test7 ko");
			System.out.println("Il y a eu une NullPointerException");
			System.out.println("Verifiez si vous avez bien cree (new) l'ensemble sous-jacent et la table des representants");
		}
		catch (Exception e) {
			testOK = false;
			System.out.println("Test7 ko");
			System.out.println("Il y a eu une exception inattendue");
			e.printStackTrace();
		}

		System.out.println();
		System.out.println("Test8 : IllegalException : une des classes d'equivalence est null");
		classes = new EnsembleAbstrait[3];
		classes[0] = new Ensemble("4");
		classes[1] = new Ensemble();
		classes[2] = null;
		try {
			new Equivalence(classes);
			testOK = false;
			System.out.println("Test8 ko");
			System.out.println("Une des classes d'equivalence est null --> Il fallait lancer une IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			System.out.println("Test8 ok");
		} catch (NullPointerException e) {
			testOK = false;
			System.out.println("Test8 ko");
			System.out.println("Il y a eu une NullPointerException");
			System.out.println("Une des classes d'equivalence est null --> Il fallait lancer une IllegalArgumentException.");
		}
		catch (Exception e) {
			testOK = false;
			System.out.println("Test8 ko");
			System.out.println("Il y a eu une exception inattendue");
			e.printStackTrace();
		}
		System.out.println();
		if(testOK)
			System.out.println("tous les tests ont reussi !");
		else
			System.out.println("Attention, tous les tests n'ont pas reussi !");
		return testOK;
	}


}

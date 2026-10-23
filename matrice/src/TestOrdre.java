public class TestOrdre {

    private static final String[] NOMS_METHODE = {
            "donnerUnElementInterieur"
    };

    public static void main(String[] args) {
        System.out.println("**************************************");
        System.out.println("Programme Test pour la classe Ordre");
        System.out.println("**************************************");

        boolean testOK = testDonnerUnElementInterieur();

        if (testOK)
            System.out.println("Le test de la méthode " + NOMS_METHODE[0] + " a réussi.");
        else
            System.out.println("Le test de la méthode " + NOMS_METHODE[0] + " a échoué.");
    }

    private static boolean testDonnerUnElementInterieur() {
        boolean testOK = true;

        Ordre or1 = new Ordre(Io.chargerRelation("or1.rel"));
        Ordre or2 = new Ordre(Io.chargerRelation("or2.rel"));
        Ordre or3 = new Ordre(Io.chargerRelation("or3.rel"));

        // Test 1 : élément intérieur existant
        try {
            Elt e = or2.donnerUnElementInterieur();

            if (e == null) {
                testOK = false;
                System.out.println("KO : la méthode a retourné null.");
            }

            EnsembleAbstrait mins = or2.minimaux(or2.depart());
            EnsembleAbstrait maxs = or2.maximaux(or2.depart());

            if (mins.contient(e) || maxs.contient(e)) {
                testOK = false;
                System.out.println("KO : l'élément retourné est minimal ou maximal.");
            }

        } catch (Exception e) {
            testOK = false;
            System.out.println("KO : exception non attendue.");
            e.printStackTrace(System.out);
        }

        // Test 2 : autre ordre avec élément intérieur
        try {
            Elt e = or3.donnerUnElementInterieur();

            if (e == null) {
                testOK = false;
                System.out.println("KO : la méthode a retourné null.");
            }

            EnsembleAbstrait mins = or3.minimaux(or3.depart());
            EnsembleAbstrait maxs = or3.maximaux(or3.depart());

            if (mins.contient(e) || maxs.contient(e)) {
                testOK = false;
                System.out.println("KO : l'élément retourné est minimal ou maximal.");
            }

        } catch (Exception e) {
            testOK = false;
            System.out.println("KO : exception non attendue.");
            e.printStackTrace(System.out);
        }

        // Test 3 : aucun élément intérieur
        try {
            or1.donnerUnElementInterieur();

            testOK = false;
            System.out.println("KO : il fallait lancer une MathException.");

        } catch (MathException e) {
            System.out.println("OK : MathException lancée.");
        } catch (Exception e) {
            testOK = false;
            System.out.println("KO : mauvais type d'exception.");
            e.printStackTrace(System.out);
        }

        return testOK;
    }
}
import java.lang.reflect.Field;

public class UtilOrdre {

    private static Class classe = Ordre.class;
    private static Field couples;

    private static void preparerField() {
        Field[] champs = classe.getDeclaredFields();
        for (Field f : champs) {
            if (f.getType() == Relation.class) {
                if (f.getName().equals("couples")) {
                    couples = f;
                    couples.setAccessible(true);
                }
            }
        }
    }

    public static Ordre creerOrdre(Relation rel) {
        if (rel == null || !rel.depart().equals(rel.arrivee()))
            throw new IllegalArgumentException();
        Relation cou = rel.clone();
        if (!cou.arrivee().equals(cou.depart()))
            throw new MathException();
        for (Elt e : cou.depart()) {
            cou.ajouter(e, e);
        }
        for (Elt el : cou.depart()) {
            for (Elt el2 : cou.depart()) {
                if (cou.contient(el2, el)) {
                    for (Elt el3 : cou.depart()) {
                        if (cou.contient(el, el3))
                            cou.ajouter(el2, el3);
                    }
                }
            }
        }
        for (Couple c : cou) {
            if (cou.contient(c.reciproque()) && !c.getX().equals(c.getY())) {
                throw new IllegalArgumentException();
            }
        }


        Ordre ordre = new Ordre(rel.depart());
        try {
            if (couples == null)
                UtilOrdre.preparerField();
            couples.set(ordre, cou);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return ordre;
    }

    public static void main(String[] args) {
        System.out.println("equiv1 :");
    }
}
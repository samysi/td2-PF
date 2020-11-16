package Exo3;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class App {



    public static void afficheSi(String header, Predicate<Etudiant> predicate, Annee annee){

        System.out.println(header);
        System.out.println("\n");
        for (Etudiant e:annee.etudiants()) {
            if(predicate.test(e)) {
                System.out.println(e.toString());
            }
        }
    }

    public static void exercise_3_question_1(Annee a){

        Predicate<Etudiant> predi = n-> true;
        afficheSi("**TOUS LES ETUDIANTS",predi , a);

    }

    static Predicate<Etudiant> aDEF = new Predicate<Etudiant>() {
        @Override
        public boolean test(Etudiant etudiant) {

            for (UE ue : etudiant.annee().ues()) {
                for (Map.Entry<Matiere, Integer> ects : ue.ects().entrySet()) {

                    Matiere matiere = ects.getKey();
                    String note = etudiant.notes().containsKey(matiere) ? etudiant.notes().get(matiere).toString() : "DEF";
                    if(note == "DEF"){
                        return true;
                    }

                }
            }
            return false;

        }
    };

    public static void exercise_3_question_2(Annee a){

        afficheSi("**ETUDIANTS DEFAILLANT",aDEF , a);

    }

    static Predicate<Etudiant> aNoteEliminatoire = new Predicate<Etudiant>() {
        @Override
        public boolean test(Etudiant etudiant) {

            for (Map.Entry<Matiere,Double> paire : etudiant.notes().entrySet()) {
                if(paire.getValue() < 6){
                    return true;
                }
            }
            return false;
        }
    };

    public static void exercise_3_question_3(Annee a){

        afficheSi("**ETUDIANTS A NOTE ELIMINATOIRE",aNoteEliminatoire , a);

    }

    static Function<Etudiant, Double> moyenne = new Function<Etudiant, Double>() {
        @Override
        public Double apply(Etudiant e) {
            if(aDEF.test(e)){
                return null;
            }else{

                Double moy = 0.0;
                Integer coef = 0;

                for (UE ue : e.annee().ues()) {
                    for (Map.Entry<Matiere, Integer> ects : ue.ects().entrySet()) {
                        Matiere matiere = ects.getKey();
                        Integer credits = ects.getValue();
                        if(e.notes().containsKey(matiere)){
                            Double note = e.notes().get(matiere);
                            moy = moy + note * credits;
                            coef = coef + credits;
                        }

                    }
                }

                moy = moy/coef;
                return moy;
            }
        }
    };

    public static void exercise_3_question_4(Annee a){

        System.out.println("**MOYENNES DES ETUDIANTS");
        System.out.println("\n");

        for (Etudiant e:a.etudiants()) {

            System.out.println(moyenne.apply(e));

        }
    }

    static Predicate<Etudiant> naPasLaMoyennev1 = n -> moyenne.apply(n) < 10;

    public static void exercise_3_question_5(Annee a){

        afficheSi("**ETUDIANTS SOUS LA MOYENNE",naPasLaMoyennev1,a);
    }

    static Predicate<Etudiant> naPasLaMoyennev2 = new Predicate<Etudiant>() {
        @Override
        public boolean test(Etudiant etudiant) {

            if(moyenne.apply(etudiant) == null || moyenne.apply(etudiant) < 10){
                return true;
            }
            return false;

        }
    };

    public static void exercise_3_question_6(Annee a){

        afficheSi("**ETUDIANTS SOUS LA MOYENNE (v2)",naPasLaMoyennev2,a);

    }

    public static void exercise_3_question_7(Annee a){

        Predicate<Etudiant> session2v1 = n -> aDEF.test(n) || aNoteEliminatoire.test(n) || naPasLaMoyennev1.test(n);

        afficheSi("**ETUDIANTS SOUS LA MOYENNE (v2)",session2v1,a);

        /*
        Si on test le prédicat naPasLaMoyennev1 en premier il y a une exception de type NullPointerException.
        Il faut donc tester si l'étudiant est défaillant avant de pouvoir tester si il n'a pas la moyenne.
         */

    }

    public static void afficheSiv2(String enTete, Predicate<Etudiant> predicate, Annee annee, Consumer<Etudiant> consum){

        System.out.println(enTete);
        System.out.println("\n");
        for (Etudiant e:annee.etudiants()) {
            if(predicate.test(e)) {
                consum.accept(e);
            }
        }
    }

    public static void exercise_3_question_8(Annee a){

        Predicate<Etudiant> predi = n-> true;

        Consumer<Etudiant> repAfficheSi = e -> {
            System.out.println(e.toString());
        };

        Consumer<Etudiant> repMoyenne = e -> {

            if(naPasLaMoyennev2.test(e)){
                System.out.println(e.nom()+" "+e.prenom()+" : defaillant");
            }else{
                System.out.println(e.nom()+" "+e.prenom()+" : "+moyenne.apply(e));
            }

        };

        afficheSiv2("**TOUS LES ETUDIANTS",predi,a, repAfficheSi);
        afficheSiv2("**MOYENNES DES ETUDIANTS",predi,a, repMoyenne);

    }

    static Function<Etudiant,Double> moyenneIndicative = new Function<Etudiant, Double>() {
        @Override
        public Double apply(Etudiant e) {

            Double moy = 0.0;
            Integer coef = 0;

            for (UE ue : e.annee().ues()) {
                for (Map.Entry<Matiere, Integer> ects : ue.ects().entrySet()) {

                    Matiere matiere = ects.getKey();
                    Integer credits = ects.getValue();
                    if(e.notes().containsKey(matiere)){
                        Double note = e.notes().get(matiere);
                        moy = moy + note * credits;
                        coef = coef + credits;
                    }else{
                        coef=coef+ects.getValue();
                    }

                }
            }

            moy = moy/coef;
            return moy;

        }
    };

    public static void exercise_3_question_9(Annee a){

        Predicate<Etudiant> predi = n-> true;


        Consumer<Etudiant> repMoyenneIndicative = e -> {
            System.out.println(e.nom()+" "+e.prenom()+" : "+moyenneIndicative.apply(e));
        };

        afficheSiv2("**MOYENNES INDICATIVE DES ETUDIANTS",predi,a, repMoyenneIndicative);

    }

    static Predicate<Etudiant> naPasLaMoyenneGeneralise = new Predicate<Etudiant>() {
        @Override
        public boolean test(Etudiant etudiant) {

            if(moyenneIndicative.apply(etudiant) == null || moyenneIndicative.apply(etudiant) < 10){
                return true;
            }
            return false;

        }
    };

    public static void exercise_3_question_10(Annee a){

        Consumer<Etudiant> repMoyenneIndicative = e -> {
            System.out.println(e.nom()+" "+e.prenom()+" : "+moyenneIndicative.apply(e));
        };

        afficheSiv2("**MOYENNES INDICATIVE DES ETUDIANTS QUI N'ONT PAS LA MOYENNE",naPasLaMoyenneGeneralise,a, repMoyenneIndicative);

    }

    public static void main(String[] args){


        Matiere m1 = new Matiere("MAT1");
        Matiere m2 = new Matiere("MAT2");
        UE ue1 = new UE("UE1", Map.of(m1, 2, m2, 2));
        Matiere m3 = new Matiere("MAT3");
        UE ue2 = new UE("UE2", Map.of(m3, 1));
        Annee a1 = new Annee(Set.of(ue1, ue2));
        Etudiant e1 = new Etudiant("39001", "Alice", "Merveille", a1);
        e1.noter(m1, 12.0);
        e1.noter(m2, 14.0);
        e1.noter(m3, 10.0);
        Etudiant e2 = new Etudiant("39002", "Bob", "Eponge", a1);
        e2.noter(m1, 14.0);
        e2.noter(m3, 14.0);
        Etudiant e3 = new Etudiant("39003", "Charles", "Chaplin", a1);
        e3.noter(m1, 18.0);
        e3.noter(m2, 5.0);
        e3.noter(m3, 14.0);

        Predicate<Etudiant> predi = n-> true;

        exercise_3_question_1(a1);

        exercise_3_question_2(a1);

        exercise_3_question_3(a1);

        exercise_3_question_4(a1);

        System.out.println("\n");

        //exercice_3_question_5(a1);
        // On a une exception de type NullPointerException car un élève retourne une moyenne null et non un int

        exercise_3_question_6(a1);

        System.out.println("\n");

        exercise_3_question_7(a1);

        System.out.println("\n");

        exercise_3_question_8(a1);

        System.out.println("\n");

        exercise_3_question_9(a1);

        System.out.println("\n");

        exercise_3_question_10(a1);

    }

}

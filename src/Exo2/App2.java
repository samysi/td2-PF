package Exo2;

import java.util.function.Predicate;

public class App2 {

    public static void main(String[] args) {
        Paire<Integer, Double> p1 = new Paire<>(145, 160.0);
        Paire<Integer, Double> p2 = new Paire<>(205, 60.0);
        Predicate<Integer> tropPetit = x -> x<100;
        System.out.println("p1 trop petit : "+tropPetit.test(p1.fst));
        Predicate<Integer> tropGrand = x -> x>200;
        System.out.println("p2 trop Grand : "+tropGrand.test(p2.fst));
        Predicate<Double> tropLourd = y -> y>150;
        System.out.println("p1 trop lourd : "+tropLourd.test(p1.snd));

        Predicate<Double> poidCorrect = tropLourd.negate();
        System.out.println("p1 à un poids correct : "+ poidCorrect.test(p1.snd));

        Predicate<Integer> composedTailleIncorrect = tropPetit.or(tropGrand);
        System.out.println("Taille incorrect p1: "+ composedTailleIncorrect.test(p1.fst));

        Predicate<Integer> composedTailleCorrect = composedTailleIncorrect.negate();
        System.out.println("Taille correct p1: "+ composedTailleCorrect.test(p1.fst));







    }
}

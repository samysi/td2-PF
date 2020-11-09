package Exo1;

public class App {

        public static void q1() {
            System.out.println("-Question 1");
            Somme<String> sommeCaract = String::concat;
            String c1 = "Hello";
            String c2 = "World";
            String rtr = sommeCaract.somme(c1, c2);
            System.out.println(rtr);
            System.out.println("------------");
            Somme<Integer> sommeInt = (x1,x2) -> x1+x2;
            int i1 = 1;
            int i2= 2;
            int rtr1 = sommeInt.somme(i1, i2);
            System.out.println(rtr1);
            System.out.println("------------");
            Somme<Double> sommeDouble = (d1,d2) -> d1+d2;
            double d1 = 1.0;
            double d2 = 2.0;
            double rtr2 = sommeDouble.somme(d1, d2);
            System.out.println(rtr1);
            System.out.println("------------");
            Somme<Long> sommeLong = (l1,l2) -> l1+l2;
            long l1 = 1000000;
            long l2= 2000000;
            long rtr3 = sommeLong.somme(l1, l2);
            System.out.println(rtr3);

        }

        public static void main(String[] args) {
            App.q1();
        }
    }


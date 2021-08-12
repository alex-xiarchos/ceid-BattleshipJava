import java.io.IOException;
import java.util.Scanner;
import java.util.Random;

public class Game {
    public static int sizeofBoard = 7; //Μέγεθος board
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        printStart(); //Γραφικά έναρξης

        //Αρχικοποίηση & δημιουργία παικτών
        System.out.println("Δώσε όνομα:");
        String personName = input.nextLine();
        String winner;
        Player person = new Player(personName);
        Player computer = new Player("Υπολογιστής");

        //Δημιουργία των ταμπλό
        Board boardPerson = new Board();
        Board boardComputer = new Board();

        //Τυχαία ή χειροκίνητη τοποθέτηση;
        boolean flag; //έλεγχος επαναλήψεων
        do {
            flag = false;
            System.out.println("Θες να τοποθετήσεις τα πλοία σου τυχαία ή όχι; (Y/N)");
            String choice = input.nextLine();
            if (choice.equals("y") || choice.equals("Y")) {
                boardPerson.placeAllShips(boardPerson, true); //τυχαία τοποθέτηση
                flag = true;
            }
            else if (choice.equals("n") || choice.equals("N")) {
                boardPerson.placeShips(boardPerson, boardPerson, boardComputer); //χειροκίνητη τοποθέτηση
                flag = true;
            }
            else
                System.out.println("Μην γράφεις ό,τι να 'ναι."); //Αμυντικός προγραμματισμός, λολ.
        }
        while (!flag);

        //Τυχαία αρχικοποίηση ταμπλό υπολογστή
        boardComputer.placeAllShips(boardComputer, true);

        System.out.println("\n= = = = [ Καλή επιτυχία! ] = = = =\n");
        //Σχεδίαση των 2 ταμπλό
        Board.drawboards(boardPerson.getBoard(), boardComputer.getBoard());

        int[] fireCoords; //πίνακας με συνταταγμένες τοποθέτησης
        do {
            fireCoords = getInput(); //εισαγωγή τιμών από το χρήστη
            person.fire(boardComputer.getBoard(), fireCoords); //βολή χρήστη
            fireCoords = getRandInput(); //αντικατάσταση με τυχαίες τιμές
            computer.fire(boardPerson.getBoard(), fireCoords); //βολή υπολογιστή
            Board.drawboards(boardPerson.getBoard(), boardComputer.getBoard()); //σχεδίαση πινάκων
        }
        while (!(boardComputer.allShipsSunk() || boardPerson.allShipsSunk())); //μέχρι να βουλιάξει 1 τλχ πλοίο

        //Τύπωση στατιστικών
        person.getStats();
        computer.getStats();

        printEnd(); //Γραφικά λήξης

        //Τύπωση ονόματος νικητή
        if (boardComputer.allShipsSunk())
            winner = personName;
        else
            winner = "Υπολογιστής";

        System.out.println("\t\t\t\t===[ Νίκησε ο " + winner + " ]===");

    }

    public static int[] getRandInput() {
        Random rand = new Random();
        int[] randCoords = new int[2];
        for (int i=0; i< randCoords.length; i++) {
            randCoords[i] = rand.nextInt(sizeofBoard);
        }
        return randCoords;
    }

    public static int[] getInput() {
        Scanner input = new Scanner(System.in);
        boolean flag;
        int flag2 = 0;
        int x=0, y=0;
        int[] coords = new int[2];

        /*
         * αν ο χρήστης δώσει λάθος τιμές για πάνω από 2 φορές, τυπώνεται επιπλέον
         * βοηθητικό μήνυμα με το τι τιμές πρέπει να δώσει. Αν δώσει λάθος τιμές πρώτη
         * ή δεύτερη φορά τυπώνει απλώς πως κάποια τιμή δεν ήταν αποδεκτή.
         */
            do {
                System.out.println("Δώσε συνταταγμένες: (γραμμή, στήλη)");
                //εισαγωγή τιμών
                y = input.nextInt();
                x = input.nextInt();
                coords[0] = y;
                coords[1] = x;
                flag = true;
                if (x < 0 || y < 0 || x >= sizeofBoard || y >= sizeofBoard) {
                    if (flag2 > 1) {
                        System.out.println("Δεν μπορείς να δώσεις αρνητικές τιμές ή τιμές");
                        System.out.println("που υπερβαίνουν τα όρια του παιχνιδιού.");
                        System.out.println("Ξαναδώσε συντεταγμένες: ");
                    } else
                        System.out.println("Κάποια τιμή δεν ήταν αποδεκτή. Ξαναδώσε συντεταγμένες:");
                    flag = false;
                    flag2++;
                }
            }
            while (!flag);

        return coords; //επιστροφή πίνακα συντεταγμένων
    }

    public static int getOrientation() {
        int orientation;
        Scanner input = new Scanner(System.in);
        boolean flag = false;
        do {
            System.out.println("Γράψε 0 για οριζόντια και 1 για κατακόρυφη τοποθέτηση:");
            orientation = input.nextInt(); //εισαγωγή τιμής από χρήστη
            if (orientation == 0 || orientation == 1) //αμυντικός προγραμματισμός
                flag = true;
        }
        while (!flag);
        return orientation; //επιστροφή τιμής
    }

    private static void printStart() {
        System.out.println("  _   _      __     ____  __          __   _______          ");
        System.out.println(" | \\ | |   /\\\\ \\   / /  \\/  |   /\\    \\ \\ / /_   _|   /\\    ");
        System.out.println(" |  \\| |  /  \\\\ \\_/ /| \\  / |  /  \\    \\ V /  | |    /  \\   ");
        System.out.println(" | . ` | / /\\ \\\\   / | |\\/| | / /\\ \\    > <   | |   / /\\ \\  ");
        System.out.println(" | |\\  |/ ____ \\| |  | |  | |/ ____ \\  / . \\ _| |_ / ____ \\ ");
        System.out.println(" |_| \\_/_/    \\_\\_|  |_|  |_/_/    \\_\\/_/ \\_\\_____/_/    \\_\\");
        System.out.println("=============================================================");
    }

    /*
     * Bonus για τα σχεδιάκια;
     * Όχι, ε;
     * Καλά. :(
     */

    private static void printEnd() {
        System.out.println("                                   .''.       ");
        System.out.println("       .''.      .        *''*    :_\\/_:     . ");
        System.out.println("      :_\\/_:   _\\(/_  .:.*_\\/_*   : /\\ :  .'.:.'.");
        System.out.println("  .''.: /\\ :   ./)\\   ':'* /\\ * :  '..'.  -=:o:=-");
        System.out.println(" :_\\/_:'.:::.    ' *''*    * '.\\'/.' _\\(/_'.':'.'");
        System.out.println(" : /\\ : :::::     *_\\/_*     -= o =-  /)\\    '  *");
        System.out.println("  '..'  ':::'     * /\\ *     .'/.\\'.   '");
        System.out.println("      *            *..*         :");
        System.out.println("       *");
        System.out.println("       *");
    }
    // Έχει και πυροτεχνήματα πάντως...
}
import java.util.Random;

public class Board {
    private Tile[][] array = new Tile[Game.sizeofBoard][Game.sizeofBoard]; //sizeofBoard: public μεταβλ στην Game

    public Board() {
        for (int i=0; i<array.length; ++i)
            for (int j=0; j<array[i].length; ++j) {
                array[i][j] = new Tile(i, j); //δημιουργία κελιών
                array[i][j].setType(typeList.Sea); //τα οποία θέτει τύπου Sea
            }
    }

    public static void drawboards(Tile[][] board1, Tile[][] board2) {
        System.out.println("---- Y O U ----\t\t--- OPPONENT --");
        System.out.print("  ");
        for (int i=0; i< Game.sizeofBoard; i++)
            System.out.print(i + " ");
        System.out.print("\t  ");
        for (int i=0; i< Game.sizeofBoard; i++)
            System.out.print(i + " ");
        System.out.println();
        for (int i=0; i<Game.sizeofBoard; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < Game.sizeofBoard; j++)
                System.out.print(board1[i][j].draw(false) + " "); //ταμπλό παίκτη
            System.out.print("\t" + i + " ");
            for (int j = 0; j < Game.sizeofBoard; j++)
                System.out.print(board2[i][j].draw(true) + " "); //ταμπλό υπολογιστή - κρυφό
            System.out.println();
        }
    }

    public void placeAllShips(Board board, boolean hidden) {
        //δημιουργία πλοίων
        Carrier carrier = new Carrier();
        Battleship battleship = new Battleship();
        Cruiser cruiser = new Cruiser();
        Submarine submarine = new Submarine();
        Destroyer destroyer = new Destroyer();
        int shipsPlaced=0; //αρχικοποίηση μετρητή
        int[] randomCoords;
        do {
            Random rand = new Random(); //για το τυχαίο προσανατολισμό
            randomCoords = Game.getRandInput(); //τυχαίες συντεταγμένες
            Tile bTile = new Tile(randomCoords[0], randomCoords[1]); //τυχαίο αρχικό κελί
            switch (shipsPlaced) {
                case 0:
                    if (carrier.placeShip(bTile, rand.nextInt(2), board, hidden)) {
                        shipsPlaced++; // αύξηση μετρητή
                    }
                    break;
                case 1:
                    if (battleship.placeShip(bTile, rand.nextInt(2), board, hidden)) {
                        shipsPlaced++;
                    }
                    break;
                case 2:
                    if (cruiser.placeShip(bTile, rand.nextInt(2), board, hidden)) {
                        shipsPlaced++;
                    }
                    break;
                case 3:
                    if (submarine.placeShip(bTile, rand.nextInt(2), board, hidden)) {
                        shipsPlaced++;
                    }
                    break;
                case 4:
                    if (destroyer.placeShip(bTile, rand.nextInt(2), board, hidden)) {
                        shipsPlaced++;
                    }
                    break;
                default:
                    System.out.println("Και γαμώ ο κώδικας ε;"); //Bonus +5% αν το είδες πλιζ :(
                    break;
            }
        }
        while (shipsPlaced < 5);
    }

    public void placeShips(Board board, Board board1, Board board2) {
        //αρχικοποίηση πλοίων
        Carrier carrier = new Carrier();
        Battleship battleship = new Battleship();
        Cruiser cruiser = new Cruiser();
        Submarine submarine = new Submarine();
        Destroyer destroyer = new Destroyer();
        int[] manualCoords;
        int shipsPlaced = 0;

        do {
            switch (shipsPlaced) { //μήνυμα τοποθέτησης προς το χρήστη
                case 0:
                    System.out.println("Τοποθέτησε το Carrier (5):");
                    break;
                case 1:
                    System.out.println("Τοποθέτησε το Battleship (4):");
                    break;
                case 2:
                    System.out.println("Τοποθέτησε το Cruiser (3):");
                    break;
                case 3:
                    System.out.println("Τοποθέτησε το Submarine (3):");
                    break;
                case 4:
                    System.out.println("Τοποθέτησε το Carrier (2):");
                    break;
            }
            manualCoords = Game.getInput(); //εισαγωγή συντεταγμένων
            Tile bTile = new Tile(manualCoords[0], manualCoords[1]);
            switch (shipsPlaced) {
                case 0:
                    if (carrier.placeShip(bTile, Game.getOrientation(), board, false))
                        shipsPlaced++;
                    break;
                case 1:
                    if (battleship.placeShip(bTile, Game.getOrientation(), board, false))
                        shipsPlaced++;
                    break;
                case 2:
                    if (cruiser.placeShip(bTile, Game.getOrientation(), board, false))
                        shipsPlaced++;
                    break;
                case 3:
                    if (submarine.placeShip(bTile, Game.getOrientation(), board, false))
                        shipsPlaced++;
                    break;
                case 4:
                    if (destroyer.placeShip(bTile, Game.getOrientation(), board, false))
                        shipsPlaced++;
                    break;
                default:
                    System.out.println("Και γαμώ ο κώδικας φίλε;"); //Bonus +10% αν είδες και αυτό πλιζ :(
                    break;
            }
            if (shipsPlaced != 5)
                Board.drawboards(board1.getBoard(), board2.getBoard()); //σχεδίαση ταμπλό μετά από κάθε τοποθέτηση
        }
        while (shipsPlaced < 5);
    }

    public boolean allShipsSunk() {
        boolean flag = true;
        for (int i=0; i< array.length; i++)
            for (int j=0; j<array[i].length; j++)
                if (array[i][j].getType() == typeList.Ship) //αν έστω και ένα κελί... μπλα μπλα
                    flag = false;

        return flag;
    }

    public Tile[][] getBoard() {
        return array; //ό,τι να 'ναι.
    }
    
    public Tile[] getAdjacentTiles(Tile tile) {
        Tile[] adjTiles = new Tile[4]; //0: πάνω, 1: δεξιά, 2: κάτω, 3: αριστερά
        Tile[][] array = getBoard();
        Tile outOfBoard = new Tile();
        outOfBoard.setType(typeList.Sea);

        //ακραίες περιπτώσεις
        if (tile.getx() == 0 && tile.gety() == 0) {
            adjTiles[0] = outOfBoard;
            adjTiles[1] = array[tile.gety()][tile.getx() + 1];
            adjTiles[2] = array[tile.gety() + 1][tile.getx()];
            adjTiles[3] = outOfBoard;
        } else if (tile.gety() == (Game.sizeofBoard - 1) && tile.getx() == 0) {
            adjTiles[0] = array[tile.gety() - 1][tile.getx()];
            adjTiles[1] = array[tile.gety()][tile.getx() + 1];
            adjTiles[2] = outOfBoard;
            adjTiles[3] = outOfBoard;
        } else if (tile.gety() == 0 && tile.getx() == (Game.sizeofBoard - 1)) {
            adjTiles[0] = outOfBoard;
            adjTiles[1] = outOfBoard;
            adjTiles[2] = array[tile.gety() + 1][tile.getx()];
            adjTiles[3] = array[tile.gety()][tile.getx() - 1];
        } else if (tile.gety() == (Game.sizeofBoard - 1) && tile.getx() == (Game.sizeofBoard - 1)) {
            adjTiles[0] = array[tile.gety() - 1][tile.getx()];
            adjTiles[1] = outOfBoard;
            adjTiles[2] = outOfBoard;
            adjTiles[3] = array[tile.gety()][tile.getx() - 1];
        } else if (tile.gety() == 0) {
            adjTiles[0] = outOfBoard;
            adjTiles[1] = array[tile.gety()][tile.getx() + 1];
            adjTiles[2] = array[tile.gety() + 1][tile.getx() ];
            adjTiles[3] = array[tile.gety()][tile.getx() - 1];
        } else if (tile.gety() == (Game.sizeofBoard - 1)) {
            adjTiles[0] = array[tile.gety() - 1][tile.getx()];
            adjTiles[1] = array[tile.gety()][tile.getx() + 1];
            adjTiles[2] = outOfBoard;
            adjTiles[3] = array[tile.gety()][tile.getx() - 1];
        } else if (tile.getx() == 0) {
            adjTiles[0] = array[tile.gety() - 1][tile.getx()];
            adjTiles[1] = array[tile.gety()][tile.getx() + 1];
            adjTiles[2] = array[tile.gety() + 1][tile.getx() ];
            adjTiles[3] = outOfBoard;
        } else if (tile. getx() == (Game.sizeofBoard - 1)) {
            adjTiles[0] = array[tile.gety() - 1][tile.getx()];
            adjTiles[1] = outOfBoard;
            adjTiles[2] = array[tile.gety() + 1][tile.getx() ];
            adjTiles[3] = array[tile.gety()][tile.getx() - 1];
        } else { //το νορμάλ
            adjTiles[0] = array[tile.gety() - 1][tile.getx()];
            adjTiles[1] = array[tile.gety()][tile.getx() + 1];
            adjTiles[2] = array[tile.gety() + 1][tile.getx() ];
            adjTiles[3] = array[tile.gety()][tile.getx() - 1];
        }
        return adjTiles;
    }
}

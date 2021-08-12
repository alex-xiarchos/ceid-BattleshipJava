public class Player {
    private String name;
    private int fires = 0, misses = 0, succeedFires = 0, alreadyFired = 0;

    public Player(String name) {
        this.name = name;
    }

    public void fire(Tile[][] board, int coords[]) {
        if (board[coords[0]][coords[1]].getType() == typeList.Ship) {
            board[coords[0]][coords[1]].setType(typeList.Hit); //κάνει το κελί τύπου Hit
            succeedFires++;
            fires++;
            System.out.println("Ο χρήστης " + name + " ΧΤΥΠΗΣΕ!");
        } else if (board[coords[0]][coords[1]].getType() == typeList.Sea) {
            board[coords[0]][coords[1]].setType(typeList.Miss); //κάνει το κελί τύπου Miss
            misses++;
            fires++;
            System.out.println("Ο χρήστης " + name + " ΑΣΤΟΧΗΣΕ!");
        } else if (board[coords[0]][coords[1]].getType() == typeList.Hit) {
            alreadyFired++;
            fires++;
            System.out.println("Ο χρήστης " + name + " ΞΑΝΑΧΤΥΠΗΣΕ!");
        } else if (board[coords[0]][coords[1]].getType() == typeList.Miss) {
            alreadyFired++;
            fires++;
            System.out.println("ο χρήστης " + name + " ΞΑΝΑΑΣΤΟΧΙΣΕ!");
        }
    }
    public void getStats() { // τύπωση στατιστικών
        System.out.println("Ο χρήστης " + name + " έκανε:");
        System.out.println(fires + " συνολικές βολές από τις οποίες " + succeedFires + " ήταν επιτυχημένες");
        System.out.println(misses + " ήταν αποτυχημένες και " + alreadyFired + " ήταν επαναλαμβανόμενες");
        System.out.println();
    }

}
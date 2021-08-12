public abstract class Ship {
    private Tile bTile;
    private int orientation; //1: κάθετα, 0: οριζόντια
    private int shipSize;

    public void setShipSize(int shipSize) {
        this.shipSize = shipSize;
    }

    private void Oversize(Tile bTile, int orientation) throws OversizeException {
        if (orientation == 1) {
            if ((bTile.gety() + shipSize) > Game.sizeofBoard)
                throw new OversizeException();
        }
        else if (orientation == 0) {
            if ((bTile.getx() + shipSize) > Game.sizeofBoard)
                throw new OversizeException();
        }
    }

    private void Overlap(Tile bTile, int orientation, Board board) throws OverlapException {
        Tile[][] array = board.getBoard();
        for (int i = 0; i < shipSize; i++)
            if (orientation == 1) {
                    if (array[bTile.gety() + i][bTile.getx()].getType() == typeList.Ship)
                        throw new OverlapException();
            } else if (orientation == 0) {
                    if (array[bTile.gety()][bTile.getx() + i].getType() == typeList.Ship) {
                        throw new OverlapException();
                }
            }
    }

    private void AdjacentTiles(Tile bTile, int orientation, Board board) throws AdjacentTilesException {
        if (orientation == 1) {
            for (int i = bTile.gety(); i <= bTile.gety() + shipSize - 1; i++) {
                Tile currentTile = new Tile(i, bTile.getx());
                if (i == bTile.gety()) {
                    for (int j = 0; j < 4; j++) {
                        if (j != 2)
                            if (board.getAdjacentTiles(currentTile)[j].getType() == typeList.Ship)
                                throw new AdjacentTilesException();
                    }
                } else if (i == (bTile.gety() + shipSize - 1)) {
                    for (int j = 0; j < 4; j++) {
                        if (j != 0)
                            if (board.getAdjacentTiles(currentTile)[j].getType() == typeList.Ship)
                                throw new AdjacentTilesException();
                    }
                } else {
                    for (int j = 0; j < 4; j++) {
                        if (j == 1 || j == 3)
                            if (board.getAdjacentTiles(currentTile)[j].getType() == typeList.Ship)
                                throw new AdjacentTilesException();
                    }
                }
            }
        } else if (orientation == 0) {
            for (int i = bTile.getx(); i <= bTile.getx() + shipSize - 1; i++) {
                Tile currentTile = new Tile(bTile.gety(), i);
                if (i == bTile.getx()) {
                    for (int j = 0; j < 4; j++)
                        if (j != 1)
                            if (board.getAdjacentTiles(currentTile)[j].getType() == typeList.Ship)
                                throw new AdjacentTilesException();
                } else if (i == (bTile.getx() + shipSize - 1)) {
                    for (int j = 0; j < 4; j++)
                        if (j != 3)
                            if (board.getAdjacentTiles(currentTile)[j].getType() == typeList.Ship)
                                throw new AdjacentTilesException();
                } else {
                    for (int j = 0; j < 4; j++) {
                        if (j == 0 || j == 2)
                            if (board.getAdjacentTiles(currentTile)[j].getType() == typeList.Ship)
                                throw new AdjacentTilesException();
                    }
                }
            }
        }
    }

    public boolean placeShip(Tile bTile, int orientation, Board board, boolean hidden) {
        Tile[][] array = board.getBoard();
        boolean successFlag = true;
        try {
            Oversize(bTile, orientation);
            Overlap(bTile, orientation, board);
            AdjacentTiles(bTile, orientation, board);
            for (int i = 0; i < shipSize; i++) {
                if (orientation == 1) {
                    array[bTile.gety() + i][bTile.getx()].setType(typeList.Ship);
                } else if (orientation == 0) {
                    array[bTile.gety()][bTile.getx() + i].setType(typeList.Ship);
                }
            }
        }
        catch (OversizeException e) {
            if (!hidden) {
                System.out.println("== Δεν χωράει το πλοιο! ==");
            }
            successFlag = false;
        }
        catch (OverlapException e) {
            if (!hidden) {
                System.out.println("== Το πλοίο είναι πάνω σε άλλο! ==");
            }
            successFlag = false;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(e.toString());
            successFlag = false;
        }
        catch (AdjacentTilesException e) {
            if (!hidden)
                System.out.println("== Το πλοιο είναι κολλητά σε άλλο! ==");
            successFlag = false;
        }
        return successFlag;
    }

}
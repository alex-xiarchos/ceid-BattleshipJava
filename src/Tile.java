enum typeList {Sea, Ship, Hit, Miss};
public class Tile {
    private int x;
    private int y;
    private typeList type;

    public Tile(int y, int x) {
        this.x = x;
        this.y = y;
    }

    public Tile() {
    }

    public void setType(typeList type) {
        this.type = type;
    }
    public typeList getType() {
        return type;
    }

    public String draw(Boolean hidden) {
        switch (type) {
            case Sea:
                return "~";
            case Ship:
                if (!hidden)
                    return "■";
                    else
                    return "~";
            case Hit:
                return "X";
            case Miss:
                return "o";
            default:
                return "?";
        }
    }

    public int getx() {
        return x;
    }
    public int gety() {
        return y;
    }
    public void setx(int x){
        this.x = x;
    }
    public void sety(int y){
        this.y = y;
    }
}

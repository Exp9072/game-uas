public class StaticEnemy extends Enemy {
    public StaticEnemy(int x, int y) {
        super(x, y, 40, 40);
    }

    @Override
    public void move() {
        // Static enemies don't move, so there's no implementation here
    }
}

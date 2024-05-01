import greenfoot.*;

public class Projectile extends Actor
{
    private double deltaX = 0;
    private double deltaY = 0;
    private double speedFactor = 7;
    private double damage = 1;
    // private GameWorld world;

    public Projectile(int inputDeltaX, int inputDeltaY)
    {
        // this.world = world;
        double directionLength = Math.sqrt(inputDeltaX * inputDeltaX + inputDeltaY * inputDeltaY);
        if (directionLength != 0)
        {
            this.deltaX = (inputDeltaX / directionLength) * speedFactor;
            this.deltaY = (inputDeltaY / directionLength) * speedFactor;
        }
    }

    public void act()
    {
        moveInDirection();
        if (checkForHitEnemy() || isAtEdge())
        {
            getWorld().removeObject(this);
        }
    }

    private void moveInDirection()
    {
        setLocation(getX() + (int) Math.round(deltaX), getY() + (int) Math.round(deltaY));
    }

    private boolean checkForHitEnemy()
    {
        Enemy hitEnemy = (Enemy) getOneIntersectingObject(Enemy.class);
        if (hitEnemy != null)
        {
            hitEnemy.dealDamage(this.damage);
            return true;
        }
        return false;
    }
}

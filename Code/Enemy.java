import greenfoot.*;

public class Enemy extends Actor
{
    private int speed;
    private Tower targetTower;
    private double health = 2;
    private int timeBetweenAttacks = 100;
    private int timeOfLastAttack = 0;
    private double damage = 1;

    public Enemy(Tower target, int level)
    {
        targetTower = target;
        // Example buff: Increase speed based on level
        speed = level;
    }

    public void act()
    {
        if (targetTower != null && !targetTower.isDead())
        {
            turnTowards(targetTower.getX(), targetTower.getY());
            move(speed);
        }
        else
        {
            getNewTarget();
        }
        if (attackTime())
        {
            checkHitTower();
        }
    }

    private boolean attackTime()
    {
        if (timeOfLastAttack >= timeBetweenAttacks)
        {
            return true;
        }
        else
        {
            timeOfLastAttack++;
            return false;
        }
    }

    private void checkHitTower()
    {
        Tower hitTower = (Tower) getOneIntersectingObject(Tower.class);
        if (hitTower != null)
        {
            hitTower.dealDamage(this.damage);
            if (hitTower.isDead())
            {
                getNewTarget();
            }
            timeOfLastAttack = 0;
        }
    }

    private void getNewTarget()
    {
        this.targetTower = ((GameWorld) getWorld()).findNearestTower();
    }

    public void dealDamage(double damage)
    {
        this.health -= damage;
        if (health <= 0)
        {
            ((GameWorld) getWorld()).addScore(1);
            getWorld().removeObject(this);
        }
    }
}

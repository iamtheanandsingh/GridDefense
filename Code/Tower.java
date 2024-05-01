import greenfoot.*;
import java.util.List;

public class Tower extends Actor
{
    private int shootInterval = 75;
    private int timeUntilNextShot = shootInterval;
    private int shootIntervalBuff = 0;
    private boolean isPlayerControlled = false;
    private int enhancedShootInterval = shootInterval / 3;
    private String shootSound = "SHOOT006.mp3";
    private double health = 2;

    public Tower()
    {
        setImage("tower.png");
    }

    public void act()
    {
        checkModeToggle();
        handleShooting();
    }

    private void checkModeToggle()
    {
        if (!isPlayerControlled && Greenfoot.mouseClicked(this))
        {
            World world = getWorld();
            if (world instanceof GameWorld)
            {
                ((GameWorld) world).towerClicked(this);
            }
            else
            {
                System.out.println("Error: GameWorld not found.");
            }
        }
    }

    public void markPlayerControlled(boolean isPlayerControlled)
    {
        this.isPlayerControlled = isPlayerControlled;
        if (isPlayerControlled)
        {
            setImage("tower-playercontrolled.png");
            this.shootInterval = 50;
        }
        else
        {
            setImage("tower.png");
            this.shootInterval = 100;
        }
    }

    private void handleShooting()
    {
        if (isPlayerControlled)
        {
            MouseInfo mouse = Greenfoot.getMouseInfo();
            if (mouse != null)
            {
                if (timeUntilNextShot <= 0)
                {
                    aimAndShoot(mouse.getX(), mouse.getY());
                }
            }
        }
        else
        {
            autoTargetAndShoot();
        }
        if (timeUntilNextShot > 0)
        {
            timeUntilNextShot--;
        }
    }

    private void autoTargetAndShoot()
    {
        Enemy nearestEnemy = findNearestEnemy();
        if (nearestEnemy != null && timeUntilNextShot <= 0)
        {
            aimAndShoot(nearestEnemy.getX(), nearestEnemy.getY());
        }
    }

    private Enemy findNearestEnemy()
    {
        List<Enemy> enemies = getWorld().getObjects(Enemy.class);
        Enemy nearestEnemy = null;
        double nearestDistance = Double.MAX_VALUE;

        for (Enemy enemy : enemies)
        {
            double distance = getDistanceTo(enemy);
            if (distance < nearestDistance)
            {
                nearestEnemy = enemy;
                nearestDistance = distance;
            }
        }

        return nearestEnemy;
    }

    private void aimAndShoot(double targetX, double targetY)
    {
        int deltaX = (int) targetX - getX();
        int deltaY = (int) targetY - getY();
        Projectile projectile = new Projectile(deltaX, deltaY);
        projectile.setImage("bullet.png");
        getWorld().addObject(projectile, getX(), getY());
        Greenfoot.playSound(shootSound);
        timeUntilNextShot = shootInterval;
    }

    private double getDistanceTo(Actor actor)
    {
        return Math.hypot(actor.getX() - getX(), actor.getY() - getY());
    }

    private void showMessage(String message)
    {
        int yOffset = 60;
        getWorld().showText(message, getX(), getY() - yOffset);
    }

    public void applyShootIntervalBuff(int buffAmount)
    {
        shootIntervalBuff += buffAmount;
    }

    public boolean isDead()
    {
        return this.health <= 0;
    }

    public void dealDamage(double damage)
    {
        this.health -= damage;
        if (this.isDead())
        {
            ((GameWorld) getWorld()).removeTower(this);
        }
    }
}

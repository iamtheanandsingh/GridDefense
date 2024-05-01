import greenfoot.Actor;
import greenfoot.GreenfootImage;
import greenfoot.Color;

public class TextActor extends Actor
{
    private int duration;

    public TextActor(String text, int size, Color fontColor, Color backgroundColor, int duration)
    {
        GreenfootImage image = new GreenfootImage(text, size, fontColor, backgroundColor);
        setImage(image);
        this.duration = duration;
    }

    public void act()
    {
        if (isShowing())
        {
            duration--;
        }
        else
        {
            getWorld().removeObject(this);
        }
    }

    public boolean isShowing()
    {
        return duration > 0;
    }

    public void forceRemove()
    {
        if (isShowing())
        {
            getWorld().removeObject(this);
        }
    }
}

import greenfoot.*;

public class Button extends Actor
{
    private String label;

    public Button(String label)
    {
        this.label = label;
        setImage(new GreenfootImage(label, 24, Color.WHITE, Color.BLACK));
    }

    public String getLabel()
    {
        return label;
    }
}

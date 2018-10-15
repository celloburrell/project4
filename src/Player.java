import java.util.ArrayList;

public class Player extends Character
{
    private Room location;
    private ArrayList<String> itemsFound = new ArrayList<>();
    private ArrayList<String> killList = new ArrayList<>();

    public Player(String name, int health, int strength)
    {
        super(name, health, strength);
    }

    public Room getLocation()
    {
        return location;
    }

    public void setLocation(Room location)
    {
        this.location = location;
    }

    public void addToKillList(String enemy)
    {
        this.killList.add(enemy);
    }

    public boolean checkKillList()
    {
        boolean completedList;
        if (this.killList.size() == 7)
        {
            System.out.println("You've killed all of the enemies!");
            completedList =  true;
        }
        else
        {
            completedList = false;
        }
        return completedList;
    }

    public boolean checkItemsList()
    {
        boolean completedList;
        if (this.itemsFound.size() == 7)
        {
            System.out.println("You've found all of the items!");
            completedList =  true;
        }
        else
        {
            completedList = false;
        }
        return completedList;
    }

    public void checkLists()
    {
        if (this.checkKillList() == true && this.checkItemsList() == true)
        {
            System.out.println("You've won the game! Great job!");
            System.exit(0);
        }
    }

    public void addItem(String item)
    {
        this.itemsFound.add(item);
    }


}

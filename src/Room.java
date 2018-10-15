
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Room
{
    private String description;
    private String name;
    private HashMap<String, Room> connections = new HashMap<>();
    static ArrayList<String> enemies = new ArrayList<>(Arrays.asList("Goblin", "Orc", "Troll", "Zombie", "Bat", "Spider", "Kobold"));
    private static ArrayList<String> itemList = new ArrayList<>(Arrays.asList("Necklace", "Key", "Book", "Scroll", "Rock", "Flint", "Locket"));
    private Character enemy;
    private String items;
    private Random rand = new Random();

    public Room(String name, String description)
    {
        this.name = name;
        this.description = description;
        if(enemies.size() > 0)
        {
            this.getRandomEnemy(enemies);
        }
        if(itemList.size()>0)
        {
            this.getRandomItems(Room.itemList);
        }
    }

    public static ArrayList<String> getItemList()
    {
        return itemList;
    }

    private void getRandomItems(ArrayList<String> itemList)
    {
        // nextInt gets value between 0 inclusive and list size exclusive
        int randomIndex = rand.nextInt(itemList.size());
        // ArrayList.remove(index) removes element at index specified and returns it
        String randomItem = itemList.remove(randomIndex);
        this.items = randomItem;
    }

    public void getRandomEnemy(ArrayList<String> enemyList)
    {
        int randomIndex = rand.nextInt(enemyList.size());
        String randomEnemy = enemyList.remove(randomIndex);
        this.enemy = new Character(randomEnemy, rand.nextInt(12) + 1, rand.nextInt(10) + 1);
    }


    public Character getEnemy()
    {
        return  this.enemy;
    }

    public String getItems()
    {
        return items;
    }

    public void showEnemy()
    {
        System.out.printf("There is a %s in the room.", this.enemy.getName());
    }

    public void setItems(String items) {
        this.items = items;
    }

    public void setEnemy(Character enemy) {
        this.enemy = enemy;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDirection(HashMap<String, Room> connections)
    {
        this.connections = connections;
    }

    public Room getDirection(String direction)
    {
        return connections.get(direction);
    }

    public String getName()
    {
        return name;
    }
}

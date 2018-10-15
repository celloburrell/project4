import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Game
{
    private Scanner console = new Scanner(System.in);
    private Random rand = new Random();
    //List of descriptions
    private String entranceDesc = "\nYou have entered a dusty shack.\n"
            + "The windows are cracked. There's a broken table tossed to the side.\n"
            + "There's a stairwell leading down into the darkness at the end of the room.\n"
            + "You can go north down the stairs.\n";
    private String lobbyDesc = "\nYou are in the entrance chamber. There is a dim torch lighting the room.\n"
            + "You feel a sense of dread. There is a passageway to the west and another to the east.\n"
            + "The shack is behind you to the south up the stairs.\n";
    private String tortureChamberDesc = "\nYou are in a torture chamber. There is a skeleton chained to the wall.\n"
            + "There appears to be a snake moving through the skeleton. You can see something hiding beneath it.\n"
            + "You can go west back to the entrance chamber.\n";
    private String bonesDesc = "\nYou are in a corridor filled entirely of half chewed up bones.\n"
            + "The air reeks of rotten meat and flies are swarming everywhere.\n"
            + "You can go go north through a steel door or east to the entrance chamber.\n";
    private String corridorDesc = "\nYou are in an empty corridor.\n"
            + "The room has a fine layer of dust over thr ground and the torches on the walls are going out.\n"
            + "You can go south or east.\n";
    private String gateDesc = "\nYou enter a room with a large steel gate blocking what you can see is a room with a chest.\n"
            + "The room with the gate seems to be incredibly well kept compared to the rest of the place.\n"
            + "There is a strange sound coming from the east room.\n"
            + "You can go north, east, or west.\n";
    private String treasureDesc = "\nYou enter a room completely lit up with multiple torches.\n"
            + "There seems to be no dust in here at all.\n"
            + "You can go south.\n";
    private String trapDesc = "\nYou enter a room with loose planks covering the floor.\n"
            + "As you take a set into the room, the door closes behind you.\n"
            + "You can go east.\n";
    private String gameOver = "\nAs you start to move the planks around, you see sharp metal spikes spring from the walls.\n"
            + "Underneath the boards is a pit that seems to have no bottom.\n"
            + "The walls are slowly moving towards you and you hear a click coming from the door you entered in.\n"
            + "You try pulling it open, but it doesn't seem to budge.\n"
            + "The walls continue to close in on you until everything fades to black.\n";
    //connections
    private HashMap<String, Room> entranceExits = new HashMap<>();
    private HashMap<String, Room> lobbyExits = new HashMap<>();
    private HashMap<String, Room> tortureExits = new HashMap<>();
    private HashMap<String, Room> bonesExits = new HashMap<>();
    private HashMap<String, Room> corridorExits = new HashMap<>();
    private HashMap<String, Room> gateExits = new HashMap<>();
    private HashMap<String, Room> treasureExits = new HashMap<>();
    //Rooms
    private Room entrance = new Room("entrance", entranceDesc);
    private Room lobby = new Room("lobby", lobbyDesc);
    private Room tortureChamber = new Room("torture chamber", tortureChamberDesc);
    private Room bones = new Room("bones", bonesDesc);
    private Room corridor = new Room("corridor", corridorDesc);
    private Room gate = new Room("gate", gateDesc);
    private Room treasureRoom = new Room("treasure room", treasureDesc);
    private Room trapRoom = new Room("trap room", trapDesc);

    private String intro = "Welcome to the Abandoned Shack!\n"
            + "In each room, you will be told which directions you can go.\n"
            + "You can move north, south, east, or west by typing n, s, e, or w.\n"
            + "You will win the game when you kill all enemies and collect all items.\n"
            + "Type help to replay this introduction. Type q to end the program.\n\n";

    private void showIntro()
    {
        System.out.println(intro);
    }

    private String name;

    private void setName()
    {
        System.out.println("What is your name?\n");
        this.name = console.nextLine();
    }

    private void setConnections()
    {
        entranceExits.put("n", lobby);
        lobbyExits.put("s", entrance);
        lobbyExits.put("w", bones);
        lobbyExits.put("e", tortureChamber);
        tortureExits.put("w", lobby);
        bonesExits.put("e", lobby);
        bonesExits.put("n", corridor);
        corridorExits.put("s", bones);
        corridorExits.put("e", gate);
        gateExits.put("n", treasureRoom);
        gateExits.put("w", corridor);
        gateExits.put("e", trapRoom);
        treasureExits.put("s", gate);


        entrance.setDirection(entranceExits);
        lobby.setDirection(lobbyExits);
        tortureChamber.setDirection(tortureExits);
        bones.setDirection(bonesExits);
        corridor.setDirection(corridorExits);
        gate.setDirection(gateExits);
        treasureRoom.setDirection(treasureExits);
    }

    private void wait(int numSeconds)
    {
        try
        {
            Thread.sleep(numSeconds * 1000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    //Run the actual game
    public void runGame()
    {
        this.setName();
        this.showIntro();
        this.setConnections();
        Player player = new Player(name, 20, 10);
        player.setLocation(entrance);
        wait(6);

        while (true)
        {
            System.out.println(player.getLocation().getDescription());
            System.out.println("What do you want do?\n" + "1. Search room\n" + "2. Leave room");
            System.out.print(">> ");
            String action = console.nextLine();
            if (action.equals("1"))
            {
                if(player.getLocation().getName().equals("trap room"))
                {
                    System.out.println(gameOver);
                    System.out.println("\nGame over!\n");
                    System.exit(0);
                }
                if (player.getLocation().getEnemy() != null)
                {
                    player.getLocation().showEnemy();
                    while (true)
                    {
                        System.out.println("\nDo you want to attack? (y/n)");
                        System.out.print(">> ");
                        String reaction = console.nextLine();
                        if (reaction.equals("y"))
                        {
                            String outcome = player.fight(player.getLocation().getEnemy());
                            if (outcome != null)
                            {
                                if (outcome.equals(player.getName()))
                                {
                                    System.out.println("\nGame over!\n");
                                    System.exit(0);
                                }
                                else
                                {
                                    player.addToKillList(outcome);
                                    player.getLocation().setEnemy(null);
                                    player.checkKillList();
                                    player.checkLists();
                                }
                                break;
                            }
                        }
                        else
                        {
                            if (rand.nextInt(2) == 1)
                            {
                                String result = player.getLocation().getEnemy().fight(player);
                                if (result != null)
                                {
                                    if (result.equals(player.getName()))
                                    {
                                        System.out.println("\nGame over!\n");
                                        System.exit(0);
                                    }
                                    else
                                    {
                                        player.addToKillList(result);
                                        player.getLocation().setEnemy(null);
                                        player.checkKillList();
                                        player.checkLists();
                                    }
                                }
                                break;
                            }
                            else
                            {
                                System.out.printf("The %s has disappeared!\n", player.getLocation().getEnemy().getName());
                            }
                        }
                    }
                }
                System.out.println("You search the room...\n");
                if (player.getLocation().getItems() == null)
                {
                    System.out.println("You find nothing of value in the room.");
                }
                else
                {
                    int find = rand.nextInt(3) + 1;
                    if (find == 1)
                    {
                        String item = player.getLocation().getItems();
                        player.getLocation().setItems(null);
                        System.out.printf("You found a %s.\n", item);
                        player.addItem(item);
                        player.checkItemsList();
                        player.checkLists();
                    }
                    else
                    {
                        System.out.println("Nothing found yet.");
                    }
                }
            }
            else
            {
                System.out.println("\n Location Reminder\n");
                System.out.println(player.getLocation().getDescription());
                System.out.println("Which way?");
                System.out.print(">> ");
                String direction = console.nextLine();
                if (direction.equalsIgnoreCase("q"))
                {
                    System.out.println("Exit game");
                    break;
                }
                else
                {
                    Room newLocation = player.getLocation().getDirection(direction);
                    if (newLocation != null)
                    {
                        player.setLocation(newLocation);
                    }
                    else
                    {
                        System.out.println("Not an exit.");
                        player.setLocation(player.getLocation());
                    }
                }
            }
        }
    }
}

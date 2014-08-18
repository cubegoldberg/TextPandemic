/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic;

import java.util.ArrayList;
import java.util.Scanner;
import pandemic.Pandemic.Action;
import pandemic.board.Node;
import pandemic.cards.PlayerCard;
import pandemic.cards.RoleCard;

/**
 *
 * @author Rylo
 */
public class Player
{
    private ArrayList<PlayerCard> hand = new ArrayList<PlayerCard>();
    private City location;
    private int actions = 4;
    private int actonsUsed = 0;
    private RoleCard role;
    private String name;
    //TODO: Player can have cubes infected.

    public ArrayList<PlayerCard> getHand()
    {
	return hand;
    }

    public void setHand(ArrayList<PlayerCard> hand)
    {
	this.hand = hand;
    }

    public City getLocation()
    {
	return location;
    }

    public void setLocation(City location)
    {
	this.location = location;
    }

    public int getActions()
    {
	return actions;
    }

    public void setActions(int actions)
    {
	this.actions = actions;
    }

    public RoleCard getRole()
    {
	return role;
    }

    public void setRole(RoleCard role)
    {
	this.role = role;
    }

    public String getName()
    {
	return name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    Player(String string)
    {
	this.name = string;
    }

    public ArrayList<PlayerCard> viewHand()
    {
	return hand;
    }

    public void addCard(PlayerCard card)
    {
	this.hand.add(card);
    }

    public Action getAction()
    {
	Scanner in = new Scanner(System.in);
	boolean done = false;
	Action result = null;
	while (!done)
	{
	    String text = in.nextLine();
	    text = text.toLowerCase();
	    //TODO process String action into an Action action and return it.
	    String firstWord = text.substring(0, text.indexOf(" "));
	    String afterFirstWord = text.substring(text.indexOf(" ") + 1);
	    if (firstWord.compareTo("treat") == 0)
	    {
		int code = Pandemic.colorToCode(afterFirstWord);
		if (code != Pandemic.NOT_A_COLOR)//not not a color is a color
		{
		    if (this.location.getColors()[code] > 0)
		    {
			done = true;
			result = new Action(Pandemic.Action.TREAT_DISEASE, this.location.getName(), afterFirstWord);
		    }
		}
		else
		{
		    int color = Pandemic.NOT_A_COLOR;
		    //Treat [playerName] [color]
		    if (afterFirstWord.endsWith("blue"))
		    {
			color = Pandemic.BLUE;
			afterFirstWord = afterFirstWord.substring(0, afterFirstWord.length() - 5);
		    }
		    else if (afterFirstWord.endsWith("black"))
		    {
			color = Pandemic.BLACK;
			afterFirstWord = afterFirstWord.substring(0, afterFirstWord.length() - 6);
		    }
		    else if (afterFirstWord.endsWith("red"))
		    {
			color = Pandemic.RED;
			afterFirstWord = afterFirstWord.substring(0, afterFirstWord.length() - 4);
		    }
		    else if (afterFirstWord.endsWith("yellow"))
		    {
			color = Pandemic.YELLOW;
			afterFirstWord = afterFirstWord.substring(0, afterFirstWord.length() - 7);
		    }
		    else if (afterFirstWord.endsWith("purple"))
		    {
			color = Pandemic.PURPLE;
			afterFirstWord = afterFirstWord.substring(0, afterFirstWord.length() - 7);
		    }
		    else
		    {
			System.out.println("We detect that you are trying to treat a player, but do not know what color you would like to treat of that player!");
		    }
		    for (int i = 0; i < this.location.getPlayersHere().size(); i++)
		    {
			if (this.location.getPlayersHere().get(i).getName().compareTo(afterFirstWord) == 0)
			{
			    //this is the player.
			    //do they have the disease needed for this to make sense?
			    if (this.location.getPlayersHere().get(i).getInfections(color) > 0)
			    {
				done = true;
				result = new Action(Pandemic.Action.TREAT_PLAYER, afterFirstWord, null);
			    }
			    System.out.println("Player is at your location, but is not infected with the disease you want to treat!");
			}
		    }
		    if (!done)
		    {
			System.out.println("Could not find the player named " + afterFirstWord + " at your location");
		    }
		}
		//Treat [color]
		//Treat [playerName]
		//done = true;

	    }
	    else if (firstWord.compareTo("build") == 0)
	    {
		for (int i = 0; i < this.hand.size(); i++)
		{
		    if (this.hand.get(i).getCity() == this.location)
		    {
			result = new Action(Pandemic.Action.BUILD_STATION, this.location.getName(), null);
			done = true;
		    }
		}
		//Build Research Station
		if (!done)
		{
		    System.out.println("You tried to build a research station. However, this action requires a city card of your current location.");
		}

	    }
	    else if (firstWord.compareTo("move") == 0)
	    {
		//Move to [cityName]
		String cityName = afterFirstWord.substring(8);
		Node node = this.location.getMyNode();
		for (int i = 0; i < node.getNumOfEdges(); i++)
		{
		    if (((City) ((((Node) (node.getAttachedTo().get(i))).getValue()))).getName().compareTo(cityName) == 0)
		    {
			//done = true;
			result = new Action(Pandemic.Action.DRIVE_FERRY, cityName, null);
		    }
		    else
		    {
			System.out.println("You are not adjacent to the city that you entered.");
		    }
		}
		done = true;

	    }//after this nothign is implemented
	    else if (firstWord.compareTo("fly") == 0)
	    {
		//Fly from [cityName] to [cityName]
		//Move to [cityName]
		done = true;

	    }
	    else if (firstWord.compareTo("teleport") == 0)
	    {
		//Teleport to [cityName]
		done = true;

	    }
	    else if (firstWord.compareTo("give") == 0)
	    {
		//Give [cityName] (card) to [playerName]
		//Give funds to [playerName]
		done = true;

	    }
	    else if (firstWord.compareTo("take") == 0)
	    {
		//Take [cityName] (card) from [playerName]
		//Take funds from [playerName]
		done = true;

	    }
	    else if (firstWord.compareTo("cure") == 0)
	    {
		//Cure [color]
		done = true;

	    }
	    else if (firstWord.compareTo("lobby") == 0)
	    {
		//Lobby government
		done = true;

	    }
	    else if (firstWord.compareTo("request") == 0)
	    {
		//Request funds
		done = true;

	    }
	    else if (firstWord.compareTo("quarantine") == 0)
	    {
		//Quarantine ([cityName])
		done = true;

	    }
	    else
	    {
		System.out.println("Did not understand action request. Please format"
			+ " it in one of the following ways:");
		System.out.println("\nTreat [color]\nBuild Station\nMove to ["
			+ "cityName]\nFly from [cityName] to [cityName]\nFly to [cit"
			+ "yName]\nTeleport to [cityName]\nGive [cityName] (card) to"
			+ " [playerName]\nTake [cityName] (card) from [playerName]\n"
			+ "Cure [color]\nLobby government\nRequest funds\nGive funds"
			+ " to [playerName]\nTake fund from [playerName]\nTreat [pla"
			+ "yerName]\nQuarantine ([cityName])");

		//Alternate Streamlined version:
		//Remove [color] from [city]
		//Build station at [city]//Not even needed.

		//Place [color] at [city]
		//Stall/Wait

		//Draw and infect

	    }
	}
	return result;
    }

    @Override
    public String toString()
    {
	String result = "";
	if (this.name != null && this.role != null)
	{
	    result += "Player \"" + this.name + "\" (" + this.role.getName() + ") has hand=";
	}
	result += this.hand;
	if (this.location != null)
	{
	    result += " in " + this.location.getName();
	}
	result += " with " + this.actions + " actions available.";
	return result;
    }

    private int getInfections(int color)
    {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    int getLargestPopulation()
    {
	int result = 0;
	for (int i = 0; i < this.hand.size(); i++)
	{
	    if (this.hand.get(i).getPopulation() > result)
	    {
		result = this.hand.get(i).getPopulation();
	    }
	}
	return result;
    }
}

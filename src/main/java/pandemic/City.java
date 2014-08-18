/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic;

import java.util.ArrayList;
import pandemic.board.Node;

/**
 *
 * @author Rylo
 */
public class City
{
    private Pandemic pandemic;
    private Node myNode;
    private ArrayList<Player> playersHere = new ArrayList<Player>();
    private String name;
    private int naturalColor;
    private String nation;
    private int colors[] = new int[]
    {
	0, 0, 0, 0, 0
    };//Black, Blue, Red, Yellow, Purple
    private int quarantine = 0;
    private int governmentActivity = 0;
    private int population = 0;
    private int populationDensity = 0;
    private boolean researchStation = false;
    private boolean government = false;
    private boolean nuked = false;
    private boolean changed = false;
    private boolean outbroke = false;

    City(String name, int color, boolean government, boolean researchStation, int population, int populationDensity, String nation)
    {
	this.naturalColor = color;
	this.name = name;
	this.government = government;
	this.researchStation = researchStation;
	this.population = population;
	this.populationDensity = populationDensity;
	this.nation = nation;
    }

    public void setNaturalColorCubes(int input)
    {
	this.colors[this.naturalColor] = input;
	boolean changed = true;
    }

    public void treat(int color)
    {
	if (this.colors[color] > 0)
	{
	    --this.colors[color];
	}
	else
	{
	    System.out.println(this.name + " has no " + Pandemic.codeToColor(color));
	}
    }

    public Node getMyNode()
    {
	return myNode;
    }

    public void setMyNode(Node myNode)
    {
	this.myNode = myNode;
    }

    public String getNation()
    {
	return nation;
    }

    public void setNation(String nation)
    {
	this.nation = nation;
    }

    public int getGovernmentActivity()
    {
	return governmentActivity;
    }

    public void setGovernmentActivity(int governmentActivity)
    {
	this.governmentActivity = governmentActivity;
    }

    public int getPopulation()
    {
	return population;
    }

    public void setPopulation(int population)
    {
	this.population = population;
    }

    public int getPopulationDensity()
    {
	return populationDensity;
    }

    public void setPopulationDensity(int populationDensity)
    {
	this.populationDensity = populationDensity;
    }

    public int infections()
    {
	int count = 0;
	for (int i = 0; i < this.colors.length; i++)
	{
	    count += this.colors[i];
	}
	return count;
    }

    public String getName()
    {
	return name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    public int[] getColors()
    {
	return colors;
    }

    public void setColors(int[] colors)
    {
	this.colors = colors;
    }

    public boolean isChanged()
    {
	return changed;
    }

    public void setChanged(boolean changed)
    {
	this.changed = changed;
    }

    public boolean isOutbroke()
    {
	return outbroke;
    }

    public void setOutbroke(boolean outbroke)
    {
	this.outbroke = outbroke;
    }

    public boolean isNuked()
    {
	return nuked;
    }

    public void setNuked(boolean nuked)
    {
	this.nuked = nuked;
    }

    public int getNaturalColor()
    {
	return naturalColor;
    }

    public void setNaturalColor(int naturalColor)
    {
	this.naturalColor = naturalColor;
    }

    public int getQuarantine()
    {
	return quarantine;
    }

    public void setQuarantine(int quarantine)
    {
	this.quarantine = quarantine;
    }

    public boolean isResearchStation()
    {
	return researchStation;
    }

    public void setResearchStation(boolean researchStation)
    {
	this.researchStation = researchStation;
    }

    public boolean isGovernment()
    {
	return government;
    }

    public void setGovernment(boolean government)
    {
	this.government = government;
    }

    public ArrayList<Player> getPlayersHere()
    {
	return playersHere;
    }

    public void setPlayersHere(ArrayList<Player> playersHere)
    {
	this.playersHere = playersHere;
    }

    public void addPlayer(Player player)
    {
	this.playersHere.add(player);
    }

    public void removePlayer(Player player)
    {
	boolean result = this.playersHere.remove(player);
	if (!result)
	{
	    System.out.println("Player could not be removed from city " + this);
	}
    }

    @Override
    public String toString()
    {
	String result = this.name + ", Population " + this.population + " with density " + this.populationDensity + " people per km^2, in nation " + this.nation;
	result += " (" + this.naturalColor + ") has ";
	if (this.colors[Pandemic.RED] > 0)
	{
	    result += this.colors[Pandemic.RED] + " red cubes, ";
	}
	if (this.colors[Pandemic.BLUE] > 0)
	{
	    result += this.colors[Pandemic.BLUE] + " blue cubes, ";
	}
	if (this.colors[Pandemic.BLACK] > 0)
	{
	    result += this.colors[Pandemic.BLACK] + " black cubes, ";
	}
	if (this.colors[Pandemic.YELLOW] > 0)
	{
	    result += this.colors[Pandemic.YELLOW] + " yellow cubes, ";
	}
	if (this.colors[Pandemic.PURPLE] > 0)
	{
	    result += this.colors[Pandemic.PURPLE] + " purple cubes";
	}
	if (this.infections() == 0)
	{
	    result += "no cubes.\t";
	}
	else
	{
	    result += "\t";
	}
	if (this.government)
	{
	    result += "Government activity is at " + this.governmentActivity + "\t";
	}
	if (this.nuked)
	{
	    result += "This city has been nuked.\t";
	}
	if (this.researchStation)
	{
	    result += "This city has a research station.\t";
	}
	result += "\tPlayers: ";
	for (int i = 0; i < this.playersHere.size(); i++)
	{
	    result += "\t" + this.playersHere.get(i);
	}
	return result;
	//"City{" + "name=" + name + ", naturalColor=" + naturalColor + ", red=" + red + ", blue=" + blue + ", yellow=" + yellow + ", black=" + black + ", quarantine=" + quarantine + ", governmentActivity=" + governmentActivity + ", researchStation=" + researchStation + ", government=" + government + ", nuked=" + nuked + ", changed=" + changed + ", outbroke=" + outbroke + ", playersHere=" + playersHere + '}';
    }

    public int infect(int color)
    {
	int outbreaksTriggered = 0;
	if (!bordersQuarantineExpert())
	{
	    if (this.colors[color] < 3)
	    {
		this.colors[color]++;
		this.changed = true;
	    }
	    else if (!outbroke)
	    {
		this.outbroke = true;
		outbreaksTriggered = 1;
		ArrayList<Node> toInfect = this.myNode.getAttachedTo();
		System.out.println(this.name + " outbreaks!");
		for (int i = 0; i < toInfect.size(); i++)
		{
		    System.out.println(this.name + " outbreaks to " + ((City) (toInfect.get(i).getValue())).getName());
		    outbreaksTriggered += ((City) (toInfect.get(i).getValue())).infect(color);
		}
	    }
	    System.out.println(this);
	}
	else
	{
	    System.out.println("The quarantine expert prevented an infection in " + this.name + "!");
	}

	//System.out.println(this);
	return outbreaksTriggered;
    }

    private boolean bordersQuarantineExpert()
    {
	boolean doesBorder = hasQuarantineExpert(); //if it's here or adjacent.
	for (int i = 0; i < this.myNode.getAttachedTo().size(); i++)
	{
	    Node node = (Node) (myNode.getAttachedTo().get(i));
	    City city = (City) (node.getValue());
	    doesBorder = doesBorder || (city.hasQuarantineExpert());//if any one of the places has it, it borders
	}
	return doesBorder;
    }

    private boolean hasQuarantineExpert()
    {
	boolean result = false;
	for (int i = 0; i < this.playersHere.size(); i++)
	{
	    result = result || (this.playersHere.get(i).getRole().getName().compareTo("Quarantine Specialist") == 0);
	}
	//System.out.println("Is the quarantine expert in " + this.name + "? " + result);
	return result;
    }

    boolean bordersPlayer()
    {
	boolean result = (this.playersHere.size()>0);
	for(int i = 0; i<this.myNode.getAttachedTo().size(); i++)
	{
	    Node node = (Node) (this.myNode.getAttachedTo().get(i));
	    City city = (City) (node.getValue());
	    result = result || (city.playersHere.size()>0);
	}
	return result;
    }
}

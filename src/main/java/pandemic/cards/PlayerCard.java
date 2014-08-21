/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.cards;

import pandemic.org.Pandemic;
import pandemic.org.City;

/**
 *
 * @author Rylo
 */
public class PlayerCard
{
    public PlayerCard(String inName, String type)
    {
	this.name = inName;
	if(type.compareTo("Virulent Epidemic")==0)
	{
	    this.virulentEpidemic = true;
	}
	else if(type.compareTo("Event")==0)
	{
	    this.event = true;
	}
	else if(type.compareTo("Mutation")==0)
	{
	    this.mutation = true;
	}
	else if (type.compareTo("Mod Event")==0)
	{
	    this.modCard = true;
	}
	else if (type.compareTo("Epidemic") == 0)
	{
	    this.normalEpidemic = true;
	}
	else
	{
	    throw new ArithmeticException("You gave us a player card with a specific type but it didn't match our library of types");
	}
    }

    public PlayerCard(City inCity)
    {
	this.city = inCity;
	this.cityCard = true;
    }
        
    private City city;
    private int population = 0;
    
    private boolean cityCard = false;
    
    private String name;
    private boolean virulentEpidemic = false;
    private boolean normalEpidemic = false;
    private boolean event = false;
    private boolean mutation = false;
    private boolean modCard = false;
    
    
    public boolean isNormalEpidemic()
    {
	return normalEpidemic;
    }

    public void setNormalEpidemic(boolean normalEpidemic)
    {
	this.normalEpidemic = normalEpidemic;
    }

    public boolean isMutation()
    {
	return mutation;
    }

    public void setMutation(boolean mutation)
    {
	this.mutation = mutation;
    }

    public boolean isModCard()
    {
	return modCard;
    }

    public void setModCard(boolean modCard)
    {
	this.modCard = modCard;
    }

    public String getName()
    {
	return name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    public boolean isVirulentEpidemic()
    {
	return this.virulentEpidemic;
    }

    public void setVirulentEpidemic(boolean virulentEpidemic)
    {
	this.virulentEpidemic = virulentEpidemic;
    }

    public boolean isEvent()
    {
	if(event)
	{
	    //System.out.println(this.toString() + " is an event!!!!");
	}
	else
	{
	   // System.out.println(this.toString() + " is NOT and event!!!!");
	}
	return event;
    }

    public void setEvent(boolean event)
    {
	this.event = event;
    }

    public boolean isCityCard()
    {
	return cityCard;
    }

    public void setCityCard(boolean cityCard)
    {
	this.cityCard = cityCard;
    }

    public City getCity()
    {
	return city;
    }

    public void setCity(City city)
    {
	this.city = city;
    }
    
    public int getPopulation()
    {
	return population;
    }

    public void setPopulation(int population)
    {
	this.population = population;
    }

    @Override
    public String toString()
    {
	String result = "Player card is a";
	if(this.cityCard)
	    result += " city card: " + this.city.getName() + " (" + Pandemic.codeToColor(this.city.getNaturalColor()) + ")";
	if(this.event)
	    result += "n event card: " + this.name;
	if(this.modCard)
	    result += " mod card: " + this.name;
	if(this.mutation)
	    result += " mutation card: " + this.name;
	if(this.normalEpidemic)
	    result += "n epidemic card: " + this.name;
	if(this.virulentEpidemic)
	    result += " virulent epidemic card: " + this.name;
	return result;
    }
}

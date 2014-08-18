/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.cards;

import pandemic.Pandemic;
import pandemic.City;

/**
 *
 * @author Rylo
 */
public class InfectionCard
{
    private boolean mutationCard = false;
    private City city;
    
    public InfectionCard(boolean isMutationCard)
    {
	this.mutationCard = isMutationCard;
    }
    
    public InfectionCard(City inCity)
    {
	this.city = inCity;
    }

    public boolean isMutationCard()
    {
	return mutationCard;
    }

    public void setMutationCard(boolean mutationCard)
    {
	this.mutationCard = mutationCard;
    }

    public City getCity()
    {
	return city;
    }

    public void setCity(City city)
    {
	this.city = city;
    }
    
    

    @Override
    public String toString()
    {
	String result = "";
	if(this.mutationCard)
	{
	    result = "Infection Card is a mutation card.";
	}
	else
	{
	    result = "Infection Card has city = " + city.getName() + " (" + Pandemic.codeToColor(getCity().getNaturalColor()) + ")";
	}
	return result;
    }
    
    
}

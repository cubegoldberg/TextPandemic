/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.client;

/**
 *
 * @author Rylo
 */
public class ClientCity
{
    /*id (number). A unique id to refer to this city.
name (string)
population (number)
latitude (number). Real world coordinates so I can add these to the map without hacks.
longitude (number)
color (string)
disease (object). Each virus you want to represent will be a numeric property on this object. For instance you'd have disease.blue = 5.

* /
* */
    private int id;
    private String name;
    private int population;
    private int latitude;
    private int longitude;
    private String color;
    private ClientDisease disease;

    public int getId()
    {
	return id;
    }

    public void setId(int id)
    {
	this.id = id;
    }

    public String getName()
    {
	return name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    public int getPopulation()
    {
	return population;
    }

    public void setPopulation(int population)
    {
	this.population = population;
    }

    public int getLatitude()
    {
	return latitude;
    }

    public void setLatitude(int latitude)
    {
	this.latitude = latitude;
    }

    public int getLongitude()
    {
	return longitude;
    }

    public void setLongitude(int longitude)
    {
	this.longitude = longitude;
    }

    public String getColor()
    {
	return color;
    }

    public void setColor(String color)
    {
	this.color = color;
    }

    public ClientDisease getDisease()
    {
	return disease;
    }

    public void setDisease(ClientDisease disease)
    {
	this.disease = disease;
    }
    
}

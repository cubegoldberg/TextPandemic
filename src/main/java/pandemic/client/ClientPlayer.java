/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.client;

/**
 *
 * @author Rylo
 */
public class ClientPlayer
{
    /*
     * Each player has:

id (unique number)
name (string),
class (object), with a name and description property, both strings
location: The starting location of the player, as a city id.

     */
    private int id;
    private String name;
    private ClientRole classField;
    private int location;

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

    public ClientRole getClassField()
    {
	return classField;
    }

    public void setClassField(ClientRole classField)
    {
	this.classField = classField;
    }

    public int getLocation()
    {
	return location;
    }

    public void setLocation(int location)
    {
	this.location = location;
    }
}

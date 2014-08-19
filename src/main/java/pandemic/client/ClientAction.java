/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.client;

/**
 *
 * @author Rylo
 */
public class ClientAction
{
    /*
     * A signal signifying whose turn it is. Call this once for everyone whenever whose turn it is changes.

playerId (number) The player id of whose turn it is.
availableActions. Kind of confusing, only needed for the current player. This is an array of objects, each object representing an action that the player can take. Each object should have:

id (number) to represent the action, as well as a
name (string), a human-readable description of the action. Make this lowercase and underscore-separated so we can feel like hackers :wink:
description (string). A human-readable description of what this action does.
parameters. This should be an array of parameters taken by the action. 
* Each element in the array should be one of 'city', 'card', or 'player'. 
* Include as few parameters as possible. For instance don't include the 'city' 
* when clearing disease (because you can only clear disease in your own city), 
* and don't make me specify a card to discard when flying to a city. (Just use the city).
* The action will use the id of whatever parameters you want.
     */
    
    private int id;
    private String name;
    private String description;
    //I think this is what you want? Oh, no, also the array of paramter objects
    private Object[] parameters;

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

    public String getDescription()
    {
	return description;
    }

    public void setDescription(String description)
    {
	this.description = description;
    }

    public Object[] getParameters()
    {
	return parameters;
    }

    public void setParameters(Object[] parameters)
    {
	this.parameters = parameters;
    }
}

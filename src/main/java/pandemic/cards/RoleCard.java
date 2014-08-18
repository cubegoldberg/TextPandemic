/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.cards;

/**
 *
 * @author Rylo
 */
public class RoleCard
{
    private String name;

    public String getName()
    {
	return name;
    }

    public void setName(String name)
    {
	this.name = name;
    }
    
    public RoleCard(String line)
    {
	this.name = line;
    }

    @Override
    public String toString()
    {
	return "RoleCard{" + "name=" + name + '}';
    }
    
    
}

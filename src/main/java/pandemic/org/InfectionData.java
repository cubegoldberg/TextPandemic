/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.org;

/**
 *
 * @author Rylo
 */
public class InfectionData
{
    private int cubesPlaced;
    private int outbreaksTriggered;

    public InfectionData(int cubesPlaced, int outbreaksTriggered)
    {
	this.cubesPlaced = cubesPlaced;
	this.outbreaksTriggered = outbreaksTriggered;
    }

    public int getCubesPlaced()
    {
	return cubesPlaced;
    }

    public void setCubesPlaced(int cubesPlaced)
    {
	this.cubesPlaced = cubesPlaced;
    }

    public int getOutbreaksTriggered()
    {
	return outbreaksTriggered;
    }

    public void setOutbreaksTriggered(int outbreaksTriggered)
    {
	this.outbreaksTriggered = outbreaksTriggered;
    }
    
}

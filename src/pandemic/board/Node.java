/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.board;

import java.util.ArrayList;

/**
 *
 * @author Rylo
 */
public class Node<T>
{
        
    private T value;
    private int numOfEdges;
    private ArrayList<Node> attachedTo = new ArrayList();

    public ArrayList<Node> getAttachedTo()
    {
	return attachedTo;
    }

    public void setAttachedTo(ArrayList<Node> attachedTo)
    {
	this.attachedTo = attachedTo;
    }


    public Node(T initValue)
    {
	this.value = initValue;
    }
    
    public int getNumOfEdges()
    {
	numOfEdges = this.getAttachedTo().size();
	return numOfEdges;
    }

    public void setNumOfEdges(int numOfEdges)
    {
	this.numOfEdges = numOfEdges;
    }
    
    public T getValue()
    {
	return value;
    }

    public void setValue(T newValue)
    {
	this.value = newValue;
    }
    
    public void addEdge(Node newNode)
    {
	if(!(this.attachedTo.contains(newNode)))//if node isn't already in the other one
	{
	    this.attachedTo.add(newNode);
	    newNode.addEdge(this);//Edges are bidirectional
	}
    }

    public void shuffleConnections()
    {
	ArrayList<Node> temp = (ArrayList<Node>) this.attachedTo.clone();
	this.attachedTo.clear();
	while(!temp.isEmpty())
	{
	    int index = (int) ((double) (temp.size()) * Math.random());
	    this.attachedTo.add(temp.get(index));
	    temp.remove(index);
	}
    }
}
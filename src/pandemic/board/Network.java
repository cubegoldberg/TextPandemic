/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.board;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

/**
 *
 * @author Rylo
 */
public class Network<K, V> //K is name of the thing (key) and V is the thing itself
{
    private TreeMap<K, Node<V>> myNetwork = new TreeMap();
    
    public void addNode(K key, V value)
    {
	myNetwork.put(key, new Node(value));
    }
    
    public void addEdge(K key1, K key2)
    {
	myNetwork.get(key1).addEdge(myNetwork.get(key2));
    }
    
    public V get(K key)
    {
	return myNetwork.get(key).getValue();
    }
    
    public int size()
    {
	return this.myNetwork.size();
    }
    
    public ArrayList<Node> getConnectedTo(K key)
    {
	if(myNetwork.get(key)==null)
	{
	    System.out.println("key not found");
	    return null;
	}
	return myNetwork.get(key).getAttachedTo();
    }
    
    public Iterator getIterator()
    {
	return this.myNetwork.values().iterator();
    }

    public Node getNode(K line)
    {
	return this.myNetwork.get(line);
    }
}

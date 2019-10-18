package dataStructure;

import java.util.ArrayList;
import java.util.Objects;

public class NodeT<T> {
    private T instance;
    private ArrayList<T> neighbors;

    public NodeT(T object) {
        instance = object;
        neighbors = new ArrayList<T>();
    }

    /**
     * Adds a new node object to its internal neighbors arrayList if the object
     * does not exist already on it and is not equal to itself
     * @param object object of type T
     * @return
     */
    public boolean addNeighbor(T object){
        boolean result = (neighbors.stream().anyMatch(node->node.equals(object)) || object.equals(instance))?  false : true;
        if(result)neighbors.add(object);
        return result;
    }

    public T getInstance() {
        return instance;
    }

    public ArrayList<T> getNeighbors() {
        return neighbors;
    }

    @Override
    public String toString() {
       String instanceToString = this.instance.toString();
       String neighborsToString = "Neighbours : ";
       for (T obj : this.neighbors) neighborsToString += obj.toString();
       return instanceToString + " " + neighborsToString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NodeT)) return false;
        NodeT<?> nodeT = (NodeT<?>) o;
        return Objects.equals(instance, nodeT.instance) &&
                Objects.equals(neighbors, nodeT.neighbors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instance, neighbors);
    }
}

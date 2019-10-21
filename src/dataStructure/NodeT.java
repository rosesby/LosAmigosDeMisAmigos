package dataStructure;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class NodeT<T> {
    private T instance;
    private ArrayList<NodeT<T>> neighbors;

    public NodeT(T object) {
        instance = object;
        neighbors = new ArrayList<NodeT<T>>();
    }

    /**
     * Adds a new node object to its internal neighbors arrayList if the object
     * does not exist already on it and is not equal to itself
     * @param object object of type T
     * @return
     */
    public boolean addNeighbor(NodeT<T> object){
        boolean result = (neighbors.stream().anyMatch(node->node.equals(object)) || object.equals(instance))?  false : true;
        if(result)neighbors.add(object);
        return result;
    }

    public boolean removeNeighbor(NodeT<T> object){
        Optional<NodeT<T>> neighbor = neighbors.stream()
                .filter(node->{
                    return node.equals(object) || object.equals(instance);
                } ).findFirst();
        if(neighbor.isPresent()){
           neighbors.remove(neighbor.get());
           return true;
        }
        return false;
    }

    public T getInstance() {
        return instance;
    }

    public ArrayList<NodeT<T>> getNeighbors() {
        return neighbors;
    }

    @Override
    public String toString() {
       String instanceToString = this.instance.toString();
       String neighborsToString = "Neighbours : ";
       for (NodeT<T> obj : this.neighbors) neighborsToString += obj.getInstance().toString();
       return instanceToString + " " + neighborsToString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NodeT)) return false;
        NodeT<?> nodeT = (NodeT<?>) o;
        return instance.equals(nodeT.instance) &&
                neighbors.equals(nodeT.neighbors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instance, neighbors);
    }
}

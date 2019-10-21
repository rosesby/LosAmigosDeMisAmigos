package dataStructure;

import domain.model.Person;

import javax.swing.text.html.Option;
import java.util.*;

//todo implement equals, check internal or nested classes for architecture
public class GraphT<T>{
    private ArrayList<NodeT<T>> nodes;
    private Object NodeT;

    public GraphT() {
        nodes = new ArrayList<NodeT<T>>();
    }

    public void addNode(T object) {
        NodeT<T> node= new NodeT<T>(object);
        nodes.add(node);
    }

    public void addNode(NodeT<T> node) {
        nodes.add(node);
    }


    public boolean checkInternalNodesInPairs(NodeT<T> nodeA, NodeT<T> nodeB){
        return (nodeA != null && nodeB != null);
    }

    public boolean checkInternalNodesInPairs(NodeT<T>[] nodePair){
        return (nodePair[0] != null && nodePair[1] != null);
    }

    public NodeT<T>[] getInternalNodesInPairs(NodeT<T> nodeA, NodeT<T> nodeB){
        NodeT<T> nodeAInternal = getInternalNodeThatMatches(nodeA);
        NodeT<T> nodeBInternal = getInternalNodeThatMatches(nodeB);
        NodeT<T>[] nodePair = new NodeT[]{nodeAInternal, nodeBInternal};
        return nodePair;
    }

    public void setUniDirectionalHedge(NodeT<T> nodeA, NodeT<T> nodeB) {
        NodeT<T>[] nodePair = getInternalNodesInPairs(nodeA,  nodeB);
        checkInternalNodesInPairs(nodePair);
        if(checkInternalNodesInPairs(nodePair)) nodePair[0].addNeighbor(nodePair[1]);
    }

    public void setBiDirectionalHedge(NodeT<T> nodeA, NodeT<T> nodeB) {
        NodeT<T>[] nodePair = getInternalNodesInPairs(nodeA,  nodeB);
        checkInternalNodesInPairs(nodePair);
        if(checkInternalNodesInPairs(nodePair)) {
            nodePair[0].addNeighbor(nodePair[1]);
            nodePair[1].addNeighbor(nodePair[0]);
        }
    }

    public void unSetUniDirectionalHedge(NodeT<T> nodeA, NodeT<T> nodeB) {
        NodeT<T>[] nodePair = getInternalNodesInPairs(nodeA,  nodeB);
        checkInternalNodesInPairs(nodePair);
        if(checkInternalNodesInPairs(nodePair)) nodePair[0].removeNeighbor(nodePair[1]);
    }

    public void unSetBiDirectionalHedge(NodeT<T> nodeA, NodeT<T> nodeB) {
        NodeT<T>[] nodePair = getInternalNodesInPairs(nodeA,  nodeB);
        checkInternalNodesInPairs(nodePair);
        if(checkInternalNodesInPairs(nodePair)) {
            nodeA.removeNeighbor(nodeB);
            nodeB.removeNeighbor(nodeA);
        }
    }

    public boolean checkUnidirectionalHedge(NodeT<T> nodeA, NodeT<T> nodeB){
        return nodeA.getNeighbors().contains(nodeB);
    }

    public boolean checkBidirectionalHedge(NodeT<T> nodeA, NodeT<T> nodeB){
        return nodeA.getNeighbors().contains(nodeB) && nodeB.getNeighbors().contains(nodeA);
    }

    public boolean checkIfNodeExist(NodeT<T> targetNode){
        return nodes.stream().anyMatch(node-> node.equals(targetNode));
    }

    public NodeT<T> getInternalNodeThatMatches(T targetNode){
        Optional<NodeT<T>> searchedNode = nodes.stream().filter(node -> node.getInstance().equals(targetNode)).findFirst();
        if (searchedNode.isPresent()) return searchedNode.get();
        else return null;
    }

    public NodeT<T> getInternalNodeThatMatches(NodeT<T> targetNode){
        Optional<NodeT<T>> searchedNode = nodes.stream().filter(node-> node.equals(targetNode)).findFirst();
        if (searchedNode.isPresent()) return searchedNode.get();
        else return null;
    }

    public NodeT<T> getInternalNodeByPositionInCollection(int targetPositionNumber){
        if(targetPositionNumber > nodes.size() || targetPositionNumber < 0 ) return null;
        else return nodes.get(targetPositionNumber);
    }



    public void executeSearchByLevel(dataStructure.NodeT<T> node, int searchLevel) {
    }

    /**
     * Ejecuta una busqueda de relacion entre elementos usando el algoritmo BFS para Grafos
     *
     * @param strCity1 nombre de la ciudad1
     * @param strCity2 nombre de la ciudad2
     * @return String con el resultado y color
     */
   /* public String searchWayToCityBFS(String strCity1, String strCity2) {
        Map.City city1 = getExistingCityByName(strCity1);
        Map.City city2 = getExistingCityByName(strCity2);

        Queue<Map.City> searchQueue = new LinkedList<Map.City>();
        ArrayList<Map.City> visitedCities = new ArrayList<Map.City>();

        searchQueue.add(city1); //add first node to queue
        while (!searchQueue.isEmpty()) { //While the queue has nodes
            if (!searchQueue.peek().getLinkedCities().isEmpty()) { //Check if actual node has vertexes
                searchQueue.peek().getLinkedCities().forEach(city -> { //Check for all node if has not been processed or added to the queue before
                    if (!(visitedCities.contains(city) || searchQueue.contains(city)))
                        searchQueue.add(city); //Check node for adding to queue
                });
                if (searchQueue.contains(city2)) return Consola.Color.GREEN + "+"; //Check result
            }
            visitedCities.add(searchQueue.peek()); //add actual node to visited nodes
            searchQueue.poll(); //remove actual (first) node from queue
        }
        return Consola.Color.RED + "-";
    }*/

    /**
     * Cierra la conexion entre la ciudad1 y ciudad1
     */
   /* public void closeConnectionBetween(NodeT<T> node1, NodeT<T> node2) {
        Map.City city1 = getExistingCityByName(node1);
        Map.City city2 = getExistingCityByName(node2);

        if ((city1 == null) || (city2 == null)) return;
        city1.linkedCities.remove(city2);
    }

    public void searchWayToCityDFS(String strCity1, String strCity2) {
        Map.City city1 = getExistingCityByName(strCity1);
        Map.City city2 = getExistingCityByName(strCity2);

        Stack<Map.City> searchStack = new Stack<Map.City>();
        ArrayList<Map.City> visitedCities = new ArrayList<Map.City>();

        searchStack.add(city1); //add first node to queue

        while (searchStack.size() > 0) {
            visitedCities.add(searchStack.peek()); //add actual node to visited nodes
            Optional<Map.City> result = searchStack.peek().getLinkedCities().stream()  //Check for new nodes in the edges of the actual node
                    .filter(city -> !(visitedCities.contains(city)))
                    .findFirst();
            if (result.isPresent()) {
                searchStack.push(result.get());
                if (searchStack.contains(city2)) { //Check result
                    printSearchTrue(searchStack);
                    return ;
                }
            } else searchStack.pop(); //remove last node from stack if doesnt have new unprocessed edges
        }
        printSearchFalse(city1, city2);
    }*/

    /*private void printSearchTrue(Stack<Map.City> pathStack) {
        List<Map.City> cities = pathStack;
        System.out.print(Consola.Color.GREEN + "+ " + pathStack.get(0).getName());
        cities.remove(0);
        cities.forEach(city -> System.out.print(" => " + city));
        System.out.println();
    }

    private void printSearchFalse(Map.City city1, Map.City city2){
        System.out.println(Consola.Color.RED + "- " + city1 + " => " + city2 + Consola.Color.RESET);
    }*/

    public ArrayList<NodeT<T>> getNodes() {
        return nodes;
    }


}

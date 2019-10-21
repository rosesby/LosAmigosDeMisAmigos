package dataStructure;

import java.util.*;

//todo implement equals, check internal or nested classes for architecture
public class GraphT<T> {
    private ArrayList<NodeT<T>> nodes;
    private Object NodeT;
    boolean checkEqualsByNodeInternalObject = true;

    public GraphT() {
        nodes = new ArrayList<NodeT<T>>();
    }

    public boolean addNode(T object) {
        NodeT<T> node = new NodeT<T>(object);
        return addNode(node);
    }

    public boolean addNode(NodeT<T> node) {
        if (!doesNodeExistInternally(node)) {
            nodes.add(node);
            return true;
        } else return false;
    }

    public boolean isNodePairNull(NodeT<T> nodeA, NodeT<T> nodeB) {
        return (nodeA != null && nodeB != null);
    }

    public boolean isNodePairNull(NodeT<T>[] nodePair) {
        return (nodePair[0] != null && nodePair[1] != null);
    }

    public NodeT<T>[] getInternalNodesInPairs(NodeT<T> nodeA, NodeT<T> nodeB) {
        NodeT<T> nodeAInternal;
        NodeT<T> nodeBInternal;

        if (checkEqualsByNodeInternalObject) {
            nodeAInternal = getInternalNodeThatMatches(nodeA.getInstance());
            nodeBInternal = getInternalNodeThatMatches(nodeB.getInstance());
        } else {
            nodeAInternal = getInternalNodeThatMatches(nodeA);
            nodeBInternal = getInternalNodeThatMatches(nodeB);
        }
        NodeT<T>[] nodePair = new NodeT[]{nodeAInternal, nodeBInternal};
        return nodePair;
    }

    public boolean setUniDirectionalHedge(NodeT<T> nodeA, NodeT<T> nodeB) {
        NodeT<T>[] nodePair = getInternalNodesInPairs(nodeA, nodeB);
        if (isNodePairNull(nodePair)) {
            boolean result = nodePair[0].addNeighbor(nodePair[1]);
            return result;
        }
        return false;
    }

    public boolean setBiDirectionalHedge(NodeT<T> nodeA, NodeT<T> nodeB) {
        NodeT<T>[] nodePair = getInternalNodesInPairs(nodeA, nodeB);
        if (isNodePairNull(nodePair)) {
            boolean resultA = nodePair[0].addNeighbor(nodePair[1]);
            boolean resultB = nodePair[1].addNeighbor(nodePair[0]);
            boolean result = resultA && resultB;

            if(!result){
                if(resultA) nodePair[0].removeNeighbor(nodePair[1]);
                if(resultA) nodePair[1].removeNeighbor(nodePair[0]);
            }
            return result;
        }
        return false;
    }

    public boolean unSetUniDirectionalHedge(NodeT<T> nodeA, NodeT<T> nodeB) {
        NodeT<T>[] nodePair = getInternalNodesInPairs(nodeA, nodeB);
        if (isNodePairNull(nodePair)) {
            boolean result = nodePair[0].removeNeighbor(nodePair[1]);
            return result;
        }
        return false;
    }

    public boolean unSetBiDirectionalHedge(NodeT<T> nodeA, NodeT<T> nodeB) {
        NodeT<T>[] nodePair = getInternalNodesInPairs(nodeA, nodeB);
        if (isNodePairNull(nodePair)) {
            boolean resultA = nodePair[0].removeNeighbor(nodePair[1]);
            boolean resultB = nodePair[1].removeNeighbor(nodePair[0]);
            boolean result = resultA && resultB;

            if(!result){
                if(resultA) nodePair[0].addNeighbor(nodePair[1]);
                if(resultA) nodePair[1].addNeighbor(nodePair[0]);
            }
            return result;
        }
        return false;
    }

    public boolean doesUnidirectionalHedgeExists(NodeT<T> nodeA, NodeT<T> nodeB) {
        NodeT<T>[] nodePair = getInternalNodesInPairs(nodeA, nodeB);
        return nodePair[0].getNeighbors().contains(nodePair[1]);
    }

    public boolean doesBidirectionalHedgeExists(NodeT<T> nodeA, NodeT<T> nodeB) {
        NodeT<T>[] nodePair = getInternalNodesInPairs(nodeA, nodeB);
        return nodePair[0].getNeighbors().contains(nodePair[1]) && nodePair[1].getNeighbors().contains(nodePair[0]);
    }

    public boolean doesNodeExistInternally(NodeT<T> targetNode) {
        NodeT<T> internalNode = getInternalNodeThatMatches(targetNode);
        return nodes.stream().anyMatch(node -> node.equals(internalNode));
    }

    public NodeT<T> getInternalNodeThatMatches(T targetObject) {
        Optional<NodeT<T>> searchedNode = nodes.stream().filter(node -> node.getInstance().equals(targetObject)).findFirst();
        if (searchedNode.isPresent()) return searchedNode.get();
        else return null;
    }

    public NodeT<T> getInternalNodeThatMatches(NodeT<T> targetNode) {
        Optional<NodeT<T>> searchedNode = nodes.stream().filter(node -> node.equals(targetNode)).findFirst();
        if (searchedNode.isPresent()) return searchedNode.get();
        else return null;
    }

    public NodeT<T> getInternalNodeByPositionInCollection(int targetPositionNumber) {
        if (targetPositionNumber > nodes.size() || targetPositionNumber < 0) return null;
        else return nodes.get(targetPositionNumber);
    }

    public boolean searchIfAnyHedgeChainExistsBFS(NodeT<T> nodeA, NodeT<T> nodeB) {
        Queue<NodeT<T>> searchQueue = new LinkedList<NodeT<T>>();
        ArrayList<NodeT<T>> alreadyRevisedNodes = new ArrayList<NodeT<T>>();

        searchQueue.add(nodeA); //add first node to queue
        while (!searchQueue.isEmpty()) { //While the queue has nodes
            if (!searchQueue.peek().getNeighbors().isEmpty()) { //Check if actual node has hedges
                searchQueue.peek().getNeighbors()
                        .forEach(node -> { //Check for all node if has not been processed or added to the queue before
                            if (!(alreadyRevisedNodes.contains(node) || searchQueue.contains(node)))
                                searchQueue.add(node); //Check node for adding to queue
                        });
                if (searchQueue.contains(nodeB)) return true; //Check result
            }
            alreadyRevisedNodes.add(searchQueue.peek()); //add actual node to visited nodes
            searchQueue.poll(); //remove actual (first) node from queue
        }
        return false;
    }

    public Stack<NodeT<T>> searchAnyHedgeChainDFS(NodeT<T> nodeA, NodeT<T> nodeB) {
        Stack<NodeT<T>> searchStack = new Stack<NodeT<T>>();
        ArrayList<NodeT<T>> alreadyRevisedNodes = new ArrayList<NodeT<T>>();

        searchStack.add(nodeA); //add first node to queue
        while (searchStack.size() > 0) {
            alreadyRevisedNodes.add(searchStack.peek()); //add actual node to visited nodes
            Optional<NodeT<T>> result = searchStack.peek().getNeighbors().stream()  //Check for new nodes in the hedges of the actual node
                    .filter(city -> !(alreadyRevisedNodes.contains(city)))
                    .findFirst();
            if (result.isPresent()) {
                searchStack.push(result.get());
                if (searchStack.contains(nodeB)) { //Check result
                    return searchStack;
                }
            } else searchStack.pop(); //remove last node from stack if doesnt have new unprocessed edges
        }
        return null;
    }

    public Queue<NodeT<T>> searchForExclusiveNodesAtHedgeChainLevelBFS(NodeT<T> baseNode, int hedgeChainLevel) {
        Queue<NodeT<T>> actualSearchQueue = new LinkedList<>();
        Queue<NodeT<T>> nextSearchQueue = new LinkedList<>();
        ArrayList<NodeT<T>> alreadyRevisedNodes = new ArrayList<NodeT<T>>();

        if(checkEqualsByNodeInternalObject) baseNode = getInternalNodeThatMatches(baseNode.getInstance());
        else baseNode = getInternalNodeThatMatches(baseNode);

        if(baseNode == null) return null;

        actualSearchQueue.add(baseNode); //add base node for the first iteration (level 0)
        for (int i = 0; i <= hedgeChainLevel; i++) {
            //Check if actual search has hedges, the first iteration (level 0) has 1 element, if no node at actual level search has hedges search it will exit the next iteration
            if (!actualSearchQueue.isEmpty()) {
                while (!actualSearchQueue.isEmpty()) {
                    actualSearchQueue.peek().getNeighbors()
                            .forEach(node -> {
                                if (!(alreadyRevisedNodes.contains(node))) {
                                    nextSearchQueue.add(node); //Check node for adding to queue
                                    alreadyRevisedNodes.add(node); //add actual level node to the visited nodes list
                                }
                            });
                    actualSearchQueue.poll(); //remove actual (first) node from queue
                }
                actualSearchQueue = new LinkedList<NodeT<T>>(nextSearchQueue);
                nextSearchQueue.clear();
            } else return null;
        }
        return actualSearchQueue;
    }

    public ArrayList<NodeT<T>> getNodes() {
        return nodes;
    }

        /*
    private void printSearchTrue(Stack<Map.City> pathStack) {
        List<Map.City> cities = pathStack;
        System.out.print(Consola.Color.GREEN + "+ " + pathStack.get(0).getName());
        cities.remove(0);
        cities.forEach(city -> System.out.print(" => " + city));
        System.out.println();
    }

    private void printSearchFalse(Map.City city1, Map.City city2){
        System.out.println(Consola.Color.RED + "- " + city1 + " => " + city2 + Consola.Color.RESET);
    }*/

}

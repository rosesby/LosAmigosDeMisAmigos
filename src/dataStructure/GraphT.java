package dataStructure;

import domain.model.Person;

import java.util.*;

//todo implement equals, check internal or nested classes for architecture
public class GraphT<T>{
    private ArrayList<NodeT<T>> nodes;

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

    /**
     * Crea una relación nueva de la ciudad 1 a la ciudad 2
     *
     * @param strCity1 nombre de la ciudad1
     * @param strCity2 nombre de la ciudad2
     */
/*    public void createRelation(String strCity1, String strCity2) {
        Map.City city1 = getExistingCityByName(strCity1);
        Map.City city2 = getExistingCityByName(strCity2);
        city1.addLink(city2);
    }*/

    /**
     * Verifica la existencia de un elemento con el nombre provisto
     * Si el elemento no existe lo crea
     *
     * @param strCity nombre de la ciudad
     */
/*    public void createCityIfDoesNotExists(String strCity) {
        boolean check = checkIfTowerExistsByName(strCity);
        if (!check) createNode(strCity);
    }*/

    /**
     * Verifica la existencia de un elemento con el nombre provisto
     * Se ignora la capitalización del parametro
     *
     * @param
     * @return regresa si el elemento existe (boolean)
     */
    private boolean checkIfTowerExistsByName(NodeT<T> nodeT) {
        return nodes.stream().
                anyMatch(node -> node.equals(nodeT));
    }

    /**
     * Obtiene el objeto en memoria con el nombre provisto
     * Se ignora la capitalización del parametro
     *
     * @param strCityName Nombre de la ciudad
     * @return regresa el objeto ciudad con el nombre provisto
     */
/*    private Map.City getExistingCityByName(String strCityName) {
        return nodes.stream()
                .filter(city -> city.getName().equalsIgnoreCase(strCityName))
                .findFirst()
                .get();
    }*/

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

    public void addBidirectionalHedge(T person1, T person2) {
    }
}

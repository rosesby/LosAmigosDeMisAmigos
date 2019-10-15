/**
 * @author Palazuelos Alvarado Saul Alonso
 * @version 1.0
 */
package dataStructures;
import util.Consola;
import java.util.*;

/**
 * Clase de estructura de datos
 * Revisa y crea elementos
 * Crea relaciones direccionales entre elementos
 * Realiza busquedas entre relaciones de uno o mas elementos
 */
public class Map {
    private ArrayList<City> cities;

    public Map() {
        cities = new ArrayList<City>();
    }

    /**
     * Crea un nuevo elemento nodo con el nombre provisto
     *
     * @param name nombre de la ciudad
     */
    private void createCity(String name) {
        City city = new City(name);
        cities.add(city);
    }

    /**
     * Crea una relación nueva de la ciudad 1 a la ciudad 2
     *
     * @param strCity1 nombre de la ciudad1
     * @param strCity2 nombre de la ciudad2
     */
    public void createRelation(String strCity1, String strCity2) {
        City city1 = getExistingCityByName(strCity1);
        City city2 = getExistingCityByName(strCity2);
        city1.addLink(city2);
    }

    /**
     * Verifica la existencia de un elemento con el nombre provisto
     * Si el elemento no existe lo crea
     *
     * @param strCity nombre de la ciudad
     */
    public void createCityIfDoesNotExists(String strCity) {
        boolean check = checkIfTowerExistsByName(strCity);
        if (!check) createCity(strCity);
    }

    /**
     * Verifica la existencia de un elemento con el nombre provisto
     * Se ignora la capitalización del parametro
     *
     * @param strCityName
     * @return regresa si el elemento existe (boolean)
     */
    private boolean checkIfTowerExistsByName(String strCityName) {
        return cities.stream().
                anyMatch(city -> city.getName().equalsIgnoreCase(strCityName));
    }

    /**
     * Obtiene el objeto en memoria con el nombre provisto
     * Se ignora la capitalización del parametro
     *
     * @param strCityName Nombre de la ciudad
     * @return regresa el objeto ciudad con el nombre provisto
     */
    private City getExistingCityByName(String strCityName) {
        return cities.stream()
                .filter(city -> city.getName().equalsIgnoreCase(strCityName))
                .findFirst()
                .get();
    }

    /**
     * Ejecuta una busqueda de relacion entre elementos usando el algoritmo BFS para Grafos
     *
     * @param strCity1 nombre de la ciudad1
     * @param strCity2 nombre de la ciudad2
     * @return String con el resultado y color
     */
    public String searchWayToCityBFS(String strCity1, String strCity2) {
        City city1 = getExistingCityByName(strCity1);
        City city2 = getExistingCityByName(strCity2);

        Queue<City> searchQueue = new LinkedList<City>();
        ArrayList<City> visitedCities = new ArrayList<City>();

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
    }

    /**
     * Cierra la conexion entre la ciudad1 y ciudad1
     * @param strCity1 nombre de la ciudad1
     * @param strCity2 nombre de la ciudad2
     */
    public void closeConnectionBetween(String strCity1, String strCity2) {
        City city1 = getExistingCityByName(strCity1);
        City city2 = getExistingCityByName(strCity2);

        if ((city1 == null) || (city2 == null)) return;
        city1.linkedCities.remove(city2);
    }

    public void searchWayToCityDFS(String strCity1, String strCity2) {
        City city1 = getExistingCityByName(strCity1);
        City city2 = getExistingCityByName(strCity2);

        Stack<City> searchStack = new Stack<City>();
        ArrayList<City> visitedCities = new ArrayList<City>();

        searchStack.add(city1); //add first node to queue

        while (searchStack.size() > 0) {
            visitedCities.add(searchStack.peek()); //add actual node to visited nodes
            Optional<City> result = searchStack.peek().getLinkedCities().stream()  //Check for new nodes in the edges of the actual node
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
    }

    private void printSearchTrue(Stack<City> pathStack) {
            List<City> cities = pathStack;
            System.out.print(Consola.Color.GREEN + "+ " + pathStack.get(0).getName());
            cities.remove(0);
            cities.forEach(city -> System.out.print(" => " + city));
            System.out.println();
    }

    private void printSearchFalse(City city1, City city2){
        System.out.println(Consola.Color.RED + "- " + city1 + " => " + city2 + Consola.Color.RESET);
    }

    /**
     * Implementa una clase nodo con lista de adayacencia
     */
    public static class City {
        private ArrayList<City> linkedCities;
        private String name;

        /**
         * Constructor
         *
         * @param name nombre de la ciudad
         */
        public City(String name) {
            this.name = name;
            linkedCities = new ArrayList<City>();
        }

        /**
         * Añade un un nuevo elemento a la lista de adayacencia de este nodo
         *
         * @param city
         */
        public void addLink(City city) {
                linkedCities.add(city);
        }

        /**
         * @return Lista de adyacencia del objeto ciudad
         */
        public ArrayList<City> getLinkedCities() {
            return linkedCities;
        }

        /**
         * @return Nombre ciudad
         */
        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}

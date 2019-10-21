/**
 * @author Palazuelos Alvarado Saul Alonso
 * @version 1.0
 */

import dataStructure.GraphT;
import dataStructure.NodeT;
import domain.model.Person;
import domain.model.Sex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Palazuelos Alvarado Saul Alonso
 * @version 1.0
 */

/**
 * Clase principal
 * Al iniciar el programa, lee el archivo de entrada
 * valida cada linea de manera independiente con regex
 * separa los elementos de informacion de cada linea
 * crea objetos, relaciones y busquedas en base a la los elementos de la informacion
 */
public class Main {
    private static GraphT<Person> graph;
    private static Pattern inLinePersonDataPattern;

    /**
     * Funcion de entrada principal
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        graph = new GraphT<Person>();

        int lineCounter = 1;

        Person pta =  new Person("Victor Manuel" , "Batiz" , Sex.M ,  LocalDate.now());
        Person ptb =  new Person("Victor Manuel" , "Batiz" , Sex.M ,  LocalDate.now());

        System.out.println(pta.equals(ptb));

        FileReader fileReaderCatalog = new FileReader("src/data/inputCatalog");
        BufferedReader inCatalog = new BufferedReader(fileReaderCatalog);

        FileReader fileReaderQuestions = new FileReader("src/data/inputActions");
        BufferedReader inQuestions = new BufferedReader(fileReaderQuestions);

        String inLinePersonDataStringPattern = "[{][\\s]*[A-z]+[[\\s]*[A-z]+]*[\\s]*,[\\s]*[A-z]+[[\\s]*[A-z]+]*[\\s]*,[\\s]*[MFU][\\s]*,[\\s]*[0-9]{2}[/][0-9]{2}[/][0-9]{4}[\\s]*[}]";
        String inLinePersonDataStringPatternGrouped = "[{][\\s]*([A-z]+[[\\s]*[A-z]+]*)[\\s]*,[\\s]*([A-z]+[[\\s]*[A-z]+]*)[\\s]*,[\\s]*(M|F|U)[\\s]*,[\\s]*([0-9]{2}[/][0-9]{2}[/][0-9]{4})[\\s]*[}]";
        String positionStringPattern = "[0-9]+";

        String commandAtCenter = "amigo|eliminar|amigos";
        String commandAtStart = "amigos";

        String stringCommandPatternA = String.format("[\\s]*(%1$s|%2$s)[\\s]*(%3$s)[\\s]*(%1$s|%2$s)(.*)", inLinePersonDataStringPattern, positionStringPattern, commandAtCenter);
        String stringCommandPatternB = String.format("[\\s]*(%1$s)[\\s]*(%2$s|%3$s)[\\s]*(%3$s)(.*)", commandAtStart, inLinePersonDataStringPattern , positionStringPattern);

        Pattern commandPatternA = Pattern.compile(stringCommandPatternA, Pattern.CASE_INSENSITIVE);
        Pattern commandPatternB = Pattern.compile(stringCommandPatternB, Pattern.CASE_INSENSITIVE);

        Pattern[] commandPatterns = {commandPatternA, commandPatternB};

        inLinePersonDataPattern = Pattern.compile(inLinePersonDataStringPatternGrouped);

        String lineCatalog;
        while ((lineCatalog = inCatalog.readLine()) != null) {
            String dataline = lineCatalog;
            Matcher m = inLinePersonDataPattern.matcher(dataline);

            if(m.find()){
                Person person = personPatternStringParser(dataline);
                graph.addNode(person);
            }
        }

        graph.getNodes().forEach(node -> System.out.println(node));

        String line;
        while ((line = inQuestions.readLine()) != null) {
            String dataline = line;

            Optional<Pattern> patternMatch = Arrays.stream(commandPatterns)
                    .filter(pattern -> pattern.matcher(dataline).find())
                    .findFirst();

            if (patternMatch.isPresent()) {
                Pattern pattern = patternMatch.get();
                Matcher m = pattern.matcher(dataline);
                m.find();

                if(pattern.toString().equals(stringCommandPatternA)){
                    String pa = m.group(1);
                    String com = m.group(2);
                    String pb = m.group(3);

                    Person personA = personPatternStringParser(pa);
                    Person personB = personPatternStringParser(pb);

                    processAction(personA, personB, com);
                }
                else if(pattern.toString().equals(stringCommandPatternB)){
                    String p = m.group(2);
                    String l = m.group(3);

                    Person person = personPatternStringParser(p);

                    processAction(person,l);
                }
            }
        }
        graph.getNodes().forEach(node -> System.out.println(node));
    }

    private static Person personPatternStringParser(String personString){
        Matcher m = inLinePersonDataPattern.matcher(personString);
        if(m.find()){
            String firstName = m.group(1).trim().replaceAll("[\\s]+"," ");
            String lastName = m.group(2).trim().replaceAll("[\\s]+"," ");;
            Sex sex = Sex.valueOf(m.group(3).toUpperCase());
            LocalDate date = parseStringDate(m.group(4));
            return new Person(firstName, lastName, sex, date);
        }
        else{
            return graph.getInternalNodeByPositionInCollection(Integer.parseInt(personString)-1).getInstance();
        }
    }

    private static void processAction(Person personA, Person personB, String action){
        NodeT<Person> NodeA = new NodeT<Person>(personA);
        NodeT<Person> NodeB = new NodeT<Person>(personB);

        switch (action){
            case "amigo":
                graph.setBiDirectionalHedge(NodeA,NodeB);
                break;
            case "eliminar":
                graph.unSetBiDirectionalHedge(NodeA,NodeB);
                break;
            case "amigos":
                graph.doesBidirectionalHedgeExists(NodeA,NodeB);
                break;
        }
    }

    private static void processAction(Person person, String searchLevelString){
        NodeT<Person> node = new NodeT<Person>(person);
        int searchLevel = Integer.parseInt(searchLevelString);
        graph.executeSearchByLevel(node, searchLevel);
    }

    private static void showMactherGroups(Matcher m){
        System.out.println("--------");
        for (int i = 0; i <= m.groupCount(); i++) {
            System.out.println(i + " : " + m.group(i));
        }
    }

    private static LocalDate parseStringDate(String date){
        int day = Integer.parseInt(date.substring(0,2));
        int month = Integer.parseInt(date.substring(3,5));
        int year = Integer.parseInt(date.substring(6,10));
        return LocalDate.of(year, month, day);
    }
}

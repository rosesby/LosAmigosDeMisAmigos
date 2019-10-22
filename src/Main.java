/**
 * @author Palazuelos Alvarado Saul Alonso
 * @version 1.0
 */

import dataStructure.GraphT;
import dataStructure.NodeT;
import domain.model.Person;
import domain.model.Sex;

import javax.sound.midi.Soundbank;
import java.io.*;
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

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String regexFilename = "^[[\\w][\\d][\\s]-_]+$";
        Pattern patternRegexFilename = Pattern.compile(regexFilename);
        Matcher matcherFileName;

        String filename = "";
        do {
            System.out.println("Ingrese el nombre del archivo de texto de salida");
            filename = bufferedReader.readLine();
            matcherFileName = patternRegexFilename.matcher(filename);
        } while (!matcherFileName.find());
        filename = "out/" + filename;

        FileReader fileReaderCatalog = new FileReader("src/data/inputCatalog");
        BufferedReader inCatalog = new BufferedReader(fileReaderCatalog);

        FileReader fileReaderQuestions = new FileReader("src/data/inputActions");
        BufferedReader inQuestions = new BufferedReader(fileReaderQuestions);

        String inLinePersonDataStringPattern = "[A-z]+[[\\s]*[A-z]+]*[\\s]*,[\\s]*[A-z]+[[\\s]*[A-z]+]*[\\s]*,[\\s]*[MFU][\\s]*,[\\s]*[0-9]{2}[/][0-9]{2}[/][0-9]{4}";
        String inLinePersonDataStringPatternGrouped = "([A-z]+[[\\s]*[A-z]+]*)[\\s]*,[\\s]*([A-z]+[[\\s]*[A-z]+]*)[\\s]*,[\\s]*(M|F|U)[\\s]*,[\\s]*([0-9]{2}[/][0-9]{2}[/][0-9]{4})";
        String positionStringPattern = "[\\d]+";

        String commandAtCenter = "eliminar|amigos|amigo"; //el orden importa primero checar amigos y despues amigo, de lo contrario { amigos = (amigos + s) }
        String commandAtStart = "amigos";

        String stringCommandPatternA = String.format("^[\\s]*(%1$s|%2$s)[\\s]*(%3$s)[\\s]*(%1$s|%2$s)(.*)", inLinePersonDataStringPattern, positionStringPattern, commandAtCenter);
        String stringCommandPatternB = String.format("^[\\s]*(%1$s)[\\s]*(%2$s|%3$s)[\\s]*(%3$s)(.*)", commandAtStart, inLinePersonDataStringPattern, positionStringPattern);

        Pattern commandPatternA = Pattern.compile(stringCommandPatternA, Pattern.CASE_INSENSITIVE);
        Pattern commandPatternB = Pattern.compile(stringCommandPatternB, Pattern.CASE_INSENSITIVE);

        Pattern[] commandPatterns = {commandPatternA, commandPatternB};

        inLinePersonDataPattern = Pattern.compile(inLinePersonDataStringPatternGrouped);

        String lineCatalog;

        while ((lineCatalog = inCatalog.readLine()) != null) {
            String dataline = lineCatalog;
            Matcher m = inLinePersonDataPattern.matcher(dataline);

            if (m.find()) {
                Person person = personPatternStringParser(dataline);
                graph.addNode(person);
            }
        }

        PrintWriter writer = new PrintWriter(filename + ".txt", "UTF-8");
        int lineCounter = 1;
        while (true) {
            String dataLine = inQuestions.readLine();
            if (dataLine != null) {
                System.out.print(lineCounter + " : ");

                if (dataLine.isBlank() || dataLine.isEmpty()) System.out.print("Linea en blanco");
                else {
                    Optional<Pattern> patternMatch = Arrays.stream(commandPatterns)
                            .filter(pattern -> pattern.matcher(dataLine).find())
                            .findFirst();

                    if (patternMatch.isPresent()) {
                        Pattern pattern = patternMatch.get();
                        Matcher m = pattern.matcher(dataLine);
                        m.find();

                        if (pattern.toString().equals(stringCommandPatternA)) {
                            String pa = m.group(1);
                            String com = m.group(2);
                            String pb = m.group(3);

                            Person personA = personPatternStringParser(pa);
                            Person personB = personPatternStringParser(pb);
                            processAction(personA, personB, com);
                        } else if (pattern.toString().equals(stringCommandPatternB)) {
                            String p = m.group(2);
                            String l = m.group(3);

                            Person person = personPatternStringParser(p);
                            processAction(person, l);
                        }
                    } else {
                        System.out.print("Linea con texto inválido");
                        writer.println(dataLine);
                    }
                }
            }else break;
            System.out.println();
            lineCounter++;
        }
        writer.close();
        graph.getNodes().forEach(node -> System.out.println(node));
    }

    private static Person personPatternStringParser(String personString) {
        Matcher m = inLinePersonDataPattern.matcher(personString);
        if (m.find()) {
            String firstName = m.group(1).trim().replaceAll("[\\s]+", " ");
            String lastName = m.group(2).trim().replaceAll("[\\s]+", " ");
            Sex sex = Sex.valueOf(m.group(3).toUpperCase());
            LocalDate date = parseStringDate(m.group(4));
            return new Person(firstName, lastName, sex, date);
        } else {
            return graph.getInternalNodeByPositionInCollection(Integer.parseInt(personString) - 1).getInstance();
        }
    }

    private static void processAction(Person personA, Person personB, String action) {
        NodeT<Person> NodeA = new NodeT<Person>(personA);
        NodeT<Person> NodeB = new NodeT<Person>(personB);

        switch (action) {
            case "amigo": {
                boolean result = graph.setBiDirectionalHedge(NodeA, NodeB);
                String message = (result) ? "Se han creado amistad entre" : "Se falló en crear amistad entre" ;
                message += " " + personA + " & " + personB;
                System.out.print(message);
            }
            break;
            case "eliminar": {
                boolean result = graph.unSetBiDirectionalHedge(NodeA, NodeB);
                String message = (result) ? "Se han terminado amistad entre" : "Se falló en terminar amistad entre" ;
                message += " " + personA + " & " + personB;
                System.out.print(message);
            }
            break;
            case "amigos": {
                boolean result = graph.doesBidirectionalHedgeExists(NodeA, NodeB);
                String message = "Se confirma que ";
                message += (result) ? personA + " es amigo de " + personB : personA + " no es amigo de " + personB;
                System.out.print(message);
            }
            break;
        }
    }

    private static void processAction(Person person, String searchLevelString) {
        NodeT<Person> node = new NodeT<Person>(person);
        int searchLevel = Integer.parseInt(searchLevelString);
        ArrayList<NodeT<Person>> queue = graph.searchForExclusiveNodesAtHedgeChainLevelBFS(node, searchLevel);

        ArrayList<NodeT<Person>> levelFriends;
        if (queue == null) levelFriends = new ArrayList<>();
        else {
            if (queue.isEmpty()) levelFriends = new ArrayList<>();
            else levelFriends = new ArrayList<>(queue);
        }

        String levelFriendList = " ";
        for (NodeT<Person> nodep : levelFriends) {
            levelFriendList += nodep.getInstance() + " ";
        }

        System.out.print("Amigos Exclusivos de Nivel " + searchLevel + " de " + person + " : " + levelFriendList);
    }

    private static void showMactherGroups(Matcher m) {
        System.out.println("--------");
        for (int i = 0; i <= m.groupCount(); i++) {
            System.out.println(i + " : " + m.group(i));
        }
    }

    private static LocalDate parseStringDate(String date) {
        int day = Integer.parseInt(date.substring(0, 2));
        int month = Integer.parseInt(date.substring(3, 5));
        int year = Integer.parseInt(date.substring(6, 10));
        return LocalDate.of(year, month, day);
    }
}

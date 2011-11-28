import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Author: Bryce Nemer-Kaiser
 * Date: 11/28/11
 * Time: 09:00 AM
 * This class contains the main method of the application and kicks off all operations. This class prompts for a file
 * containing adjacency lists, translates the lists to an AdjacencyMatrix type object and determines the Hamiltonian
 * cycle of least weight.
 */
public class AdjacencyListReader {

    AdjacencyMatrix adjMatrix;

    /**
     * Default constructor
     */
    public AdjacencyListReader() {

    }

    /**
     * Displays prompt for user
     */
    public void promptForInputFile() {
        //prompt for file
        System.out.println("Enter an absolute path to a file containing adjacency lists:");

    }

    /**
     * Gets an absolute path for a file containing adjacency lists
     */
    public String getAdjacencyListFilePathFromUser() {

        //open scanner for reading
        Scanner sc = new Scanner(System.in);
        //get file path
        String filePath = sc.nextLine();
        //check if it exists
        if (!new File(filePath).exists()) {
            //exit, the file does not exist
            System.out.println("File " + filePath + " does not exist. Exiting...");
            System.exit(1);
        }

        return filePath;
    }


    /**
     * Determines if the given input signifies an end of a relationship
     * @param line -- the line of input to check
     * @return true if it is the end of a relationship, false otherwise
     */
    public boolean isEndOfRelationship(String line) {
        if (line.equals("E")) {
            //then end of relationship
            return true;
        } else {
            return false;
        }

    }

    /**
     * Breakdown a line of input and choose the correct action based on type of information
     * @param lineTokens -- tokenized line of input
     */
    public void determineLineInformation(String[] lineTokens) {
        switch (lineTokens.length) {
            case 1: //end or start of adjacency list

                //end, print off matrix
                if (isEndOfRelationship(lineTokens[0])) {
                    System.out.println("Printing Matrix");
                    System.out.println(adjMatrix.print());
                } else { //start, set up matrix

                    adjMatrix = new AdjacencyMatrix(Integer.parseInt(lineTokens[0]));
                }
                break;
            default: //line contains information about a relationship

                //map to matrix
                adjMatrix.mapRelationship(lineTokens[0], lineTokens[1], lineTokens[2]);
                break;

        }
    }


    /**
     * Read each line of the file, line by line
     * @param filePath -- an absolute path to a user supplied file
     */
    public void readAdjacencyListFile(String filePath) {

        try {
            //valid file. read file
            Scanner sc = new Scanner(new FileReader(filePath));
            while (sc.hasNext()) {

                String input = sc.nextLine();
                //tokenize line to determine relations
                String lineTokens[] = input.split(",");

                //determine the purpose of the line
                determineLineInformation(lineTokens);

            }
            //close file
            sc.close();
        } catch (FileNotFoundException fnf) {
            System.out.println("Exception caught trying to open file " + filePath);
        }

    }

    /**
     * Main method to kick off the program
     * @param args
     */
    public static void main(String[] args) {
        //display welcome information
        System.out.println("Adjacency list reader 1.0 \n Author: Bryce Nemer-Kaiser \n");
        System.out.println("Usage: provide an absolute path to a file containing a set of adjacency lists. \n" +
                "Functionality: \n \t (1) translate the adjacency list to a matrix \n" +
                "\t (2) creates the adjacency matrix for the weighted relation(s) \n" +
                "\t (3) determines and displays a Hamiltonian cycle of least weight \n");


        AdjacencyListReader adjReader = new AdjacencyListReader();
        adjReader.promptForInputFile();
        adjReader.readAdjacencyListFile(adjReader.getAdjacencyListFilePathFromUser());
        System.out.println("\n\nExecution complete. \nNow exiting");


    }

}




import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Created by IntelliJ IDEA.
 * User: bryce
 * Date: 11/27/11
 * Time: 10:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class AdjacencyListReader {

    AdjacencyMatrix adjMatrix;

    public AdjacencyListReader() {

    }

    public void promptForInputFile() {
        //prompt for file
        System.out.println("Enter an absolute path to a file containing adjacency lists:");

    }

    /**
     *
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
     * @param line
     * @return
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
     *
     */
    public void readNumberOfVertices() {

    }

    /**
     * @param lineTokens
     */
    public void determineLineInformation(String[] lineTokens) {
        switch (lineTokens.length) {
            case 1: //end or start of adjacency list
                //TODO: based on if start or end, do right action

                //end, print off matrix
                if (isEndOfRelationship(lineTokens[0])) {
                    adjMatrix.print();
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
     * @param filePath
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
        } catch (FileNotFoundException fnf) {
            System.out.println("Exception caught trying to open file " + filePath);
        }

    }


}




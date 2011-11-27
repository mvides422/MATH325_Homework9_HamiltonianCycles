                                                                     
                                                                     
                                                                     

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by IntelliJ IDEA.
 * Author: Bryce Nemer-Kaiser
 * Date: 10/26/11
 * Time: 10:06 PM
 * Description:
 * 1. reads a text file containing the information for one or two relations,
 * 2. creates the adjacency matrix for the relation(s),
 * 3. determines the total order for the collection of posets
 */

public class Posetter {

    /**
     * readFile prompts for the path to a file, calls printHashMap to display a visual representation
     * of the matrix and calls printTotalOrder to display the total order.
     * Returns nothing, but outputs results to standard out.
     */
    public static void readFile() {
        //declare variables
        int listSize = 0;

        HashMap matrixMap = new HashMap();
        String vertex = "";
        String relatedVertex = "";


        //prompt for file
        System.out.println("Enter an absolute path to a file containing adjacency lists:");

        //set up scanner
        try {
            Scanner sc = new Scanner(System.in);
            String filePath = sc.nextLine();
            sc.close();
            File f = new File(filePath);
            if (!f.exists()) {
                //file does not exist, inform user
                System.out.println("File " + f.toString() + " does not exist. Exiting");
                System.exit(1);

            } else {
                //valid file. read file
                sc = new Scanner(new FileReader(filePath));
                while (sc.hasNext()) {

                    String input = sc.nextLine();
                    //tokenize line to determine relations
                    String line[] = input.split(",");


                    //if E then end relation and start a new one
                    if (input.contains("E")) {
                        System.out.println(printHashMap(matrixMap));

                        //print total order
                        System.out.println(printTotalOrder(matrixMap));

                        matrixMap = new HashMap();
                        listSize = -1;

                    } else {
                        //iterate over elements
                        for (int i = 0; i < line.length; i++) {
                            //check if row has been created

                            if (matrixMap.containsKey(listSize)) {
                                //append to existing data
                                String value = matrixMap.get(listSize).toString();
                                matrixMap.put(listSize, value + ":" + line[i].trim());
                            } else {
                                //create new entry
                                matrixMap.put(listSize, line[i]);
                            }
                        }
                    }


                    //increment list size
                    listSize++;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Found error:" + e);
        }


    }

    /**
     *  isDone determines if every element is accounted for and present
     *  in the total order. It does so by check the list of dirty elements
     *  and determining if it equals the number of total elements.
     *
     * @param dirtyList -- an int array of dirty (printed) elements
     * @return true if all elements are present. False otherwise.
     */
    public static boolean isDone(int[] dirtyList) {
        int counter = 0;
        for (int i : dirtyList) {
            counter += i;
        }

        return counter == dirtyList.length;
    }

    /**
     * printTotalOrder translates a hashMap of posets and translates them
     * them into a total order
     * @param posetMap -- a hashmap to be translated
     * @return -- the total order in string format
     */

    public static String printTotalOrder(HashMap posetMap) {
        String output = "";
        //the list of dirty (sorted) elements
        int dirtyList[] = new int[posetMap.size()];

        while (!isDone(dirtyList)) {
            //for each key (column)
            for (int i = 0; i < posetMap.size(); i++) {

                //determine if element is a relative minimal
                boolean isMinimal = true;
                //if the key has not already been printed
                if (dirtyList[i] < 1) {
                    //check each row for that key
                    for (int r = 0; r < posetMap.size(); r++) {
                        //if the row has not already been printed
                        if (dirtyList[r] < 1) {
                            String row[] = posetMap.get(r).toString().split(":");

                            for (String s : row) {
                                //if present
                                 if (Integer.toString(i).trim().equals(s.trim())) {

                                    //then it is not a relative minimal element
                                    isMinimal = false;


                                    //stop search for this element
                                    break;

                                }
                            }
                        }


                    }


                    //if not present, print it
                    if (isMinimal) {

                        //add to dirty list
                        dirtyList[i] = 1;
                        //append to output string
                        output+=":" + Integer.toString(i);
                    }

                }
            }
        }


        return output;

    }


    /**
     * printHashMap creates a visual representation of the relationship through a matrix
     *
     * @param map -- a hashmap defining the relationship
     * @return -- return a String matrix ready for print
     */
    public static String printHashMap(HashMap map) {

        int mapSize = map.size();
        String outputString = "";

        //iterate over each row
        for (int r = 0; r < mapSize; r++) {
            outputString += "[";
            //iterate over each column
            for (int c = 0; c < mapSize; c++) {

                //if a relation exists between row and column, print 1
                if (map.get(r).toString().contains(Integer.toString(c))) {
                    outputString += " 1";


                    //otherwise, print 0
                } else {
                    outputString += " 0";
                }

                //formatting
                if (c == mapSize - 1 && r != mapSize - 1) {
                    outputString += "]\n";
                } else if (c == mapSize - 1 && r == mapSize - 1) {
                    outputString += "]";
                }


            }


        }


        return outputString;
    }


    public static void main(String[] args) {
        //display welcome information
        System.out.println("Adjacency list reader 1.0 \n Author: Bryce Nemer-Kaiser \n");
        System.out.println("Usage: provides an absolute path to a file containing a set of adjacency lists. \n" +
                "Functionality: \n \t (1) translate the adjacency list to a matrix \n" +
                "\t (2) creates the adjacency matrix for the relation(s) \n" +
                "\t (3) determines and displays total order \n");


        readFile();

        System.out.println("\n\nExecution complete. \nNow exiting");


    }
}

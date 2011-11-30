import java.util.HashMap;

/**
 * Author: Bryce Nemer-Kaiser
 * Date: 11/28/11
 * Time: 09:00 AM
 * Data structure to represent an adjacency matrix in mathematics.
 */
public class AdjacencyMatrix {

    /* The main data structure to hold relationships     */
    private HashMap adjacencyMap;

    /**
     * Constructor for the adjacency matrix
     * @param numberOfVertices -- how many vertices contained in a adjacency list
     */
    public AdjacencyMatrix(int numberOfVertices) {
        //populate the matrix with empty data
        setUpMatrix(numberOfVertices);
    }

    /**
     * Given to vertices, determine the relationship weight.
     * @param firstVertex -- the starting vertex
     * @param secondVertex -- the next vertex
     * @return the weight of the relationship. 0 if there is no path
     */
    public String getRelationshipWeight(String firstVertex, String secondVertex){

        String weight="0";
        //get relationships involving first vertex
        String[] firstVertexRelations = getValue(firstVertex).split(":");
        //find relationship with second vertex
        for(String s : firstVertexRelations){
            //split into vertex and weight
            String[] relationshipTokens= s.split(",");
            //check for first vertex
            if(relationshipTokens[0].contains(secondVertex)){
                       weight=relationshipTokens[1];
                break;
            }
        }
        return weight;

    }
    /**
     * Populate the matrix with vertices, but no relationships
     * @param numberOfVertices -- the number of vertices in the adjacency list
     */
    public void setUpMatrix(int numberOfVertices){
       adjacencyMap= new HashMap();
        //for every vertex, fill its location
        for(int i=0;i<numberOfVertices;i++){
            adjacencyMap.put(Integer.toString(i),"");
        }
    }

    /**
     * Get the set of relationships for a provided vertex
     * @param key -- the vertex to look at
     * @return the set of relationships for that vertex
     */
    public String getValue(String key){
        return adjacencyMap.get(key).toString();
    }

    /**
     * Get the size of the map. Should always be the same as the number of vertices.
     * @return the size of the map
     */
    public int getSize(){
        return adjacencyMap.size();

    }

    /**
     * Map a relationship, including weight, to the matrix.
     * @param firstVertex
     * @param secondVertex
     * @param weight
     */
    public void mapRelationship(String firstVertex, String secondVertex, String weight){
        //map the relationship as defined
        adjacencyMap.put(firstVertex,getValue(firstVertex)+secondVertex+","+weight+":");
        //list is non directed, so map the reverse path too
        adjacencyMap.put(secondVertex,getValue(secondVertex)+firstVertex+","+weight+":");

    }


    /**
     * Display the adjacency matrix
     * @return
     */
    public String print(){

        int mapSize =getSize() ;
        String outputString = "";

        //iterate over each row
        for (int r = 0; r < mapSize; r++) {
            outputString += "[";
            //iterate over each column
            for (int c = 0; c < mapSize; c++) {

                //format the string for display purposes
                outputString+=" "+String.format("%2s",getRelationshipWeight(Integer.toString(r),Integer.toString(c)));

                //formatting
                if (c == mapSize - 1 && r != mapSize - 1) {
                    outputString += "]\n";
                } else if (c == mapSize - 1 && r == mapSize - 1) {
                    outputString += "]";
                }


            }


        }

        determineShortestPath();

        return outputString;
    }


    /**
     * Finds a cycle of least weight that travels every node.  Uses every permutation of the vertex set to
     * find the cycle through brute force.
     */
    public void determineShortestPath(){
        int leastWeight=999999;
        int[] bestPath=null;
        int pathWeight=0;
        //create vertex array
        int[] vertexArray = new int[getSize()];
        for(int i=0;i<getSize();i++){
            vertexArray[i]=i;
        }
        //permute the vertex set
        Permutations perm = new Permutations(getSize());
        perm.permute(vertexArray,0,vertexArray.length);
        System.out.println("Number of permutations: "+perm.getNumberOfPermutations());

        //for every permutation, determine path weight
        for(int i=0;i<perm.getNumberOfPermutations();i++){
            //get path
            int[] intPath= perm.getPermutation(i);


            //for each vertex in array, check weight to next neighbor
            pathWeight=0;
            for(int v=0;v<getSize();v++){

                //get vertex
                String firstVertex=Integer.toString(intPath[v]);
                String nextVertex;

                //get next vertex
                if(v==getSize()-1){
                    //next vertex is first vertex
                     nextVertex=Integer.toString(intPath[0]);
                }else{
                    //next vertex is next indexed position
                     nextVertex=Integer.toString(intPath[v+1]);
                }

                //get weight
                int relationshipWeight=Integer.parseInt(getRelationshipWeight(firstVertex,nextVertex));

                //if not related, end search. this path does not exist
                if(relationshipWeight==0){
                    //abandon search!
                    relationshipWeight=99999;
                    pathWeight+=relationshipWeight;
                }else{  //add the relationship weight to the overall path weight
                    pathWeight+=relationshipWeight;
                }

            }
                //check if less than existing paths
                if(pathWeight<leastWeight){
                    //update least weight
                    leastWeight=pathWeight;
                    bestPath=intPath;
                }

        }

        //print least weighted path
        System.out.print("Best path: ");

        //print each vertex
        for(int v=0;v<bestPath.length;v++)
        {
            System.out.print(bestPath[v]+ " ");
        }

        //print the weight of the path
        System.out.println("Weight: "+leastWeight);


    }
}

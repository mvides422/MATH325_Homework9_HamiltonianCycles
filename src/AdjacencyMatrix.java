import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: bryce
 * Date: 11/27/11
 * Time: 11:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class AdjacencyMatrix {

    HashMap adjacencyMap;

    /**
     *
     * @param numberOfVertices
     */
    public AdjacencyMatrix(int numberOfVertices) {
        setUpMatrix(numberOfVertices);
    }


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
     *
     * @param numberOfVertices
     */
    public void setUpMatrix(int numberOfVertices){
       adjacencyMap= new HashMap();

        for(int i=0;i<numberOfVertices;i++){
            adjacencyMap.put(Integer.toString(i),"");
        }
    }


    /**
     *
     * @param key
     * @return
     */
    public String getValue(String key){
        return adjacencyMap.get(key).toString();
    }

    /**
     *
     * @return
     */
    public int getSize(){
        return adjacencyMap.size();

    }

    /**
     *
     * @param firstVertex
     * @param secondVertex
     * @param weight
     */
    public void mapRelationship(String firstVertex, String secondVertex, String weight){

        adjacencyMap.put(firstVertex,getValue(firstVertex)+secondVertex+","+weight+":");

        adjacencyMap.put(secondVertex,getValue(secondVertex)+firstVertex+","+weight+":");

    }

    /**
     *
     * @param vertex
     * @return
     */
    public String findClosestNeighbor(String vertex){

        int leastWeight=99;
        String closestNeighbor="-1";
        //get relationships involving vertex
        String[] vertexRelations = getValue(vertex).split(":");
        //find relationship with second vertex
        for(String s : vertexRelations){
            //split into vertex and weight
            String[] relationshipTokens= s.split(",");
            //check for first vertex
            if(Integer.parseInt(relationshipTokens[1])<=leastWeight){
                       leastWeight=Integer.parseInt(relationshipTokens[1]);
                       closestNeighbor=relationshipTokens[0];
                break;
            }
        }
        return closestNeighbor;
    }

    public void DEBUGprintClosestNeighbors(){
        for(int i=0;i<getSize();i++){
            System.out.println(i + "'s closet neighbor " + findClosestNeighbor(Integer.toString(i)));
        }

    }

    /**
     *
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




    public void determineShortestPath(){
        int leastWeight=999999;
        int[] bestPath=null;
        int pathWeight=0;
        //create vertex array
        int[] vertexArray = new int[getSize()];
        for(int i=0;i<getSize();i++){
            vertexArray[i]=i;
        }

        Permutations perm = new Permutations(getSize());
        perm.permute(vertexArray,0,vertexArray.length);
        System.out.println("num of perms: "+perm.getNumberOfPermutations());

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
                }else{
                    pathWeight+=relationshipWeight;

                    /*
                    //check if less than existing paths
                    if(relationshipWeight<leastWeight){
                        //update least weight
                        leastWeight=relationshipWeight;
                        bestPath=intPath;
                    }
                    */
                }

            }
                //check if less than existing paths
                if(pathWeight<leastWeight){
                    //update least weight
                    leastWeight=pathWeight;
                    bestPath=intPath;
                }

        }

        //print least path
        System.out.print("Best path: ");

        //print each vertex
        for(int v=0;v<bestPath.length;v++)
        {
            System.out.print(bestPath[v]+ " ");
        }


        System.out.println("Weight: "+leastWeight);


    }
}

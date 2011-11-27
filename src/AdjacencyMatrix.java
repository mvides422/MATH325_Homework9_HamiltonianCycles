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


        return outputString;
    }
}

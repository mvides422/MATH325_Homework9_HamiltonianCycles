import java.util.HashMap;

/**
 * Author: Bryce Nemer-Kaiser
 * Date: 11/28/2011
 * Time: 09:00 AM
 * Given a size, this class will create a set of integers and permute them, returning a set of all possible
 * permutations.
 */
public class Permutations {
    /* The main data structure */
    private int[][] permMap;
    /* Used to keep current index */
    private int mapSize;

    /**
     * Constructor that creates a permutation object based on a given size.
     * @param size -- the number of integers to permute
     */
    public Permutations(int size) {
        //create the data structure
        permMap = new int[factorial(size)][size];
        //set the index to zero
        mapSize=0;
    }

    /**
     * Determine the number of possible permutations given the size provided
     * @param n -- number of elements
     * @return -- number of permutations
     */
    public int factorial(int n){
        if(n==0){
            return 1;
        }else{
            return  n*(factorial(n-1));
        }

    }

    /**
     * Get a desired permutation
     * @param key -- the index of the permutation array to return
     * @return -- an array of permutated ints
     */
    public int[] getPermutation(int key){

        return permMap[key];
    }

    /**
     * Return the total number of permutations
     * @return -- the total number of permutations
     */
    public int getNumberOfPermutations(){
        return permMap.length;
    }

    /**
     * Permute the set of integers
     * @param v -- an integer set
     * @param start -- the first position to swap
     * @param n -- the size of hte set
     */
    public void permute(int v[], int start, int n) {


        if (start == (n - 1)) {
            //if its the end of the sequence generated then add to perm array


            permMap[mapSize]=v.clone();

            mapSize++;
            //print(v,n);
        } else {
            for (int i = start; i < n; i++) {
                //swap the start element with the ith element to get n first sequences
                int temp = v[start];
                v[start] = v[i];
                v[i] = temp;
                permute(v, start + 1, n);
                //of the n the first is kept constant the same is applied for the rest sequence
                v[i] = v[start];
                v[start] = temp;
            }
        }

    }

}
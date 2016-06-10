package strategictoastinsertion;

import java.util.ArrayList;

/**
 *
 * Creators: Matthew Godfrey, Seth Thomson, Jonah Monaghan
 Created: May 18th, 2016
 Purpose: the class that handles score sorting
 */
public class AscendingSorter{
   
    public AscendingSorter(){
        
    }
    
    
    
    static ArrayList <Integer> array;
    /**
     * 
     * @param arr an array to be sorted
     */
    public static void quickSort(ArrayList arr){
        array = new ArrayList(arr);
        int lowIndex = 0;
        int highIndex = array.size() - 1;
        quickSortAction(lowIndex, highIndex);
    }
    
    
   /**
    * 
    * @param lowIndex the lowest index
    * @param highIndex the highest index
    */
   private static void quickSortAction(int lowIndex, int highIndex){
        int l = lowIndex;
        int h = highIndex;
        int pivot = array.get(lowIndex+(highIndex-lowIndex)/2);
        // Divide into two arrays
        while (l <= h) {
            while (array.get(l) > pivot) {
                l++;
            }
            while (array.get(h) < pivot) {
                h--;
            }
            if (l <= h) {
                exchangeNumbers(l, h);
                l++;
                h--;
            }
        }
        if (lowIndex < h){
            quickSortAction(lowIndex, h);
        }
        if (l < highIndex){
            quickSortAction(l, highIndex);
        }
   }
   
   /**
    * 
    * @param l the first number
    * @param h the second number
    */
   private static void exchangeNumbers(int l, int h){
       int tempNum = array.get(l);
       array.set(l, array.get(h));
       array.set(h, tempNum);
       String tempStr = Credits.names.get(l);
       Credits.names.set(l, Credits.names.get(h));
       Credits.names.set(h, tempStr);
   }
   
   public static ArrayList getArray(){
       return array;
   }
}
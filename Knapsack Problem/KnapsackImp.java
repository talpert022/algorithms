import java.util.*;

/**
 * A class for solving discrete and fractional knapsack problem.
 * Created for CITS3001 at the University of Western Australia.
 * @author Thomas Alpert. 
 **/
public class KnapsackImp implements Knapsack {
	
	/**
	 * Implements the fractional knapsack problem
	 * @param weights the array of weights of each type of product available.
	 * @param values the array of values of each type of product available.
	 * @param capacity the size of the knapsack
	 * @return the greatest int less than or equal to the maximum possible value of the knapsack.
	**/
	public int fractionalKnapsack(int[] weights, int[] values, int capacity){
	
		Item[] ItemList = new Item[weights.length];
		
		for(int i = 0; i<ItemList.length; i++)
		{
			ItemList[i] = new Item(weights[i], values[i]);
		}
		
		//sorts Items by their value divided by weight
		Arrays.sort(ItemList, new Comparator<Item>(){
		@Override
        public int compare(Item i, Item o) {
            if(i.relevance == o.relevance){
                return 0;
            }
            else if(i.relevance < o.relevance){
                return -1;
            }
            else{
                return 1;
            }
        }
			
		});
		//gets max value of different Items in a knapsack with a given capacity
		return getMaxVal(ItemList, capacity);		
	}
		
	/**
	 * Implements the 0-1 knapsack problem.
	 * Uses a dynamic programming solution
	 * @param weights the array of weights of each type of product available.
	 * @param values the array of values of each type of product available.
	 * @param capacity the size of the knapsack
	 * @return the maximum possible value of the knapsack.
	**/
    public int discreteKnapsack(int[] weights, int[] values, int capacity){
    	
    	int length = weights.length;
    	int[][] valArr = new int[length][capacity+1];
    	
    	//fills dynamic array for 0-1 knapsack
    	valArr = fillArr(valArr, weights, values, capacity);
    	
    	return valArr[length-1][capacity];
    }
    
    //helper method for fractionalKnapsack
    public int getMaxVal(Item[] ItemList, int c){
		
		double maxVal = 0.0;
		int i = ItemList.length-1;
		while(c > 0 && i >= 0)
		{
			if(ItemList[i].weight <= c)
			{
				maxVal = maxVal + ItemList[i].value;
				c = c - ItemList[i].weight;
				i--;
			}
			if(ItemList[i].weight > c) 
			{
				maxVal = maxVal + ((double)ItemList[i].value * ((double)c/(double)ItemList[i].weight));
				c = 0;
				i--;
			}
		}
		return (int)maxVal;
	}
	
	//helper method for discreteKnapsack
    public int[][] fillArr(int[][] valArr, int[] weight, int[] values, int capacity){
    	
    	int length = valArr.length;
    	int i,j;
    	
    	for (int x = 0; x<length; x++){
    		valArr[x][0] = 0;
    	}
    	
    	for(i = 0; i<length; i++)
    	{
    		for(j = 1; j<= capacity; j++)
    		{
    			if (weight[i] > j && i < 1)
    				valArr[i][j] = 0;
    			else if (weight[i] <= j && i < 1)
    				valArr[i][j] = values[i];
    			
    			if (weight[i] > j && i > 0)
    				valArr[i][j] = valArr[i-1][j];
    			else if (weight[i] <= j && i > 0)
    				valArr[i][j] = ((values[i] + valArr[i-1][j-weight[i]]) > valArr[i-1][j]) ? values[i] + valArr[i-1][j-weight[i]]:valArr[i-1][j];
    		}
    	}
    	return valArr;
    }	
}

/*
 * Represents an Item for fractional Knapsack problem
 */

class Item  {
	 
	public int weight, value;
	public double relevance;
	
	public Item(int weight1, int value1){
		this.weight = weight1;
		this.value = value1;
		this.relevance = (double)value1/(double)(weight1);
		}
	}
			
	  
  	  	  
  	  
  	  
  	  
  	  
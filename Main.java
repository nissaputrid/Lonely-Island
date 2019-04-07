import java.io.*;
import java.util.Scanner;
import java.util.LinkedList; 

public class Main {
	static int island;
	static int bridge;
	static int start;
	static int[] solusi = new int[200001];
	static LinkedList<Integer> adjListArray[] = new LinkedList[2000001];
	static LinkedList<Integer> jalur = new LinkedList(); 
	static int[] final_arr = new int[200001];
	static int[] printed = new int[200001];
  public static void BacaFile(String namaFile){
    try{
      Scanner inFile = new Scanner(new FileInputStream(namaFile));
      int sum_input = 1;
      int line = 1;
      int number;
      int i = 0;
      int j;
      
      while(inFile.hasNext()){
        String oneline = inFile.next();
        number = Integer.parseInt(oneline);
		
		if (sum_input == 1 && line ==1){
			island = number;  
			System.out.println("Island: "+island);
		}else if (sum_input == 2 && line ==1){
			bridge = number;
			System.out.println("Bridge: "+bridge);
		}else if (sum_input == 3 && line ==1){
			start = number;
			System.out.println("Start: "+start);
			line++;
			sum_input = 0;
			
			for(i=1;i<=island;i++){
				adjListArray[i] = new LinkedList<>();
				
			}
		}else if (line>1){
			if (sum_input == 1){
				i = number;
				sum_input++;
			}else{
				j = number;
				sum_input = 0;
				adjListArray[i].add(j); 
			}
		}
		sum_input++;
      }
      inFile.close(); 
    }catch(FileNotFoundException fnfe){
      System.out.println("File not found!!");
    } 
  }
  
  public static int search(int i, int prev_island){
	  int j;
	  j=0;
	  int element = adjListArray[i].getFirst();
	  int size = adjListArray[i].size();
	 
	  while (solusi[element] == 1 && j!=size){
		j++;
		
		if (j<size)
			element = adjListArray[i].get(j);
	  }
	  
	  if (j+1 > size){
		  if (!count(prev_island)){
			return prev_island;
		}else
		return i;
	  }else{
		element = adjListArray[i].get(j);
		return element;
		}
  }
  
  public static boolean count(int prev_island){
	  int counter = 0;
	  int size = adjListArray[prev_island].size();

		for(int k =0;k<size;k++){
			if (solusi[adjListArray[prev_island].get(k)] == 1)
				counter++;
		}
		
		return counter==size;
  
  }
 
  public static void lonely_island(int current_island, int prev_island){
		int next_island;
		int final_island;
		int i,j;
		int size;
		
		next_island = search(current_island, prev_island);
		
		while (next_island == current_island && jalur.size() != 0){
			current_island = jalur.getLast();
			next_island = search(current_island, current_island);
			
			if (next_island == current_island){
				int dum = jalur.removeLast();
				int k = 0;
				int dum_size = adjListArray[dum].size();
				boolean found = false;
				while (k<dum_size && !found){
					int el= adjListArray[dum].get(k);
					if (final_arr[el] == 1)
						found = true;
					else{
					k++;
					}
				}
				if (printed[dum] == adjListArray[dum].size()){
					k = 0;
					while (k<dum_size){
						solusi[adjListArray[dum].get(k)] = 0;
						k++;
					}
				}
			}
		}
		size = adjListArray[next_island].size();

		if (jalur.size() != 0){
			jalur.addLast(next_island);
			printed[jalur.getLast()]++;
		}
		
		if (size == 0 && count(next_island)){
			final_island = next_island;
			final_arr[final_island] = 1;
			int dummy = jalur.removeLast();
			solusi[dummy] =1;
            printed[jalur.getLast()]++;
            
			for(Integer pCrawl: jalur){ 
					System.out.print(pCrawl+ " -> "); 
			}
			System.out.print(dummy); 
			System.out.println("\n");
            
			if (jalur.size() != 0){
				
				current_island = jalur.getLast();
				lonely_island(current_island, current_island);
			}
		}else{
			if (jalur.size() != 0){
			solusi[next_island] = 1;
			lonely_island(next_island, current_island);
			}
		}
	}
  
  public static void main(String[] args){
	long startTime = System.currentTimeMillis();
	BacaFile("input.txt");
	int i,j;
	
	solusi[start] = 1;
	jalur.push(start);
	System.out.println("Lintasan: ");
	lonely_island(start, start);
	boolean ada = false;
	
	System.out.println("Lonely Island:");
	for (i=1;i<=island;i++){
		if (final_arr[i] == 1){
			System.out.println(i);
			ada = true;
		}
	}
	if (!ada){
		System.out.println("Tidak ada.");
	}
	long endTime   = System.currentTimeMillis();
	long totalTime = endTime - startTime;
	System.out.println(totalTime+ " milisekon");
	}
}

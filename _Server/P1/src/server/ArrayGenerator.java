package server;

import java.util.Random;

public class ArrayGenerator {
	private Random rnd = new Random();
	
	public ArrayGenerator() {
		
	}
	
	public int[][] generateMultipleSets(int elements, int elements2, int fromValue, int toValue) {
		int[][] res = new int[elements][elements2];
		for(int i = 0; i < elements; i++) {
			res[i] = generateSet(elements2, fromValue, toValue); 
		}
		return res;
	}
	
	public int[] generateSet(int elements, int fromValue, int toValue) {
		int[] res = new int[elements];
		for(int i = 0; i < elements; i++) {
			res[i] = (rnd.nextInt(toValue-fromValue+1)+fromValue);
		}
		return res;
	}
}

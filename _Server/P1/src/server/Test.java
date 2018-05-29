package server;

import java.util.Random;

import javax.swing.JOptionPane;

public class Test {
	private Random rnd = new Random();
	

	
	public Test() {
		
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
	
	
//	public static void main(String[]args) {
//		Test test = new Test();
//		int [][] res = test.generateMultipleSets(120, 16, 0, 1);
//		for(int i = 0; i < res.length; i++) {
//			for(int j = 0; j < 16; j++) {
//				System.out.println((res[i][j]));
//			}
//		}
//	}
}

import java.util.Random;

import javax.swing.JOptionPane;

public class ArrayGenarator {
	private String output = "";
	private Random rnd = new Random();
	
	private static final String MENU ="1: 1D array\n"
									+ "2: 2D array\n"
									+ "0: Exit\n";
	int menuChoice;
	
	public ArrayGenarator() {
		while(menuChoice != 0 || menuChoice != 1 || menuChoice != 2) {
			menuChoice = Integer.parseInt(JOptionPane.showInputDialog(MENU));
			if(menuChoice == 1) {
				int elem = Integer.parseInt(JOptionPane.showInputDialog("how many elements?"));
				int fromValue = Integer.parseInt(JOptionPane.showInputDialog("from what value?"));
				int toValue = Integer.parseInt(JOptionPane.showInputDialog("to what value?"));
				System.out.println(generateSet(elem, fromValue, toValue));
			}else if(menuChoice == 2) {
				int elem = Integer.parseInt(JOptionPane.showInputDialog("how many elements?"));
				int elem2 = Integer.parseInt(JOptionPane.showInputDialog("how many elements in each set?"));
				int fromValue = Integer.parseInt(JOptionPane.showInputDialog("from what value?"));
				int toValue = Integer.parseInt(JOptionPane.showInputDialog("to what value?"));
				System.out.println(generateMultipleSets(elem, elem2, fromValue, toValue));
			}else if(menuChoice == 0) {
				System.exit(0);
			}
		}
	}
	
	public String generateMultipleSets(int elements, int elements2, int fromValue, int toValue) {
		String res = "{";
		for(int i = 0; i < elements; i++) {
			res += "\n\t" + generateSet(elements2, fromValue, toValue) + ", "; 
		}
		res = res.substring(0, res.length()-2) + "\n}";
		return res;
	}
	
	public String generateSet(int elements, int fromValue, int toValue) {
		String res = "{";
		for(int i = 0; i < elements; i++) {
			res += (rnd.nextInt(toValue-fromValue+1)+fromValue) + ", ";
		}
		res = res.substring(0, res.length()-2) + "}";
		return res;
	}
	
	public static void main(String[] args) {
		ArrayGenarator gen = new ArrayGenarator();
	}
}

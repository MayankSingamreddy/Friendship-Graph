package friends;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class driver {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(System.in);
		Graph g;
		System.out.print("\nEnter the file: ");
		String file = sc.nextLine();
		
		if (file.length() != 0) {
			g = new Graph(new Scanner(new File("src/friends/" + file)));
		} else {
			sc.close();
			throw new FileNotFoundException("file not found");
		}

		System.out.println(g.map);
		
		while (true) {
			System.out.println("Method:");
			
			System.out.println("[1] shortestChain");
			System.out.println("[2] cliques");
			System.out.println("[3] connectors");
			
			String method = sc.nextLine();
			
			ArrayList<String> result = new ArrayList<>();
			ArrayList<ArrayList<String>> resultschool = new ArrayList<>();
			
			if (method.equals("1")) {
				System.out.println("1st friend:");
				String start = sc.nextLine();
				
				System.out.println("2nd friend:");
				String end = sc.nextLine();
				
				result = Friends.shortestChain(g, start, end);
			} else if (method.equals("2")) {
				System.out.println("school name:");
				String school = sc.nextLine();
				
				resultschool = Friends.cliques(g, school);
			} else if (method.equals("3")) {
				result = Friends.connectors(g);
			} else {
				break;
			}
			
			if (result == null || result.isEmpty()) {
				if (resultschool == null || resultschool.isEmpty()) {
					System.out.println("not found");
				} else {
					System.out.println("Result (School):");
					for (int i = 0; i < resultschool.size(); i++) {
						System.out.println("#" + (i + 1) + " list:");
						ArrayList <String> temp = resultschool.get(i);
						
						for (int j = 0; j < temp.size(); j++) {
							System.out.print(temp.get(j) + " ");
						}
						
						System.out.println("\n");
					}
					
				}
			} else {
				System.out.println("Result: ");
				
				for (int i = 0; i < result.size(); i++) {
					System.out.print(result.get(i) + " ");
				}
				
				System.out.println();
			}
			
		}
		
		sc.close();
	}
}

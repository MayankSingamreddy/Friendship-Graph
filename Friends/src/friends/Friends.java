package friends;

import java.util.ArrayList;

import structures.Queue;
import structures.Stack;

public class Friends {

	/**
	 * Finds the shortest chain of people from p1 to p2.
	 * Chain is returned as a sequence of names starting with p1,
	 * and ending with p2. Each pair (n1,n2) of consecutive names in
	 * the returned chain is an edge in the graph.
	 *
	 * @param g Graph for which shortest chain is to be found.
	 * @param p1 Person with whom the chain originates
	 * @param p2 Person at whom the chain terminates
	 * @return The shortest chain from p1 to p2. Null if there is no
	 *         path from p1 to p2
	 */

	/*
	public static ArrayList<String> shortestChain(Graph g, String p1, String p2) {


		ArrayList<String> ans = new ArrayList<>();

		p1 = p1.toLowerCase();
		p2 = p2.toLowerCase();

		boolean exist1 = false;
		boolean exist2 = false;

		for (Person y : g.members) {
			if (y.name.equals(p1))
				exist1 = true;
			if (y.name.equals(p2))
				exist2 = true;
		}
		if (!exist1 || !exist2) {
			System.out.println("Correct name and re-enter");
			return null;
		}

		//what things have been visited so far
		int[] visited = new int[g.members.length];

		//storage of the path to take
		ArrayList<String> names = new ArrayList<>();

		for(int i =0;i<g.members.length;i++){
			visited[i] = -1;
		}

		int key = g.map.get(p2);
		int search = g.map.get(p1);

		Person person;
		Friend ex;

		Queue<Person> holder = new Queue<>();

		visited[search] = 0;
		holder.enqueue(g.members[search]);

		while(!holder.isEmpty()){
			person = holder.dequeue();
			ex = person.first;

			while(ex!=null){
				if(visited[g.map.get(g.members[ex.fnum].name)] == -1){
					visited[ex.fnum] = visited[g.map.get(person.name)]+1;
					ans.add(person.name);
					holder.enqueue(g.members[g.map.get(g.members[ex.fnum].name)]);
				}
				ex = ex.next;
			}
		}

		if(visited[g.map.get(p2)]==-1||p2.equals(p1)){
			return null;
		}

		int index = g.map.get(p1);

		boolean c = false;

		return ans;

		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY
		// CHANGE AS REQUIRED FOR YOUR IMPLEMENTATION
	}

	*/


	public static ArrayList<String> shortestChain(Graph g, String p1, String p2) {

		ArrayList<String> ans = new ArrayList<>();

		int p1Index = g.map.get(p1);

		boolean[] visited = new boolean[g.members.length];
		int[] prev = new int[g.members.length];


		for(int i =0;i<visited.length;i++){
			visited[i] = false;
			prev[i] = -1;
		}
		Queue<Person> queue = new Queue<>();

		visited[p1Index] = true;
		queue.enqueue(g.members[p1Index]);

		Person person;
		Friend friend;

		while(!queue.isEmpty()){
			person = queue.dequeue();
			friend = person.first;

			while(friend != null){
					if(visited[friend.fnum] == false){
						prev[friend.fnum] = g.map.get(person.name);
						visited[friend.fnum] = true;
//						ans.add(person.name);

						queue.enqueue(g.members[friend.fnum]);
					}
					friend = friend.next;
			}
		}
		int i = g.map.get(p2);
		while (i != -1) {
			ans.add(0,g.members[i].name);
			i = prev[i];
		}
		return ans;
	}


		/**
         * Finds all cliques of students in a given school.
         *
         * Returns an array list of array lists - each constituent array list contains
         * the names of all students in a clique.
         *
         * @param g Graph for which cliques are to be found.
         * @param school Name of school
         * @return Array list of clique array lists. Null if there is no student in the
         *         given school
         */
	public static ArrayList<ArrayList<String>> cliques(Graph g, String school) {

		ArrayList<ArrayList<String>> answer = new ArrayList<ArrayList<String>>();
		school = school.toLowerCase();

		boolean[] visited = new boolean[g.members.length];

		for(int a = 0;a<visited.length;a++){
			visited[a]=false; //fill boolean array with false
		}

		for(int y =0;y<g.members.length;y++){

			if(visited[y]) //if already checked
				continue;

			//if the person has a school and
			//if the person's school doesn't match the target school
			if(g.members[y].school==null||!g.members[y].school.equals(school))
				continue;

			Queue<Person> queue = new Queue<>();
			ArrayList<String> single = new ArrayList<>();
			single.add(g.members[y].name);

			queue.enqueue(g.members[y]);
			visited[y] = true;
			Person person;
			Friend ex;


			while(!queue.isEmpty()) {

				person = queue.dequeue();
				ex = person.first;

				while(ex!=null){
					if(!visited[ex.fnum]){
						visited[ex.fnum]= true;

						if(g.members[ex.fnum].school!=null&&g.members[ex.fnum].school.equals(school)){
							single.add(g.members[ex.fnum].name);
							queue.enqueue(g.members[ex.fnum]);
						}

					}
					ex = ex.next;
				}
			}
			answer.add(single);
		}


		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY
		// CHANGE AS REQUIRED FOR YOUR IMPLEMENTATION
		return answer;

	}

	/**
	 * Finds and returns all connectors in the graph.
	 *
	 * @param g Graph for which connectors needs to be found.
	 * @return Names of all connectors. Null if there are no connectors.
	 */
	public static ArrayList<String> connectors(Graph g) {


		boolean [] visited = new boolean[g.members.length];
		int[] backNum = new int[g.members.length];
		int [] dfsNum =  new int[g.members.length];
		int dfsNumCounter = 0; int backNumCounter = 0;

		HashMap<Integer,Integer> connectors = new HashMap<>();
		Person[] arr = new Person[g.members.length];

		for(int i =0;i<g.members.length;i++){
			if(!visited[i]){
				connectors.put(i,1);

				helpConnectors(g.members[i],visited,dfsNum,backNum,dfsNumCounter,backNumCounter,connectors,g);
			}
		}



		ArrayList<String> answer = new ArrayList<>();



		if(connectors.containsValue(3) == false) {
			System.out.println("No connectors.");
			return null;
		}

		connectors.forEach((key,value)->if(value==3)answer.add(key));



		return null;
	}

	private static Person helpConnectors(Person user, boolean[] visited, int[] dfsNum, int[] backNum, int dfsNumCounter,
									   int backNumCounter, HashMap<Integer, Integer> connectors,Graph g){

		int userIndex = connectors.get(user.name);
		visited[userIndex] = true;
		dfsNum[userIndex] = dfsNumCounter; dfsNumCounter++;
		backNum[userIndex] = dfsNumCounter; backNumCounter++;

		Friend friend = g.members[userIndex].first;
		while(friend!=null){
			if(!visited[friend.fnum]){
				Person person = g.members[friend.fnum];
				helpConnectors(user,visited,dfsNum,backNum,dfsNumCounter,backNumCounter,connectors,g);

				if(dfsNum[userIndex] >backNum[friend.fnum]){
					backNum[userIndex] = Math.min(backNum[userIndex], backNum[friend.fnum]);
				}
				else{
					if(connectors.get(userIndex) == null){
						connectors.put(userIndex,3);
					}
					else if(connectors.get(userIndex) ==1){
						connectors.remove(userIndex);
						connectors.put(userIndex,2);
					}
					else if(connectors.get(userIndex) ==2){
						connectors.remove(userIndex);
						connectors.put(userIndex,3);
					}
				}

			}
			else{
				backNum[userIndex] = Math.min(backNum[userIndex], dfsNum[friend.fnum]);
			}
			friend = friend.next;
		}
	}

}



// 11700KB, 76ms

package bj1043;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 50;

	static int V, M;
	static int[][] adjMatrix = new int[MAX_V + 1][MAX_V + 1];
	static boolean[] isVisited = new boolean[MAX_V + 1];
	static Set<Integer> finalKnowers = new HashSet<>();
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 사람 수, 파티 수 입력
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		// 진실을 아는 사람들 입력받아서 연결
		st = new StringTokenizer(br.readLine(), " ");
		int countKnow = Integer.parseInt(st.nextToken());
		int firstKnower = -1;
		if (countKnow > 0) {
			firstKnower = Integer.parseInt(st.nextToken());
			for (int i = 1; i < countKnow; i++) {
				int knower = Integer.parseInt(st.nextToken());
				adjMatrix[firstKnower][knower] = 1;
				adjMatrix[knower][firstKnower] = 1;
			}
		}

		// 파티마다 파티에 속한 사람들을 parties에 저장하고, 같은 파티에 있는 사람들을 adjMatrix에서 연결
		List<Integer>[] parties = new ArrayList[M];
		for (int i = 0; i < M; i++) {
			parties[i] = new ArrayList<Integer>();
		}
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int numPeopleInParty = Integer.parseInt(st.nextToken());
			int firstPerson = Integer.parseInt(st.nextToken());
			parties[i].add(firstPerson);
			for (int k = 1; k < numPeopleInParty; k++) {
				int person = Integer.parseInt(st.nextToken());
				parties[i].add(person);
				adjMatrix[firstPerson][person] = 1;
				adjMatrix[person][firstPerson] = 1;
			}
		}

		// 처음부터 진실을 안 사람과 연결된 모든 사람들은 최종적으로 진실을 아는 사람이 된다.
		if (1 <= firstKnower && firstKnower <= V) {
			dfs(firstKnower);
		}
		
		// 진실을 아는 사람들이 없는 파티의 수를 센다
		int answer = 0;
		for (int partyNum = 0; partyNum < M; partyNum++) {
			if (canLie(parties[partyNum])) {
				answer++;
			}
		}
		
		// 출력
		System.out.println(answer);
		
	} // end main

	/** 파티에 속한 사람들이 리스트 형태로 주어졌을 때, 이 파티에 거짓말을 할 수 있는지 여부를 리턴 */
	public static boolean canLie (List<Integer> partyPeople) {
		for (int person : partyPeople) {
			if (finalKnowers.contains(person)) {
				return false;
			}
		}
		
		return true;
	}
	
	/** dfs로 연결된 정점들을 방문하면서 진실을 최종적으로 알게 되는 사람으로 만든다 */
	public static void dfs(int here) {
		if (isVisited[here]) {
			return;
		}
		
		isVisited[here] = true;
		finalKnowers.add(here);
		
		for (int there = 1; there <= V; there++) {
			if (adjMatrix[here][there] == 1) {
				dfs(there);
			}
		}
		
		return;
	}
}
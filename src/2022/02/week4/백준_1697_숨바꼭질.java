// 20284KB, 140ms

package bj1697;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_POS = 100000;
	static boolean[] isVisited = new boolean[MAX_POS + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		st = new StringTokenizer(br.readLine(), " ");
		
		
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		
		int answer = bfs(N, K);
		
		System.out.println(answer);
		
	} // end main
	
	public static int bfs(int start, int target) {
		Queue<Integer> q = new LinkedList<>();
		
		q.offer(start);
		isVisited[start] = true;
		
		int depth = 0;
		while(!q.isEmpty()) {
			int size = q.size();
			
			for (int i = 0; i < size; i++) {
				int here = q.poll();
				if (here == target) {
					return depth;
				}
				
				int[] theres = new int[3];
				theres[0] = here + 1;
				theres[1] = here - 1;
				theres[2] = here * 2;
				
				for (int th = 0; th < 3; th++) {
					int there = theres[th];
					if (isInRange(there) && !isVisited[there]) {
						q.offer(there);
						isVisited[there] = true;
					}
				}
			}
			
			depth++;
		}
		
		return -1;
	}
	
	public static boolean isInRange(int x) {
		if (0 <= x && x <= MAX_POS) {
			return true;
		}
		return false;
	}
}
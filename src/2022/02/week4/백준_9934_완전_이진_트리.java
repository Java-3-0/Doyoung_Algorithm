// 12004KB, 92ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	public static int[] inorder;
	public static List<Integer>[] levels;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		int depth = Integer.parseInt(br.readLine());
		
		st = new StringTokenizer(br.readLine(), " ");
		int numNodes = (int)Math.pow(2, depth) - 1;
		inorder = new int[numNodes];
		for (int i = 0; i < numNodes; i++) {
			inorder[i] = Integer.parseInt(st.nextToken());
		}

		levels = new ArrayList[depth];
		for (int i = 0; i < levels.length; i++) {
			levels[i] = new ArrayList<Integer>();
		}
		
		getParent(0, numNodes, 0);
		
		for (int i = 0; i < levels.length; i++) {
			for (int j = 0; j < levels[i].size(); j++) {
				sb.append(levels[i].get(j)).append(" ");
			}
			sb.append("\n");
		}
		
		System.out.print(sb.toString());
		
	} // end main
	
	public static void getParent (int startIdx, int length, int depth) {
		if (length == 0) {
			return;
		}
		
		int half = length / 2;
		int midIdx = startIdx + length / 2;
		int mid = inorder[midIdx];
		
		levels[depth].add(mid);
		getParent(startIdx, half, depth + 1);
		getParent(midIdx + 1, half, depth + 1);
	}
}
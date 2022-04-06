// 11836KB, 76ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static int MAX_N = 50;

	static int N;
	static List<Integer>[] children;
	static boolean[] isVisited;
	static int eliminated;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 트리 노드 개수 입력
		N = Integer.parseInt(br.readLine());

		// 전역변수 메모리 할당
		children = new ArrayList[N];
		for (int i = 0; i < N; i++) {
			children[i] = new ArrayList<Integer>();
		}
		isVisited = new boolean[N];

		// 트리 입력
		st = new StringTokenizer(br.readLine(), " ");
		int root = -1;
		for (int i = 0; i < N; i++) {
			int parent = Integer.parseInt(st.nextToken());
			if (parent == -1) {
				root = i;
			} else {
				children[parent].add(i);
			}
		}

		// 지울 노드 입력
		eliminated = Integer.parseInt(br.readLine());

		// 지울 노드는 방문하지 못하게 하기 위해 isVisited라고 보자
		isVisited[eliminated] = true;

		int answer = dfs(root);

		System.out.println(answer);

	} // end main

	public static int dfs(int start) {
		// 이미 방문했다면 0을 리턴
		if (isVisited[start]) {
			return 0;
		}

		isVisited[start] = true;

		// 리프 노드라면 1를 리턴
		if (children[start].isEmpty() || (children[start].size() == 1 && children[start].get(0) == eliminated)) {
			return 1;
		}

		// 리프 노드가 아니라면, 이 노드 아래에 있는 리프 노드들의 수를 세어서 리턴
		int ret = 0;
		for (int next : children[start]) {
			ret += dfs(next);
		}

		return ret;
	}

}
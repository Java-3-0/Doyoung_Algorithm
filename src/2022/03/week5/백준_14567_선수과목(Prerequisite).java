// 130512KB, 576ms

package bj14567;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static final int MAX_N = 1000;
	static final int MAX_M = 500000;

	static int N, M;
	static int[] inDegrees = new int[MAX_N + 1];
	static List<Integer>[] adjList = new ArrayList[MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// adjList 메모리 할당
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		// N, M 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		// 선수과목 조건 입력
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int before = Integer.parseInt(st.nextToken());
			int after = Integer.parseInt(st.nextToken());
			adjList[before].add(after);
			inDegrees[after]++;
		}

		int[] answer = getSemesters();

		for (int i = 1; i <= N; i++) {
			sb.append(answer[i]).append(" ");
		}
		sb.append("\n");

		System.out.print(sb.toString());
	} // end main

	public static int[] getSemesters() {
		Queue<Integer> q = new LinkedList<>();
		int[] ret = new int[N + 1];

		int semester = 1;

		// 처음부터 바로 이수 가능한 과목들을 큐에 넣는다.
		for (int i = 1; i <= N; i++) {
			if (inDegrees[i] == 0) {
				q.offer(i);
			}
		}
		while (!q.isEmpty()) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				int here = q.poll();
				ret[here] = semester;

				for (int there : adjList[here]) {
					inDegrees[there]--;
					if (inDegrees[there] == 0) {
						q.offer(there);
					}
				}
			}

			semester++;
		}

		return ret;
	}

}
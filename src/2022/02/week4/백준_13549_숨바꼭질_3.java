package bj13549;

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

		// N, K 입력
		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		// bfs를 통해 가장 빨리 찾는 시간을 구한다.
		int answer = bfs(N, K);

		// 출력
		System.out.println(answer);

	} // end main

	/** bfs를 통해 가장 빨리 찾는 시간을 구해서 그 둘을 배열에 담아 리턴 */
	public static int bfs(int start, int finish) {
		Queue<Integer> q = new LinkedList<>();

		// 시작 지점
		for (int i = start; i <= MAX_POS; i *= 2) {
			isVisited[i] = true;
			q.offer(i);

			if (i == finish) {
				return 0;
			}

			// 0인 경우 무한루프를 돌지 않기 위해 빠져나온다.
			if (i == 0) {
				break;
			}
		}

		// bfs 수행
		int depth = 0;
		while (!q.isEmpty()) {
			depth++;

			int size = q.size();
			for (int i = 0; i < size; i++) {
				int here = q.poll();

				// 다음으로 갈 수 있는 위치들을 큐에 넣는다.
				int[] there = new int[2];
				there[0] = here - 1;
				there[1] = here + 1;
				for (int th = 0; th < there.length; th++) {
					for (int pos = there[th]; 0 <= pos && pos <= MAX_POS && !isVisited[pos]; pos *= 2) {
						isVisited[pos] = true;
						q.offer(pos);

						// 목적지를 찾은 경우 depth를 리턴
						if (pos == finish) {
							return depth;
						}

						// 0인 경우 무한루프를 돌지 않기 위해 빠져나온다.
						if (pos == 0) {
							break;
						}
					}
				}
			}
		}

		return -1;
	}
}
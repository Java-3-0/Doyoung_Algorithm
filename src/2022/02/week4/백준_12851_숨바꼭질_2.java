// 53280KB, 168ms

package bj12851;

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

		// bfs를 통해 가장 빨리 찾는 시간, 그 방법의 수를 구한다.
		int[] answer = bfs(N, K);

		// 정답 출력
		for (int i = 0; i < answer.length; i++) {
			System.out.println(answer[i]);
		}

	} // end main

	/** bfs를 통해 가장 빨리 찾는 시간, 그 방법의 수를 구해서 그 둘을 배열에 담아 리턴 */
	public static int[] bfs(int start, int finish) {
		Queue<Integer> q = new LinkedList<>();
		
		// 시작 지점
		isVisited[start] = true;
		q.offer(start);

		// bfs 수행
		int[] ret = new int[2];
		int depth = 0;
		while (!q.isEmpty()) {
			int size = q.size();
			int cnt = 0;

			for (int i = 0; i < size; i++) {
				int here = q.poll();
				
				// 이 정점까지 오는 같은 길이의 경로를 여러 개 찾아야 하므로, 방문 체크를 큐에 넣을 때가 아니라 큐에서 꺼낼 때 한다.
				isVisited[here] = true;
				
				// 목적지를 찾은 경우 방법의 수를 하나 카운트
				if (here == finish) {
					cnt++;
				}

				// 다음으로 갈 수 있는 위치들을 큐에 넣는다.
				int[] there = new int[3];
				there[0] = here - 1;
				there[1] = here + 1;
				there[2] = here * 2;
				for (int th = 0; th < there.length; th++) {
					if (0 <= there[th] && there[th] <= MAX_POS && !isVisited[there[th]]) {
						q.offer(there[th]);
					}
				}
			}

			// 찾은 방법의 수가 1개 이상이라면, 이 depth가 가능하다는 것이므로 이때의 depth와 cnt가 정답이 된다.
			if (cnt != 0) {
				ret[0] = depth;
				ret[1] = cnt;
				break;
			}

			// 찾지 못하고 한 바퀴 돌았다면 다음 depth로 간다.
			depth++;
		}

		return ret;
	}
}
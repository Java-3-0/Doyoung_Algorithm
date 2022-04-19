// 34320KB, 264ms

package bj13913;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_POS = 100000;
	static boolean[] isVisited = new boolean[MAX_POS + 1];
	static int[] prevPos = new int[MAX_POS + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// N, K 입력
		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		// bfs를 통해 가장 빨리 찾는 시간을 구한다.
		int minTime = bfs(N, K);

		// 출력 스트링빌더에 최소 시간 담기
		sb.append(minTime).append("\n");
		
		// 방문 경로 역순으로 추적해서 스택에 담기
		Stack<Integer> stack = new Stack<>();
		int cur = K;
		while (cur != -1) {
			stack.add(cur);
			int prev = prevPos[cur];
			cur = prev;
		}
		
		// 스택에 있는 걸 pop해와서 sb에 담기
		while (!stack.isEmpty()) {
			sb.append(stack.pop()).append(" ");
		}
		sb.append("\n");

		// 출력
		System.out.print(sb.toString());

	} // end main

	/** bfs를 통해 가장 빨리 찾는 시을 구해서 리턴하고, 역추적을 위한 prevPos 배열도 계산 */
	public static int bfs(int start, int finish) {
		Queue<Integer> q = new LinkedList<>();
		Arrays.fill(prevPos, -1);

		// 시작 지점
		isVisited[start] = true;
		q.offer(start);

		// bfs 수행
		int depth = 0;
		while (!q.isEmpty()) {
			int size = q.size();

			for (int i = 0; i < size; i++) {
				int now = q.poll();

				// 목적지를 찾은 경우 방법의 수를 하나 카운트
				if (now == finish) {
					return depth;
				}

				// 다음으로 갈 수 있는 위치들을 큐에 넣는다.
				int[] nexts = new int[3];
				nexts[0] = now - 1;
				nexts[1] = now + 1;
				nexts[2] = now * 2;
				for (int next : nexts) {
					if (0 <= next && next <= MAX_POS && !isVisited[next]) {
						isVisited[next] = true;
						prevPos[next] = now;
						q.offer(next);
					}
				}
			}

			// 찾지 못하고 한 바퀴 돌았다면 다음 depth로 간다.
			depth++;
		}

		return -1;
	}
}
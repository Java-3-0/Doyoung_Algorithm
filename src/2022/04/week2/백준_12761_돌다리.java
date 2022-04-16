// 43892KB, 304ms

package bj12761;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_POS = 100000;
	static int A, B, start, end;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 입력
		st = new StringTokenizer(br.readLine(), " ");
		A = Integer.parseInt(st.nextToken());
		B = Integer.parseInt(st.nextToken());
		start = Integer.parseInt(st.nextToken());
		end = Integer.parseInt(st.nextToken());

		// 정답 계산
		int answer = bfs();

		// 출력
		System.out.println(answer);

	} // end main

	/** bfs를 수행하며 start에서 end까지 도달하는 최단 경로의 길이를 구한다 */
	public static int bfs() {
		// 큐, 방문 체크 배열 선언
		Queue<Integer> q = new ArrayDeque<>();
		boolean[] isVisited = new boolean[MAX_POS + 1];

		// 시작 정점 처리
		isVisited[start] = true;
		q.offer(start);

		int depth = 0;
		while (!q.isEmpty()) {
			// 한 depth씩 수행
			int size = q.size();
			for (int i = 0; i < size; i++) {
				// 현 위치를 큐에서 가져온다
				int now = q.poll();
				
				// 도착 시 depth 리턴하고 종료
				if (now == end) {
					return depth;
				}

				// 다음 위치들 계산
				List<Integer> nexts = new ArrayList<>();
				nexts.add(now + 1);
				nexts.add(now - 1);
				nexts.add(now + A);
				nexts.add(now + B);
				nexts.add(now - A);
				nexts.add(now - B);
				nexts.add(now * A);
				nexts.add(now * B);

				// 다음 위치들 중 범위 내이면서 방문하지 않은 위치들을 큐에 넣는다
				for (int next : nexts) {
					if (isInRange(next) && !isVisited[next]) {
						isVisited[next] = true;
						q.offer(next);
					}
				}
			}

			depth++;
		}

		return -1;
	}

	/** 범위 체크 */
	public static boolean isInRange(int x) {
		if (0 <= x && x <= MAX_POS) {
			return true;
		}

		return false;
	}

}
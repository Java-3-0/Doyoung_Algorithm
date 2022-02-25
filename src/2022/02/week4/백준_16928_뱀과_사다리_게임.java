// 11736KB, 92ms

package bj16928;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int BOARD_SIZE = 100;

	static int numLadders, numSnakes;
	static int[] gameboard = new int[BOARD_SIZE + 1];
	static boolean[] isVisited = new boolean[BOARD_SIZE + 1];

	static Map<Integer, Integer> snakeOrLadder = new HashMap<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 사다리 수, 뱀 수 입력
		st = new StringTokenizer(br.readLine(), " ");
		numLadders = Integer.parseInt(st.nextToken());
		numSnakes = Integer.parseInt(st.nextToken());

		// 사다리, 뱀 입력받아서 map 형태로 저장
		for (int i = 0; i < numLadders + numSnakes; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			snakeOrLadder.put(from, to);
		}

		// bfs를 통해 도착하는 데 걸리는 주사위 횟수를 구한다.
		int answer = bfs(1);

		// 출력
		System.out.println(answer);

	} // end main

	/** bfs를 통해 마지막 칸에 도달하는 데 필요한 depth를 리턴한다 */
	public static int bfs(int start) {
		Queue<Integer> q = new LinkedList<>();
		isVisited[start] = true;
		q.offer(start);

		int depth = 0;
		while (!q.isEmpty()) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				int here = q.poll();

				// 마지막 칸에 도착했다면 성공
				if (here == BOARD_SIZE) {
					return depth;
				}

				for (int dice = 1; dice <= 6; dice++) {
					// 주사위 수만큼 이동한 위치 계산
					int there = here + dice;
					if (there < 0 || there > BOARD_SIZE) {
						continue;
					}

					// 뱀과 사다리를 탄 이후의 위치 계산
					if (snakeOrLadder.containsKey(there)) {
						there = snakeOrLadder.get(there);
					}

					// 이 위치를 아직 방문한 적이 없다면 방문하고 큐에 넣는다
					if (!isVisited[there]) {
						isVisited[there] = true;
						q.offer(there);
					}

				}
			}

			depth++;
		}

		return -1;
	}

}
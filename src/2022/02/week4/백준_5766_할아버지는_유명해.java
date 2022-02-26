// 154580KB, 1344ms

package bj5766;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		while (true) {
			// N, M 입력
			st = new StringTokenizer(br.readLine(), " ");
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());

			// 종료 조건
			if (N == 0 && M == 0) {
				break;
			}

			// 입력을 받아서 각 선수 번호의 등장 횟수를 센다
			Map<Integer, Integer> count = new TreeMap<>();
			for (int week = 0; week < N; week++) {
				st = new StringTokenizer(br.readLine(), " ");
				for (int rank = 0; rank < M; rank++) {
					int playerNum = Integer.parseInt(st.nextToken());
					if (count.containsKey(playerNum)) {
						count.put(playerNum, count.get(playerNum) + 1);
					} else {
						count.put(playerNum, 1);
					}
				}
			}

			// 가장 많은 등장횟수와 두번째로 많은 등장횟수를 센다.
			int max = Integer.MIN_VALUE;
			int secondMax = Integer.MIN_VALUE;
			for (int cnt : count.values()) {
				if (max < cnt) {
					secondMax = max;
					max = cnt;
				}
				
				else if (secondMax < cnt) {
					secondMax = cnt;
				}
			}

			// 두 번째로 많은 등장횟수만큼 등장한 선수번호들을 출력 스트링빌더에 append
			for (int key : count.keySet()) {
				if (count.get(key) == secondMax) {
					sb.append(key).append(" ");
				}
			}
			sb.append("\n");
		}

		// 출력
		System.out.print(sb.toString());

	} // end main

}
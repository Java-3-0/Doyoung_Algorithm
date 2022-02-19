// 59040KB, 652ms

package bj19598;

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

		int N = Integer.parseInt(br.readLine());

		/** 시각 -> 그 시각에서의 진행중인 회의 개수 변화량으로의 매핑 */
		Map<Integer, Integer> delta = new TreeMap<>();
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int startTime = Integer.parseInt(st.nextToken());
			int endTime = Integer.parseInt(st.nextToken());

			// 회의 시작 시각에 따라 delta 맵에서 해당하는 value를 1 증가시킨다.
			if (delta.containsKey(startTime)) {
				delta.put(startTime, delta.get(startTime) + 1);
			} else {
				delta.put(startTime, 1);
			}

			// 회의 종료 시각에 따라 delta 맵에서 해당하는 value를 1 감소시킨다.
			if (delta.containsKey(endTime)) {
				delta.put(endTime, delta.get(endTime) - 1);
			} else {
				delta.put(endTime, -1);
			}

		}
		
		// 동시에 일어날 수 있는 회의 개수의 최대치를 파악한다
		int maxMeetingsSameTime = 0;
		int count = 0;
		for (int time : delta.keySet()) {
			count += delta.get(time);
			if (maxMeetingsSameTime < count) {
				maxMeetingsSameTime = count;
			}
		}
		
		// 정답 출력
		System.out.println(maxMeetingsSameTime);

	}
}
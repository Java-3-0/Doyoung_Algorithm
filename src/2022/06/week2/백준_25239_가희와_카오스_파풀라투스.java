// 11696KB, 80ms

package bj25239;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int AREAS = 6;
	static final int MAX_TIME = 12 * 60;

	static int[] heals = new int[AREAS + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 파풀라투스의 초기 시각 입력
		st = new StringTokenizer(br.readLine(), ":");
		int hour = Integer.parseInt(st.nextToken());
		int minute = Integer.parseInt(st.nextToken());
		int time = hour * 60 + minute;

		// 구역들의 힐량 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i <= AREAS; i++) {
			heals[i] = Integer.parseInt(st.nextToken());
		}

		// 이벤트 개수 입력
		int L = Integer.parseInt(br.readLine());

		// 이벤트 수행
		for (int i = 0; i < L; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			double sec = Double.parseDouble(st.nextToken());
			String event = st.nextToken();

			if (sec >= 60.0) {
				continue;
			}

			if (event.equals("^")) {
				lock(time);
			} else if (event.equals("10MIN")) {
				time = addTime(time, 10);
			} else if (event.equals("30MIN")) {
				time = addTime(time, 30);
			} else if (event.equals("50MIN")) {
				time = addTime(time, 50);
			} else if (event.equals("2HOUR")) {
				time = addTime(time, 2 * 60);
			} else if (event.equals("4HOUR")) {
				time = addTime(time, 4 * 60);
			} else if (event.equals("9HOUR")) {
				time = addTime(time, 9 * 60);
			}
		}

		// 결과 출력
		int answer = 0;
		for (int heal : heals) {
			answer += heal;
		}
		if (answer > 100) {
			answer = 100;
		}

		System.out.println(answer);

	} // end main

	/** 시간이 나타내는 구역 번호를 리턴 */
	public static int getAreaNum(int time) {
		if (time < 2 * 60) {
			return 1;
		} else if (time < 4 * 60) {
			return 2;
		} else if (time < 6 * 60) {
			return 3;
		} else if (time < 8 * 60) {
			return 4;
		} else if (time < 10 * 60) {
			return 5;
		} else {
			return 6;
		}
	}

	public static int addTime(int time, int plus) {
		int nextTime = (time + plus) % MAX_TIME;

		return nextTime;
	}

	public static void lock(int time) {
		int areaNum = getAreaNum(time);
		heals[areaNum] = 0;
	}

}
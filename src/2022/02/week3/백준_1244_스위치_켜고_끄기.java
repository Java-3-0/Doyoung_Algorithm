// 11612KB, 76ms

package bj1244;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;

		// 스위치 개수 입력
		int numSwitches = Integer.parseInt(br.readLine());

		// 각 스위치 초기 상태 입력
		int[] switches = new int[numSwitches + 1];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i <= numSwitches; i++) {
			switches[i] = Integer.parseInt(st.nextToken());
		}

		// 학생 수 입력
		int numStudents = Integer.parseInt(br.readLine());

		// 각 학생 정보 입력받아서, 스위치 상태 변경
		for (int i = 0; i < numStudents; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int gender = Integer.parseInt(st.nextToken());
			int num = Integer.parseInt(st.nextToken());

			// 남학생의 경우 배수인 것이 더 있으면 더 끈다
			if (gender == 1) {
				switches[num] = switches[num] == 0 ? 1 : 0;

				int multiply = 2;
				while (true) {
					int idx = num * multiply;
					if (idx > numSwitches) {
						break;
					}

					switches[idx] = (switches[idx] == 0 ? 1 : 0);

					multiply++;
				}
			}

			// 여학생의 경우 양 옆으로 더 끌 수 있으면 더 끈다
			else if (gender == 2) {
				switches[num] = (switches[num] == 0 ? 1 : 0);

				int dist = 1;
				while (true) {
					int idx1 = num - dist;
					int idx2 = num + dist;

					if (idx1 < 1 || idx2 > numSwitches) {
						break;
					}

					if (switches[idx1] == switches[idx2]) {
						switches[idx1] = (switches[idx1] == 0 ? 1 : 0);
						switches[idx2] = (switches[idx2] == 0 ? 1 : 0);
					} else {
						break;
					}

					dist++;
				}
			}

		}

		// 출력
		int length = switches.length;
		for (int i = 1; i < length; i++) {
			sb.append(switches[i]).append(" ");
			if (i % 20 == 0) {
				sb.append("\n");
			}
		}

		System.out.print(sb.toString());
	}
}
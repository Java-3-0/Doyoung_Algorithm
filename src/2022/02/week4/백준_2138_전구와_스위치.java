// 15572KB, 144ms

package bj2138;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// 입력
		int N = Integer.parseInt(br.readLine());
		String s1 = br.readLine();
		String s2 = br.readLine();

		// 최소 스위치 누르는 횟수 계산
		int answer = minPresses(s1, s2);

		// 출력
		System.out.println(answer);
	}

	/** s1을 s2로 만들기 위해 스위치를 누르는 최소 횟수를 리턴 */
	public static int minPresses(String s1, String s2) {
		int answer1 = minPresses(s1, s2, true);
		int answer2 = minPresses(s1, s2, false);
		
		if (answer1 == -1) return answer2;
		if (answer2 == -1) return answer1;
		return Math.min(answer1, answer2);
	}
	
	public static int minPresses(String s1, String s2, boolean pressFirst) {
		int len = s1.length();

		// 두 문자열의 차이 파악
		boolean[] needSwitch = new boolean[len];
		Arrays.fill(needSwitch, false);
		for (int i = 0; i < len; i++) {
			if (s1.charAt(i) != s2.charAt(i)) {
				needSwitch[i] = true;
			}
		}

		int ret = 0;
		
		// 첫 스위치를 어떻게 할지 주어진 대로 누르거나 안 누른다.
		if (pressFirst) {
			needSwitch[0] = !needSwitch[0];
			needSwitch[1] = !needSwitch[1];
			ret++;
		}

		// 앞에서부터 탐색하면서 스위치 클릭
		for (int pos = 0; pos < len - 1; pos++) {
			if (needSwitch[pos]) {
				for (int delta = 0; delta < 3; delta++) {
					int here = pos + delta;
					if (0 <= here && here < len) {
						needSwitch[here] = !needSwitch[here];
					}
				}
				ret++;
			}
		}

		// 마지막 칸 하나만 남아서 바꿀 방법이 없는 경우 불가능
		if (needSwitch[len - 1]) {
			return -1;
		}
		
		// 이외의 경우 성공했으니 스위치 클릭 수 리턴
		return ret;
	}
}
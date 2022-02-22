// 11488KB, 84ms

package bj12933;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static final int IMPOSSIBLE = -1;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 입력
		String s = br.readLine();

		// 최소 오리 수 계산
		int answer = minDucks(s);

		// 출력
		System.out.println(answer);

	} // end main

	public static int minDucks(String s) {
		int len = s.length();
		int ret = 0;

		int[] counts = new int[5];
		for (int i = 0; i < len; i++) {
			// 이번에 나온 문자
			int here = charToInt(s.charAt(i));

			// 이것보다 앞의 와야 하는 문자의 개수가 각각 이 문자의 개수보다 많아야 함
			for (int before = 0; before < here; before++) {
				if (counts[before] <= counts[here]) {
					return IMPOSSIBLE;
				}
			}

			// 와도 되는 문자라면 카운트를 세고 최대 오리를 갱신
			counts[here]++;
			ret = Math.max(ret, counts[here]);

			// 한 마리가 끝났다면 모든 문자의 카운트를 하나 줄임
			if (here == 4) {
				for (int k = 0; k < 5; k++) {
					counts[k]--;
				}
			}

		}

		// 울음소리가 다 끝나지 않았다면 실패
		for (int k = 0; k < 5; k++) {
			if (counts[k] != 0) {
				return IMPOSSIBLE;
			}
		}
		
		return ret;
	}

	public static int charToInt(char c) {
		switch (c) {
		case 'q':
			return 0;
		case 'u':
			return 1;
		case 'a':
			return 2;
		case 'c':
			return 3;
		case 'k':
			return 4;
		default:
			return -1;
		}
	}
}
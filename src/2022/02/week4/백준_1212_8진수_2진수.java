// 62804KB, 304ms

package tmp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringBuilder sb = new StringBuilder();

		// 입력
		String s = br.readLine();

		// 8진수 -> 2진수 변환
		int len = s.length();
		for (int i = 0; i < len; i++) {
			char c = s.charAt(i);

			sb.append(eightToTwo(c));
		}

		// 스트링빌더에 담긴 정답을 스트링으로 변환
		String answer = sb.toString();

		// 앞부분 0 제거
		int startIdx = answer.length() - 1;
		for (int i = 0; i < answer.length(); i++) {
			if (answer.charAt(i) != '0') {
				startIdx = i;
				break;
			}
		}
		answer = answer.substring(startIdx);

		// 스트링 전체가 0이면 0 출력, 이외의 경우 스트링 그대로 출력
		System.out.println(answer);

	} // end main

	/** 한자리 수 8진법 숫자 하나를 2진법으로 변환 */
	public static String eightToTwo(char c) {
		StringBuilder sb = new StringBuilder();
		int num = c - '0';

		for (int i = 0; i < 3; i++) {
			sb.append(num % 2);
			num /= 2;
		}

		return sb.reverse().toString();
	}
}
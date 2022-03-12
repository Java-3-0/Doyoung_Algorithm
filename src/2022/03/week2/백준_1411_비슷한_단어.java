// 12712KB, 112ms

package bj1411;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {
	static int length;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// N 입력
		int N = Integer.parseInt(br.readLine());

		// 단어들 입력
		String[] words = new String[N];
		for (int i = 0; i < N; i++) {
			words[i] = br.readLine();
		}

		// 모든 단어의 길이는 같으므로 하나의 길이를 재 둔다.
		length = words[0].length();

		// 비슷한 쌍의 개수를 센다
		int answer = 0;
		for (int i = 0; i < N; i++) {
			for (int j = i + 1; j < N; j++) {
				if (isSimilar(words[i], words[j])) {
					answer++;
				}
			}
		}

		// 출력
		System.out.println(answer);

	}

	/** word1과 word2가 비슷한지 여부를 리턴 */
	public static boolean isSimilar(String word1, String word2) {
		Map<Character, Character> map = new HashMap<>();

		// 문자열 내 각 문자마다 반복 처리
		for (int i = 0; i < length; i++) {
			char c1 = word1.charAt(i);
			char c2 = word2.charAt(i);

			// 이미 변환된 적이 있는 문자인 경우
			if (map.containsKey(c1)) {
				// 변환해도 안 맞으면 실패
				if (map.get(c1) != c2) {
					return false;
				}
			}
			// 아직 변환된 적이 없는 문자면
			else {
				// c2로 변환할 수 없다면 실패
				if (map.containsValue(c2)) {
					return false;
				}

				// 변환할 수 있다면 변환
				else {
					map.put(c1, c2);
				}
			}
		}

		return true;
	}

}
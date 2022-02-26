// 11572KB, 76ms

package bj1283;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		Set<Character> isUsed = new HashSet<>();

		int N = Integer.parseInt(br.readLine());
		for (int i = 0; i < N; i++) {

			String line = br.readLine();
			String[] words = line.split(" ");

			boolean assignedShortcut = false;

			// shortcutPos[0]는 단어 인덱스, shortcutPos[1]은 단어에서의 알파벳 인덱스
			int[] shortcutPos = new int[2];

			// 단어 첫글자들 탐색
			for (int w = 0; w < words.length; w++) {
				char c = words[w].charAt(0);
				char cLower = c < 'a' ? (char) (c + ('a' - 'A')) : c;

				if (!isUsed.contains(cLower)) {
					isUsed.add(cLower);
					assignedShortcut = true;
					shortcutPos[0] = w;
					shortcutPos[1] = 0;
					break;
				}
			}

			// 모든 글자 앞에서부터 탐색
			if (!assignedShortcut) {
				outer: for (int w = 0; w < words.length; w++) {
					for (int idx = 1; idx < words[w].length(); idx++) {
						char c = words[w].charAt(idx);
						char cLower = c < 'a' ? (char) (c + ('a' - 'A')) : c;

						if (!isUsed.contains(cLower)) {
							isUsed.add(cLower);
							assignedShortcut = true;
							shortcutPos[0] = w;
							shortcutPos[1] = idx;
							break outer;
						}
					}
				}
			}

			// 단축키 지정 결과를 출력 스트링빌더에 추가
			for (int w = 0; w < words.length; w++) {
				for (int idx = 0; idx < words[w].length(); idx++) {
					// 단축키가 지정되었다면 대괄호로 감싼다
					if (assignedShortcut && w == shortcutPos[0] && idx == shortcutPos[1]) {
						sb.append("[").append(words[w].charAt(idx)).append("]");
					} else {
						sb.append(words[w].charAt(idx));
					}
				}
				sb.append(" ");
			}
			sb.append("\n");
		}

		// 출력
		System.out.print(sb.toString());

	} // end main

}
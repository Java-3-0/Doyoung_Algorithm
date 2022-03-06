// 96476KB, 368ms

package bj13506;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		// 입력
		char[] arr = br.readLine().toCharArray();

		// T의 가능한 최대 길이 계산
		int maxLen = getMaxLengthOfT(arr);

		// 형식에 맞게 출력 스트링빌더에 추가
		if (maxLen == 0) {
			sb.append(-1).append("\n");
		} else {
			for (int i = 0; i < maxLen; i++) {
				sb.append(arr[i]);
			}
			sb.append("\n");
		}

		// 출력
		System.out.print(sb.toString());

	} // end main

	/** 문제에서 요구하는 T의 최대 길이를 리턴 */
	public static int getMaxLengthOfT(char[] arr) {
		int len = arr.length;

		// 접두사 접미사 일치 테이블 생성
		int[] prefixTable = getPrefixTable(arr);

		// 문자열의 중간에도 등장하는 접두사의 길이들을 possibles에 저장
		Set<Integer> possibles = new HashSet<>();
		for (int i = 0; i < len - 1; i++) {
			possibles.add(prefixTable[i]);
		}

		// 접두사와 접미사가 일치하는 최대 길이부터 길이를 점점 줄여가면서 possibles에 존재하는지 확인
		int longest = prefixTable[len - 1];
		while (longest > 0) {
			// 존재하면 그대로 리턴
			if (possibles.contains(longest)) {
				return longest;
			}
			// 존재하지 않으면 접두사와 접미사가 일치하는 길이 중 다음으로 큰 길이로 다시 시도
			else {
				longest = prefixTable[longest - 1];
			}
		}

		return 0;
	}

	/** KMP 알고리즘을 이용하여 접두사 접미사 일치 테이블을 생성해서 리턴 */
	public static int[] getPrefixTable(char[] arr) {
		int len = arr.length;
		int[] p = new int[len];
		p[0] = 0;

		for (int i = 1, j = 0; i < len; i++) {
			while (j > 0 && arr[i] != arr[j]) {
				j = p[j - 1];
			}

			if (arr[i] == arr[j]) {
				p[i] = ++j;
			}

			else {
				p[i] = 0;
			}
		}

		return p;
	}
}
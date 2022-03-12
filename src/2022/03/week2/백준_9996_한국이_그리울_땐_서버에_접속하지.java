// 11620KB, 80ms

package bj9996;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		// N 입력
		int N = Integer.parseInt(br.readLine());

		// 패턴 입력
		String line = br.readLine();
		int len = line.length();

		// 별표 이전의 패턴과 별표 이후의 패턴으로 나눈다.
		int starIdx = 0;
		for (int i = 0; i < len; i++) {
			if (line.charAt(i) == '*') {
				starIdx = i;
				break;
			}
		}
		String frontPattern = line.substring(0, starIdx);
		String backPattern = line.substring(starIdx + 1);

		// 각 파일명을 처리
		for (int i = 0; i < N; i++) {
			String fileName = br.readLine();

			if (canMatchPattern(fileName, frontPattern, backPattern)) {
				sb.append("DA\n");
			} else {
				sb.append("NE\n");
			}
		}

		System.out.print(sb.toString());
	}

	public static boolean canMatchPattern(String fileName, String frontPattern, String backPattern) {
		int fileLen = fileName.length();
		int frontLen = frontPattern.length();
		int backLen = backPattern.length();

		if (fileLen < frontLen + backLen) {
			return false;
		}
		
		// 맨 앞에서 frontPattern 찾기
		for (int i = 0; i < frontLen; i++) {
			if (fileName.charAt(i) != frontPattern.charAt(i)) {
				return false;
			}
		}

		// 맨 뒤에서 backPattern 찾기
		for (int i = 0; i < backLen; i++) {
			if (fileName.charAt(fileLen - backLen + i) != backPattern.charAt(i)) {
				return false;
			}
		}

		return true;
	}

}
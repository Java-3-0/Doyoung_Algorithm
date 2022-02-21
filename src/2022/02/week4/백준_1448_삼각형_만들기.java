// 104208KB, 2112ms

package bj1448;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 입력
		int N = Integer.parseInt(br.readLine());
		Integer[] straws = new Integer[N];
		for (int i = 0; i < N; i++) {
			straws[i] = Integer.parseInt(br.readLine());
		}

		// 최대 삼각형 둘레 계산
		int answer = maxTriangle(straws);
		
		// 출력
		System.out.println(answer);

	}

	/** 빨대 길이들이 배열로 주어질 때, 만들 수 있는 삼각형의 최대 둘레를 리턴. 불가능하면 -1 리턴 */
	public static int maxTriangle(Integer[] straws) {
		int len = straws.length;
		
		// 길이 내림차순으로 정렬
		Arrays.sort(straws, Collections.reverseOrder());
		
		for (int i = 0; i < len - 2; i++) {
			int A = straws[i];
			int B = straws[i+1];
			int C = straws[i+2];
			if (A < B + C) {
				return A + B + C;
			}
		}
		
		return -1;
	}
}

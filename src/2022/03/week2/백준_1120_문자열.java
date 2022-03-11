package bj1120;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 입력
		st = new StringTokenizer(br.readLine(), " ");
		char[] A = st.nextToken().toCharArray();
		char[] B = st.nextToken().toCharArray();

		int lenA = A.length;
		int lenB = B.length;

		int minCnt = lenB;

		// B의 각 위치마다 A를 놓아보면서 차이를 계산하고, 최소 차이 갱신
		for (int startIdx = 0; startIdx <= lenB - lenA; startIdx++) {
			int cnt = 0;
			for (int i = 0; i < lenA; i++) {
				int idx = startIdx + i;
				if (A[i] != B[idx]) {
					cnt++;
				}
			}

			minCnt = cnt < minCnt ? cnt : minCnt;
		}

		// 출력
		System.out.println(minCnt);
	}
}
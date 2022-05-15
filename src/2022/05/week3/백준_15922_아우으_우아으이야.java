// 41316KB, 352ms

package bj15922;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MIN_POS = (int) -1e9;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 선분 개수 입력
		int N = Integer.parseInt(br.readLine());

		// 현재 그어진 선분 중 맨 오른쪽 선분의 끝 위치
		int lineRight = MIN_POS;
		
		// 선분 길이의 총합
		int totalLen = 0;
		
		// 선분을 입력받아서 처리
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int left = Integer.parseInt(st.nextToken());
			int right = Integer.parseInt(st.nextToken());

			// 이전 구간에 완전히 포함되지는 않는 선분이 생기는 경우
			if (lineRight <= right) {
				// 일부만 겹치는 선분인 경우
				if (left <= lineRight) {
					totalLen += (right - lineRight);
				}

				// 아예 오른쪽에 새로 생기는 선분인 경우
				else {
					totalLen += (right - left);
				}

				lineRight = right;
			}
		}

		// 결과 출력
		System.out.println(totalLen);

	} // end main
}
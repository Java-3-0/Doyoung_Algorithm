// 29876KB, 236ms

package bj12782;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 *  위치를 바꾸면 2자리가 고쳐진다.
 *  한 비트를 바꾸면 1자리가 고쳐진다.
 *  따라서 위치 바꾸기를 먼저 시도
 */

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());
		for (int testCase = 1; testCase <= T; testCase++)
		{
			// 입력
			st = new StringTokenizer(br.readLine(), " ");
			String N = st.nextToken();
			String M = st.nextToken();
			
			// 비트 우정 지수 계산
			int answer = bitFriendliness(N, M);
			
			// 출력 스트링빌더에 담기
			sb.append(answer).append("\n");
		}
		
		// 출력
		System.out.print(sb.toString());

	}
	
	public static int bitFriendliness(String s1, String s2) {
		// s1과 s2가 다른 부분이 있을 때 s1에서 그 부분이 0인지 1인지 그 개수를 센다.
		int countDiffZeros = 0;
		int countDiffOnes = 0;
	
		int len = s1.length();
		for (int i = 0; i < len; i++) {
			if (s1.charAt(i) != s2.charAt(i)) {
				if (s1.charAt(i) == '0') {
					countDiffZeros++;
				} else {
					countDiffOnes++;
				}
			}
		}
		
		// 그 중 작은 쪽만큼은 swap 해버리는 타입2 연산을 하면 된다.
		int numOp2 = Math.min(countDiffZeros, countDiffOnes);
		
		// 나머지는 비트 하나를 바꾸는 타입1 연산을 해야 된다.
		int numOp1 = Math.max(countDiffZeros, countDiffOnes) - numOp2;
		
		return numOp1 + numOp2;
	}
}
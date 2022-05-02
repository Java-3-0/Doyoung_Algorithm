// 11840KB, 80ms

package bj6603;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static final int MAX_K = 13;
	
	static int K;
	static int[] seq = new int[MAX_K];
	static int[] combi = new int[6];
	static StringBuilder sb = new StringBuilder();
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		

		while (true) {
			st = new StringTokenizer(br.readLine(), " ");
			
			// 수열 크기 입력
			K = Integer.parseInt(st.nextToken());
			if (K == 0) {
				break;
			}
			
			// 수열 입력
			for (int i = 0; i < K; i++) {
				seq[i] = Integer.parseInt(st.nextToken());
			}
			
            // 조합 만들기
			combination(0, 0);
			
			// 테스트 케이스 사이 빈 줄
			sb.append("\n");
		}
		
		System.out.print(sb.toString());

	} // end main
	
	public static void combination(int startIdx, int cnt) {
		// 조합을 완성한 경우
		if (cnt == 6) {
			for (int num : combi) {
				sb.append(num).append(" ");
			}
			sb.append("\n");
			return;
		}
		
		// 조합을 완성하지 못한 채로 끝까지 간 경우
		if (startIdx == K) {
			return;
		}
		
		// 조합을 더 만들어봐야 하는 경우
		for (int idx = startIdx; idx < K; idx++) {
			combi[cnt] = seq[idx];
			combination(idx + 1, cnt + 1);
		}
	}

}
// 11584KB, 84ms

package bj1449;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	public static final int MAX_POS = 1000;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 첫 줄 입력
		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int L = Integer.parseInt(st.nextToken());
		
		// 둘째 줄 입력
		boolean[] isLeaked = new boolean[MAX_POS + 1];
		Arrays.fill(isLeaked, false);
		
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			int pos = Integer.parseInt(st.nextToken());
			isLeaked[pos] = true;
		}
		
		// 모든 칸을 확인
		int count = 0;
		for (int pos = 0; pos <= MAX_POS; pos++) {
			// 물이 새고 있으면 이 칸부터 오른쪽으로 테이프 길이만큼 막는다.
			if (isLeaked[pos]) {
				for (int delta = 0; delta < L; delta ++) {
					if (pos + delta <= MAX_POS) {
						isLeaked[pos + delta] = false;
					}
				}
				
				count++;
			}
		}
		
		// 결과 출력
		System.out.println(count);
	}
}
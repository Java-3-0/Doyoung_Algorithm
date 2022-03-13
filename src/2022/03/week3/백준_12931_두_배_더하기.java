// 11572KB, 76ms

package bj12931;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N 입력
		int N = Integer.parseInt(br.readLine());

		// 수열 입력
		int[] seq = new int[N];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}
		
		// 값을 1 증가시키는 연산의 회수를 센다
		int cntAdd = 0;
		for (int num : seq) {
			cntAdd += Integer.bitCount(num);
		}
		
		// 값을 2배 시키는 연산의 회수를 센다.
		int cntMult = 0;
		int maxNum = 0;
		for (int num : seq) {
			maxNum = maxNum < num ? num : maxNum;
		}
		while (maxNum > 1) {
			maxNum /= 2;
			cntMult++;
		}
		
		// 두 연산 횟수의 합이 정답
		int answer = cntAdd + cntMult;

		// 출력
		System.out.println(answer);
	}

}
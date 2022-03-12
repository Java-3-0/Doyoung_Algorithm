// 11652KB, 80ms

package bj1527;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_NUM = 1000000000;
	static int[] permu = new int[9];
	static List<Integer> numbersGM = new ArrayList<>();
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		// 입력
		st = new StringTokenizer(br.readLine(), " ");
		int A = Integer.parseInt(st.nextToken());
		int B = Integer.parseInt(st.nextToken());
		
		// 모든 순열 완전탐색
		permutation(0);
		
		// 모든 금민수들 중 조건을 만족하는 금민수의 개수 파악
		int answer = 0;
		for (int num : numbersGM) {
			if (A <= num && num <= B) {
				answer++;
			}
		}

		// 정답 출력
		System.out.println(answer);
	}

	/** 모든 순열을 완전탐색하면서 금민수를 만들어서 numbersGM에 넣는다 */
	public static void permutation(int idx) {
		// 현재까지 만들어진 순열을 numbersGM에 추가한다.
		if (idx >= 1) {
			int tmp = 0;
			for (int i = 0; i < idx; i++) {
				tmp *= 10;
				tmp += permu[i];
			}
			
			numbersGM.add(tmp);
		}
		
		// 10자리 수 이상은 만들지 않는다.
		if (idx == 9) {
			return;
		}
		
		// 순열을 계속 만들어본다
		permu[idx] = 4;
		permutation(idx + 1);
		
		permu[idx] = 7;
		permutation(idx + 1);
	}
}
// 13048KB, 100ms

package bj5568;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Main {
	static final int MAX_N = 10;
	static final int MAX_K = 4;

	static Set<Long> set = new HashSet<>();
	static int N;
	static int K;
	static int[] cards = new int[MAX_N];
	static boolean[] isSelected = new boolean[MAX_N];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// N, K 입력
		N = Integer.parseInt(br.readLine());
		K = Integer.parseInt(br.readLine());
		
		// 카드 입력
		for (int i = 0; i < N; i++) {
			cards[i] = Integer.parseInt(br.readLine());
		}

		// 모든 순열을 시도해 보면서 만들 수 있는 수들을 set에 넣어본다.
		permutation(0, "");
		
		// 만든 수의 개수를 출력한다.
		System.out.println(set.size());
	}

	/** 카드를 넣는 모든 순열을 시행해 보면서, 만들어진 수를 set에 넣는다 */
	public static void permutation(int cnt, String accum) {
		// base case: 원하는 개수만큼 넣었다면 정수를 만들어서 set에 넣고 리턴
		if (cnt == K) {
			long l = Long.parseLong(accum);
			set.add(l);
			return;
		}
		
		// 아직 순열이 완성되지 않은 경우 계속 진행
		for (int i = 0; i < N; i++) {
			if (isSelected[i]) {
				continue;
			}
			
			isSelected[i] = true;
			permutation(cnt + 1, accum + cards[i]);
			isSelected[i] = false;
		}
	}
}
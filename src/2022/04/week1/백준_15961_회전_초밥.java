// 345476KB, 1228ms

package bj15961;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 3000000;
	static final int MAX_D = 3000;
	static final int MAX_K = 3000;
	static final int MAX_C = MAX_D;

	static int N, D, K, C;
	static int[] seq;
	static Map<Integer, Integer> counts = new HashMap<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, D, K, C 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		D = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());

		// 수열을 입력받아서 원형으로 seq에 담기
		seq = new int[2 * N - 1];
		for (int i = 0; i < N - 1; i++) {
			int input = Integer.parseInt(br.readLine());
			seq[i] = input;
			seq[i + N] = input;
		}
		seq[N - 1] = Integer.parseInt(br.readLine());

		// 초기 k개 처리
		for (int i = 0; i < K; i++) {
			addCount(seq[i]);
		}
		
		// 초기 상태에서의 초밥 가짓수 계산
		int maxTypes = counts.size();
		if (!counts.containsKey(C)) {
			maxTypes++;
		}
		
		// 나머지 처리
		int len = seq.length;
		for (int i = K; i < len; i++) {
			addCount(seq[i]);
			removeCount(seq[i - K]);
			int types = counts.size();
			if (!counts.containsKey(C)) {
				types++;
			}
			
			// 정답 갱신
			maxTypes = maxTypes < types ? types : maxTypes;
		}
		
		System.out.println(maxTypes);

	} // end main

	public static void addCount(int key) {
		if (counts.containsKey(key)) {
			counts.put(key, 1 + counts.get(key));
		} else {
			counts.put(key, 1);
		}
	}

	public static boolean removeCount(int key) {
		if (!counts.containsKey(key)) {
			return false;
		}

		int val = counts.get(key);
		if (val == 1) {
			counts.remove(key);
		} else {
			counts.put(key, val - 1);
		}

		return true;
	}

}
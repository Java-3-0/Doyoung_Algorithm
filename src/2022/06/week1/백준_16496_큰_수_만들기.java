// 20380KB, 124ms

package bj16496;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// N 입력
		int N = Integer.parseInt(br.readLine());

		// 수들을 문자열 형태로 입력받기
		PriorityQueue<String> pq = new PriorityQueue<>(comp);
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			pq.add(st.nextToken());
		}

		// 사전순으로 가장 앞서는 단어의 첫 글자를 쓰는 것을 반복
		while (!pq.isEmpty()) {
			String word = pq.poll();
			sb.append(word);
		}
		
		// 앞쪽의 0 제거
		String answer = sb.toString();
		
		// 정답 출력
		if (answer.charAt(0) == '0') {
			System.out.println(0);
		}
		else {
			System.out.println(answer);
		}

	} // end main

	/** 문자열을 사전 역순으로 정렬. (s1s2와 s2s1을 비교하는 방식으로) */
	public static Comparator<String> comp = new Comparator<String>() {
		@Override
		public int compare(String s1, String s2) {
			String s1s2 = s1 + s2;
			String s2s1 = s2 + s1;

			return -s1s2.compareTo(s2s1);
		}
	};

}
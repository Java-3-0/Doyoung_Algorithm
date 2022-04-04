// 688964KB, 2408ms

package bj11003;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Deque;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;

		// N, L 입력
		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int L = Integer.parseInt(st.nextToken());

		// 인덱스를 저장할 dq
		Deque<Integer> dq = new LinkedList<>();

		// 값을 저장할 배열
		int[] values = new int[N];

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			int val = Integer.parseInt(st.nextToken());
			values[i] = val;

			// 나보다 앞에 들어간 값들 중, 나보다 큰 값들은 이제부터 최소값이 될 수 없으므로 없앤다.
			while (!dq.isEmpty() && val <= values[dq.peekLast()]) {
				dq.pollLast();
			}

			// 내가 dq의 맨 뒤에 들어간다
			dq.addLast(i);

			// L 범위를 지나친 것들을 dq의 앞부분에서 삭제
			while (dq.peekFirst() < i - L + 1) {
				dq.pollFirst();
			}

			// 찾으려는 최소값
			int answer = values[dq.peekFirst()];
			bw.write(answer + " ");
		}

		// 출력
		bw.flush();
		bw.close();

	} // end main

}
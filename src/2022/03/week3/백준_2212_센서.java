// 15132KB, 152ms

package bj2212;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static int N, K;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 센서 수, 집중국 수 입력
		N = Integer.parseInt(br.readLine());
		K = Integer.parseInt(br.readLine());

		// 센서들의 좌표 입력
		int[] sensors = new int[N];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			sensors[i] = Integer.parseInt(st.nextToken());
		}

		// 센서들을 오름차순으로 정렬
		Arrays.sort(sensors);

		// 센서들 사이의 거리 차이들을 내림차순으로 꺼내 올 우선순위 큐를 만든다.
		PriorityQueue<Integer> diffs = new PriorityQueue<Integer>(Collections.reverseOrder());
		for (int i = 1; i < N; i++) {
			diffs.offer(sensors[i] - sensors[i - 1]);
		}

		// K개의 집중국이 있다면, K - 1개의 차이를 무시할 수 있다
		for (int i = 0; i < K - 1; i++) {
			if (!diffs.isEmpty()) {
				diffs.poll();
			}
		}

		// 남은 거리 차이들을 모두 더한 값이 집중국의 수신 가능 영역 길이 합의 최소값이다.
		int answer = 0;
		for (int num : diffs) {
			answer += num;
		}

		System.out.println(answer);
	}

}

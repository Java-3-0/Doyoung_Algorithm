// 22956KB, 248ms

package bj20117;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 100000;

	static int N;
	static int[] qualities;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 호반우 개수 입력
		N = Integer.parseInt(br.readLine());

		// 각 호반우의 품질 입력
		qualities = new int[N];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			qualities[i] = Integer.parseInt(st.nextToken());
		}

		// 호반우 품질 오름차순 정렬
		Arrays.sort(qualities);

		// (가장 낮은 것, 가장 높은 것)으로 2개씩 짝짓는 것이 최선
		int answer = 0;
		for (int i = 0; i < N / 2; i++) {
			answer += (2 * qualities[N - 1 - i]);
		}

		// 홀수 개면, 마지막 남은 중간값 하나를 따로 처리
		if (N % 2 == 1) {
			answer += qualities[N / 2];
		}

		// 출력
		System.out.println(answer);

	}

}
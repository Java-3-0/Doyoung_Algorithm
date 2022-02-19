// 17012KB, 188ms

package bj20300;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		Long[] loss;

		// 개수가 짝수이면 그대로 쓰고
		if (N % 2 == 0) {
			loss = new Long[N];
		}
		// 개수가 홀수이면 0인 무게를 하나 넣는다.
		else {
			loss = new Long[N + 1];
			loss[loss.length - 1] = 0L;
		}

		// 입력
		for (int i = 0; i < N; i++) {
			loss[i] = Long.parseLong(st.nextToken());
		}

		// 무게 오름차순으로 정렬
		Arrays.sort(loss);

		// 매 순간마다 제일 무게가 낮은 것 하나와 제일 무게가 높은 것 하나를 함께 사용
		long answer = 0;
		int len = loss.length;
		for (int i = 0; i < len / 2; i++) {
			long sumOfTwo = loss[i] + loss[len - 1 - i];
			if (answer < sumOfTwo) {
				answer = sumOfTwo;
			}
		}

		System.out.println(answer);
	}
}

// 33224KB, 452ms

package bj20115;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 에너지 드링크 개수 입력
		int N = Integer.parseInt(br.readLine());
		
		// 에너지 드링크들을 입력받아서 배열에 저장
		Long[] drinks = new Long[N];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			drinks[i] = Long.parseLong(st.nextToken());
		}
		
		// 에너지 드링크들을 양의 오름차순으로 정렬
		Arrays.sort(drinks);
		
		// 제일 양이 큰 곳에 나머지 드링크들을 붓는다.
		double sum = 0.0;
		for (int i = 0; i < N - 1; i++) {
			sum += (double) drinks[i] / 2.0;
		}
		sum += (double) drinks[N - 1];
		
		// 정답 출력
		System.out.printf("%.7f\n", sum);
	}
}
// 24952KB, 352ms

package bj2217;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		Integer[] ropes = new Integer[N];
		
		// 로프 입력
		for (int i = 0; i < N; i++) {
			ropes[i] = Integer.parseInt(br.readLine());
		}
		
		// 로프를 버틸 수 있는 중량의 내림차순으로 정렬
		Arrays.sort(ropes, Collections.reverseOrder());
		
		// 제일 강한 로프부터 하나씩 넣어보면서 최대 무게 갱신
		int maxWeight = 0;
		for (int i = 0; i < ropes.length; i++) {
			int weight = (i + 1) * ropes[i];
			if (maxWeight < weight) {
				maxWeight = weight;
			}
		}
		
		// 정답 출력
		System.out.println(maxWeight);
	}
}
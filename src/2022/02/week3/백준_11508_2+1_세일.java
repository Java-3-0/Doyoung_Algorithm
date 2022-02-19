// 26212KB, 388ms

package bj11508;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 입력을 받고, 전체 가격의 합 계산
		int N = Integer.parseInt(br.readLine());
		Long[] costs = new Long[N];
		long sum = 0L;
		for (int i = 0; i < N; i++) {
			long inputNum = Long.parseLong(br.readLine());
			costs[i] = inputNum;
			sum += inputNum;
		}
		
		// 가격이 비싼 것부터 내림차순으로 정렬 
		Arrays.sort(costs, Collections.reverseOrder());
		
		// 최대 할인량 계산
		long discount = 0;
		for (int i = 2; i < N; i += 3) {
			discount += costs[i];
		}
		
		// 전체 가격 - 최대 할인량이 최소 가격
		long answer = sum - discount;
		
		// 정답 출력
		System.out.println(answer);
	}
}
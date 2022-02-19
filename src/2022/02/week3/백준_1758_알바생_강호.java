// 23092KB, 248ms

package bj1758;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine());
		
		// 손님 정보 입력
		Long[] customers = new Long[N];
		for (int i = 0; i < N; i++) {
			customers[i] = Long.parseLong(br.readLine());
		}
		
		// 팁의 내림차순으로 손님 정렬
		Arrays.sort(customers, Collections.reverseOrder());
		
		// 최대 팁 계산 : 팁을 줄 수 있는 손님 중 가장 많이 주는 손님을 택한다
		long waiting = 0L;
		long sum = 0L;
		for (long tip : customers) {
			if (waiting < tip) {
				sum += (tip - waiting);
				waiting++;
			}
			else {
				break;
			}
		}
		
		// 정답 출력
		System.out.println(sum);
	}
}
// 11524KB, 80ms

package bj1669;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		// 입력
		int monkey = Integer.parseInt(st.nextToken());
		int dog = Integer.parseInt(st.nextToken());
		
		// 증가시켜야 하는 키의 총량 계산
		int diff = dog - monkey;
		
		// 그 총량을 증가시킬 수 있는 일 수 중 최소치를 구함
		int answer = minDays(diff);
		
		// 정답 출력
		System.out.println(answer);
	}
	
	/** 이분탐색을 통해 diff 이상의 키를 변화시킬 수 있는 최소 일수를 찾는다 */
	public static int minDays(int diff) {
		int left = 0;
		int right = Integer.MAX_VALUE;
		
		// left <= answer <= right;
		while (left < right) {
			int mid = (left + right) / 2;
			if (diff <= maxHeightChange(mid)) {
				right = mid;
			}
			else {
				left = mid + 1;
			}
		}
		
		return right;
	}
	
	/** 주어진 기간 동안 가장 많이 키를 변화시킬 수 있는 양을 리턴 */
	public static long maxHeightChange (int days) {
		long half = (long) (days / 2) ;
		
		if (days % 2 == 0) {
			return half * (half + 1L);
		}
		else {
			return half * (half + 1L) + half + 1L;
		}
	}
}
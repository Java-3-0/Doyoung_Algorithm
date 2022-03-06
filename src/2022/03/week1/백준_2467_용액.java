// 32384KB, 272ms

package bj2467;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 용액 수 입력
		int N = Integer.parseInt(br.readLine());
		
		// 용액 입력
		st = new StringTokenizer(br.readLine(), " ");
		long[] liquids = new long[N];
		for (int i = 0; i < N; i++) {
			liquids[i] = Long.parseLong(st.nextToken());
		}
		
		// 두 용액의 합이 0에 가장 가까운 것을 찾는다.
		int left = 0;
		int right = N - 1;
		
		long minVal = Long.MAX_VALUE;
		int minIdx1 = left;
		int minIdx2 = right;
		
		while (left < right) {
			// 용액의 특성값 합계 계산
			long sum = liquids[left] + liquids[right];
			
			// 특성값과 0과의 차이가 얼마나 되는지 계산하고, 기존보다 줄어들었다면 갱신
			long sumAbs = Math.abs(sum);
			if (sumAbs < minVal) {
				minVal = sumAbs;
				minIdx1 = left;
				minIdx2 = right;
			}
			
			// 합계가 양수라면 오른쪽 커서를 한 칸 왼쪽으로 옮긴다.
			if (sum > 0L) {
				right--;
			}
			// 합계가 음수라면 왼쪽 커서를 한 칸 오른쪽으로 옮긴다.
			else if (sum < 0L) {
				left++;
			}
			// 합계가 0이라면 이것보다 더 좋은 것은 없으니 탐색 종료한다.
			else {
				break;
			}
		}
		
		// 찾은 답을 스트링빌더에 추가
		sb.append(liquids[minIdx1]).append(" ").append(liquids[minIdx2]).append("\n");

		// 출력
		System.out.print(sb.toString());
		
	} // end main

}
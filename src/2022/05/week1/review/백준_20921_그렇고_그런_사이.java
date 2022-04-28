// 12124KB, 88ms

package bj20921;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 입력
		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		
		// 조건에 맞는 수열을 만든다
		int[] seq = solve(N, K);

		// 수열을 출력용 스트링빌더에 담는다
		for (int num : seq) {
			sb.append(num).append(" ");
		}
		sb.append("\n");
		
		// 출력
		System.out.print(sb.toString());
		
		
	} // end main
	
	/** N개의 정수에서 K개의 그렇고 그런 사이가 만들어지게 하는 수열을 리턴한다 */
	public static int[] solve(int N, int K) {
		// 사용 가능한 수들을 오름차순으로 deque에 넣는다
		Deque<Integer> deque = new ArrayDeque<Integer>();
		for (int num = 1; num <= N; num++) {
			deque.addLast(num);
		}
		
		// 앞에서부터, 수열의 각 위치에 들어갈 수를 정한다
		int[] ret = new int[N];
		for (int i = 0; i < N; i++) {
			// 이 위치보다 오른쪽에 있는 수 개수를 센다
			int rightCnt = N - 1 - i;
			
			// 필요한 남은 쌍 수가 rightCnt개 이상이면, 가장 큰 수를 넣는다. (rightCnt개의 쌍이 만들어짐)
			if (rightCnt <= K) {
				ret[i] = deque.pollLast();
				K -= rightCnt;
			}
			
			// 그렇지 않다면, 가장 작은 수를 넣는다
			else {
				ret[i] = deque.pollFirst();
			}
		}
		
		return ret;
	}

}
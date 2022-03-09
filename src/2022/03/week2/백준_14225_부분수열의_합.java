// 57452KB, 392ms

package bj14225;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N 입력
		int N = Integer.parseInt(br.readLine());
		
		// 수열 입력
		st = new StringTokenizer(br.readLine(), " ");
		int[] seq = new int[N];
		for (int i = 0; i < N; i++) {
			int input = Integer.parseInt(st.nextToken());
			seq[i] = input;
		}
		
		// 모든 permutation을 bitmask로 나타내고, 각 경우에 대한 합계를 possibleNumbers에 저장
		Set<Integer> possibleNumbers = new HashSet<>();
		for (int bitmask = 1; bitmask < (1 << N); bitmask++) {
			int sum = 0;
			for (int i = 0; i < N; i++) {
				if ((bitmask & (1 << i)) != 0) {
					sum += seq[i];
				}
			}
			
			possibleNumbers.add(sum);
		}
		
		// possibleNumbers에 없는 가장 작은 자연수를 찾는다.
		int answer = 20 * 100000 + 1;
		for (int num = 1; num <= 20 * 100000; num++) {
			if (!possibleNumbers.contains(num)) {
				answer = num;
				break;
			}
		}
		
		// 출력
		System.out.println(answer);
		
	} // end main

}
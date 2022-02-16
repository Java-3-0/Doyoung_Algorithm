// 359636KB, 1780ms

package bj18870;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
 
public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;

		int N = Integer.parseInt(br.readLine());

		// 입력을 받아서 배열에 저장
		st = new StringTokenizer(br.readLine(), " ");
		int[] input = new int[N];
		for (int i = 0; i < N; i++) {
			input[i] = Integer.parseInt(st.nextToken());
		}
		
		// 입력 받은 배열이 오름차순으로 정렬된 배열 하나 생성
		int[] inputCopyAsc = Arrays.copyOf(input, input.length);
		Arrays.sort(inputCopyAsc);
		
		// X -> X` 으로의 매핑 생성
		Map<Integer, Integer> prime = new HashMap<>();
		int cnt = 0;
		for (int num : inputCopyAsc) {
			if (!prime.containsKey(num)) {
				prime.put(num, cnt);
				cnt++;
			}
		}
		
		// 각 input에 들은 X에 대해 X`을 출력에 저장
		for (int num : input) {
			sb.append(prime.get(num)).append(" ");
		}
		sb.append("\n");
		
		// 출력
		System.out.print(sb.toString());
	}
}
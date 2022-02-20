// 11660KB, 76ms

package bj16206;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
	public static int N;
	public static int M;
	
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(br.readLine(), " ");
		Integer[] cakes = new Integer[N];
		for (int i = 0; i < N; i++) {
			cakes[i] = Integer.parseInt(st.nextToken());
		}
		
		// 최대 케익 수 계산
		int answer = maxCakes(cakes);
		
		// 출력
		System.out.println(answer);
	}
	
	/** 케익 길이의 배열이 주어질 때, 10짜리 크기의 케익을 만들 수 있는 최대 개수를 리턴 */
	public static int maxCakes (Integer[] cakes) {
		// 10으로 나눈 나머지로 오름차순, 같을 경우에는 원래 수의 오름차순으로 정렬
		Arrays.sort(cakes, new Comparator<Integer>() {
			@Override
			public int compare(Integer num1, Integer num2) {
				if (num1 % 10 == num2 % 10) {
					return num1 - num2;
				}
				return num1 % 10 - num2 % 10;
			}
		});

		int cakeCount = 0;
		int cutCount = 0;
		int idx = 0;
		while (idx < N) {
			// 케익이 10이면 세고, 다음 케익으로 진행
			if (cakes[idx] == 10) {
				cakeCount++;
				idx++;
			}
			
			// 케익이 10보다 작은 상태면 다음 케익으로 진행
			else if (cakes[idx] < 10) {
				idx++;
			}
			
			// 케익이 10보다 크고 아직 더 자를 수 있는 횟수가 남아있는 상태면 10짜리 한 조각 자르고, 이번 케익 다시 진행해야 하니 인덱스는 그대로
			else {
				if (cutCount < M) {
					cakes[idx] -= 10;
					cakeCount++;
					cutCount++;
				}
				else {
					idx++;
				}
			}
		}
		
		return cakeCount;
	}
}
// 11772KB, 80ms

package bj2503;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static final int MAX_NUM = 999;
	public static final int DIGITS = 10;
	
	public static int N;
	public static int[] guesses;
	public static int[] strikes;
	public static int[] balls;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 입력 받아서 배열에 저장
		N = Integer.parseInt(br.readLine());
		guesses = new int[N];
		strikes = new int[N];
		balls = new int[N];
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			guesses[i] = Integer.parseInt(st.nextToken());
			strikes[i] = Integer.parseInt(st.nextToken());
			balls[i] = Integer.parseInt(st.nextToken());
		}
		
		// 모든 수에 대해 가능 여부 파악
		int count = 0;
		for (int digit1 = 1; digit1 <= 9; digit1++) {
			for (int digit2 = 1; digit2 <= 9; digit2++) {
				if (digit2 == digit1) continue;
				
				for (int digit3 = 1; digit3 <= 9; digit3++) {
					if (digit3 == digit1 || digit3 == digit2) continue;
					
					int number = digit1 * 100 + digit2 * 10 + digit3;
					if (isPossible(number)) {
						count++;
					}
				}
			}
		}
		
		// 가능한 개수 출력
		System.out.println(count);
	}

	/** 모든 guess들에 수 x가 가능한 지 여부를 리턴 */
	public static boolean isPossible(int x) {
		for (int i = 0; i < N; i++) {
			if (!isPossible(x, guesses[i], strikes[i], balls[i])) {
				return false;
			}
		}
		
		return true;
	}
	
	/** guess 하나의 케이스에 대해서 수 x가 가능한 지 여부를 리턴 */
	public static boolean isPossible(int x, int guess, int strike, int ball) {
		String gStr = String.valueOf(guess);
		String xStr = String.valueOf(x);
		
		int strikeCount = 0;
		int ballCount = 0;

		for (int i = 0; i < 3; i++) {
			if (gStr.charAt(i) == xStr.charAt(i)) {
				strikeCount++;
			}
			else {
				for (int j = 0; j < 3; j++) {
					if (j == i) {
						continue;
					}
					
					if (gStr.charAt(i) == xStr.charAt(j)) {
						ballCount++;
						break;
					}
				}
			}
		}
	
		if (strikeCount == strike && ballCount == ball) {
			return true;
		}
		return false;
	}
}
// 11688KB, 80ms

package bj1992;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static final int MAX_N = 64;
	public static char[][] arr;
	public static StringBuilder sb= new StringBuilder();
	
	public static void main(String[] args) throws IOException {
		// 입력
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		arr = new char[N][];
		for (int i = 0; i < N; i++) {
			arr[i] = br.readLine().toCharArray();
		}
		
		// 쿼드 트리 압축을 실행
		quad(0, 0, N);
		
		// 결과 출력
		System.out.println(sb.toString());
	}

	/** 쿼드트리 압축을 수행하면서 sb에 결과를 담는다 */
	public static void quad (int startY, int startX, int size) {
		// 한 글자로 압축 가능한 경우
		if (isSame(startY, startX, size)) {
			sb.append(arr[startY][startX]);
			return;
		}
		
		// 압축 불가능한 경우 1/4씩 나눠서 재귀
		sb.append("(");
		int nextSize = size / 2;
		quad(startY, startX, nextSize);
		quad(startY, startX + nextSize, nextSize);
		quad(startY + nextSize, startX, nextSize);
		quad(startY + nextSize, startX + nextSize, nextSize);
		sb.append(")");
	}
	
	/** arr에서 범위 내의 값이 모두 같다면 true, 아니면 false를 리턴 */
	public static boolean isSame(int startY, int startX, int size) {
		char firstVal = arr[startY][startX];
		
		for (int dy = 0; dy < size; dy++) {
			for (int dx = 0; dx < size; dx++) {
				if (arr[startY + dy][startX + dx] != firstVal) {
					return false;
				}
			}
		}
		
		return true;
	}
}

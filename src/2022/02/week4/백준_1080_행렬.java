// 11632KB, 80ms

package bj1080;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static final int MAX_SIZE = 50;
	public static int[][] matrixA = new int[MAX_SIZE][MAX_SIZE];
	public static int[][] matrixB = new int[MAX_SIZE][MAX_SIZE];
	
	public static int height, width;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		height = Integer.parseInt(st.nextToken());
		width = Integer.parseInt(st.nextToken());

		// 행렬 A 입력
		for (int y = 0; y < height; y++) {
			String line = br.readLine();
			for (int x = 0; x < width; x++) {
				matrixA[y][x] = (line.charAt(x) - '0');
			}
		}

		// 행렬 B 입력
		for (int y = 0; y < height; y++) {
			String line = br.readLine();
			for (int x = 0; x < width; x++) {
				matrixB[y][x] = (line.charAt(x) - '0');
			}
		}
		
		// 최소 연산 수 계산
		int answer = countOperations();
		
		// 정답 출력
		System.out.println(answer);
	}

	/** matrixA를 matrixB로 바꾸는데 필요한 연산의 횟수를 리턴. 바꿀 수 없다면 -1 리턴 */
	public static int countOperations() {
		int count = 0;
		// 모든 시작 지점에 대해서 그 칸의 값이 matrixB의 칸의 값과 다르면 그 칸부터의 3 * 3 칸을 갱신
		for (int startY = 0; startY < height - 2; startY++) {
			for (int startX = 0; startX < width - 2; startX++) {
				if (matrixA[startY][startX] != matrixB[startY][startX]) {
					flip3By3(startY, startX);
					count++;
				}
			}
		}
		
		// 모두 수행하고 났는데도 A != B라면 불가능
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (matrixA[y][x] != matrixB[y][x]) {
					return -1;
				}
			}
		}
		
		// 가능했던 경우, 카운트를 리턴
		return count;
	}

	/** matrixA에서 (startY, startX) 지점부터 시작되는 3x3 영역을 뒤집는다. */
	public static void flip3By3(int startY, int startX) {
		for (int dy = 0; dy < 3; dy++) {
			for (int dx = 0; dx < 3; dx++) {
				matrixA[startY + dy][startX + dx] = (matrixA[startY + dy][startX + dx] == 1 ? 0 : 1);
			}
		}
	}
}
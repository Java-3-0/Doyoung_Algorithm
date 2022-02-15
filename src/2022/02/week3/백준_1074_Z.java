// 11552KB, 76ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws NumberFormatException, IOException {
		// 입력
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int y = Integer.parseInt(st.nextToken());
		int x = Integer.parseInt(st.nextToken());
		br.close();

		// 방문 순서 계산
		int answer = visitCount(y, x, (int) Math.pow(2, N));

		// 출력
		System.out.println(answer);
	}

	/** size * size 크기의 정사각형에서 (y, x)가 몇 번째로 방문되는지를 리턴 */
	public static int visitCount(int y, int x, int size) {
		// base case : 1x1 사각형인 경우. 문제에서 방문 순서는 1이 아니라 0부터 시작한다고 보니까 0 리턴
		if (size == 1) {
			return 0;
		}

		// 다음 재귀호출에서 사용할 size, y좌표, x좌표 계산
		int nextSize = size / 2;
		int nextY = y % nextSize;
		int nextX = x % nextSize;

		// 왼쪽 사분면인지 오른쪽 사분면인지 나타냄
		int quadrantX = x / nextSize;
		// 위쪽 사분면인지 아래쪽 사분면인지 나타냄
		int quadrantY = y / nextSize;

		// 한 사분면의 넓이
		int oneQuadrantArea = nextSize * nextSize;

		// 내가 속한 사분면보다 먼저 방문하는 다른 사분면들의 넓이의 합 + 내가 속한 사분면을 재귀호출해서 얻은 내 순서
		return (quadrantY * 2 + quadrantX) * oneQuadrantArea + visitCount(nextY, nextX, nextSize);
	}
}

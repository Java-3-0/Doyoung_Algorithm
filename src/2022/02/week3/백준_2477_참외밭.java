// 17672KB, 204ms

package baek2477;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
	// 각 숫자가 나타내는 방향
	public static final int EAST = 1, WEST = 2, SOUTH = 3, NORTH = 4;

	// 동 -> 북 -> 서 -> 남 -> 동 방향이 시계방향
	public static final Map<Integer, Integer> counterClockwise = new HashMap<Integer, Integer>() {
		private static final long serialVersionUID = -2184080850108774261L;
		{
			put(EAST, NORTH);
			put(NORTH, WEST);
			put(WEST, SOUTH);
			put(SOUTH, EAST);
		}
	};

	public static void main(String[] args) {
		// 입력
		Scanner sc = new Scanner(System.in);
		int multiply = sc.nextInt();
		
		int[] directions = new int[6];
		int[] lengths = new int[6];
		for (int i = 0; i < 6; i++) {
			directions[i] = sc.nextInt();
			lengths[i] = sc.nextInt();
		}

		// 혼자만 시계방향으로 진행되는 선 파악 -> 그 선과 그 이전 선이 작은 사각형을 이룸
		int smallSide1 = 0;
		int smallSide2 = 0;
		for (int i = 0; i < 6; i++) {
			int prevDir = directions[prevIdx(i)];
			int curDir = directions[i];

			if (counterClockwise.get(prevDir) != curDir) {
				smallSide1 = lengths[prevIdx(i)];
				smallSide2 = lengths[i];
			}
		}
		
		// 작은 사각형의 넓이 계산
		int smallArea = smallSide1 * smallSide2;

		// 큰 사각형의 넓이 계산
		int bigSide1 = 0;
		int bigSide2 = 0;
		for (int i = 0; i < 3; i++) {
			bigSide1 = Math.max(bigSide1, lengths[2 * i]);
			bigSide2 = Math.max(bigSide2, lengths[2 * i + 1]);
		}
		int bigArea = bigSide1 * bigSide2;
		
		// 참외밭의 넓이 계산
		int area = bigArea - smallArea;
		
		// 참외 수 계산
		int fruits = area * multiply;
		
		// 출력
		System.out.println(fruits);
	}
	
	/** 배열 범위를 벗어나지 않고 원형으로 탐색할 때, 이전 인덱스를 구한다 */
	public static int prevIdx (int n) {
		return (n + 6 - 1) % 6;
	}
}
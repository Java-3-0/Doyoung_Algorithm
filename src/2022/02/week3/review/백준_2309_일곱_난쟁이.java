// 12912KB, 108ms

package baek2309;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
	public static final int NUM_DWARVES = 9;
	public static final int TARGET_HEIGHT = 100;
	
	public static void main(String[] args) {
		// 입력
		Scanner sc = new Scanner(System.in);
		int[] heights = new int[NUM_DWARVES];
		for (int i = 0; i < NUM_DWARVES; i++) {
			heights[i] = sc.nextInt();
		}
		sc.close();
		
		// 난쟁이들을 키의 오름차순으로 정렬
		Arrays.sort(heights);
		
		// 9명의 키의 합 계산
		int total = getSum(heights);
		// 목표하는 키와의 차이를 계산해서 difference에 저장
		int difference = total - TARGET_HEIGHT;
		// 2명의 합이 difference가 되는 두 난쟁이를 찾음
		Set<Integer> excludeIndexes = new HashSet<>();
		outer: for (int i = 0; i < NUM_DWARVES; i++) {
			for (int j = i + 1; j < NUM_DWARVES; j++) {
				if (heights[i] + heights[j] == difference) {
					excludeIndexes.add(i);
					excludeIndexes.add(j);
					break outer;
				}
			}
		}
		
		// 이 두 난쟁이를 제외한 나머지 난쟁이들을 출력
		for (int i = 0; i < NUM_DWARVES; i++) {
			if (!excludeIndexes.contains(i)) {
				System.out.println(heights[i]);
			}
		}

		return;
	}

	/** 정수형 배열에 담긴 모든 수의 합을 리턴 */
	public static int getSum(int[] arr) {
		int sum = 0;
		for (int num : arr) {
			sum += num;
		}
		return sum;
	}
}

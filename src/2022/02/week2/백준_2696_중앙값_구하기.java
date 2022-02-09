// 32160KB, 456ms

package baek2698;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int T = sc.nextInt();
		for (int testCase = 1; testCase <= T; testCase++) {
			// 입력
			int length = sc.nextInt();
			int[] seq = new int[length];
			for (int i = 0; i < length; i++) {
				seq[i] = sc.nextInt();
			}
			
			// 홀수 인덱스마다 중간값 계산
			List<Integer> answer = getMedians(seq);
			
			// 출력
			StringBuilder sb = new StringBuilder();
			sb.append(answer.size()).append("\n");
			for (int num : answer) {
				sb.append(num).append(" ");
			}
			sb.append("\n");
			System.out.print(sb.toString());
		}

		sc.close();
	}

	/** 홀수 인덱스마다 중간값을 계산한 결과를 리스트에 담아서 리턴 */
	public static List<Integer> getMedians(int[] seq) {
		// 작은 숫자 절반을 내림차순으로 관리할 우선순위 큐
		PriorityQueue<Integer> smalls = new PriorityQueue<Integer>(Collections.reverseOrder());
		// 큰 숫자 절반을 오름차순으로 관리할 우선순위 큐
		PriorityQueue<Integer> bigs = new PriorityQueue<Integer>();

		// 중간값을 저장해서 리턴할 리스트
		List<Integer> ret = new ArrayList<>();

		for (int i = 0; i < seq.length; i++) {
			int num = seq[i];

			// 숫자를 크기에 맞게 한 쪽에 넣는다
			if (!bigs.isEmpty() && num > bigs.peek()) {
				bigs.offer(num);
			} else {
				smalls.offer(num);
			}

			// smalls 쪽이 1개 많거나 양쪽이 같도록 균형을 맞춰준다
			while (smalls.size() < bigs.size()) {
				smalls.offer(bigs.poll());
			}

			while (smalls.size() >= bigs.size() + 2) {
				bigs.offer(smalls.poll());
			}

			// 짝수 인덱스이면 메디안 계산
			if (i % 2 == 0) {
				int median = smalls.peek();
				ret.add(median);
			}
		}

		return ret;
	}
}

// 17680KB, 96ms

package bj2635;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int maxCount = 0;
		List<Integer> answer = new ArrayList<>();

		int firstNum = Integer.parseInt(br.readLine());

		for (int secondNum = 0; secondNum <= firstNum; secondNum++) {
			List<Integer> seq = getSequence(firstNum, secondNum);
			int count = seq.size();
			if (maxCount < count) {
				maxCount = count;
				answer = seq;
			}
		}

		sb.append(maxCount).append("\n");
		for (int num : answer) {
			sb.append(num).append(" ");
		}
		System.out.print(sb.toString());

	}

	public static List<Integer> getSequence(int firstNum, int secondNum) {
		List<Integer> ret = new LinkedList<>();

		ret.add(firstNum);
		ret.add(secondNum);

		int prev = firstNum;
		int now = secondNum;
		while (true) {
			int next = prev - now;
			if (next < 0) {
				return ret;
			} else {
				ret.add(next);
				prev = now;
				now = next;
			}
		}
	}
}

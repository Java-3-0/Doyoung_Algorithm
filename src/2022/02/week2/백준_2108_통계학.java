// 75120KB, 852ms

package bj2108;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int N = Integer.parseInt(br.readLine());
		;

		// 중앙값을 구하기 위한 배열
		long[] arr = new long[N];
		// 최빈값을 구하기 위한 맵
		Map<Long, Integer> map = new TreeMap<>();
		for (int i = -4000; i <= 4000; i++) {
			map.put((long)i, 0);
		}
		// 산술평균을 구하기 위한 합계
		long sum = 0;
		// 범위를 구하기 위한 최소, 최대
		long min = Long.MAX_VALUE;
		long max = Long.MIN_VALUE;

		for (int i = 0; i < N; i++) {
			long num = Long.parseLong(br.readLine());
			arr[i] = num;
			map.put(num, map.get(num) + 1);
			sum += num;
			if (num < min) {
				min = num;
			}
			if (max < num) {
				max = num;
			}
		}

		Arrays.sort(arr);

		double avg = (double) sum / N;
		long median = arr[N / 2];
		long range = max - min;

		long common = 0;

		int maxCount = 0;
		int nth = 0;
		for (Long key : map.keySet()) {
			int count = map.get(key);
			if (maxCount < count) {
				maxCount = count;
				common = key;
				nth = 1;
			}

			else if (maxCount == count) {
				nth++;
				if (nth == 2) {
					common = key;
				}
			}
		}

		// -0은 0으로 바꿔준다.
		String avgStr = String.format("%.0f", avg);
		if (avgStr.equals("-0")) {
			avgStr = "0";
		}
		
		sb.append(avgStr).append("\n");
		sb.append(median).append("\n");
		sb.append(common).append("\n");
		sb.append(range).append("\n");

		System.out.print(sb.toString());
	}
}

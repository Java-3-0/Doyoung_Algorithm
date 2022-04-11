// 11696KB, 80ms

package bj1700;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 100, MAX_K = 100;
	static final int INF = 987654321;

	/** 멀티탭 구멍 수, 전기용품 사용 횟수 */
	static int N, K;
	/** (각 장치마다 사용되는 시간들을 담는 Queue)의 배열 */
	static Queue<Integer>[] usedTimes;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, K 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		// usedTimes 메모리 할당
		usedTimes = new Queue[K + 1];
		for (int i = 0; i < usedTimes.length; i++) {
			usedTimes[i] = new LinkedList<Integer>();
		}

		// 시간마다 사용하는 전기용품 입력
		int[] seq = new int[K + 1];
		st = new StringTokenizer(br.readLine(), " ");
		for (int time = 1; time <= K; time++) {
			int device = Integer.parseInt(st.nextToken());
			seq[time] = device;
			usedTimes[device].offer(time);
		}

		// 플러그가 꽂혀 있는 장치들의 번호를 담고 있는 집합
		Set<Integer> set = new HashSet<>();

		// 플러그 뽑고 꽂기 시뮬레이션
		int answer = 0;
		for (int time = 1; time <= K; time++) {
			// 이 장치를 한 번 사용하니까 usedTimes 큐에서 하나 제거
			int deviceNum = seq[time];
			usedTimes[deviceNum].poll();

			// 이미 플러그가 꽂혀 있는 장치면 넘어간다
			if (set.contains(deviceNum)) {
				continue;
			}

			// 아직 콘센트 공간에 여유가 있다면 꽂는다
			if (set.size() < N) {
				set.add(deviceNum);
			}

			// 콘센트가 다 찼다면, 가장 늦게 사용될 장치를 하나 뽑고, 이 장치를 꽂는다.
			else {
				int deviceToRemove = getDeviceToPlugOut(set);
				set.remove(deviceToRemove);
				answer++; // 플러그를 뽑는 횟수 카운트
				set.add(deviceNum);
			}
		}

		// 플러그 뽑은 횟수 출력
		System.out.println(answer);

	} // end main

	/** set에 있는 장치 번호들 중, 플러그를 뽑을 장치(가장 나중에 사용될 장치)의 번호를 리턴 */
	public static int getDeviceToPlugOut(Set<Integer> set) {
		int latestTime = 0;
		int latestDevice = 0;

		for (int deviceNum : set) {
			// 다시는 사용되지 않을 장치면 뽑자
			if (usedTimes[deviceNum].size() == 0) {
				return deviceNum;
			}
			// 사용될 장치라면, 그 중 가장 늦게 사용될 장치를 찾자
			else {
				int time = usedTimes[deviceNum].peek();
				if (latestTime < time) {
					latestTime = time;
					latestDevice = deviceNum;
				}
			}
		}

		// 가장 늦게 사용될 장치를 뽑자
		return latestDevice;
	}

}
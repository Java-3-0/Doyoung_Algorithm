// 15144KB, 160ms

package bj1624;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

public class Main {
	static final int MAX_N = 1000;

	static int N;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 수의 개수 입력
		N = Integer.parseInt(br.readLine());

		// 수마다 등장하는 index들이 리스트 형태로 담긴 맵을 만든다
		TreeMap<Integer, List<Integer>> map = new TreeMap<>();
		for (int idx = 0; idx < N; idx++) {
			int num = Integer.parseInt(br.readLine());
			putToMap(map, num, idx);
		}

		// deque마다 최대한 많이 넣는 것을 시도한다
		int answer = 0;
		LOOP_DEQUE: while (!map.isEmpty()) {
			answer++;
			
			boolean isAscending = false;
			int prevIdx = MAX_N + 1;

			// 작은 수부터 시도
			int mapSize = map.size();
			Integer[] keys = map.keySet().toArray(new Integer[mapSize]);
			Arrays.sort(keys);
			for (int i = 0; i < mapSize; i++) {
				int key = keys[i];
				List<Integer> list = map.get(key);

				// idx의 내림차순으로 넣어본다
				if (!isAscending) {
					Collections.sort(list);
					
					int listSize = list.size();
					for (int l = listSize - 1; l >= 0; l--) {
						int idx = list.get(l);
						if (prevIdx > list.get(l)) {
							prevIdx = idx;
							list.remove(l);
						}
						
					} // end for l

					// 더 넣을 것이 남았다면 오름차순으로 바꾼다
					if (list.size() > 0) {
						isAscending = true;
					}
					
				} // end if (!isAscending)

				// idx의 오름차순으로 넣어본다
				if (isAscending) {
					Collections.sort(list, Collections.reverseOrder());
					
					int listSize = list.size();
					for (int l = listSize - 1; l >= 0; l--) {
						int idx = list.get(l);
						if (prevIdx < list.get(l)) {
							prevIdx = idx;
							list.remove(l);
						}
						
					} // end for l
					
				} // end if (isAscending)
				
				// 이 수를 전부 넣었다면 맵에서 삭제하고, 다음 수를 계속 시도
				if (list.isEmpty()) {
					map.remove(key);
				}
				// 이 수가 남았다면, 다른 덱을 만들어야 함
				else {
					continue LOOP_DEQUE;
				}

			} // end for key

		} // end while(!map.isEmpty())

		// 정답 출력
		System.out.println(answer);

	} // end main

	/** map에서 key가 num일 때 value가 되는 list에 idx를 넣는다 */
	public static void putToMap(TreeMap<Integer, List<Integer>> map, int num, int idx) {
		if (map.containsKey(num)) {
			List<Integer> list = map.get(num);
			list.add(idx);
			map.put(num, list);
		} else {
			List<Integer> list = new ArrayList<>();
			list.add(idx);
			map.put(num, list);
		}
	}
}
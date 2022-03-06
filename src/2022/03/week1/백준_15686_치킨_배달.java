// 16044KB, 192ms

package bj15686;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 50;
	static final int MAX_M = 13;
	static final int MAX_HOUSES = MAX_N * 2;
	static final int INF = 987654321;

	static int N, M;
	static List<Position> houses = new ArrayList<>();
	static List<Position> chickens = new ArrayList<>();
	static int[] comb;
	static int minDist = INF;
	
	static class Position {
		int y;
		int x;

		public Position(int y, int x) {
			super();
			this.y = y;
			this.x = x;
		}

		public int getDistanceTo(Position p) {
			int yDist = Math.abs(this.y - p.y);
			int xDist = Math.abs(this.x - p.x);

			return yDist + xDist;
		}

		@Override
		public String toString() {
			return "(" + y + ", " + x + ")";
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// N, M 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		comb = new int[M];
		
		// 맵 정보를 입력받아서 집과 치킨집의 위치를 리스트 형태로 저장
		for (int y = 0; y < N; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < N; x++) {
				int input = Integer.parseInt(st.nextToken());
				if (input == 1) {
					houses.add(new Position(y, x));
				}
				else if (input == 2) {
					chickens.add(new Position(y, x));
				}
			}
		}

		// 모든 조합을 완전탐색하면서 치킨 거리 최소값을 갱신
		combination(0, 0);
		
		// 출력
		System.out.println(minDist);

	} // end main
	
	/** M개의 치킨집을 남기는 모든 조합을 완전탐색하면서 minDist를 갱신한다 */
	static public void combination(int hereIdx, int cnt) {
		// 조합이 완성된 경우
		if (cnt == M) {
			minDist = Math.min(minDist, calcChickenDist());
			return;
		}
		
		// 조합을 완성하지 못하고 인덱스 끝까지 간 경우
		if (hereIdx >= chickens.size()) {
			return;
		}
		
		// 아직 조합이 완성되지 않은 경우
		comb[cnt] = hereIdx;
		combination(hereIdx + 1, cnt + 1);
		combination(hereIdx + 1, cnt);
	}
	
	/** 현재 comb 상태에서의 치킨 거리를 계산해서 리턴 */
	static public int calcChickenDist() {
		int ret = 0;
		
		// 모든 집에 대해서 수행
		for (Position house : houses) {
			// 집에서 가장 가까운 치킨집까지의 거리를 계산
			int dist = INF;
			for (int idx : comb) {
				Position chicken = chickens.get(idx);
				dist = Math.min(dist, house.getDistanceTo(chicken));
			}
			
			// 그 거리를 리턴값에 더한다
			ret += dist;
		}
		
		return ret;
	}

}
// 20572KB, 284ms

package bj13168;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
	// 상수 선언
	static final int MAX_V = 100;
	static final double INF = 9876543210.0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 교통 수단 -> 교통수단의 할인된 가격 비율로의 매핑 생성
		Map<String, Double> vehicleToRate = new HashMap<>();
		vehicleToRate.put("Subway", 1.0);
		vehicleToRate.put("Bus", 1.0);
		vehicleToRate.put("Taxi", 1.0);
		vehicleToRate.put("Airplane", 1.0);
		vehicleToRate.put("KTX", 1.0);
		vehicleToRate.put("S-Train", 0.5);
		vehicleToRate.put("V-Train", 0.5);
		vehicleToRate.put("ITX-Saemaeul", 0.0);
		vehicleToRate.put("ITX-Cheongchun", 0.0);
		vehicleToRate.put("Mugunghwa", 0.0);

		// 정점 수, 내일로 티켓 가격 입력
		st = new StringTokenizer(br.readLine(), " ");
		int V = Integer.parseInt(st.nextToken());
		double ticketPrice = Double.parseDouble(st.nextToken());

		// 도시 이름들을 입력받아서 도시 이름 -> 도시 번호로의 매핑 생성
		st = new StringTokenizer(br.readLine(), " ");
		Map<String, Integer> cityToIdx = new HashMap<>();
		for (int v = 0, idx = 0; v < V; v++) {
			String cityName = st.nextToken();
			if (!cityToIdx.containsKey(cityName)) {
				cityToIdx.put(cityName, idx++);
			}
		}

		// 방문할 도시들의 수 입력
		int M = Integer.parseInt(br.readLine());

		// 방문할 도시들 입력
		st = new StringTokenizer(br.readLine(), " ");
		List<Integer> targetCities = new ArrayList<>();
		for (int m = 0; m < M; m++) {
			String cityName = st.nextToken();
			targetCities.add(cityToIdx.get(cityName));
		}

		// 간선 수 입력
		int E = Integer.parseInt(br.readLine());

		// 일반 승객의 인접 행렬
		double[][] adjMatrixNormal = new double[V][V];
		// 내일로 티켓 구매 승객의 인접 행렬
		double[][] adjMatrixTomorrow = new double[V][V];
		// 인접 행렬 INF로 초기화
		for (int i = 0; i < V; i++) {
			Arrays.fill(adjMatrixNormal[i], INF);
			Arrays.fill(adjMatrixTomorrow[i], INF);
		}

		for (int i = 0; i < V; i++) {
			adjMatrixNormal[i][i] = 0.0;
			adjMatrixTomorrow[i][i] = 0.0;
		}

		// 인접 행렬 입력
		for (int e = 0; e < E; e++) {
			st = new StringTokenizer(br.readLine(), " ");
			String vehicleName = st.nextToken();
			int from = cityToIdx.get(st.nextToken());
			int to = cityToIdx.get(st.nextToken());
			double price = Double.parseDouble(st.nextToken());
			double salePriceRate = vehicleToRate.get(vehicleName);

			adjMatrixNormal[from][to] = Math.min(adjMatrixNormal[from][to], price);
			adjMatrixNormal[to][from] = Math.min(adjMatrixNormal[to][from], price);

			adjMatrixTomorrow[from][to] = Math.min(adjMatrixTomorrow[from][to], price * salePriceRate);
			adjMatrixTomorrow[to][from] = Math.min(adjMatrixTomorrow[to][from], price * salePriceRate);
		}

		// 일반 승객과 내일로 구매 승객의 최단 경로들을 계산
		double[][] normal = floydWarshall(adjMatrixNormal);
		double[][] tomorrow = floydWarshall(adjMatrixTomorrow);

		// 최단 경로로 도시들을 순서대로 방문하면서 비용의 합을 구한다.
		double normalTotalCost = 0.0;
		double tomorrowTotalCost = 0.0;
		for (int i = 1; i < targetCities.size(); i++) {
			int prev = targetCities.get(i - 1);
			int now = targetCities.get(i);

			normalTotalCost += normal[prev][now];
			tomorrowTotalCost += tomorrow[prev][now];
		}

		// 티켓을 사는 것이 이득인지 출력한다.
		if (ticketPrice + tomorrowTotalCost < normalTotalCost) {
			System.out.println("Yes");
		} else {
			System.out.println("No");
		}

	} // end main

	/** 플로이드 와샬 알고리즘으로 모든 정점 쌍 간의 최단 경로를 구해서 이차원 배열 형태로 리턴 */
	public static double[][] floydWarshall(double[][] adjMatrix) {
		int V = adjMatrix.length;
		double[][] ret = new double[V][V];

		// 인접 행렬 정보로 ret를 초기화
		for (int y = 0; y < V; y++) {
			for (int x = 0; x < V; x++) {
				ret[y][x] = adjMatrix[y][x];
			}
		}

		// 플로이드 와샬 알고리즘으로 각 정점 간의 거리를 업데이트
		for (int m = 0; m < V; m++) {
			for (int from = 0; from < V; from++) {
				for (int to = 0; to < V; to++) {
					if (ret[from][m] == INF || ret[m][to] == INF) {
						continue;
					}

					if (ret[from][to] > ret[from][m] + ret[m][to]) {
						ret[from][to] = ret[from][m] + ret[m][to];
					}
				}
			}
		}

		return ret;
	}
}
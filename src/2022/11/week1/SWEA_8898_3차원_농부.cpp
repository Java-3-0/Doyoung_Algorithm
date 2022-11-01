#define _CRT_SECURE_NO_DEPRECATE
#define _CRT_SECURE_NO_WARNINGS

#include <iostream>
#include <vector>
#include <string>
#include <queue>
#include <map>
#include <set>
#include <sstream>
#include <algorithm>
#include <cstring>
#include <cmath>
#include <climits>

using namespace std;
typedef long long ll;

int main() {
	ios::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	int TESTS;
	cin >> TESTS;
	for (int tc = 1; tc <= TESTS; tc++) {
		// 소 마리수, 말 마리수 입력
		int N, M;
		cin >> N >> M;

		// X 좌표들 입력
		int C1, C2;
		cin >> C1 >> C2;

		// X축 거리 계산
		int DX = abs(C2 - C1);

		// 소, 말들의 위치 입력받아서 오름차순 정렬
		vector<int> cowList, horseList;
		for (int i = 0; i < N; i++) {
			int cowPos;
			cin >> cowPos;
			cowList.push_back(cowPos);
		}
		for (int i = 0; i < M; i++) {
			int horsePos;
			cin >> horsePos;
			horseList.push_back(horsePos);
		}
		sort(cowList.begin(), cowList.end());
		sort(horseList.begin(), horseList.end());

		// 소들의 위치와 말들의 위치를 하나씩 가져오면서 처리
		int minDZ = INT_MAX;
		int cnt = 0;

		int idxC = 0, idxH = 0;
		while (idxC < N && idxH < M) {
			int cow = cowList[idxC];
			int horse = horseList[idxH];

			// 최소 거리와 쌍 개수 갱신
			int dz = abs(horse - cow);
			if (dz < minDZ) {
				minDZ = dz;
				cnt = 1;
			}
			else if (dz == minDZ) {
				cnt++;
			}

			// 다음으로
			if (idxC == N - 1) {
				idxH++;
			}
			else if (idxH == M - 1) {
				idxC++;
			}
			else {
				if (cow < horse) {
					idxC++;
				}
				else {
					idxH++;
				}
			}
		}

		ll distance = (ll) DX + (ll) minDZ;
		cout << "#" << tc << " " << distance << " " << cnt << "\n";
	}

	return 0;
}
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

using namespace std;
typedef long long ll;

const int INF = 987654321;
const int NOT_PARKED = -1;

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	int TESTS;
	cin >> TESTS;
	for (int tc = 1; tc <= TESTS; tc++)
	{
		// 주차 공간 개수, 차량 개수 입력
		int P, C;
		cin >> P >> C;

		// 주차 공간들의 무게당 요금 입력
		vector<int> prices(P, 0);
		for (int i = 0; i < P; i++)
		{
			cin >> prices[i];
		}

		// 차량들의 무게 입력
		vector<int> weights(C, 0);
		for (int i = 0; i < C; i++)
		{
			cin >> weights[i];
		}

		// 초기화
		queue<int> waitingCars;
		priority_queue<int, vector<int>, greater<int>> avaliablePositions;
		for (int i = 0; i < P; i++)
		{
			avaliablePositions.push(i);
		}
		vector<int> parkedAt(C, NOT_PARKED);

		// 요청 처리
		ll totalPrice = 0LL;
		for (int i = 0; i < 2 * C; i++)
		{
			int move;
			cin >> move;

			// 들어온 경우
			if (move > 0)
			{
				int car = move - 1;

				waitingCars.push(car);
			}
			// 나간 경우
			else
			{
				int car = -move - 1;
				int pos = parkedAt[car];

				// 주차 공간 하나 생김
				avaliablePositions.push(pos);

				// 차 빼기
				parkedAt[car] = NOT_PARKED;

				// 요금 계산
				int price = weights[car] * prices[pos];
				totalPrice += (ll)price;
			}

			// 큐에 대기중인 차들을 최대한 주차
			while (!waitingCars.empty() && !avaliablePositions.empty())
			{
				// 제일 앞 차
				int car = waitingCars.front();
				waitingCars.pop();

				// 제일 앞 자리
				int pos = avaliablePositions.top();
				avaliablePositions.pop();

				// 주차하기
				parkedAt[car] = pos;
			}
		}

		cout << "#" << tc << " " << totalPrice << "\n";

	} // end for tc

	return 0;
}
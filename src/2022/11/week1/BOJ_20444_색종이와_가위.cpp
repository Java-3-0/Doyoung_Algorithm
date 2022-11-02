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
typedef unsigned long long ull;
typedef long long ll;

const int INF = 987654321;

bool isPossible(ll N, ll K);
ll getPieceCount(ll horizontalCuts, ll N);

int main() {
	ios::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	ll N, K;
	cin >> N >> K;

	if (isPossible(N, K)) {
		cout << "YES";
	}
	else {
		cout << "NO";
	}
	cout << "\n";

	return 0;
}

bool isPossible(ll N, ll K) {
	ll left = 0;
	ll right = N / 2;

	while (left <= right) {
		ll mid = (left + right) / 2;
		ll pieces = getPieceCount(mid, N);

		// 조각이 딱 맞으면
		if (pieces == K) {
			return true;
		}

		// 조각이 부족하면
		else if (pieces < K) {
			left = mid + 1;
		}
		// 조각이 너무 많으면
		else {
			right = mid - 1;
		}
	}

	return false;
}

ll getPieceCount(ll horizontalCuts, ll N) {
	ll verticalCuts = N - horizontalCuts;

	ll ret = (horizontalCuts + 1) * (verticalCuts + 1);

	return ret;
}
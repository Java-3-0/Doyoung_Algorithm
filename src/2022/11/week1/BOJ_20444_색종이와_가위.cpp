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

bool isPossible(ull N, ull K);
ull getPieceCount(ull horizontalCuts, ull N);

int main() {
	ios::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	ull N, K;
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

bool isPossible(ull N, ull K) {
	ull left = 0;
	ull right = N / 2;

	while (left <= right) {
		ull mid = (left + right) / 2;
		ull pieces = getPieceCount(mid, N);

		// 조각이 딱 맞으면
		if (pieces == K) {
			return true;
		}

        if (left == right) break;

        // 조각이 부족하면
        if (pieces < K) {
            left = mid + 1;
        }
        // 조각이 너무 많으면
        else {
            right = mid - 1;
        }
        
	}

	return false;
}

ull getPieceCount(ull horizontalCuts, ull N) {
	ull verticalCuts = N - horizontalCuts;

	ull ret = (horizontalCuts + 1) * (verticalCuts + 1);

	return ret;
}
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

vector<ll> lengthList;

int getKth(int i, ll K);

int main() {
	ios::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	// 오버플로우 나기 전까지 P(i)의 길이를 미리 계산
	ll length = 1LL;
	while (length >= 0) {
		lengthList.push_back(length);
		length = length * 2LL + 1LL;
	}

	// 테스트케이스 처리
	int TESTS;
	cin >> TESTS;
	for (int tc = 1; tc <= TESTS; tc++) {
		ll K;
		cin >> K;

		int answer = getKth(lengthList.size() - 1, K - 1LL);

		cout << "#" << tc << " " << answer << "\n";
	}

	return 0;
}

// P(i)에서 K번 인덱스의 수를 리턴
int getKth(int i, ll K) {
	ll len = lengthList[i];
	ll halfLen = len / 2LL;

	// 왼쪽
	if (K < halfLen) {
		return getKth(i - 1, K);
	}
	// 중앙
	else if (K == halfLen) {
		return 0;
	}
	// 오른쪽
	else {
		return 1 - getKth(i - 1, len - 1 - K);
	}
}
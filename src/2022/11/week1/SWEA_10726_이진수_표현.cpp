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
#include <cctype>

using namespace std;
typedef unsigned long long ull;
typedef long long ll;

const int INF = 987654321;
const ll INF_LL = 98765432109876543;

int main() {
	ios::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	int TESTS;
	cin >> TESTS;
	for (int tc = 1; tc <= TESTS; tc++) {
		int N, M;
		cin >> N >> M;

		int bitMask = (1 << N) - 1;
		string answer = ((M & bitMask) == bitMask) ? "ON" : "OFF";

		cout << "#" << tc << " " << answer << "\n";

	} // end for tc

	return 0;

} // end main
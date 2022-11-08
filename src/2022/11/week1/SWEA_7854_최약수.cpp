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

const ll MAX_NUM = (ll)1e9;

set<ll> getDivisors();
ll power(long base, long exp);

int main() {
	ios::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	int TESTS;
	cin >> TESTS;

	set<ll> divisors = getDivisors();

	for (int tc = 1; tc <= TESTS; tc++) {
		ll X;
		cin >> X;

		int answer = 0;
		for (ll div : divisors) {
			if (div <= X) {
				answer++;
			}
			else {
				break;
			}
		}

		cout << "#" << tc << " ";
		cout << answer << "\n";

	} // end for tc

	return 0;

} // end main

set<ll> getDivisors() {
	set<ll> ret;

	ll log2N = (ll)(log(MAX_NUM) / log(2) + 1);
	ll log5N = (ll)(log(MAX_NUM) / log(5) + 1);

	for (int i = 0; i <= log5N; i++) {
		for (int j = 0; j <= log2N; j++) {
			ll num = power(2, i) * power(5, j);
			if (num > 0 && num <= MAX_NUM) {
				int len = to_string(num).length();
				if (power(10, len) % num == 0) {
					ret.insert(num);
				}
			}
			else {
				break;
			}
		}
	}


	return ret;
}

ll power(long base, long exp) {
	if (exp == 0) {
		return 1;
	}

	ll half = power(base, exp / 2);
	if (exp % 2 == 0) {
		return half * half;
	}
	else {
		return half * half * base;
	}
}
#define _CRT_SECURE_NO_DEPRECATE
#define _CRT_SECURE_NO_WARNINGS

#include <iostream>
#include <vector>
#include <string>
#include <map>
#include <set>
#include <sstream>
#include <algorithm>
#include <cstring>
#include <cmath>

using namespace std;
typedef long long ll;

const int INF = 987654321;

vector<string> getSuffixArray(string str);

int main() {
	freopen("input.txt", "r", stdin);

	ios::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	int TESTS;
	cin >> TESTS;

	for (int tc = 1; tc <= TESTS; tc++) {
		int K;
		cin >> K;

		string str;
		cin >> str;

		vector<string> suffixArray = getSuffixArray(str);
		string answer = K - 1 < suffixArray.size() ? suffixArray[K - 1] : "none";

		cout << "#" << tc << " " << answer << "\n";
	}

	return 0;
}

vector<string> getSuffixArray(string str) {
	vector<string> ret;

	int len = str.length();
	for (int i = 0; i < len; i++) {
		string sub = str.substr(i);
		ret.push_back(sub);
	}

	sort(ret.begin(), ret.end());
	return ret;
}
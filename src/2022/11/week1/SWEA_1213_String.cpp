#define _CRT_SECURE_NO_DEPRECATE
#define _CRT_SECURE_NO_WARNINGS

#include <iostream>
#include <vector>
#include <string>
#include <map>
#include <sstream>
#include <algorithm>

using namespace std;
typedef long long ll;

const int INF = 987654321;
const int TESTS = 10;

vector<int> kmp(string text, string pattern);
vector<int> getPrefixTable(string pattern);

int main() {
	ios::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	for (int tc = 1; tc <= TESTS; tc++) {
		int tcNum;
		cin >> tcNum;

		string pattern, text;
		cin >> pattern >> text;

		vector<int> foundPositions = kmp(text, pattern);
		int answer = foundPositions.size();

		cout << "#" << tcNum << " ";
		cout << answer << "\n";
	}

	return 0;
}

vector<int> kmp(string text, string pattern) {
	vector<int> ret;

	int lenT = text.length();
	int lenP = pattern.length();

	vector<int> p = getPrefixTable(pattern);

	for (int i = 0, j = 0; i < lenT; i++) {
		// j가 0이 되기 전까지, 문자열이 일치하지 않으면 j 갱신
		while (j > 0 && text[i] != pattern[j]) {
			j = p[j - 1];
		}

		if (text[i] == pattern[j]) {
			if (j == lenP - 1) {
				ret.push_back(i - j + 1);
				j = p[j];
			}
			else {
				j++;
			}
		}

	}

	return ret;
}

vector<int> getPrefixTable(string pattern) {
	int lenP = pattern.length();

	vector<int> p(lenP, 0);

	for (int i = 1, j = 0; i < lenP; i++) {
		while (j > 0 && pattern[i] != pattern[j]) {
			j = p[j - 1];
		}

		if (pattern[i] == pattern[j]) {
			p[i] = ++j;
		}

		else {
			p[i] = 0;
		}
	}

	return p;
}
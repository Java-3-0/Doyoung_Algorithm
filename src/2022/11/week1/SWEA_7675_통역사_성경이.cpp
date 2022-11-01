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

vector<string> split(string str, char delim);
bool isName(string s);
bool isLastWord(string word);

int main() {
	ios::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	int TESTS;
	cin >> TESTS;
	for (int tc = 1; tc <= TESTS; tc++) {
		// 테스트케이스 번호 출력
		cout << "#" << tc << " ";

		int N;
		cin >> N;

		int sentenceCnt = 0;
		int answer = 0;

		while (sentenceCnt < N) {
			string word;
			cin >> word;

			// 문장이 끝나는 경우
			if (isLastWord(word)) {
				word = word.substr(0, word.length() - 1);
				if (isName(word)) {
					answer++;
				}

				cout << answer << " ";
				sentenceCnt++;
				answer = 0;
			}

			// 문장이 아직 끝나지 않는 경우
			else {
				if (isName(word)) {
					answer++;
				}
			}

		} // end while

		cout << "\n";

	} // end for tc

	return 0;

} // end main

bool isLastWord(string word) {
	int len = word.length();
	if (word[len - 1] == '.' || word[len - 1] == '?' || word[len - 1] == '!') {
		return true;
	}

	return false;
}

bool isName(string word) {
	int len = word.length();
	if (len == 0) {
		return false; 
	}

	if (!isupper(word[0])) {
		return false;
	}

	for (int i = 1; i < len; i++) {
		if (!islower(word[i])) {
			return false;
		}
	}

	return true;
}

vector<string> split(string str, char delim) {
	stringstream ss(str);
	string buffer;

	vector<string> ret;
	while (getline(ss, buffer, delim)) {
		ret.push_back(buffer);
	}

	return ret;
}
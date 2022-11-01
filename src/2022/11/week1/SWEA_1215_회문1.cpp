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
const int TESTS = 10, GRID_SIZE = 8;
char grid[GRID_SIZE][GRID_SIZE];

bool isInRange(int pos);
bool isPalindromeHorizontal(int startY, int startX, int len);
bool isPalindromeVertical(int startY, int startX, int len);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	for (int tc = 1; tc <= TESTS; tc++)
	{
		// 찾을 길이 입력
		int L;
		cin >> L;

		// 글자판 입력
		for (int y = 0; y < GRID_SIZE; y++)
		{
			string line;
			cin >> line;
			for (int x = 0; x < GRID_SIZE; x++)
			{
				grid[y][x] = line[x];
			}
		}

		// 팰린드롬 수 계산
		int answer = 0;
		for (int y = 0; y < GRID_SIZE; y++)
		{
			for (int x = 0; x < GRID_SIZE; x++)
			{
				if (isPalindromeHorizontal(y, x, L))
				{
					answer++;
				}
				if (isPalindromeVertical(y, x, L))
				{
					answer++;
				}
			}
		}

		// 출력
		cout << "#" << tc << " ";
		cout << answer << "\n";
	}

	return 0;
}

bool isPalindromeHorizontal(int startY, int startX, int len)
{
	for (int i = 0; i < len / 2; i++)
	{
		int x1 = startX + i;
		int x2 = startX + len - 1 - i;

		if (!(isInRange(x1) && isInRange(x2) && grid[startY][x1] == grid[startY][x2]))
		{
			return false;
		}
	}

	return true;
}

bool isPalindromeVertical(int startY, int startX, int len)
{
	for (int i = 0; i < len / 2; i++)
	{
		int y1 = startY + i;
		int y2 = startY + len - 1 - i;

		if (!(isInRange(y1) && isInRange(y2) && grid[y1][startX] == grid[y2][startX]))
		{
			return false;
		}
	}

	return true;
}

bool isInRange(int pos)
{
	if (0 <= pos && pos < GRID_SIZE)
	{
		return true;
	}

	return false;
}
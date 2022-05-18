#include <iostream>
#include <string>
#include <cstring>
#include <vector>
#include <map>
#include <stack>
#include <queue>
#include <algorithm>
#include <climits>
#include <cfloat>
#include <cmath>
#include <complex>

using namespace std;

typedef complex<double> cpx;
typedef long long ll;

const double PI = M_PI;
const ll GROUPSIZE = 2;

void fft(vector<cpx> &f, bool isReverse);
vector<ll> multiply(vector<cpx>& f, vector<cpx>& g);
void printVector(const vector<ll> v);
void print(vector<ll> v);

int main(void)
{
    // 문자열 입력
    string s1, s2;
    cin >> s1 >> s2;

    int newSize1 = s1.size() / GROUPSIZE;
    if (s1.size() % GROUPSIZE != 0) {
        newSize1++;
    }

    int newSize2 = s2.size() / GROUPSIZE;
    if (s2.size() % GROUPSIZE != 0) {
        newSize2++;
    }

    // 각 문자열을 벡터 형태의 다항식으로 만든다
    vector<cpx> f(newSize1, 0);
    vector<cpx> g(newSize2, 0);

    // 시간복잡도가 터지지 않게 하기 위해서 묶어서 처리
    int size1 = s1.size();
    for (int i = 0; i < size1; i++) {
        f[i / GROUPSIZE] += (s1[size1 - 1 - i] - '0') * pow(10, i % GROUPSIZE);
    }

    int size2 = s2.size();
    for (int i = 0; i < size2; i++) {
        g[i / GROUPSIZE] += (s2[size2 - 1 - i] - '0') * pow(10, i % GROUPSIZE);
    }

    // 다항식의 곱 계산
    vector<ll> product = multiply(f, g);

    // 출력
    print(product);

    return 0;
}

/** 다항식 f를 고속 퓨리에 변환한 결과를 f에 저장 */
void fft(vector<cpx> &f, bool isReverse) {
    int n = f.size();

    // 스왑하기
    for (int i = 1, j = 0; i < n; i++) {
        int bit = n / 2;
        while (j >= bit) {
            j -= bit;
            bit /= 2;
        }
        j += bit;
        if (i < j) {
            swap(f[i], f[j]);
        }
    }

    // 재귀 대신 for문으로 풀어서 메모리 아끼기
    for (int len = 2; len <= n; len <<= 1) {
        double angle = 2.0 * PI / len;
        if (isReverse) {
            angle = -angle;
        }

        cpx wLen(cos(angle), sin(angle));
        for (int i = 0; i < n; i += len) {
            cpx w(1.0, 0.0);
            for (int j = 0; j < len / 2; j++) {
                cpx u = f[i + j];
                cpx v = f[i + j + len / 2] * w;
                f[i + j] = u + v;
                f[i + j + len / 2] = u - v;
                w *= wLen;
            }
        }
    }

    // 역변환이면 n으로 나눠주기
    if (isReverse) {
        for (int i = 0; i < n; i++) {
            f[i] /= n;
        }
    }

    return;
}

/** 다항식 f와 g의 곱을 계산해서 리턴 */
vector<ll> multiply(vector<cpx>& f, vector<cpx>& g) {
    int size1 = f.size();
    int size2 = g.size();

    // 2의 제곱수 중 f * g를 담을 수 있는 최소 크기를 구한다
    int n = 1;
    while (n < size1 + size2) {
        n *= 2;
    }
    n *= 2;

    // 그 크기로 배열 길이를 늘린다
    f.resize(n);
    g.resize(n);

    // 각각의 다항식을 FFT 변환한다
    fft(f, false);
    fft(g, false);

    // FFT한 값들끼리 곱한다
    for (int i = 0; i < n; i++) {
        f[i] *= g[i];
    }

    // 얻은 결과를 FFT 역변환한다
    fft(f, true);

    // 실수 부분만 round 처리해서 리턴한다
    vector<ll> ret(n);
    for (int i = 0; i < n; i++) {
        ret[i] = (ll) (round(f[i].real()));
    }
    return ret;
}

/** 벡터를 출력한다 */
void print(vector<ll> v) {
    ll carry = 0;
    for (int i = 0; i < v.size(); i++) {
        v[i] += carry;

        carry = v[i] / (ll) pow(10, GROUPSIZE);
        v[i] %= (ll) pow(10, GROUPSIZE);
    }

    string ret = "";

    // 맨 앞 부분 0들은 제외하고 출력
    bool meetNonZero = false;

    for (int i = v.size() - 1; i >= 0; i--) {
        if (meetNonZero) {
            cout.width(GROUPSIZE);
            cout.fill('0');
            cout << v[i];
            continue;
        }
        else {
            if (v[i] != 0) {
                cout << v[i];
                meetNonZero = true;
            }
        }
    }

    // 0이 아닌 게 아예 없다면 이 수 전체가 0이다
    if (!meetNonZero) {
        cout << 0;
    }

    return;
}

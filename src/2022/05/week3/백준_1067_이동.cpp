// 13732KB, 68ms

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

void fft(vector<cpx> &f, bool isReverse);
vector<ll> multiply(vector<ll>& f, vector<ll>& g);

int main(void)
{
    ios::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    int N;
    cin >> N;

    // X�� �Է¹޾Ƽ� ���̸� 2��� �Ͽ� f�� �ִ´�
    vector<ll> f(2 * N);
    for (int i = 0; i < N; i++) {
        cin >> f[i];
        f[i + N] = f[i];
    }

    // Y�� �Է¹޾Ƽ� �������� g�� �ִ´�
    vector<ll> g(N);
    for (int i = N - 1; i >= 0; i--) {
        cin >> g[i];
    }

    // �� ���׽��� ���Ѵ�
    vector<ll> product = multiply(f, g);

    // ����� �ִ��� ���Ѵ�
    ll answer = 0;
    for (ll num : product) {
        answer = max(answer, num);
    }

    // ����Ѵ�
    cout << answer << endl;

    return 0;
}

/** ���׽� f�� ��� Ǫ���� ��ȯ�� ����� f�� ���� */
void fft(vector<cpx> &f, bool isReverse) {
    int n = f.size();

    // �����ϱ�
    for (int i = 1, j = 0; i < n; i++) {
        int bit = (n >> 1);
        while(!((j ^= bit) & bit)) {
            bit >>= 1;
        }

        if (i < j) {
            swap(f[i], f[j]);
        }
    }

    // ��� ��� for������ Ǯ� �޸� �Ƴ���
    for (int len = 2; len <= n; len <<= 1) {
        int halfLen = (len >> 1);

        double angle = 2 * PI / len;
        if (isReverse) {
            angle *= -1;
        }

        cpx wLen(cos(angle), sin(angle));
        for (int i = 0; i < n; i += len) {
            cpx w(1, 0);
            for (int j = 0; j < halfLen; j++) {
                cpx u = f[i + j];
                cpx v = f[i + j + halfLen] * w;
                f[i + j] = u + v;
                f[i + j + halfLen] = u - v;
                w *= wLen;
            }
        }
    }

    // ����ȯ�̸� n���� �����ֱ�
    if (isReverse) {
        for (int i = 0; i < n; i++) {
            f[i] /= n;
        }
    }

    return;
}

/** ���׽� f�� g�� ���� ����ؼ� ���� */
vector<ll> multiply(vector<ll>& fll, vector<ll>& gll) {
    vector<cpx> f(fll.begin(), fll.end());
    vector<cpx> g(gll.begin(), gll.end());

    int size1 = fll.size();
    int size2 = gll.size();

    // 2�� ������ �� f * g�� ���� �� �ִ� �ּ� ũ�⸦ ���Ѵ�
    int n = 1;
    while (n < size1 + size2) {
        n <<= 1;
    }

    // �� ũ��� �迭 ���̸� �ø���
    f.resize(n);
    g.resize(n);

    // ������ ���׽��� FFT ��ȯ�Ѵ�
    fft(f, false);
    fft(g, false);

    // FFT�� ���鳢�� ���Ѵ�
    for (int i = 0; i < n; i++) {
        f[i] *= g[i];
    }

    // ���� ����� FFT ����ȯ�Ѵ�
    fft(f, true);

    // �Ǽ� �κи� round ó���ؼ� �����Ѵ�
    vector<ll> ret(n);
    for (int i = 0; i < n; i++) {
        ret[i] = (ll) (round(f[i].real()));
    }
    return ret;
}

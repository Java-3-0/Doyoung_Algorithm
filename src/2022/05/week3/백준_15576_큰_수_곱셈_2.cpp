// 101364KB, 576ms

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
vector<ll> multiply(vector<cpx>& f, vector<cpx>& g);
void printVector(const vector<ll> v);
string print(vector<ll> v);

int main(void)
{
    // ���ڿ� �Է�
    string s1, s2;
    cin >> s1 >> s2;

    // �� ���ڿ��� ���� ������ ���׽����� �����
    vector<cpx> f(s1.size());
    vector<cpx> g(s2.size());

    int size1 = s1.size();
    for (int i = 0; i < size1; i++) {
        f[i] = s1[size1 - 1 - i] - '0';
    }

    int size2 = s2.size();
    for (int i = 0; i < size2; i++) {
        g[i] = s2[size2 - 1 - i] - '0';
    }

    vector<ll> product = multiply(f, g);

    print(product);

    return 0;
}

/** ���׽� f�� ��� ǻ���� ��ȯ�� ����� f�� ���� */
void fft(vector<cpx> &f, bool isReverse) {
    int n = f.size();

    // �����ϱ�
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

    // ��� ��� for������ Ǯ� �޸� �Ƴ���
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

    // ����ȯ�̸� n���� �����ֱ�
    if (isReverse) {
        for (int i = 0; i < n; i++) {
            f[i] /= n;
        }
    }

    return;
}

/** ���׽� f�� g�� ���� ����ؼ� ���� */
vector<ll> multiply(vector<cpx>& f, vector<cpx>& g) {
    int size1 = f.size();
    int size2 = g.size();

    // 2�� ������ �� f * g�� ���� �� �ִ� �ּ� ũ�⸦ ���Ѵ�
    int n = 1;
    while (n < size1 + size2) {
        n *= 2;
    }
    n *= 2;

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

/** ���͸� ����Ѵ� */
string print(vector<ll> v) {
    int size = v.size();

    ll carry = 0;
    for (int i = 0; i < size; i++) {

        v[i] += carry;

        carry = v[i] / 10;
        v[i] %= 10;
    }

    string ret = "";

    // �� �� �κ� 0���� �����ϰ� ���
    bool meetNonZero = false;
    for (int i = size; i >= 0; i--) {
        if (v[i] != 0) {
            meetNonZero = true;
        }

        if (!meetNonZero) {
            continue;
        }
        else {
            cout << v[i];
        }
    }

    // 0�� �ƴ� �� �ƿ� ���ٸ� �� �� ��ü�� 0�̴�
    if (!meetNonZero) {
        cout << 0;
    }

    return ret;
}

clear all;

X = rand(4,5);
Y = rand(4,2);
X = orth(X.')';
Y = orth(Y);
%Si quitamos la ortonormalización, Y y X*W no coinciden

W = X'*Y;
Y
X*W
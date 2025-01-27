clear all;

load DatosAND
%load DatosLS5
%load DatosLS10
%load DatosLS50
%load DatosOR
%load DatosXOR

X = Data(:,1:end-1);
Y = Data(:,end);
k = size(Data,2) - 1;
N = size(Data,1);

Xext = [X -ones(N,1)];
%size(Xext);
%Xext;

w1 = inv(Xext' * Xext)*Xext' * Y;
w2 = pinv(Xext)*Y;

predicted = Xext * w1;   %predicted = y gorro
label = Signo(predicted);
ccr = sum(label == Y)/N;        %genera un vector (vector de booleanos) a partir de comparar ambos vectores componente a componente, colocando 1 si el elemento es igual y 0 si es distinto
%correct classification rate

ECM = norm(predicted - Y,2);
function [difW, difT] = retropropagacionError(patron, Z, y, w, s, h, u, Beta, eta)
%% Función que calcula los diferenciales de los pesos W y T

%% Incialización de variables
nSalidas=size(y,1);
nOcultas=size(w,2);

delta2=zeros(nSalidas,1);
difW=zeros(nSalidas, nOcultas);
delta1=zeros(nOcultas,1);
difT=zeros(nOcultas,size(patron,2));

err = Z-y;
g1d = derivadaLogistica(h,Beta);
g2d = derivadaLogistica(u,Beta);

%% --> Cálculo de deltas2 y difW <--
delta2 = err .* g1d;
difW = eta * delta2 * s'; %size=nSalida,nOcultas

%% --> Cálculo de deltas1 y difT <--
delta1 = (delta2 * w') .* g2d;
difT = eta * delta1 * patron; %size=nOcultas,k

end


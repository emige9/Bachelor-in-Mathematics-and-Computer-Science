function [y, h, s, u] = salidaRed(patron, t, w, Beta)
%% Función que calcula la salida de la red (y), los pesos (h, s) y la salida de la capa oculta (s)
u = t * patron';
s = logistica(u,Beta);
h = w * s;
y=logistica(h,Beta);            %cálculo de salida de la red, capa de salida


end

%{
    Estamos usando el modelo funcional para calcular la salida del modelo
    para un patron n-esimo x_n (ver formula de los apuntes de y gorro) usando la funcion logistica para g1 y g2 (funciones de
    transferencia).
%}
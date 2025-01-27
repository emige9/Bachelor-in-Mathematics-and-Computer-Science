clear all;

% Definimos el patrón que queremos memorizar en la red
s = [-1 -1 1 1 1];    %Patrón que memorizamos

% Inicializamos la matriz de pesos
w = zeros(size(s,2),size(s,2));  % Matriz de pesos sinápticos de la red (n x n)
w = (1/size(s,2))*(s'*s);        % Calculamos la matriz de pesos según la regla de Hebb
w = w - diag(diag(w));          % Eliminamos la contribución de los pesos autoconectados (diagonal)

% Inicializamos el conjunto de estados a lo largo del tiempo
S = zeros(size(s,2),21);    % Matriz para guardar los estados de las neuronas en cada iteración
S(:,1) = [1 1 -1 -1 1];     % Estado inicial de la red (patrón de entrada)

% Comienza la iteración para actualizar los estados de las neuronas
for t=2:21
    cambio=false;       % Bandera para detectar si hubo cambios en los estados
    S(:,t) = S(:,t-1);  % Inicializamos el estado actual con el estado anterior

    % Iteramos sobre cada neurona para actualizar su estado
    for i=1:size(s,2)
        h = w(i,:)*S(:,t);      % Calculamos el potencial local (suma ponderada de entradas)
        S(i,t)=(h>0)*2-1;          % Actualizamos el estado de la neurona: 1 si h > 0, -1 si h <= 0
        cambio = cambio || S(i,t) ~= S(i,t-1);    % Verificamos si hubo algún cambio
    end

    % Si no hubo cambios en ningún estado, la red se estabiliza y terminamos
    if ~cambio
        S(:,t-1)   % Mostramos el estado final estabilizado
        return    % Salimos del bucle porque la red ya está estable
    end
end

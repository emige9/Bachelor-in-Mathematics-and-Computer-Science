clear all;
d(:,:,1)=[1 1 -1 -1 -1 1 1; 
1 1 -1 1 -1 1 1;
1 1 -1 1 -1 1 1;
1 1 -1 1 -1 1 1;
1 -1 -1 -1 -1 -1 1;
1 -1 1 1 1 -1 1;
1 -1 1 1 1 -1 1;
1 -1 1 1 1 -1 1;
-1 -1 1 1 1 -1 -1;];
d(:,:,2)=[1 -1 -1 -1 -1 -1 1; 
1 -1 1 1 1 1 -1;
1 -1 1 1 1 1 -1;
1 -1 1 1 1 1 -1;
1 -1 -1 -1 -1 -1 1;
1 -1 1 1 1 1 -1;
1 -1 1 1 1 1 -1;
1 -1 1 1 1 1 -1;
1 -1 -1 -1 -1 -1 1;];


% Inicialización de la matriz de pesos y vectores de patrones
W = zeros(9*7,9*7);          % Matriz de pesos sinápticos de la red (63 x 63, porque 9*7=63)
dVect = zeros(5,9*7);        % Matriz donde se almacenarán los patrones aplanados (5 patrones de tamaño 63)

% Transformamos los patrones en vectores y calculamos la matriz de pesos
for i = 1:2
    dVect(i,:) = reshape(d(:,:,i), 1, 9*7); % Aplana el patrón \(d(:,:,i)\) a un vector de 63 elementos
    W = W + dVect(i,:)' * dVect(i,:);      % Actualiza la matriz de pesos usando la regla de Hebb
end

% Normalización y eliminación de pesos autoconectados
W = (1/size(W,1)) * W;       % Normalizamos los pesos para evitar que crezcan demasiado
W = W - diag(diag(W));       % Eliminamos las conexiones autoconectadas (diagonal de la matriz)

% Inicialización de los estados y patrón inicial
S = zeros(size(dVect,2), 21);               % Matriz para almacenar los estados de las neuronas (63 x 21)
S(:,1) = reshape(d(:,:,2), 9*7, 1);         % Establecemos el estado inicial como el patrón \(d(:,:,4)\)

% Proceso iterativo para actualizar los estados
for t = 2:21
    cambio = false;                         % Bandera para detectar si hubo cambios en los estados
    S(:,t) = S(:,t-1);                      % Inicializamos el estado actual con el estado anterior
    
    % Actualizamos cada neurona
    for i = 1:size(dVect,2)
        h = W(i,:) * S(:,t);                % Calculamos el potencial local para la neurona \(i\)
        S(i,t) = (h > 0) * 2 - 1;           % Actualizamos el estado de la neurona (\(1\) si \(h > 0\), \(-1\) si \(h \leq 0\))
        cambio = cambio || S(i,t) ~= S(i,t-1); % Verificamos si hubo algún cambio en la neurona \(i\)
    end
    
    % Si no hubo cambios en los estados, la red se estabilizó
    if ~cambio
        reshape(S(:,t-1), 9, 7)             % Reconstruimos y mostramos el estado final estabilizado como una matriz \(9 \times 7\)
        return                              % Terminamos la ejecución, ya que la red es estable
    end
end

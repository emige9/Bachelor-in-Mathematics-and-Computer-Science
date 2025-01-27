clear all; % Limpia el espacio de trabajo.

tic; % Inicia el cronómetro para medir el tiempo de ejecución.
% Parámetros iniciales
N = 30; % Tamaño del tablero NxN (3x3).

% Inicialización de la matriz de pesos sinápticos 4D.
w = zeros(N, N, N, N); % Pesos entre cada par de neuronas (i,j) y (l,k).

% Umbrales de activación de las neuronas.
Theta = -ones(N, N); % Umbrales iniciales fijados a -1 para todas las neuronas.

% Configuración de la matriz de pesos
for i = 1:N
    for j = 1:N
        % Penalización (peso -2) para la fila de la neurona actual (i,j).
        w(i, j, i, 1:N) = -2;
        
        % Penalización (peso -2) para la columna de la neurona actual (i,j).
        w(i, j, 1:N, j) = -2;
        
        % Sin autoconexión para la neurona actual (peso 0).
        w(i, j, i, j) = 0;
    end
end

% Número de iteraciones máximas permitidas.
epoc = 20;

% Historial de estados de la red.
Shist = zeros(N, N, epoc); % Tensor que almacena el estado del tablero en cada época.
%Shist(:, :, 1) = [0, 0, 1; 0,1 , 1; 0, 0, 0]; % Estado inicial predefinido.
% Alternativa: inicialización aleatoria del tablero.
Shist(:, :, 1) = round(rand(N, N), 0);

% Inicio de las iteraciones de actualización.
for e = 2:epoc
    cambio = false; % Variable para verificar si hay cambios en el tablero.
    Shist(:, :, e) = Shist(:, :, e-1); % Inicializamos con el estado anterior.

    % Recorrido de todas las neuronas del tablero.
    for i = 1:N
        for j = 1:N
            h = 0; % Potencial sináptico acumulado para la neurona (i, j).
            
            % Cálculo del potencial sináptico como suma ponderada de entradas.
            for l = 1:N
                for k = 1:N
                    h = h + Shist(l, k, e) * w(i, j, l, k); % Acumula contribuciones de (l,k).
                end
            end

            h = h + Theta(i, j); % Añadir el umbral

            % Determinar el cambio de estado (Delta S)
            Delta_S = int16(h >= Theta(i, j)) - Shist(i, j, e); % Estado nuevo - estado anterior

            % Calcular el diferencial de energía (Delta E)
            Delta_E = -h * Delta_S;

            % Mostrar el diferencial de energía para esta neurona
            fprintf('Delta_E para la neurona (%d, %d): %f\n', i, j, Delta_E);
            
            % Actualización del estado de la neurona según el umbral.
            Shist(i, j, e) = int16(h >= Theta(i, j)); % Activada si h >= Theta(i,j).
            
            % Detectar si hubo cambios en el estado de esta neurona.
            cambio = cambio || Shist(i, j, e) ~= Shist(i, j, e-1);
        end
    end

    % Verificar convergencia: si no hay cambios, la red se estabilizó.
    if ~cambio
        % Mostrar el estado final estabilizado.
        Shist(:, :, e)
        elapsed_time = toc; % Detiene el cronómetro al finalizar el script.
        fprintf('Tiempo de ejecución: %.4f segundos.\n', elapsed_time);
        return; % Salir del bucle si no hay más cambios.
    end
end



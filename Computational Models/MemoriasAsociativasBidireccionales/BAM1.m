clear all;

% Número máximo de épocas para ejecutar el algoritmo
epocMax = 21;

% Matriz de patrones de entrada (cada fila es un patrón)
X(1,:) = [1 1 1 -1 1 -1 -1 1 -1]; % Primer patrón de entrada
X(2,:) = [1 -1 -1 1 -1 -1 1 1 1]; % Segundo patrón de entrada

% Matriz de patrones de salida asociados a los patrones de entrada
Y(1,:) = [1 -1 -1]; % Salida asociada al primer patrón
Y(2,:) = [-1 -1 1]; % Salida asociada al segundo patrón

% Cálculo de la matriz de pesos sinápticos utilizando la regla de Hebb
w = X' * Y; % Producto entre la transpuesta de X y Y

% Inicialización de matrices para almacenar los estados de entrada y salida
S = zeros(size(X,2), epocMax);  % Matriz para estados de entrada
S2 = zeros(size(Y,2), epocMax); % Matriz para estados de salida

% Inicialización del vector de entrada (puede ser uno de los patrones en X)
sinit = [1 1 1 -1 1 -1 -1 1 -1]; % Patrón inicial de entrada

% Estado inicial de salida, calculado con el patrón inicial y la matriz de pesos
sinit2 = sign(sinit * w);

% Almacenar los estados iniciales en las matrices S y S2
S(:,1) = sinit;    % Primer columna: patrón inicial de entrada
S2(:,1) = sinit2;  % Primer columna: patrón inicial de salida

% Iteraciones para actualizar los estados de entrada y salida
for epoc = 2:1:epocMax
    % Actualizar el estado del patrón de entrada basado en el estado de salida previo
    S(:,epoc) = sign(w * S2(:,epoc-1));
    
    % Actualizar el estado del patrón de salida basado en el estado de entrada actual
    S2(:,epoc) = sign(S(:,epoc)' * w);

    % Verificar la convergencia: si los estados no cambian entre épocas consecutivas
    if (sum(S(:,epoc) == S(:,epoc-1)) == size(X,2)) && (sum(S2(:,epoc) == S2(:,epoc-1)) == size(Y,2))
        % Mostrar los estados finales convergidos
        S(:,epoc)  % Estado convergido del patrón de entrada
        S2(:,epoc) % Estado convergido del patrón de salida
        epoc       % Número de épocas necesarias para converger
        
        % Asumiendo que las entradas son imágenes de 3x3 píxeles para X y 1x3 para Y
        rows_input = 3; % Número de filas de la matriz de entrada
        cols_input = 3; % Número de columnas de la matriz de entrada
        
        % Convertir el vector de entrada inicial sinit en matriz
        input_matrix = reshape(sinit, rows_input, cols_input);
        
        % Determinar las dimensiones del patrón reconocido
        % En este caso, las dimensiones corresponden a las dimensiones de entrada.
        recognized_matrix = reshape(S(:, epoc-1), rows_input, cols_input);
        
        % Visualización de los patrones
        figure;
        
        % Mostrar el patrón de entrada inicial
        subplot(1, 2, 1);
        imagesc(input_matrix); 
        colormap gray; 
        axis equal;
        title('Patrón de entrada inicial');
        
        % Mostrar el patrón reconocido
        subplot(1, 2, 2);
        imagesc(recognized_matrix); 
        colormap gray; 
        axis equal;
        title('Patrón reconocido');

        return     % Terminar el algoritmo si hay convergencia
    end
end



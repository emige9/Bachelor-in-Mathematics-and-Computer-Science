clear all;

D = load('handwriting.mat');
X = D.X;

[N, K] = size(X);
J = 10;

Y = zeros(N,J);

% Generate the Y Label
for i =1:10
    Y(1+(500*(i-1)):i*500,i) =1;
end

% Scale the data
Xscaled = (X-min(X))./(max(X)-min(X));

% Remove the NaN elements
Xscaled = Xscaled(:,any(~isnan(Xscaled)));

% Compute again the number of total elements and attributes
[N, K] = size(Xscaled);

CVHO = cvpartition(N,'HoldOut',0.25);

XscaledTrain = Xscaled(CVHO.training(1),:);
XscaledTest = Xscaled(CVHO.test(1),:);
YTrain = Y(CVHO.training(1),:);
YTest = Y(CVHO.test(1),:);


% Create the validation set
[NTrain, K] = size(XscaledTrain);
CVHOV = cvpartition(NTrain,'HoldOut',0.25);

% Generate the validation sets
XscaledTrainVal = XscaledTrain(CVHOV.training(1),:);
XscaledVal = XscaledTrain(CVHOV.test(1),:);
YTrainVal = YTrain(CVHOV.training(1),:);
YVal = YTrain(CVHOV.test(1),:);

% Performance Matrix
Performance = zeros(7,6);

i = 0;
j = 0;
% Estimate the hyper-parameters values
for C = [10^(-3) 10^(-2) 10^(-1) 1 10 100 1000]
    i = i+1;
    for L = [50 100 500 1000 1500 2000]
        j = j+1;
    
        % Inicializar pesos aleatorios
        t = rand(L,K)*2 - 1;   % Pesos de entrada de tamanno (L x K)
        u = XscaledTrainVal*t';  % u de tamanno (NTrainVal x L)
        H = 1./(1 + exp(-u));  % Aplicación de la funcion sigmoide, tamanno (L x NTrainVal)

        % Calcular los pesos de salida usando la ecuación de ELM
        w = inv(eye(L)/C + H'*H)*H'*YTrainVal;

        % Salida para el conjunto de validación
        Hval = 1./(1 + exp(-t * XscaledVal'));
        Yvale = Hval'*w;

        %Calcular el error cuadratico medio
        err = mean(mean((Yvale - YVal).^2));
        
        %Almacenar el rendimiento
        epsilon = 1.0e-9;
        Performance(i,j) = 1 /(err + epsilon);
        
    end
    j=0;
end

C = [10^(-3) 10^(-2) 10^(-1) 1 10 100 1000];
L = [50 100 500 1000 1500 2000];

[maxValue, linearIndexesOfMaxes] = max(Performance(:));
[rowsOfMaxes, colsOfMaxes] = find(Performance == maxValue);

Copt = C(rowsOfMaxes(1));
Lopt = L(colsOfMaxes(1));

% Calcular con el conjunto de entrenamiento el ELM neuronal y
% reportar el error cometido en test

t = rand(Lopt, K)*2 - 1;
u = XscaledTrain*t';
H = 1./(1 + exp(-u)); %Funcion sigmoide
w = inv(eye(Lopt)/Copt + H'*H)*H'*YTrain;

Htest = 1./(1 + exp(-t * XscaledTest'));
Yteste = Htest' * w;

% Calcular el error en el conjunto de test
errorTest = mean(mean((Yteste - YTest).^2));
disp(['Error en el conjunto de test (MSE): ', num2str(errorTest)]);

% Convertir predicciones a etiquetas de clase
[~, predictedLabels] = max(Yteste, [], 2);  % Obtener el índice del valor maximo en cada fila

% Convertir YTest a etiquetas de clase
[~, trueLabels] = max(YTest, [], 2);  % Obtener el índice del valor maximo en cada fila

% Calcular la Tasa de Clasificación Correcta (CCR)
correctPredictions = sum(predictedLabels == trueLabels);
totalPredictions = length(trueLabels);
CCR = correctPredictions / totalPredictions;

disp(['Tasa de Clasificación Correcta (CCR): ', num2str(CCR)]);

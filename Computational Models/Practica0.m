
x = 10;
nombre = 'Juan';
altura = 1.75;
a = 5 ;
b = 2 ;
suma = a + b ;
resta = a - b ;
producto = a * b ;
division = a / b ;
potencia = a^b ;

x = 2.0 ;
senox = sin( x ) ;
cosenox = cos( x ) ;
raizx = sqrt( x ) ;
exponencialx = exp( x ) ;
logaritmox = log( x ) ;

x = 3;
n = 1;

%Estructura if
if x > 0 
    disp('x es positivo');
else 
    disp('x no es positivo');
end

%Estructura for
for i = 1:1:5     %El numero del centro indica el incremento del indice. Si no se indica entonces se sobreentiende que es 1 paso
    disp(['It: ', num2str(i)]);
end

%Estructura while
while n <10
    n = n+1;
end


%Vector fila
v_fila = [1, 2, 3, 4, 5];

%Vector columna
v_columna = [1; 2; 3; 4; 5];

%Matriz
A = [1, 2, 3; 4, 5, 6; 7, 8, 9];

%Vectores
vector = [1, 2, 3, 4, 5];
%Acceder a un elemento especifico
elemento = vector(3);
%Slicing
subvector = vector(2:4);

%Matrices
matriz1 = [1, 2, 3; 4, 5, 6; 7, 8, 9];
%Acceder a un elemento especifico
elementomatriz = matriz(2,3);
%Slicing en filas y columnas
submatriz = matriz(1:2, 2:3);

matriz2 = [1, 2, 3; 4, 5, 6; 7, 8, 9];

productousual = matriz1*matriz2;
productohadamard = matriz1.*matriz2;
traspuesta = matriz';
inversa = inv(matriz1);
penroseinversa = pinv(matriz1);


longitud = length(vector);
dimensiones = size(matriz1);
maximo_valor = max(vector);
minimo_valor = min(vector);
suma_total = sum(vector);
promedio = mean(vector);



% Datos de ejemplo
x = linspace(0, 2*pi , 100); % Crear un vector de valores x
y1 = sin(x) ; y2 = cos(x) ;
% Crear un grafico
figure ; % Crear una nueva figura
plot(x, y1, 'r--', 'LineWidth', 2); % Plot de sin(x)
hold on; % Mantener el plot actual
plot(x , y2, 'b-', 'LineWidth', 2) ; % Plot de cos(x)
% Etiquetas y titulo
xlabel('Eje X');
ylabel('Eje Y');
title('Plot de las Funciones Seno y Coseno');
% Mostrar la cuadricula
grid on ;
% Annadir leyenda
legend('sin(x)', 'cos(x)');


function resultado = cuadrado(x)
    resultado = x^2;
end

valor = 5;
resultado = cuadrado(valor);

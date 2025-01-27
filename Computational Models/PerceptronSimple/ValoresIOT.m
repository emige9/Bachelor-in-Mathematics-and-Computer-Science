function [Input, Output, Target] = ValoresIOT(Data,W,i)
    Input = Data(i, 1:end-1);  
    Target = Data(i,end);
    Output = Signo(Input*W(1:end-1) - W(end));    % W(end) es el theta                                 
end

%{
    Esta funcion toma un conjunto de datos (Data), los pesos de un perceptron (W), y un
    indice i para un patron especifico del conjunto de datos. Devuelve la
    entrada (Input), la salida calculada por el perceptron(Output), y el objetivo deseado
    (Target) para ese patron.
    Data(i, 1:end-1) toma todo el contenido de la fila excepto la última columna (la cual es el target)
    El valor objetivo se encuentra en la ultima columna de la fila i.
    Input*W(1:end-1): Esto es el producto escalar entre el vector de entradas Input y el vector de pesos correspondientes a las entradas W(1:end-1). Es decir, multiplica cada entrada por su respectivo peso y suma los resultados.
    
%}
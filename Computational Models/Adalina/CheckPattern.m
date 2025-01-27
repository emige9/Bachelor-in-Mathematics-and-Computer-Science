function correct = CheckPattern(Data,W)

correct = true;

for i=1:1:size(Data,1)
    [Input, Output, Target] = ValoresIOT(Data,W,i);
    if(Signo(Output)~=Target)   % ~= significa distinto que
        correct = false;
        break;
    end
end

end

%{
    Se compara la salida del perceptron (Output) con el valor objetivo (Target). A diferencia del perceptron simple, el valor Output es la salida neta del Adaline (es decir, la suma ponderada de las entradas menos el sesgo), no la salida final despues de aplicar la funcion de activacion. Para ello, se aplica la funcion Signo antes de comparar con el Target, que convierte la salida neta en una decision binaria. Si no coinciden (Output es diferente de Target), significa que el adaline ha clasificado incorrectamente este patron. 
    Si correct es true, significa que todos los patrones fueron clasificados correctamente por el adaline. 
%}
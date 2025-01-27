function correct = CheckPattern(Data,W)
    correct = true;
    
    for i=1:1:size(Data,1)
        [Input, Output, Target] = ValoresIOT(Data,W,i);
        if(Output~=Target)   % (~= significa distinto que)
            correct = false;
            break;
        end
    end

end

%{
    Se compara la salida del perceptron (Output) con el valor objetivo
    (Target). Si no coinciden (Output es diferente de Target), significa
    que el perceptron ha clasificado incorrectamente este patron. 
    Si correct es true, significa que todos los patrones fueron
    clasificados correctamente por el perceptron. 
%}
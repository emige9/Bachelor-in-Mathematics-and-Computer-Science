function W=UpdateNet(W,LR,Output,Target,Input)
    diffW = LR*(Target - Output)*[Input, -1];
    W = W + diffW';
end

%{ 
    El objetivo de esta funcion es actualizar los pesos W usando la regla de aprendizaje del perceptron simple.  
    Se calcula la correccion de los pesos (diffW), usando la regla de aprendizaje del perceptron.
    Target - Output es el error o la diferencia entre la salida esperada (Target) y la salida actual (Output).
    [Input, -1] crea un nuevo vector de entrada que incluye la entrada original Input y annade un valor -1 al final. Esto es importante porque este -1 corresponde al peso asociado al sesgo (theta).  
    W = W + diffW' actualiza los pesos sumando la correccion calculada en el paso anterior.
%}
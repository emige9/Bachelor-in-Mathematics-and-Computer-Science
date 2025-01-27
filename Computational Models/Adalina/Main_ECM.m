clear;
clc;
close all;

%load DatosAND
%load DatosLS5
%load DatosLS10
%load DatosLS50
%load DatosOR
load DatosXOR

LR=0.5;
Limites=[-1.5, 2.5, -1.5, 2.5];
MaxEpoc=30;

%W=PerceptronWeigthsGenerator(Data);
W = [0,0,0]';

Epoc=1;
ECM = zeros(MaxEpoc - 1,1);

while ~CheckPattern(Data,W) && Epoc<MaxEpoc
     ET = 0;
     for i=1:size(Data,1)
        [Input,Output,Target]=ValoresIOT(Data,W,i);
        ET = ET + (Output - Target)^2;
        GrapDatos(Data,Limites);
        GrapPatron(Input,Output,Limites);
        GrapNeuron(W,Limites);hold off;
        drawnow
%         pause;

        if Signo(Output) ~= Target
            W=UpdateNet(W,LR,Output,Target,Input);
        end

        
        GrapDatos(Data,Limites);
        GrapPatron(Input,Output,Limites)
        GrapNeuron(W,Limites);hold off;
        drawnow
%         pause;
     
     end
    ECM(Epoc,1) = ET/size(Data,1);  %size(Data,1) = 2
    Epoc=Epoc+1;
end

figure;
plot(1:Epoc-1, ECM(1:Epoc-1), '-o', 'LineWidth',2);
xlabel('Épocas');
ylabel('ECM');
title('Evolución del Error Cuadrático Medio (ECM)');
function [Ganadoras] = CompeticionSOFM(Model,Muestras)
[~,NumMuestras] = size(Muestras); %Muestras traspuesta a la forma usual, 3 filas y tantas columnas como pixeles
Ganadoras = zeros(NumMuestras,1);
NumColsMapa = Model.NumColsMapa;
NumFilasMapa = Model.NumFilasMapa;
NumNeuronas = NumColsMapa*NumFilasMapa;

for n=1:NumMuestras
    MiMuestra = Muestras(:,n);
    DistsCuadrado = sum((repmat(MiMuestra,1,NumNeuronas)-Model.Medias(:,:)).^2,1);
    [~,NidxGanadora] = min(DistsCuadrado);
    Ganadoras(n) = NidxGanadora;
end
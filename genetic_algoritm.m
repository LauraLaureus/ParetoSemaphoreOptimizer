function [max_fitnesses,mean_fitnesses] = genetic_algoritm(siz,probability)
%%Devuelve como max, un array con los valores m�ximos de bondad de cada
%%generacion, y como mean, un array con los valores medios de bondad.
%%Recibe como primer par�metro el siza�o de la poblaci�n, la probabilidad
%%para el m�todo de selecci�n (m�todo de selecci�n por torneo
%%probabilistico) y el n�mero de iteraciones.

max_fitnesses = [];
mean_fitnesses =[];

if(mod(siz,3) ~= 0)
    siz = siz + (3- mod(siz,3));
end

Pop = round(rand(12,4,siz));

for i=1:siz

    fitnesses = computeFitness(Pop);
    seleccion = probabilistic_tourneau(fitnesses,Pop,probability);
    Pop = three_parent_crossover(seleccion);
    Pop = mutation(probability,Pop);
    
    max_fitnesses=[ max_fitnesses max(fitnesses)];
    mean_fitnesses = [mean_fitnesses mean(fitnesses)];
end;


end
function [max_fitnesses,mean_fitnesses] = genetic_algoritm(siz,probability)
%%Devuelve como max, un array con los valores máximos de bondad de cada
%%generacion, y como mean, un array con los valores medios de bondad.
%%Recibe como primer parámetro el sizaño de la población, la probabilidad
%%para el método de selección (método de selección por torneo
%%probabilistico) y el número de iteraciones.

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
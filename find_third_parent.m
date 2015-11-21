function [vector_tercer_padre]= encuentra_al_tercer_padre(tabla_de_emparejamientos_simple)
    num_parejas = size(tabla_de_emparejamientos_simple,1);
    vector_tercer_padre = zeros(num_parejas,1);
    
    for i =1:size(tabla_de_emparejamientos_simple,1)
        vector_tercer_padre(i) = tabla_de_emparejamientos_simple(mod(i,num_parejas)+1,randi(2));
    end
end
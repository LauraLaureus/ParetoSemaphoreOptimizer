function [hijo] = aplica_mask(mask,padre,madre,padre2)

    hijo = zeros(12,4);
    
    for i=1:12*4
        if mask(i) ==1
            hijo(i) = padre(i);
        end
        if mask(i) == 2
            hijo(i) = madre(i);
        end
        if mask(i) == 3
            hijo(i) = padre2(i);
        end 
    end
end
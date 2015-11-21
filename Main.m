function Main(tam_pop, p_seleccion)
[max,mean] = genetic_algoritm(tam_pop,p_seleccion);

figure
x_axis = 1:size(max,2);

plot(x_axis,max,'r');
hold on
plot(x_axis,mean,'b');
axis([1 tam_pop 0 1]);
legend('max/generacion','media/generacion');
grid on;
str = ['Pop size: ', num2str(tam_pop), ' p_seleccion/mutacion: ', num2str(p_seleccion)];
title(str);








all: yank
yank: main.o fun.o
	gcc main.o fun.o -o yank
main.o: main.c
	gcc -c main.c -o main.o
fun.o: fun.c
	gcc -c fun.c -o fun.o

#include "utils.h"
#include <string.h>
#include <stdlib.h>
#include <stdio.h>

#define MAX_INPUT 50
#define LIST_CHUNCK 50

// Funcoes auxiliares 

struct lstString{
	String* lista;
	int tamanho;
	int max;
};

String mystrdup(const String s){
	if(s == NULL) return NULL;
	String d = (String) malloc (strlen(s) + 1);
	if (d == NULL) return NULL;
	strcpy(d,s);
	return d;
	free(d);
}

Boolean strequals(const String s1, const String s2){
	return (strcmp(s1, s2) == 0);
}

Boolean strempty(const String s){
	return (strcmp(s, "") == 0);
}

String userInput(void){
	static String buff;
	if(buff != NULL) free(buff);
	buff = malloc((MAX_INPUT+1) * sizeof(char));
	if(fgets(buff, MAX_INPUT, stdin) == NULL) exit(EXIT_FAILURE);
	int i;
	for(i=0 ; i < MAX_INPUT; i++){
		if(buff[i] == '\n'){
			buff[i] = '\0';
			break;
		} 
	}
	return buff;
}


// FUNCOES DE LISTA
List_String new_listString(){
	List_String c = (List_String) malloc(sizeof(struct lstString));
	c->lista = (String*) malloc(LIST_CHUNCK * sizeof(String));
	c->max = LIST_CHUNCK;
	c->tamanho = 0;
	return c;
}

void insert_listString(List_String l, String p){
	if(l->tamanho+1 >= l->max){
		String* a = realloc(l->lista, (l->max+LIST_CHUNCK)* sizeof(String));
		if(a == NULL){
			printf("Memory Overfloaded.\n");
			return;
		}
		l->lista = a;
		l->max = l->max+LIST_CHUNCK;
		insert_listString(l, p);
	}else{
		l->lista[l->tamanho] = mystrdup(p);
		l->tamanho++;
	}
	
}

void destroy_listString(List_String l){
	for(int i = 0 ; i > l->tamanho ; i++)
		free(l->lista[i]);
	free(l);
}

int tamanho_listString(List_String l){
	return l->tamanho;
}

String get_listString(List_String l, int n){
	if(n > l->tamanho)
		return NULL;
	return (String) l->lista[n];
}
void remove_listString(List_String l, int n){
	l->lista[n] = NULL;
}

void coucou(int i){
	printf("Coucou : %d\n", i);
}

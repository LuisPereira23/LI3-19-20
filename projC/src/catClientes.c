#include "catClientes.h"
#include <glib.h>
#include <stdio.h>
#include "utils.h"
#include "cliente.h"

#define NBLETRAS 26

//Gtree ordenada por Letra
struct catClientes{
	GTree* avl[NBLETRAS];
};

// funcao para ordenar avls
static int cmpint (int* a, int* b){
	return *a - *b;
}

// Cria cliente
Cat_Clientes new_catClis(){
	Cat_Clientes c = malloc(sizeof(struct catClientes));
	for(int i = 0 ; i < NBLETRAS ; i++)
		c->avl[i] = g_tree_new((GCompareFunc)cmpint);
	return c;
}

//cria catalogo clientes a partir de uma lista de strings de clientes
Cat_Clientes new_catClisList(List_String clientes){
	Cat_Clientes c = new_catClis();
	for (int i = 0 ; i < tamanho_listString(clientes) ; i ++){
		String s = get_listString(clientes, i);
		Cliente cl = newCliente(s);
		if(!existe_catClis(c,cl))
			insert_catClis(c, cl);
	}
	return c;
}

// free catalogo clientes
void free_catClis(Cat_Clientes c){
	for(int i = 0 ; i < NBLETRAS ; i++)
		g_tree_destroy(c->avl[i]);
	free(c);
}

// Insere cliente em catalogo de clientes
void insert_catClis(Cat_Clientes c, Cliente cl){
	String s = toStringCliente(cl);
	Cliente clcl = newCliente(s);
	insert_withdata_catClis(c, cl, clcl);
	free(cl);
}

// Insere data na Gtree 
void insert_withdata_catClis(Cat_Clientes c, Cliente cl, Data data){
	int codigo = getCodigoCliente(cl) - 'A';
	
	int *digit = malloc(sizeof(int));
	*digit = getDigitCliente(cl);
	
	g_tree_insert(c->avl[codigo], digit, data);
}

// Verifica se cliente existe no catalogo
Boolean existe_catClis(Cat_Clientes c, Cliente cl){
	int codigo = getCodigoCliente(cl) - 'A';
	int* key = malloc(sizeof(int));
	*key = getDigitCliente(cl);
	Boolean a = (g_tree_lookup(c->avl[codigo], key) != NULL);
	free(key);
	return a;
}

// Tamanho de um catalogo de clientes
int tamanho_catClis(Cat_Clientes c){
	int sum = 0;
	for(int i = 0 ; i < NBLETRAS ; i++)
		sum += g_tree_nnodes(c->avl[i]);
	return sum;	
}

// Devolve data de um cliente do catalogo de clientes
Data get_data_catClis(Cat_Clientes c, Cliente cl){
	int codigo = getCodigoCliente(cl) - 'A';
	int* key = malloc(sizeof(int));
	*key = getDigitCliente(cl);
	return g_tree_lookup(c->avl[codigo], key);
}

// aux lista Completa
typedef struct auxListaCompleta{
	List_String l;
	int x;
} *AuxLstComplet;

// aux que transforma avl para uma lista
static GTraverseFunc avlToListComplet(int* key, Data d, AuxLstComplet aux){
	String s = malloc(6*sizeof(char));
	s[0] = aux->x+'A';
	
	char buff[12];
	sprintf(buff, "%d", *key);
	s[1] = buff[0];
	s[2] = buff[1];
	s[3] = buff[2];
	s[4] = buff[3];
	s[5] = '\0';
	insert_listString(aux->l, s);
	free(s);
	return FALSE;
}

// Lista de strings do catalogo completo
List_String lista_completa_catClis(Cat_Clientes c){
	AuxLstComplet aux = (AuxLstComplet) malloc(sizeof(struct auxListaCompleta));
	List_String lista = new_listString();
	aux->l = lista;
	for(int i = 0 ; i < NBLETRAS ; i++){
			aux->x = i;
			g_tree_foreach(c->avl[i], (GTraverseFunc) avlToListComplet, aux);
	}
	free(aux);
	return lista;
}

// adiciona catalogo de clientes a lista 
void add_to_lista_catClis(Cat_Clientes c, List_String l){
	AuxLstComplet aux = (AuxLstComplet) malloc(sizeof(struct auxListaCompleta));
	aux->l = l;
	for(int i = 0 ; i < NBLETRAS ; i++){
			aux->x = i;
			g_tree_foreach(c->avl[i], (GTraverseFunc) avlToListComplet, aux);
	}
	free(aux);
}


#include <glib.h>
#include "catProds.h"
#include "produto.h"
#include <stdlib.h>
#include <stdio.h>

#define NBLETRAS 26

// Gtree de 26 x 26 Letras
struct catProds{
	GTree* table[NBLETRAS][NBLETRAS];
};

// funcao para ordenar avls
static int cmpint (int* a, int* b){
	return *a - *b;
}

// Construtor catalogo produtos
Cat_Prods new_catProds(){
	Cat_Prods c = malloc(sizeof(struct catProds));

	for(int i = 0 ; i < NBLETRAS ; i++){
		for(int j = 0 ; j < NBLETRAS ; j++){			
			c->table[i][j] = g_tree_new((GCompareFunc) cmpint);
		}
	}
	return c;
}

// free do catalogo produtos
void free_catProds(Cat_Prods c){
	for(int i = 0 ; i < NBLETRAS ; i++)
		for(int j = 0 ; j < NBLETRAS ; j++)
			g_tree_destroy(c->table[i][j]);
	free(c);
}

// insere data no nodos do catalogo produtos
void insert_withdata_catProds(Cat_Prods c, Produto p, Data data){
	String codigo = getCodigoProduto(p);
	int primeiraLetra = codigo[0]-'A';
	int segundaLetra = codigo[1]-'A';
	int* key = malloc(sizeof(int));
	*key = getDigitProduto(p);
	g_tree_insert(c->table[primeiraLetra][segundaLetra], key, data);
	
}

// insere produto no catalogo de produtos
void insert_catProds(Cat_Prods c, Produto p){
	String s = toStringProduto(p);
	Produto pp = newProduto(s);
	insert_withdata_catProds(c, p, pp);
	free(s);
}

// devolve data do catalogo de produtos de certo produto p
Data get_data_catProds(Cat_Prods c, Produto p){
	String codigo = getCodigoProduto(p);
	int primeiraLetra = codigo[0]-'A';
	int segundaLetra = codigo[1]-'A';
	
	int* key = (int*) malloc(sizeof(int));
	*key = getDigitProduto(p);
	void* data = (g_tree_lookup(c->table[primeiraLetra][segundaLetra], key));
	return data;
}


// produto existe no catalogo de produtos?
Boolean existe_catProds(Cat_Prods c, Produto p){
	String codigo = getCodigoProduto(p);
	int primeiraLetra = codigo[0]-'A';
	int segundaLetra = codigo[1]-'A';
	int* key =  malloc(sizeof(int));
	*key = getDigitProduto(p);
	Boolean a = (g_tree_lookup(c->table[primeiraLetra][segundaLetra], key) != NULL);
	free(key);
	return a;
}

// devolve tamanho de um catalogo de produtos
int tamanho_catProds(Cat_Prods c){
	int sum = 0;
	for(int i = 0 ; i < NBLETRAS ; i++)
		for(int j = 0 ; j < NBLETRAS ; j++)
			sum += g_tree_nnodes(c->table[i][j]);
	return sum;		
}


// funcoes aux listaPorLetra
static GTraverseFunc avlToList(int* key, Produto p, List_String l){
	String s = toStringProduto(p);	
	printf("%s\n",s );
	insert_listString(l, s);
	free(s);
	return FALSE;
}

typedef struct auxListaCompleta{
	List_String l;
	int x;
	int y;

} *AuxLstComplet;

// aux que transforma avl para uma lista
static GTraverseFunc avlToListComplet(int* key, Data d, AuxLstComplet aux){
	String s = malloc(7*sizeof(char));
	s[0] = aux->x+'A';
	s[1] = aux->y+'A';
	
	char buff[12];
	sprintf(buff, "%d", *key);
	s[2] = buff[0];
	s[3] = buff[1];
	s[4] = buff[2];
	s[5] = buff[3];
	s[6] = '\0';
	insert_listString(aux->l, s);
	free(s);
	return FALSE; 
}

// Cria List string de um catalogo produtos
List_String lista_completa_catProds(Cat_Prods c){
	AuxLstComplet aux = (AuxLstComplet) malloc(sizeof(struct auxListaCompleta));
	List_String lista = new_listString();
	aux->l = lista;
	for(int i = 0 ; i < NBLETRAS ; i++)
		for(int j = 0 ; j < NBLETRAS ; j++){
			aux->x = i;
			aux->y = j;
			g_tree_foreach(c->table[i][j], (GTraverseFunc) avlToListComplet, aux);
	}
	free(aux);
	return lista;
}


// Cria lista string ordenada por letra do catalogo de produtos
List_String lista_por_letra_catProds(Cat_Prods c, char letra){
	int letraInt = letra - 'A';
	List_String lista = new_listString();
	for(int i = 0 ; i < NBLETRAS ; i++)
		g_tree_foreach(c->table[letraInt][i], (GTraverseFunc) avlToList, lista);
	return lista;
	destroy_listString(lista);
}


// Aplica uma funcao f a cada nodo do catalogo de produtos
void foreach_catProds(Cat_Prods c, GFunc f, Data d){
	for(int i = 0 ; i < NBLETRAS ; i++){
		for(int j = 0 ; j < NBLETRAS ; j++){
			g_tree_foreach(c->table[j][i], (GTraverseFunc) f, d);
		}
	}
}




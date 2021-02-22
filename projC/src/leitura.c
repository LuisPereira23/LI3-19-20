#include "leitura.h"
#include "utils.h"
#include "catProds.h"
#include "catClientes.h"
#include "produto.h"
#include "cliente.h"
#include "venda.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>

#define MAXBUF 50

// Le ficheiro Produtos.txt,valida e devolve tamanho 
int fileToCatalogoProdutos(String path, Cat_Prods c){
	FILE* fp = fopen(path, "r");
	if (fp==NULL) { printf("I/O error\n"); exit(EXIT_FAILURE); }
	char str[MAXBUF];
	String elem;	
	int i =0;
	
	while(fgets(str, MAXBUF, fp)){
		i++;	
		elem = strtok(str,"\n\r");
		if(produtoValido(elem)){
			Produto p = newProduto(elem);
			insert_catProds(c, p);
		}			
	}
	fclose(fp);
	return i;
}

// Le Ficheiro Clientes.txt,valida e devolve tamanho
int fileToCatalogoClientes(String path, Cat_Clientes c){
	FILE* fp = fopen(path, "r");
	if (fp==NULL) { printf("I/O error"); exit(EXIT_FAILURE); }

	char str[MAXBUF];
	String elem;	
	int i =0;
	while(fgets(str,MAXBUF, fp)){
		i++;	
		elem = strtok(str,"\n\r");
		if(clienteValido(elem))
			insert_catClis(c, newCliente(elem));
	}
	fclose(fp);
	return i;
}

// Le ficheiro Vendas,valida e devolve tamanho
int fileToListStringVendas(String path, List_String vendas, Cat_Prods catProds, Cat_Clientes catClientes){
	FILE* fp = fopen(path, "r");
	if (fp==NULL) { printf("I/O error"); exit(EXIT_FAILURE); }

	char str[MAXBUF];
	String elem;	
	int i =0;
	while(fgets(str,MAXBUF, fp)){
		i++;	
		elem = strtok(str,"\n\r");
		String vendastr = mystrdup(elem);
		if(vendaValida(elem, catProds, catClientes))
			insert_listString(vendas, vendastr);
		free(vendastr);
	}
	fclose(fp);
	return i;
}






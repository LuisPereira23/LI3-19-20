#include "venda.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "utils.h"
#include "produto.h"
#include "cliente.h"
#include "catProds.h"
#include "catClientes.h"

#define CAMPOSVENDA 7

//Struct venda
struct venda {
       char codProd[7];
       char codCli[6];
       double precoUnit;
       int quantidade;
       char tipo;
       int mes;
       int filial;
    };


static String* tokenizeLinhaVenda(String vendaRaw) {
    int index = 0;
    String* campos = (String*) malloc(CAMPOSVENDA * sizeof(String));
    String token = strtok(vendaRaw," "); 
    while(!(token == NULL)) {
	
        campos[index] = mystrdup(token);
        token = strtok(NULL," ");
        index++;
    }
    return campos;

}

// construtor de vendas
Venda newVenda(String linhaVenda) {
    Venda v = (Venda) malloc(sizeof(struct venda));
    String* campos = tokenizeLinhaVenda(linhaVenda);
    strcpy(v -> codProd,campos[0]);
    strcpy(v -> codCli,campos[4]);
    v -> precoUnit = atof(campos[1]);
    v -> quantidade = atoi(campos[2]);
    v -> tipo = campos[3][0];
    v -> mes = atoi(campos[5]);
    v -> filial = atoi(campos[6]);
    free(campos);  
    return v;  
}

void destroyVenda(Venda v){
	free(v);
}

// valida venda
Boolean vendaValida(String venda, Cat_Prods cp, Cat_Clientes cc){
	int index = 0;
    	String campos[7];
	String vendaCopy = mystrdup(venda);
    	String token = strtok(venda," ");  
	
    	while(!(token == NULL)) {
		if (index > 6) return false;
        	campos[index] = strdup(token);
        	token = strtok(NULL," ");
        	index++;
    	}
	
	if(!produtoValido(campos[0])) return false;

	double preco = atof(campos[1]);
	if (preco < 0 || preco > 1000) return false;
	
	int quantidade = quantidade = atoi(campos[2]);
	if (quantidade < 1 || quantidade > 200) return false;

	if(!clienteValido(campos[4])) return false;
	
	int tipo = campos[3][0];
	if(tipo != 'P' && tipo != 'N' ) return false;
	
	int mes = atoi(campos[5]);
	if (mes < 1 || mes > 12) return false;

	int filial = atoi(campos[6]); 
	if (filial <1 || filial > 3) return false;
	
	Venda v = newVenda(vendaCopy);
	Produto p = newProduto(getCodProd(v));
	if(!existe_catProds(cp, p)){
		free(p);
		return false;
	}
	free(p);
	Cliente c = newCliente(getCodCli(v));
	if(!existe_catClis(cc, c)){
		free(c);
		return false;
	}
	free(c);
	free(vendaCopy);
	free(v);
	return true;
}

//getters 
String getCodProd(Venda v){
	String s = malloc (sizeof(char)*(strlen(v->codProd)+1));
	if (s == NULL) return NULL;
	strcpy(s, v->codProd);
	return s;
}

String getCodCli(Venda v){
	String s = malloc (sizeof(char)*(strlen(v->codCli)+1));
	if (s == NULL) return NULL;
	strcpy(s, v->codCli);
	return s;
}

double getPrecoUnit(Venda v){
	return v->precoUnit;
}

int getQuantidade(Venda v){
	return v->quantidade;
}

char getTipo(Venda v){
	return v->tipo;
}

int getMes(Venda v){
	return v->mes;
}

int getFilial(Venda v){
	return v->filial;
}

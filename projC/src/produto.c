#include "produto.h"
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

// Struct produto
struct produto {
       char codigo[3];
       int digit;
    };

// Contrutor produto
Produto newProduto(String linhaProduto){
	Produto p = (Produto) malloc(sizeof(struct produto));
	
	p->codigo[0] = linhaProduto[0]; 	
	p->codigo[1] = linhaProduto[1];	
	p->codigo[2] = '\0';	

	int digit;
	digit = (linhaProduto[2] - '0') * 1000;
	digit += (linhaProduto[3] - '0') * 100;
	digit += (linhaProduto[4] - '0') * 10;
	digit += (linhaProduto[5] - '0');
	
	p->digit = digit;

	return p;
	free(p);
}

// Tranforma produto em string
String toStringProduto(Produto p){
	String s = (String) malloc (7 *sizeof(char));
	s[0] = p->codigo[0];
	s[1] = p->codigo[1];
	
	int resto = p->digit %1000;
	s[2] = (p->digit/1000) +'0';
	s[3] = (resto/100) + '0';
	resto = resto % 100;
	s[4] = (resto/10) + '0';
	resto = resto % 10;
	s[5] = resto + '0';
	s[6] = '\0';
	return s;
}

// Valida produto
Boolean produtoValido(String linhaProduto){
	if (strlen(linhaProduto) != 6) return false;
	if (!isalpha(linhaProduto[0])) return false;
	if (!isalpha(linhaProduto[1])) return false;
	if (linhaProduto[2] == '0' ) return false;
	int i;
	for ( i = 2 ; i<6 ; i++){
		if (!isdigit(linhaProduto[i])) return false;
	}
	return true;
}

// get de Char de produto
String getCodigoProduto(Produto p){
	return p->codigo;
}

//get de digitos inteiros do produto
int getDigitProduto(Produto p){
	return p->digit;
}




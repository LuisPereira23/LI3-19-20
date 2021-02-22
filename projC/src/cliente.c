#include "cliente.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

// Struct Cliente
struct cliente {
       char codigo;
       int digit;
    };

// Cria cliente
Cliente newCliente(String linhaCliente){
	Cliente c = (Cliente) malloc(sizeof(struct cliente));
	c->codigo = linhaCliente[0];
	
	int digit;
	digit = (linhaCliente[1] - '0') * 1000;
	digit += (linhaCliente[2] - '0') * 100;
	digit += (linhaCliente[3] - '0') * 10;
	digit += (linhaCliente[4] - '0');
	
	c->digit = digit;

	return c;
}

// transforma cliente em string
String toStringCliente(Cliente c){
	String s = (String) malloc (6 *sizeof(char));
	s[0] = c->codigo;
	int resto = c->digit %1000;
	s[1] = (c->digit/1000) + '0';
	s[2] = (resto/100) + '0';
	resto = resto % 100;
	s[3] = (resto/10) + '0';
	resto = resto % 10;
	s[4] = resto + '0';
	s[5] = '\0';
	return s;
}

//Verifica se cliente valido
Boolean clienteValido(String linhaCliente){
	if (strlen(linhaCliente) != 5) return false;
	if (!isalpha(linhaCliente[0])) return false;
	if (linhaCliente[1] == '0' ) return false;
	int i;
	for ( i = 1 ; i<5 ; i++){
		if (!isdigit(linhaCliente[i])) return false;
	}
	return true;
}

// Devolve codigo Char do cliente
char getCodigoCliente(Cliente c){
	return c->codigo;
}

// Devolve Digitos Inteiros do cliente
int getDigitCliente(Cliente c){
	return c->digit;
}

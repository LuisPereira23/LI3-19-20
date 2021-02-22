#ifndef __CLIENTE_H__
#define __CLIENTE_H__

#include "utils.h"

typedef struct cliente* Cliente;

Cliente newCliente(String linhaCliente); //cria novo cliente
String toStringCliente(Cliente);
Boolean clienteValido(String linhaCliente); // existência do cliente

// getters
char getCodigoCliente(Cliente);
int getDigitCliente(Cliente);

#endif

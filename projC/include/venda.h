#ifndef __VENDA_H__
#define __VENDA_H__

#include "utils.h"
#include "catProds.h"
#include "catClientes.h"

typedef struct venda* Venda;

Venda newVenda(String linhaVenda);
Boolean vendaValida(String venda, Cat_Prods cp, Cat_Clientes cc);
void destroyVenda(Venda v);

// getters 

String getCodProd(Venda v);
String getCodCli(Venda v);
double getPrecoUnit(Venda v);
int getQuantidade(Venda v);
char getTipo(Venda v);
int getMes(Venda v);
int getFilial(Venda v); 

#endif

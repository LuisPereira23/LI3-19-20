#ifndef __PRODUTO_H__
#define __PRODUTO_H__

#include "utils.h"

typedef struct produto* Produto;

Produto newProduto(String linhaProduto);
String toStringProduto(Produto);
Boolean produtoValido(String linhaProduto);

// getters e setters

int getDigitProduto(Produto);
String getCodigoProduto(Produto);

#endif

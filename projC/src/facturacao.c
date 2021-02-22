#include "facturacao.h"
#include "catProds.h"
#include "catClientes.h"
#include "produto.h"
#include <glib.h>
#include <stdio.h>

// struct facturacao com catalogos de produtos
struct facturacao{
	Cat_Prods produtos;
};

// devolve catalogo de produtos de uma facturazao
Cat_Prods getCatProd(Facturacao f){
	return f->produtos;
}

// tipos auxiliares
typedef struct factura{
	int quantidade;
	double precoUnitario;
} *Factura;

// Cria Factura
static Factura new_Factura(int quantidade, double precoUnitario){
	Factura f = (Factura) malloc(sizeof(struct factura));
	if(f == NULL)
		return NULL;

	f->quantidade = quantidade;
	f->precoUnitario = precoUnitario;
	
	return f;
}

typedef struct facturaTotal{
	GSList* facturas[12][3][2]; // 0 = N, 1 = P
}  *FacturaTotal;

// Cria Factura total
static FacturaTotal new_FacturaTotal(){
	FacturaTotal f = (FacturaTotal) malloc(sizeof(struct facturaTotal));
	
	for (int i = 0 ; i < 12; i++){
		for (int j = 0 ; j < 3; j++){
			for (int z = 0 ; z < 2; z++){
				f->facturas[i][j][z] = g_slist_alloc();
				if(f->facturas[i][j][z] == NULL)
					return NULL;
			}
		}
	}
	return f;
}

void destroyFacturaTotal(FacturaTotal f){
	for (int i = 0; i < 12; i++)
	{
		for (int j = 0; j < 3; j++)
		{
			for (int k = 0; k < 2; k++)
			{
				g_slist_free(f->facturas[i][j][k]);
			}
		}
	}
}

// Cria Facturacao 
Facturacao new_facturacao(List_String listaProdutos){
	Facturacao f = (Facturacao) malloc(sizeof(struct facturacao));
	f->produtos = new_catProds();
	
	int nbProdutos = tamanho_listString(listaProdutos);
	for (int i = 0 ; i < nbProdutos ; i++){
		String linhaProduto = get_listString(listaProdutos, i);
		Produto p = newProduto(linhaProduto);
		FacturaTotal facturaTotal = new_FacturaTotal();
		insert_withdata_catProds(f->produtos, p, facturaTotal);
		free(p);
	}

	return f;
}

// free facturacao
void free_facturacao(Facturacao f){
	free_catProds(f->produtos);
	free(f);
}

// Adiciona uma venda a facturacao
Boolean add_venda_facturacao(Facturacao f, Venda v){
	String stringProduto = getCodProd(v);
	Produto p = newProduto(stringProduto);
	
	FacturaTotal facturaTotal = (FacturaTotal) get_data_catProds(f->produtos, p);
	free(p);
	
	if(facturaTotal == NULL)
		return false;

	int mes = getMes(v);
	int filial = getFilial(v);
	
	int quantidade = getQuantidade(v);
	double precoUnitario = getPrecoUnit(v);

	int tipo;
	if(getTipo(v) == 'N')
		tipo = 0;
	else
		tipo = 1;

	Factura ff = new_Factura(quantidade, precoUnitario);
	facturaTotal->facturas[mes-1][filial-1][tipo] = g_slist_prepend(facturaTotal->facturas[mes-1][filial-1][tipo], ff);
	
	return true;
}


////  totalVendas e total Facturado
static int auxTotalVendas(Factura f, int* total){
	if(f == NULL)
		return 1;
	*total += f->quantidade;
	return 0;
}

static int auxTotalVendas2(Factura f, double* total){
	if(f == NULL)
		return 1;

	*total += f->precoUnitario * ((double)f->quantidade);
	return 0;

}

// Total de vendas de um produto
int total_vendas_full (Facturacao f, Produto p, int mes, int filial, char modo){
	int tipo;
	if(modo == 'N')
		tipo = 0;
	else
		tipo = 1;
	FacturaTotal facturaTotal = (FacturaTotal) get_data_catProds(f->produtos, p);
	GSList* facturas = facturaTotal->facturas[mes-1][filial-1][tipo];
	int total = 0;
	g_slist_foreach(facturas, (GFunc) auxTotalVendas, &total);
	return total;
}

// total facturado por um produto
double total_facturado_full (Facturacao f, Produto p, int mes, int filial, char modo){
	int tipo;
	if(modo == 'N')
		tipo = 0;
	else
		tipo = 1;
	FacturaTotal facturaTotal = get_data_catProds(f->produtos, p);
	GSList* facturas = facturaTotal->facturas[mes-1][filial-1][tipo];

	double total= 0;
	g_slist_foreach(facturas, (GFunc) auxTotalVendas2, &total);

	return total;
}

/////////////

typedef struct aux_total_vendas{
	int mes;
	int totalV;
	double totalF;
	Facturacao f;
} *Aux_Total_Vendas;


static gboolean auxTotalVendas3(int* key,Produto p,Aux_Total_Vendas aux){
	char modo;

	if(p=NULL){
		return TRUE;
	}
	else{
		
	for (int i = 1 ; i <= 3 ; i++){
		for (int j = 0 ; j < 2 ; j++){
			if (j = 0){
				modo='N';
			}
			else{
				modo='P';
			}

		aux->totalV += total_vendas_full(aux->f, p, aux->mes, i, modo);

		aux->totalF += total_facturado_full (aux->f, p, aux->mes, i, modo);
		}
			
		}
		 return FALSE;
	}
}

// Total facturado e vendas de um catalogo de produtos
void total_vendas_facturado (Facturacao f, int mes, int* vendas, double* facturado){

	Aux_Total_Vendas aux = (Aux_Total_Vendas) malloc(sizeof(struct aux_total_vendas));
	aux->totalF = 0;
	aux->mes = mes;
	aux->totalV = 0;
	aux->f = f;


	foreach_catProds(f->produtos, (GFunc) auxTotalVendas3, aux);

	*vendas = aux->totalV;
	*facturado = aux->totalF;
	free(aux);
}



// Lista de produtos nunca comprados
List_String produtos_nao_comprados(Facturacao f, int filial){	
	List_String listaAReturn = new_listString();
	List_String listaTotal = lista_completa_catProds(f->produtos);

	for (int a = 0 ; a < tamanho_listString(listaTotal) ; a++){
		
		String prodstr = get_listString(listaTotal, a);
		Produto p = newProduto(prodstr);
	
		FacturaTotal ft = (FacturaTotal) get_data_catProds(f->produtos, p);
		free(p);
		int stop = 0;
		for (int i = 0 ; i < 12; i++){
			if(stop)
				break;
			for (int j = 0 ; j < 3; j++){
				if(stop)
					break;

				if(filial ==4)
					for (int z = 0 ; z < 2; z++){		
						if(stop)
							break;		
						GSList* listaFacturas = ft->facturas[i][j][z];
						if(g_slist_length(listaFacturas) > 1){
							stop = 1;
						}
						
					}

				else{
					if(stop)
						break;		
					GSList* listaFacturas = ft->facturas[i][j][filial-1];
					if(g_slist_length(listaFacturas) > 1){
						stop = 1;
					}
				}
			}
		}

		if(!stop){
			insert_listString(listaAReturn, prodstr);
		}
	}
	
	destroy_listString(listaTotal);
	return listaAReturn;	
}



#include <stdio.h>
#include <stdlib.h>
#include "leitura.h"
#include "facturacao.h"
#include "filial.h"
#include "catProds.h"
#include "catClientes.h"
#include "utils.h"



List_String query2(Cat_Prods catProds, char letra){
	return lista_por_letra_catProds(catProds, letra);
}

void query3(Facturacao f, String codigoProduto, int filial, int mes, char modo, int* tVendas, double* tFacturado){
	Produto p = newProduto(codigoProduto);
	if(filial == 4){
		*tVendas = total_vendas_full(f, p, mes, 1, modo);
		*tFacturado = total_facturado_full(f, p, mes, 1, modo);
		*tVendas += total_vendas_full(f, p, mes, 2, modo);
		*tFacturado += total_facturado_full(f, p, mes, 2, modo);
		*tVendas += total_vendas_full(f, p, mes, 3, modo);
		*tFacturado += total_facturado_full(f, p, mes, 3, modo);
	} else{
		*tVendas = total_vendas_full(f, p, mes, filial, modo);
		*tFacturado = total_facturado_full(f, p, mes, filial, modo);
	}
	
}

List_String query4(Facturacao f, int filial){
	return produtos_nao_comprados(f, filial);
}

List_String query5(Filiais f){
	return clientes_en_todas_filiais(f);
}

void query6(Facturacao ff, Filiais f, int* clientesSemCompras, int* produtosSemCompras,  Cat_Clientes allClientes){
	List_String clientesSemComprasStr = clientes_sem_compras(f, allClientes);
	List_String produtosSemComprasStr = produtos_nao_comprados(ff, 4);
	
	*clientesSemCompras = tamanho_listString(clientesSemComprasStr);
	*produtosSemCompras = tamanho_listString(produtosSemComprasStr);

	destroy_listString(produtosSemComprasStr);
	destroy_listString(clientesSemComprasStr);
}

void query7(Filiais fs, String codigoCliente, int** tabela){
	for (int i = 0 ; i < 12 ; i++){
		int totalMes = 0;
		for (int j = 0 ; j < 3 ; j++){
			int total = compras_cliente(fs, codigoCliente, j , i, 'N');
			total += compras_cliente(fs, codigoCliente, j , i, 'P');
			tabela[i][j] = total;
			totalMes += total;
		}
		tabela[i][3] = totalMes;
	}
}


void query8(Facturacao f, int mesMin, int mesMax, int* totalVendas, int* totalFacturado){
	double totalFact = 0;
	int totalVen = 0;


	for (int i = mesMin ; i <= mesMax ; i ++){
		int vendas;
		double facturado;
		total_vendas_facturado (f, i, &vendas, &facturado);
		totalVen += vendas;
		totalFact += facturado;
	}


	*totalVendas = totalVen;
	*totalFacturado = totalFact;
	
}

void query9(Filiais f,Facturacao fa, int filial, String codigoProduto, List_String listaN, List_String listaP,int* comprasN,int* comprasP){
	char modoN = 'N';
	char modoP = 'P';
	int tVendasN = 0;
	int tVendasP = 0;
	double tFacturado = 0;


	for (int i=1; i<=12;i ++){

	query3(fa,codigoProduto, filial,i,modoN,&tVendasN,&tFacturado);
	*comprasN += tVendasN;

	query3(fa,codigoProduto, filial,i,modoP,&tVendasP,&tFacturado);	
	*comprasP += tVendasP;

}



}

List_String query10(Filiais f, int mes, String codigoCliente){
	
}

List_String query11(Facturacao ff, Filiais f, int n){

}

void query12(Filiais f, String codigoCliente, String codigoProdutoUm, String codigoProdutoDois, String codigoProdutoTres){


}

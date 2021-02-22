#ifndef __UTILS_H__
#define __UTILS_H__


typedef char* String;
typedef struct lstString* List_String;
typedef void* Data;
typedef enum {false=0, true=1} Boolean;

// funções sobre strings
String mystrdup(const String);
Boolean strequals(const String s1, const String s2);
Boolean strempty(const String);

// função para recuperar um input do utilizador
String userInput(void);

// funções sobre lista de strings
List_String new_listString();
void insert_listString(List_String l, String p);
void destroy_listString(List_String l);
int tamanho_listString(List_String l);
String get_listString(List_String l, int n);
void remove_listString(List_String l, int n);


#endif

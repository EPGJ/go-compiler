parser grammar GoParser;

options {
  tokenVocab = GoLexer; 
}

program:
  PACKAGE MAIN import_section? func_section
;

func_section: 
  func_declaration* func_main func_declaration*
;

import_section: 
  IMPORT package_import
;

package_import: 
  L_PAREN INTERPRETED_STRING_LIT+ R_PAREN
| INTERPRETED_STRING_LIT
;

func_main:
  FUNC MAIN L_PAREN func_args? R_PAREN var_types? statement_section
;

// Declarations

func_declaration:
  FUNC IDENTIFIER L_PAREN func_args? R_PAREN var_types? statement_section
;

var_declaration:
  VAR IDENTIFIER (var_types | var_types? ASSIGN  expression | array_declaration) SEMI?
;

declare_assign:
  IDENTIFIER DECLARE_ASSIGN ( array_init | expression) SEMI?
;  

array_declaration:
  L_BRACKET DECIMAL_LIT R_BRACKET var_types
;

array_init:
  array_declaration L_CURLY expression_list? R_CURLY
;

// Functions

input:
  INPUT L_PAREN AMPERSAND id R_PAREN
;

output:
  OUTPUT L_PAREN expression_list? R_PAREN
;

func_args:
  id var_types (COMMA id var_types)*
;

func_call:
  IDENTIFIER L_PAREN expression_list? R_PAREN 
;

// Statements

statement_section:
  L_CURLY statement* return_statement? R_CURLY
;

return_statement: 
  RETURN expression? SEMI?
;

statement:
  var_declaration
| declare_assign
| if_statement
| for_statement
| assign_statement
| switch_statement
| func_call SEMI?
| input
| output
;

if_statement:
  IF expression statement_section (ELSE statement_section)?
;

for_statement:
  FOR expression? statement_section                                             #while
| FOR declare_assign SEMI expression SEMI assign_statement statement_section    #for
;

assign_statement: 
  id op=(ASSIGN | MINUS_ASSIGN | PLUS_ASSIGN) expression SEMI?    #assignExpression
| id op=(PLUS_PLUS | MINUS_MINUS) SEMI?                           #assignPPMM
;

switch_statement:
  SWITCH (id | func_call)? L_CURLY case_statement R_CURLY
;

case_statement: 
  (CASE expression COLON statement*)* default_statement?
;

default_statement: 
  DEFAULT COLON statement*
;

// Expression

expression_list: 
  expression (COMMA expression)*
;

expression:
  expression op=(STAR | DIV | MOD) expression     #starDivMod
| expression op=(PLUS | MINUS) expression         #plusMinus
| expression op=(  
    EQUALS
    | NOT_EQUALS
    | LESS
    | LESS_OR_EQUALS
    | GREATER
    | GREATER_OR_EQUALS
  ) expression                                    #relationalOperators
| L_PAREN expression R_PAREN                      #expressionParen
| id                                              #expressionId
| func_call                                       #expressionFuncCall
| DECIMAL_LIT                                     #intVal
| FLOAT_LIT                                       #floatVal
| INTERPRETED_STRING_LIT                          #stringVal
| BOOLEAN_LIT                                     #boolVal
;

id:
  IDENTIFIER (L_BRACKET expression R_BRACKET)?
;

// Var types

var_types: 
  INT         #intType
| STRING      #stringType
| BOOL        #boolType
| FLOAT32     #float32Type
;

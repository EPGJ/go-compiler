# Go compiler
## Analisador Léxico, sintático e semântico

Para compilar siga os seguintes passos:

Clone o projeto: 

 ```sh
 git clone https://github.com/EPGJ/go-compiler.git
```
Altere a variável `ANTLR_PATH` para o path do Antlr no seu computador.
Compile o código: 
```sh
make
```

Para rodar um teste específico, deve se definir qual o modo de opeção é desejado. Caso queira um interpretardor, utilize `flag=-i`, porém caso queira um gerador de código utilize `flag=-c`. Feito isso, basta digitar o comando a seguir. `make run file=tests/[PASTA-DE-TESTES]/[ARQUIVO-DE-TESTES] flag=-[MODO-DE-OPERAÇÃO]` por exemplo:
```sh
make run file=tests/no-errors/helloworld.go flag-c
```

Para executar todos os testes por vez, basta executar o seguinte comando
```sh
./gen_tests.sh
```
É válido resaltar que o script acima testa apenas a pasta com testes corretos. Para alterar a pasta de testes, basta mudar aterar a variável `folder` para a pasta desejada.

Por fim, o comando `make clean` limpará todos os arquivos gerados pelo programa.


## License

MIT

**Free Software, Hell Yeah!**

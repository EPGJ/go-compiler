# Go compiler
## Analisador Léxico e sintático

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

Para rodar um teste específico digite o comando `make run FILE=tests/[PASTA-DE-TESTES]/[ARQUIVO-DE-TESTES]` por exemplo:
```sh
make run FILE=tests/no-errors/helloworld.go
```
Para executar todos os testes por vez, basta executar o seguinte comando
```sh
./gen_tests.sh
```
É válido resaltar que o script acima testa apenas a pasta com testes corretos. Para alterar a pasta de testes, basta mudar aterar a variável `FOLDER` para a pasta desejada.

Por fim, o comando `make clean` limpará todos os arquivos gerados pelo programa.


## License

MIT

**Free Software, Hell Yeah!**

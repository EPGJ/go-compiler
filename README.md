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


# Gopiler
A compiler for simple Go programs. This is a project proposed as part of the Compilers course at UFES by the Professor Eduardo Zambon.

## How to run

Open the terminal and compile the parser with
```bash
make
```

The `Main.java` file expects a flag that will determine how the AST will be used.

Flags:
  * `-c` (default)
  * `-i`

So to run the project would be
```bash
# Runs the Interpreter
make run file=<file_path> flag=-i

# Either one will run the CodeGen
make run file=<file_path>
make run file=<file_path> flag=-c
```


There is also the option to run on all files at once, but it will only run with the `-c` flag since the interpreter would cause interruptions when waiting for input
```bash
sh runall.sh
```

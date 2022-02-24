package main

func main() {
	notBool := 42
	x := 0

	for notBool { //SEMANTIC ERROR: for expression must have 'bool' type
		x++
	}
}
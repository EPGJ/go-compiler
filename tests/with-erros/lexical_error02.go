package main

// Program with lexical error
func main() {
	x := 10
    
	x = x # 2; // LEXICAL ERROR: unknown operator '#'
}
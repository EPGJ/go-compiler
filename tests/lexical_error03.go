package main

// Program with lexical error
func main() {
	x := 1_000_000
	y := 1__000 // LEXICAL ERROR: '_' must separate successive digits
}
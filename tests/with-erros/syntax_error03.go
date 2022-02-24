package main

// Program with syntax error
func main() {
	var a int
	for i:=0; i < 10; i++ // SYNTAX ERROR: unexpected newline, expecting { after for clause
		a += 10
}
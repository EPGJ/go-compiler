package main

/*
 * Demonstrates the possible ways to declare a variable
 */
func main() {

	// var declares one variable
	var a int = 10

	// Go will infer the type of initialized variables
	var b = "b"

	// Variables declared without a corresponding initialization are zero-valued. 
	// For example, the zero value for an bool is false and for an int is 0.
	var c bool

	// The := syntax is shorthand for declaring and initializing a variable
	d := true

}

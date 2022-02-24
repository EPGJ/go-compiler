package main

import "fmt"

/*
 * Demonstrates the possible ways to declare and use an if/else statement
 */
func main() {
	var a = true
	b := 2

	// Basic example of if/else
	if b == 1 {
		fmt.Println("b is one")
	} else {
		fmt.Println("b is not one")
	}

	// Using parenthesis is optional
	if 7%2 == 0 {

	} else {

	}

	// The else statement is also optional
	if a {

	}

}

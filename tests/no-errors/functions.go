package main

import "fmt"

// Go requires explicit returns, i.e. it won’t automatically
// return the value of the last expression.

// Here's a basic function that has no arguments and returns a string
func foo() string {
	return "foo"
}

// Here's a basic function that takes an int and
// returns whether it is bigger than 10
func bar(x int) bool {
	return x > 10
}

// The main function of the program
func main() {
	var a = foo()
	var b = bar(7)

	print(a, b)
}

// Functions don't need to be declared only on top of the main function
func print(a string, b bool) {
	fmt.Println(a, b)
}

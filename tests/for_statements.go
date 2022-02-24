package main

import "fmt"

/*
 * Demonstrates the possible ways to use a for loop
 */
func main() {

	fmt.Println("Counting until 3...")
	// The most basic type, with a single condition.
	i := 1
	for i <= 3 {
		i = i + 1
	}

	fmt.Println("Counting until 3...")
	// A classic initial/condition/after for loop.
	for j := 0; j < 10; j++ {

	}

	// for without any condition (will cause an infinit loop)
	for {
		fmt.Println("To infinity and beyond!")
	}

}

package main

import "fmt"

func fibonacci(n int) int {
	if n <= 1 {
		return n
	}

	return fibonacci(n-1) + fibonacci(n-2)
}

func main() {
	var index int

	fmt.Scanln(&index)

	var fibonacciNumber = fibonacci(index)

	fmt.Println("the", index, "fibonacci number is:", fibonacciNumber)
}

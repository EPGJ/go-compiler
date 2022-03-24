package main

import "fmt"

func main() {
	var text string

	fmt.Println("Enter text:")
	fmt.Scanln(&text)

	fmt.Println(text)
}

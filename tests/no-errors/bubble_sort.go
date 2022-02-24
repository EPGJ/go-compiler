package main

import "fmt"

// Bubble sort algorithm
func main() {

	var array [5]int
	array[0] = 0
	array[1] = 10
	array[2] = 2
	array[3] = 6
	array[4] = 4

	fmt.Println("Not sorted:", array)

	var aux int
	for i := 0; i < 5; i++ {
		for j := 0; j < 5-i-1; j++ {
			if array[j] > array[j+1] {
				aux = array[j]
				array[j] = array[j+1]
				array[j+1] = aux
			}
		}
	}

	fmt.Println("Sorted:", array)
}

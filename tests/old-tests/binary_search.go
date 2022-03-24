package main

import "fmt"

func binarySearch(arr [20]int, lo int, hi int, num int) bool {
	if hi >= lo {
		var mid int = lo + (hi-lo)/2

		if arr[mid] == num {
			return true
		}

		if arr[mid] > num {
			return binarySearch(arr, lo, mid-1, num)
		}

		return binarySearch(arr, mid+1, hi, num)
	}

	return false
}

func main() {
	sortedArray := [20]int{1, 2, 8, 10, 13, 17, 24, 29, 39, 48, 52, 67, 82, 96, 100, 105, 112, 123, 156, 200}

	number := 36

	var foundNumber = binarySearch(sortedArray, 0, 19, number)

	fmt.Println("Is number", number, "present?", foundNumber)
}

package main


func foo() string {
	return false; // SEMANTIC ERROR: cannot use false as type string in return argument
}


func main() {
	foo() 
}


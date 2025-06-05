package main
import ("fmt")

func main() {
	var x float64 = 123.78
  	var y float64 = 3123.4
  	fmt.Printf("Type: %T, value: %v\n", x, x)
  	fmt.Printf("Type: %T, value: %v", y, y)
}
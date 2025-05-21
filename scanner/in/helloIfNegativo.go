package main

import "fmt"

func main() {
    x := 5
    if x > 0 {
        // Erro léxico: literal não fechado
        fmt.Println("Positive)
    }
}

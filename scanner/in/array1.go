package main

import "fmt"

func main() {
    // Array de 5 inteiros
    var a [5]int = [5]int{10, 20, 30, 40, 50}

    // Acessa por índice e imprime
    fmt.Println("Terceiro elemento:", a[2]) // índice começa em 0

    // Percorre todo o array
    for i := 0; i < len(a); i++ {
        fmt.Printf("a[%d] = %d\n", i, a[i])
    }
}

package main

import "fmt"

func main() {
    // Gera uma pequena tabuada até 3×3, mas interrompe tudo se achar produto == 4
outer:
    for i := 1; i <= 3; i++ {
        for j := 1; j <= 3; j++ {
            produto := i * j
            fmt.Printf("%d×%d=%d  ", i, j, produto)
            if produto == 4 {
                fmt.Println("\nEncontrou 4, saindo dos dois loops.")
                break outer
            }
        }
        fmt.Println()
    }
}

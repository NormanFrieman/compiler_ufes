package main

import "fmt"

func main() {
    n := 5
    resultado := 1

    // for sem inicialização e pós, equivale a while (n > 0)
    for n > 0 {
        resultado *= n
        n-- // decrementa
    }

    fmt.Printf("5! = %d\n", resultado)
}

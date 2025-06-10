package main

import "fmt"

func main() {
    nums := []int{1, 2, 3}
    // Erro léxico: identificador 'in' não é palavra-reservada nem ID válido no contexto
    for i # nums {
        fmt.Println(i)
    }
}

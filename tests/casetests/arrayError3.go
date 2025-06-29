package main

import "fmt"

func main() {
    // Erro semântico: inicializando array ultrapassando o valor máximo
    // array index 5 out of bounds [0:5]
    var a [5]int = [5]int{10, 20, 30, 40, 50, 60}

    for i := 0; i < len(a); i++ {
        fmt.Printf("a[%d] = %d\n", i, a[i])
    }
}

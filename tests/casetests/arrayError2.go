package main

import "fmt"

func main() {
    // Erro semântico: Tipos de array diferentes
    // cannot use [5]int literal (type [5]int) as type [4]int in assignment
    var a [4]int = [5]int{10, 20, 30, 40, 50}

    for i := 0; i < len(a); i++ {
        fmt.Printf("a[%d] = %d\n", i, a[i])
    }
}

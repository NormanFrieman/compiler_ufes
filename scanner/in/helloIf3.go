package main

import "fmt"

func main() {
    nota := 76

    if nota >= 90 {
        fmt.Println("Conceito A")
    } else if nota >= 75 {
        fmt.Println("Conceito B")
    } else if nota >= 60 {
        fmt.Println("Conceito C")
    } else {
        fmt.Println("Reprovado")
    }
}

package main

import "fmt"

func main() {
    // Map de string para float64
    precos := map[string]float64{
        "maçã":   2.49,
        "banana": 1.99,
        "uva":    4.50,
    }

    // Acessa valor existente
    fmt.Println("Preço da uva:", precos["uva"])

    // Insere novo item
    precos["laranja"] = 3.10

    // Deleta item
    delete(precos, "banana")

    // Itera chaves e valores
    for fruta, preco := range precos {
        fmt.Printf("%s: R$ %.2f\n", fruta, preco)
    }
}

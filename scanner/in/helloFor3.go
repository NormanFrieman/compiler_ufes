package main

import "fmt"

func main() {
    frutas := []string{"maçã", "banana", "kiwi", "laranja"}

    for idx, fruta := range frutas {
        if fruta == "kiwi" {
            // pula o kiwi
            continue
        }
        if idx >= 2 {
            // interrompe após o segundo item não-kiwi
            break
        }
        fmt.Println("Fruta:", fruta)
    }
}

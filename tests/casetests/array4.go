package main

import "fmt"

// Pessoa tem nome e lista de hobbies
type Pessoa struct {
    Nome    string
    Idade   int
    Hobbies []string
}

func main() {
    // Cria instância de Pessoa
    p := Pessoa{
        Nome:    "Ana",
        Idade:   30,
        Hobbies: []string{"futebol", "leitura", "cozinha"},
    }

    // Acessa campo slice e itera
    fmt.Printf("%s tem %d anos e gosta de:\n", p.Nome, p.Idade)
    for _, h := range p.Hobbies {
        fmt.Println(" -", h)
    }
}

package main

import "fmt"

func main() {
    // Cria slice vazio e adiciona elementos
    s := []string{}
    s = append(s, "go")
    s = append(s, "python", "java")

    // Imprime len e cap
    fmt.Printf("Slice: %v (len=%d cap=%d)\n", s, len(s), cap(s))

    // Itera com for-range
    for idx, lang := range s {
        fmt.Printf("%d: %s\n", idx, lang)
    }
}

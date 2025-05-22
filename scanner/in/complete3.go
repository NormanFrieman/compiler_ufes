package main

import (
    "bufio"
    "fmt"
    "os"
    "strings"
)

func main() {
    reader := bufio.NewReader(os.Stdin)
    fmt.Print("Digite uma frase: ")
    text, _ := reader.ReadString('\n')

    words := strings.Fields(text)           // slice de strings
    freq := make(map[string]int)            // map[string]int
    var hasDuplicates bool                  // bool flag

    for _, w := range words {
        w = strings.ToLower(w)
        freq[w]++
        if freq[w] > 1 {
            hasDuplicates = true
        }
    }

    fmt.Println("\nFrequência de palavras:")
    for w, c := range freq {
        fmt.Printf("  %-10s: %d\n", w, c)
    }

    // encontra a palavra mais comum
    most, maxCount := "", 0
    for w, c := range freq {
        if c > maxCount {
            maxCount, most = c, w
        }
    }

    fmt.Printf("\nPalavra mais comum: '%s' (%d vezes)\n", most, maxCount)
    if hasDuplicates {
        fmt.Println("Existem palavras repetidas.")
    } else {
        fmt.Println("Todas as palavras são únicas.")
    }
}

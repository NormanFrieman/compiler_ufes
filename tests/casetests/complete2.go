package main

import (
    "bufio"
    "fmt"
    "os"
    "strconv"
    "strings"
)

// retorna true se o nome for “longo” (>5 letras)
func isLongName(name string) bool {
    return len(name) > 5
}

func main() {
    reader := bufio.NewReader(os.Stdin)
    fmt.Print("Quantas pessoas? ")
    line, _ := reader.ReadString('\n')
    n, err := strconv.Atoi(strings.TrimSpace(line))
    if err != nil || n <= 0 {
        fmt.Println("Número inválido. Encerrando.")
        return
    }

    var names []string // slice de strings
    for i := 0; i < n; i++ {
        fmt.Printf("Nome %d: ", i+1)
        nm, _ := reader.ReadString('\n')
        names = append(names, strings.TrimSpace(nm))
    }

    longCount := 0
    for _, nm := range names {
        if isLongName(nm) {
            fmt.Printf("%s → longo (%d caracteres)\n", nm, len(nm))
            longCount++
        } else {
            fmt.Printf("%s → curto (%d caracteres)\n", nm, len(nm))
        }
    }

    // booleano de verificação
    hasManyLong := longCount > n/2
    fmt.Printf("\nTotal de nomes longos: %d de %d\n", longCount, n)
    if hasManyLong {
        fmt.Println("Mais da metade dos nomes são longos.")
    } else {
        fmt.Println("Menos da metade dos nomes são longos.")
    }
}

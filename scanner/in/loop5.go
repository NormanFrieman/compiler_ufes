package main

import (
    "bufio"
    "fmt"
    "os"
    "strconv"
    "strings"
)

func main() {
    reader := bufio.NewReader(os.Stdin)
    soma := 0

    // for com init curto: lê até digitar "0"
    for {
        fmt.Print("Digite um inteiro (0 para parar): ")
        text, _ := reader.ReadString('\n')
        text = strings.TrimSpace(text)

        n, err := strconv.Atoi(text)
        if err != nil {
            fmt.Println("Entrada inválida, tente de novo.")
            continue
        }

        if n == 0 {
            break // sentinela para sair do loop
        }

        soma += n
    }

    fmt.Printf("Soma dos valores digitados: %d\n", soma)
}

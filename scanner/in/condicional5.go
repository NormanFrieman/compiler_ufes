package main

import (
    "bufio"
    "fmt"
    "os"
    "strconv"
    "strings"
)

// função auxiliar que retorna dupla (valor, erro)
func readInt(prompt string) (int, error) {
    reader := bufio.NewReader(os.Stdin)
    fmt.Print(prompt)
    text, _ := reader.ReadString('\n')
    text = strings.TrimSpace(text)
    return strconv.Atoi(text)
}

func main() {
    // if com declaração curta e verificação de erro
    if x, err := readInt("Digite um número (x): "); err != nil {
        fmt.Println("Erro na leitura de x:", err)
        return
    } else if y, err2 := readInt("Digite outro número (y): "); err2 != nil {
        fmt.Println("Erro na leitura de y:", err2)
        return
    } else {
        // aninhamento: dentro do else, outro if
        if y == 0 {
            fmt.Println("Não é possível dividir por zero")
        } else {
            quociente := x / y
            resto := x % y

            // combinação de condições para saída detalhada
            if resto == 0 {
                fmt.Printf("Divisão exata: %d / %d = %d\n", x, y, quociente)
            } else if resto > 0 && resto < y/2 {
                fmt.Printf("Divisão com pequeno resto: %d / %d = %d (resto %d)\n", x, y, quociente, resto)
            } else {
                fmt.Printf("Divisão com grande resto: %d / %d = %d (resto %d)\n", x, y, quociente, resto)
            }
        }
    }
}

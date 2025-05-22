package main

import (
    "bufio"
    "fmt"
    "os"
    "strconv"
    "strings"
)

// converte Celsius para Fahrenheit
func toFahrenheit(c float64) float64 {
    return c*9/5 + 32
}

func main() {
    reader := bufio.NewReader(os.Stdin)
    var temps []float64 // slice dinâmico

    // lê temperaturas até linha vazia
    for {
        fmt.Print("Digite temperatura em °C (ou Enter para terminar): ")
        line, _ := reader.ReadString('\n')
        line = strings.TrimSpace(line)
        if line == "" {
            break
        }
        c, err := strconv.ParseFloat(line, 64)
        if err != nil {
            fmt.Println("Entrada inválida, tente de novo.")
            continue
        }
        temps = append(temps, c)
    }

    if len(temps) == 0 {
        fmt.Println("Nenhuma temperatura inserida.")
        return
    }

    // soma e média
    sum := 0.0
    for _, c := range temps {
        sum += c
    }
    avg := sum / float64(len(temps))

    // flag booleano e comparações
    isBelowZero := avg < 0
    isAboveHundred := avg > 100

    fmt.Printf("\nTemperaturas: %v\n", temps)
    fmt.Printf("Média: %.2f°C / %.2f°F\n", avg, toFahrenheit(avg))

    if isBelowZero {
        fmt.Println("Alerta: média abaixo de zero.")
    } else if isAboveHundred {
        fmt.Println("Alerta: média acima de 100°C.")
    } else {
        fmt.Println("Média dentro do intervalo normal.")
    }
}

package main

import (
    "bufio"
    "fmt"
    "os"
    "strconv"
    "strings"
)

// calcula média de um slice de ints
func average(grades []int) float64 {
    sum := 0
    for _, g := range grades {
        sum += g
    }
    return float64(sum) / float64(len(grades))
}

func main() {
    reader := bufio.NewReader(os.Stdin)
    // map de slice de int
    subjects := make(map[string][]int)

    // leitura até “fim”
    for {
        fmt.Print("Disciplina (ou 'fim'): ")
        subj, _ := reader.ReadString('\n')
        subj = strings.TrimSpace(subj)
        if subj == "fim" {
            break
        }
        fmt.Printf("Notas de %s (separadas por espaço): ", subj)
        line, _ := reader.ReadString('\n')
        parts := strings.Fields(line)
        for _, p := range parts {
            g, err := strconv.Atoi(p)
            if err == nil {
                subjects[subj] = append(subjects[subj], g)
            }
        }
    }

    if len(subjects) == 0 {
        fmt.Println("Nenhuma disciplina registrada.")
        return
    }

    // processamento
    for subj, grades := range subjects {
        avg := average(grades)           // função
        passCount := 0
        for _, g := range grades {       // loop
            if g >= 60 {                 // comparação
                passCount++
            }
        }
        allPassed := passCount == len(grades) // bool
        fmt.Printf("\n%s → média %.2f, %d/%d passaram\n",
            subj, avg, passCount, len(grades))
        if allPassed {
            fmt.Println("  Todos passaram!")
        } else {
            fmt.Println("  Nem todos passaram.")
        }
    }
}

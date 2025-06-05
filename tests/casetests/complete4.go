package main

import (
    "fmt"
)

// struct com campo composto (slice)
type Employee struct {
    Name   string
    Age    int
    Salary float64
    Active bool
}

// retorna somente funcionários ativos
func filterActive(list []Employee) []Employee {
    var out []Employee
    for _, e := range list {
        if e.Active {
            out = append(out, e)
        }
    }
    return out
}

// calcula média salarial
func avgSalary(list []Employee) float64 {
    total := 0.0
    for _, e := range list {
        total += e.Salary
    }
    return total / float64(len(list))
}

func main() {
    // slice de structs
    emps := []Employee{
        {"Ana", 30, 5200.0, true},
        {"Bruno", 22, 3100.5, false},
        {"Carla", 27, 4500.0, true},
    }

    active := filterActive(emps)
    if len(active) == 0 {
        fmt.Println("Nenhum funcionário ativo.")
        return
    }

    fmt.Println("Funcionários ativos:")
    for _, e := range active {
        status := "júnior"
        if e.Age >= 25 {
            status = "sênior"
        }
        fmt.Printf("  %s (%d anos) → %s, R$ %.2f\n", e.Name, e.Age, status, e.Salary)
    }

    avg := avgSalary(active)
    fmt.Printf("\nSalário médio dos ativos: R$ %.2f\n", avg)
}

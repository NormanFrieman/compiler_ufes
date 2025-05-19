package main

import "fmt"

// Evento representa um evento com nome e local
type Evento struct {
    Nome  string
    Local string
}

func main() {
    // Map que agrupa eventos por tipo
    agenda := map[string][]Evento{
        "conferência": {
            {Nome: "GoLab", Local: "Auditório A"},
            {Nome: "PyData", Local: "Sala 101"},
        },
        "workshop": {
            {Nome: "Aprenda Go", Local: "Sala 202"},
        },
    }

    // Itera tipo → lista de eventos
    for tipo, evts := range agenda {
        fmt.Println("Tipo:", tipo)
        for _, e := range evts {
            fmt.Printf("  • %s em %s\n", e.Nome, e.Local)
        }
    }

    // Adiciona um novo evento num slice existente
    nova := Evento{Nome: "RustConf", Local: "Hall Principal"}
    agenda["conferência"] = append(agenda["conferência"], nova)

    fmt.Println("\nApós adicionar RustConf:")
    for _, e := range agenda["conferência"] {
        fmt.Printf("  • %s em %s\n", e.Nome, e.Local)
    }
}
